package RoutineModule.src.controller;

import CommunicationModule.src.api.ICommController;
import DatabaseModule.src.api.IDbController;
import RoutineModule.src.api.IEmsRoutine_model;
import RoutineModule.src.api.IRoutineController;
import RoutineModule.src.api.IUpdates_model;
import Utilities.*;

import java.util.*;

/**
 * Created by NAOR on 28/04/2015.
 */
public class RoutineController_V1 implements IRoutineController {

    private static final String GIS_URL = null;
    private static final String GIS_UNAME = null;
    private static final String GIS_PW = null;
    private static final String EMS_URL = null;
    private static final String EMS_UNAME = null;
    private static final String EMS_PW = null;

    private ICommController commController = null;
    private IUpdates_model updates = null;
    private IEmsRoutine_model ems = null;
    private AssistantFunctions assistent = null;
    private IDbController dbController = null;
    private PatientDetails memberDetail = null;
    private VerifyDetail verify = null;
    private SendAssistant sendAssist = null;


    public RoutineController_V1()
    {
        ModelsFactory models = new ModelsFactory();
        commController = models.determineCommControllerVersion();
        dbController = models.determineDbControllerVersion();
        updates = models.determineIUpdatesVersion();
        ems = models.determineIEmsRoutineVersion();
        assistent = new AssistantFunctions();
        memberDetail = new PatientDetails();
        verify = new VerifyDetail();
        sendAssist = new SendAssistant();
    }

    public Object transferLocation(HashMap<String, String> data)
    {
        // just transfer the location data to the GIS
        HashMap<Integer,HashMap<String,String>> response = new HashMap<Integer,HashMap<String,String>>();
        response.put(1, data);
        ArrayList<String> sendTo = new ArrayList<String>();
        sendTo = addReceiver("GIS", sendTo);
        commController.setCommToUsers(response, sendTo, true);
        return commController.sendResponse();
    }

    private ArrayList<String> addReceiver(String receiver,ArrayList<String> sendTo) {
        if(receiver.equals("EMS")){
            sendTo.add(EMS_URL);
            sendTo.add(EMS_UNAME);
            sendTo.add(EMS_PW);
        }
        else if (receiver.equals("GIS")){
            sendTo.add(GIS_URL);
            sendTo.add(GIS_UNAME);
            sendTo.add(GIS_PW);
        }
        return sendTo;
    }

    //ToDo-need for changes in the next future
    public Object getUpdatesFields(HashMap<String, String> data)
    {
        // ToDo-need to verify cmid and password
        HashMap<Integer,HashMap<String,String>> fields = updates.getFieldsForUpdate(data);
        //Todo-maby need to insert requestID....
        ArrayList<String> target = new ArrayList<String>();
        // Request update of doctor/ems
        if(data.get("reg_id") == "0")
        {
            commController.setCommToUsers(fields, null, false);
        }
        // Request update of patient
        else
        {
            target.add(data.get("reg_id"));
            commController.setCommToUsers(fields, target, false);
        }
        // Sends response to the proper user
        return commController.sendResponse();
    }


    // TODO: Shumulik

    public Object updateCommunicationParameters(String code)
    {
        HashMap<Integer, HashMap<String, String>> ret =
                new HashMap<Integer, HashMap<String, String>>();

        ArrayList<String> target = new ArrayList<String>();


        int numStatus = 0;//dbController.

        HashMap<Integer, HashMap<String, String>> allCmid = dbController.getAllCmidsByStatus(numStatus);
        //pass all over cmid in db
        //String code = "setLocationFrequency";
        String messge = "please update your communication parameters";
        HashMap<String, String> basic = updates.buildBasicResponse(messge, code);
        //ret.put(basic);
        for (Map.Entry<Integer,HashMap<String,String>> objs : allCmid.entrySet()){
            HashMap<String,String> obj = objs.getValue();
            int c = new Integer(obj.get("community_member_id"));
            HashMap<String, String> comParameter = updates.getCommunicationParameters(
                    c,code);

            if (comParameter == null) {
                // this mean that is parameter of Patient and this is a doctor
                continue;
            }
            basic.putAll(comParameter);
            ret.put(1,basic);
            String regId = memberDetail.getRegId(c);
            if (memberDetail.ifTypeISPatientOrGuardian(regId))
            {
                commController.setCommToUsers(ret,
                        target, false);
            }
            // is a doctor
            else
            {
                commController.setCommToUsers(ret,
                        null, false);
            }
            commController.sendResponse();
            //clean target
            target.clear();
        }
        return null;
    }

    @Override
    public Object updateMemberDetails(HashMap<Integer,HashMap<String, String>> data) {
        boolean needVerify = false;
        int cmid =  0;//Integer.parseInt(data.get("community_member_id"));
        int index = 0;
        String password = "";// data.get("password");
        for (Map.Entry<Integer,HashMap<String,String>> objs : data.entrySet()){
                HashMap<String,String> obj = objs.getValue();
                if (index == 0)
                {
                    cmid = Integer.parseInt(obj.get("community_member_id"));
                    password = obj.get("password");
                }
                if(!assistent.checkCmidAndPassword(password,cmid)) {
                    return null;
                }

                String col = obj.get("field_name");
                String val = obj.get("value"); // TODO-need to change
                if (obj.get("needs_verification").equals("1"))
                    needVerify = true;
                else
                {
                    //update in table
                    updates.updateUserDetails(cmid,col,val);
                }


            }
            if (needVerify) {
                verify.verifyDetail(new Integer(cmid).toString());
            }
        return null;
    }

    @Override
    public Object handleRefreshDetails() {
        HashMap<Integer, HashMap<String, String>> data =
                dbController.getRegistrationFieldsWithRefreshTime();

        for (Map.Entry<Integer,HashMap<String,String>> objs : data.entrySet()) {

            int cmid = objs.getKey();

            HashMap<String, String> obj = objs.getValue();
            HashMap<Integer,HashMap<String,String>> ret = new
                    HashMap<Integer,HashMap<String,String>>();
            HashMap<String,String> buildRet = new
                    HashMap<String,String>();

            //check if we need to refresh
            if(updates.FieldneedRefresh(objs));
            {
                String reg = memberDetail.getRegId(cmid);
                ArrayList<String> target = new ArrayList<String>();


                sendAssist.buildBasicRespone("need refresh parameter", "refresh");
                ret.put(cmid,obj);


                if (memberDetail.ifTypeISPatientOrGuardian(reg))
                {
                    target.add(reg);
                    commController.setCommToUsers(ret, target, false);
                }
                // is a doctor
                else
                {
                    target.add("url...");//*
                    commController.setCommToUsers(ret,null, false);
                }
                commController.sendResponse();

            }
        }
        return null;
    }

    @Override
    public Object handleRefreshResponse(HashMap<Integer, HashMap<String, String>> data) {
        for (Map.Entry<Integer,HashMap<String,String>> objs : data.entrySet()) {
            HashMap<String, String> obj = objs.getValue();
            int cmid = Integer.parseInt(obj.get("community_member_id"));
            String password = obj.get("password");
            if (assistent.checkCmidAndPassword(password,cmid))
            {

                if (obj.get("Request_ID").equals("acceptRefresh"))
                {
                    //set  to urget to 0
                    dbController.updateUrgentInRefreshDetailsTime
                            (cmid, obj.get("field_name"), 0);
                    //TODO add and check if we need verifiction
                    if (obj.get("needs_verification").equals("1"))
                    {

                    }
                    else
                    {
                        //update in table
                        //updates.updateUserDetails(cmid,col,val);
                    }
                }
                //"Request_ID" equal to rejectRefresh
                else
                {
                    //set  to urget to 1
                    dbController.updateUrgentInRefreshDetailsTime
                            (cmid, obj.get("field_name"), 1);
                }

            }
            else
                return null;
        }
        return null;
    }

    @Override
    public Object updateStatus(HashMap<String, String> data) {
        return null;
    }

    @Override
    public Object logoff(HashMap<String, String> data) {
        return null;
    }

    @Override
    public Object deleteMember(HashMap<String, String> data) {
        return null;
    }

    // TODO: Shmulik
    @Override
    public Object getEmsEventsByDispatcherCmid(HashMap<String, String> data) {
        int cmid = Integer.parseInt(data.get("community_member_id"));
        String password = data.get("password");
        if(assistent.checkCmidAndPassword(password,cmid))
        {
               return ems.getEmsEventsByDispatcherCmid(cmid);
        }
        return null;
    }
}
