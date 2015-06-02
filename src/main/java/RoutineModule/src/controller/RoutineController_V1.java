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

    private ICommController commController = null;
    private IUpdates_model updates = null;
    private IEmsRoutine_model ems = null;
    private AssistantFunctions assistent = null;
    private IDbController dbController = null;
    private PatientDetails memberDetail = null;
    private VerifyDetail verify = null;
    private SendAssistant sendAssist = null;
    private AssistantFunctions assistantFuncs = null;


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
        assistantFuncs = new AssistantFunctions();
    }

    public Object transferLocation(HashMap<String, String> data)
    {
        // just transfer the location data to the GIS
        HashMap<Integer,HashMap<String,String>> response = new HashMap<Integer,HashMap<String,String>>();
        response.put(1, data);
        ArrayList<String> sendTo = new ArrayList<String>();
        sendTo = assistantFuncs.addReceiver("GIS", sendTo);
        commController.setCommToUsers(response, sendTo, true);
        return commController.sendResponse();
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
        int cmid = 0;
        int tempCmid = 0;
        int i = 1;
        for (Map.Entry<Integer,HashMap<String,String>> objs : data.entrySet()) {
            HashMap<String, String> obj = objs.getValue();
            HashMap<Integer, HashMap<String, String>> ret = new
                    HashMap<Integer, HashMap<String, String>>();
            if (updates.checkIfWeFinishWithOnePatient(i, cmid, tempCmid, objs)) {
                HashMap<String, String> buildRet = new
                        HashMap<String, String>();
                //check if we need to refresh
                if (updates.FieldneedRefresh(objs)) ;
                {
                    ret.put(i,obj);
                }
            }
            else
            {
                //send

                String reg = memberDetail.getRegId(cmid);
                ArrayList<String> target = new ArrayList<String>();
                sendAssist.buildBasicRespone("need refresh parameter", "refresh");
                ret.put(cmid, obj);
                if (memberDetail.ifTypeISPatientOrGuardian(reg)) {
                    target.add(reg);
                    commController.setCommToUsers(ret, target, false);
                }
                // is a doctor
                else {
                    target.add("url...");//*
                    commController.setCommToUsers(ret, null, false);
                }
                commController.sendResponse();

                ret.clear();
                i = 0;
            }
            i++;
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
                    //if we need verify details
                   if (updates.CheckIfNeedVerifyAndUpdateOrSendToVer(cmid,obj) != null)
                   {

                       return verify.verifyDetail(Integer.toString(cmid));
                   }
               }
               //"Request_ID" equal to rejectRefresh
               else
               {
                    //set  to urget to 1
                   updates.updateUrgentInRefreshDetailsTimeToField(cmid,obj);

                }

            }
            else
                return null;
        }
        return null;
    }

    @Override
    public Object updateStatus(HashMap<String, String> data) {
        int cmid = Integer.parseInt(data.get("community_member_id"));
        String password = data.get("password");
        if (assistent.checkCmidAndPassword(password,cmid))
        {
            String oldStatus = updates.getCurrentStatusOfPatient(cmid);
            dbController.updateStatus(cmid, "'" + oldStatus + "'", "'verifying details'");
        }
        return null;
    }

    @Override
    public Object logoff(HashMap<String, String> data) {
        return null;
    }

    @Override
    public Object deleteMember(HashMap<String, String> data) {
        int cmid = Integer.parseInt(data.get("community_member_id"));
        String password = data.get("password");
        if (assistent.checkCmidAndPassword(password,cmid))
        {
            dbController.deleteUser(cmid);
        }
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
