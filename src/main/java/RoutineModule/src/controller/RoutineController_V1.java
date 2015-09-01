package RoutineModule.src.controller;

import CommunicationModule.src.api.ICommController;
import DatabaseModule.src.api.IDbController;
import RoutineModule.src.api.IEmsRoutine_model;
import RoutineModule.src.api.IRoutineController;
import RoutineModule.src.api.IUpdates_model;
import Utilities.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by NAOR on 28/04/2015.
 */
public class RoutineController_V1 implements IRoutineController {

    //How to communicate with the patient or the doctor
    private ICommController commController = null;
    //For updates process routine
    private IUpdates_model updates = null;
    //For Ems process routine
    private IEmsRoutine_model ems = null;
    //How to connect to database
    private IDbController dbController = null;

    //this class Contain general functions for all processes
    private AssistantFunctions assistent = null;
    private PatientDetails memberDetail = null;
    private VerifyDetail verify = null;
    private SendAssistant sendAssist = null;


    private Logger logger = Logger.getLogger(this.getClass().getName());

    //init all helper class
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

    //transper location from patient to gis
    public Object transferLocation(HashMap<String, String> data)
    {
        // just transfer the location data to the GIS
        HashMap<Integer,HashMap<String,String>> response = new HashMap<Integer,HashMap<String,String>>();
        //put location in map
        response.put(1, data);
        ArrayList<String> sendTo = new ArrayList<String>();
        //init sendTo to gis
        sendTo = assistent.addReceiver("GIS", sendTo);
        commController.setCommToUsers(response, sendTo, true);
        //send data
        return commController.sendResponse();
    }

    //to send fields with data for a specific patient
    public Object getUpdatesFields(HashMap<String, String> data)
    {
        //Checks whether the password is correct
        int cmid = Integer.parseInt(data.get("community_member_id"));
        String password = data.get("password");
        if(!assistent.checkCmidAndPassword(password,cmid)) {
            return null;
        }

        //Getting fields with data for a specific patient
        HashMap<Integer,HashMap<String,String>> fields = updates.getFieldsForUpdate(data);


        //For those who send the data
        ArrayList<String> target = new ArrayList<String>();
        // Request update of doctor/ems

        int type = dbController.getUserType(data.get("community_member_id"));

        //One = doctor and three = ems
        if(type == 1 || type == 3 )
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


    //to update communication Parameters
    //Receives a code that these parameters need updating
    public Object updateCommunicationParameters(String code, String newVal)
    {

        HashMap<Integer, HashMap<String, String>> ret =
                new HashMap<Integer, HashMap<String, String>>();

        ArrayList<String> target = new ArrayList<String>();

        //Receives the ID number for the active status
        String status =  dbController.getStatusByName("active");
        int numStatus = Integer.parseInt(status);

        //Get all the Members for active status
        HashMap<Integer, HashMap<String, String>> allCmid = dbController.getAllCmidsByStatus(numStatus);

        //add messge to send
        String messge = "please update your communication parameters";
        HashMap<String, String> basic = updates.buildBasicResponse(messge, code, newVal);

        //pass all over cmid in db
        for (Map.Entry<Integer,HashMap<String,String>> objs : allCmid.entrySet()){
            HashMap<String,String> obj = objs.getValue();
            int c = new Integer(obj.get("community_member_id"));
            String type = obj.get("user_type");
            //Getting the contract parameters for a specific user
            HashMap<String, String> comParameter = updates.getCommunicationParameters(c, type);
            basic.putAll(comParameter);
            ret.put(1,basic);
            String regId = memberDetail.getRegId(c);

            //Examining whether it is the patient or doctor
            if (memberDetail.ifTypeISPatientOrGuardian(regId))
            {

                target.add(regId);
                commController.setCommToUsers(ret,
                        target, false);
                target.clear();
            }
            // is a doctor
            else
            {
                commController.setCommToUsers(ret,
                        null, false);
            }
            return commController.sendResponse();


        }
        return null;
    }

    @Override
    //Send an email to the user's own password
    //data contain email address
    public Object forgotPassword(HashMap<String, String> data) {
        logger.info("data = " + data);
        String email = data.get("email_address");

        HashMap<String, String> userD = memberDetail.getUserByMail(email);
        logger.info("userD != null = " + (userD != null));
        //ok checking if a user exists
        boolean ok = false;
        //Checks for an existing user
        if (userD != null || userD.size() != 0) {
            //authMethod = Where intention to send the password for example to send an email or phone by sms
            int authMethod = dbController.getAuthenticationMethod(userD.get("state"));
            //get password
            HashMap<String,String> sendData =  updates.forgotPassword(email, userD,authMethod);
            //craete communication and send data
            ICommController commAuthMethod = new ModelsFactory().determineCommControllerVersion();
            commAuthMethod.setCommOfficial(sendData,authMethod);
            commAuthMethod.sendMessage();
            ok = true;
        }
        return new JSONArray().put(new JSONObject().put("status", ok? "ok" : "error"));

    }


    @Override
    //After the user returns the parameters he wants to update this function is activated
    public Object updateMemberDetails(HashMap<String, String> data) {
        //Checks if the user wanted to update parameter should be OK
        boolean needVerify = false;
        int index = 0;
        //Checks whether the password is correct
        int cmid = Integer.parseInt(data.get("community_member_id"));
        String password = data.get("password");
        if(!assistent.checkCmidAndPassword(password,cmid)) {
            return null;
        }

        data.remove("password");
        data.remove("community_member_id");

        //get user type
        String type = String.valueOf(dbController.getUserType(String.valueOf(cmid)));
        //Passing on any parameters sent
        for (String key : data.keySet()){
            String col = key;
            String val = data.get(key);
            //check if field need verification
            //For this we pull out details on the spesipic field
            HashMap<String, String> fieldDetails = dbController.getFieldDetails(key, type);
            if (fieldDetails.get("needs_verification").equals("true"))
                needVerify = true;
            else
            {
                //update in table
                updates.updateUserDetails(cmid,col,val);
            }
        }
        //if need verify sends the data to who confirming the same type of user
        if (needVerify) {
            verify.verifyDetail(new Integer(cmid).toString());
        }
        return null;
    }

    @Override

    public Object handleRefreshDetails() {

        //For each field gets in a while you have to update it
        HashMap<Integer, HashMap<String, String>> data =
                dbController.getRegistrationFieldsWithRefreshTime();
        //int cmid = 0;
        int tempCmid = 0;
        int i = 1;
        //pass all over fields
        for (Map.Entry<Integer,HashMap<String,String>> objs : data.entrySet()) {
            HashMap<String, String> obj = objs.getValue();
            HashMap<Integer, HashMap<String, String>> ret = new
                    HashMap<Integer, HashMap<String, String>>();
            int cmid = objs.getKey();
            //Checking if we moved to a new user
            if (updates.checkIfWeFinishWithOnePatient(i, cmid, tempCmid, objs)) {
                HashMap<String, String> buildRet = new
                        HashMap<String, String>();

                //check if we need to refresh
                if (updates.FieldneedRefresh(objs)) ;
                {
                    //put all fields for send to verify
                    ret.put(i,obj);
                }
            }
            else
            {
                //send
                String reg = memberDetail.getRegId(cmid);
                ArrayList<String> target = new ArrayList<String>();
                sendAssist.buildBasicRespone("need refresh parameter", "refresh", null);
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
    //Activated after the user's reaction to update information
    public Object handleRefreshResponse(HashMap<Integer, HashMap<String, String>> data) {
        for (Map.Entry<Integer,HashMap<String,String>> objs : data.entrySet()) {
            HashMap<String, String> obj = objs.getValue();
            int cmid = Integer.parseInt(obj.get("community_member_id"));
            String password = obj.get("password");
            //Checks whether the password is correct
            if (assistent.checkCmidAndPassword(password,cmid))
            {
                //If the user updated details
                if (obj.get("RequestID").equals("acceptRefresh"))
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
    //User's status update
    public Object updateStatus(HashMap<String, String> data) {
        //Checks whether the password is correct
        int cmid = Integer.parseInt(data.get("community_member_id"));
        String password = data.get("password");
        if (assistent.checkCmidAndPassword(password,cmid))
        {
            //Get the current status of the user
            String oldStatus = updates.getCurrentStatusOfPatient(cmid);
            //change status in db
            dbController.updateStatus(cmid, oldStatus, "verifying details");
        }
        return null;
    }

    @Override

    public Object logoff(HashMap<String, String> data) {
        //Checks whether the password is correct
        int cmid = Integer.parseInt(data.get("community_member_id"));
        String password = data.get("password");
        if (assistent.checkCmidAndPassword(password,cmid))
        {
            //change status to onHold
            HashMap<String, String> userDetails = memberDetail.getUserByCmid(cmid);
            String oldStatus = this.memberDetail.getStatus(userDetails);
            dbController.updateStatus(cmid, oldStatus,"onHold");

        }
        return null;
    }

    @Override
    //Deleting a user from the database
    public Object deleteMember(HashMap<String, String> data) {
        //Checks whether the password is correct
        int cmid = Integer.parseInt(data.get("community_member_id"));
        String password = data.get("password");
        if (assistent.checkCmidAndPassword(password,cmid))
        {
            //delete
            dbController.deleteUser(cmid);
        }
        return null;
    }

    @Override
    public Object getEmsEventsByDispatcherCmid(HashMap<String, String> data) {
        //Checks whether the password is correct
        int cmid = Integer.parseInt(data.get("community_member_id"));
        String password = data.get("password");
        if(assistent.checkCmidAndPassword(password,cmid))
        {
               return ems.getEmsEventsByDispatcherCmid(cmid);
        }
        return null;
    }
}
