package RoutineModule.src.controller;

import CommunicationModule.src.api.ICommController;
import DatabaseModule.src.api.IDbController;
import RoutineModule.src.api.IEmsRoutine_model;
import RoutineModule.src.api.IRoutineController;
import RoutineModule.src.api.IUpdates_model;
import Utilities.AssistantFunctions;
import Utilities.ModelsFactory;
import Utilities.PatientDetails;
import Utilities.VerifyDetail;

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
    }

    public Object transferLocation(HashMap<String, String> data)
    {
        // just transfer the location data to the GIS
        HashMap<Integer,HashMap<String,String>> response = new HashMap<Integer,HashMap<String,String>>();
        response.put(1, data);
        ArrayList<String> target = new ArrayList<String>();
        target.add("url...");//*
        commController.setCommToUsers(response, target, true);
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
                // this mean that is paramter of Patient and this is a doctor
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
        String password = "";// data.get("password");
        data.remove("password");
        data.remove("community_member_id"); 
        if(assistent.checkCmidAndPassword(password,cmid)) {
            for (Map.Entry<Integer,HashMap<String,String>> objs : data.entrySet()){
                HashMap<String,String> obj = objs.getValue();
                String col = obj.get("name");
                if (obj.get("needs_verification").equals("1"))
                    needVerify = true;
                else
                {
                            //update in table
                    //updates.updateUserDetails(cmid,col);
                }


            }
            if (needVerify)
                verify.verifyDetail(new Integer(cmid).toString());
        }

        return null;
    }

    @Override
    public Object handleRefreshDetails() {
        return null;
    }

    @Override
    public Object handleRefreshResponse(HashMap<Integer, HashMap<String, String>> data) {
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
