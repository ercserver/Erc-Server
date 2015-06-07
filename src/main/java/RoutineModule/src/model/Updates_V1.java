package RoutineModule.src.model;

import DatabaseModule.src.api.IDbController;
import RoutineModule.src.api.IUpdates_model;
import Utilities.CommunicationParameters;
import Utilities.ModelsFactory;
import Utilities.PatientDetails;
import Utilities.SendAssistant;

import java.util.*;

/**
 * Created by Maor on 02/05/2015.
 */
public class Updates_V1 implements IUpdates_model {

    private IDbController dbController = null;
    private CommunicationParameters comParam = null;
    private PatientDetails memberDetail = null;
    private SendAssistant sendAssist = null;

    public Updates_V1() {
        ModelsFactory models = new ModelsFactory();
        dbController = models.determineDbControllerVersion();
        comParam = new CommunicationParameters();
        memberDetail = new PatientDetails();
        sendAssist = new SendAssistant();
    }

    public HashMap<Integer,HashMap<String,String>> getFieldsForUpdate(HashMap<String, String> data)
    {
        /*//generate data to send
        HashMap<Integer,HashMap<String,String>> dataToSend =
                dbController.getRegistrationFields(Integer.parseInt(data.get("userType")));
        dataToSend.get(1).put("RequestID", "registration");
        ArrayList<String> sendTo = sendTo(request);
        //determine how to send the data
        commController.setCommToUsers(dataToSend, sendTo, false);
        //send the data
        return commController.sendResponse();

        return null;*/
        return null;
    }

    @Override
    public HashMap<String, String> getCommunicationParameters(int cmid,String type) {
       // HashMap<Integer,HashMap<String, String>> ret =
       //         new HashMap<Integer,HashMap<String, String>>();

        HashMap<String, String> ret = new HashMap<String, String>();


        if (type.equals("setConnectServerFrequency"))
        {
            ret.putAll(comParam.getFrequency("'connect_server_frequency'"));
        }

        if (type.equals("setTimesToConnectToServer")) {
            ret.putAll(comParam.getFrequency("'times_to_connect_to_server'"));
        }

        String reg = memberDetail.getRegId(cmid);
        if (memberDetail.ifTypeISPatientOrGuardian(reg)) {
            if (type.equals("setLocationFrequency")) {
                ret.putAll(comParam.getFrequency("'location_frequency'"));
            }
            if (type.equals("setCallingDefault")) {
                ret.putAll(comParam.getDefaultInEmergency(memberDetail.getState(cmid)));
            }
        }
        // this mean that is paramter of Patient and this is a doctor
        return null;

        /*
        HashMap<String, String> param = new HashMap<String, String>();
        param.putAll(comParam.getFrequency("'connect_server_frequency'"));
        param.putAll(comParam.getFrequency("'times_to_connect_to_server'"));
        String reg = memberDetail.getRegId(cmid);
        if (memberDetail.ifTypeISPatientOrGuardian(reg)) {
            param.putAll(comParam.getFrequency("'location_frequency'"));
            param.putAll(comParam.getDefaultInEmergency(memberDetail.getState(cmid)));
        }
        //ret.put(1,param);
        return param;
        */
    }

    @Override
    public HashMap<String, String> buildBasicResponse(String message,
                                                      String code) {
        return sendAssist.buildBasicRespone(message,code);
    }


    public void updateUserDetails(int cmid,String col,String value) {
            HashMap<String, String> member = new HashMap<String, String>();
            member.put(col, "'" + value + "'");
            member.put("community_member_id", Integer.toString(cmid));
            dbController.updateUserDetails(member);
    }

    @Override
    public boolean FieldneedRefresh(Map.Entry<Integer, HashMap<String, String>> objs) {

        HashMap<String, String> obj = objs.getValue();
        String lastUpdate = obj.get("last_update_time");
        Date date = new Date(lastUpdate);
        String timeToRefresh = obj.get("refresh_time");

        Calendar timeRef = Calendar.getInstance();
        timeRef.setTime(date);
        timeRef.add(Calendar.MINUTE, new Integer(timeToRefresh));

        Calendar currentTime = Calendar.getInstance();
        //if last update time + refresh_time > current time
        //then we need to refresh
        if(currentTime.before(timeRef))
                return true;
        else
            return false;
    }

    @Override
    public boolean checkIfWeFinishWithOnePatient(int i, int cmid, int tempCmid,Map.Entry<Integer, HashMap<String, String>> objs) {
        if (i == 1) {
            cmid = objs.getKey();
            tempCmid = cmid;
        }
        else
        {
            tempCmid = objs.getKey();
        }
        return tempCmid == cmid;
    }

    @Override
    public HashMap<String, String> CheckIfNeedVerifyAndUpdateOrSendToVer(int cmid,HashMap<String, String> data) {
        boolean needVer = false;
        String reg = data.get("redId");
        //TODO- how i get user type
        String type = data.get("user_type");

        data.remove("user_type");// ?????
        data.remove("redId"); // ???
        Set<String> keys = data.keySet();
        for (String fieldName : keys) {
            //set  to urget to 0
            dbController.updateUrgentInRefreshDetailsTime
                    (cmid, data.get(fieldName), 0);
            HashMap<String, String> filedData = dbController.getFieldDetails(fieldName,type );
            if (filedData.get("needs_verification").equals("1")) {
                needVer = true;
            } else {
                //update in table
                updateUserDetails(cmid, fieldName,data.get(fieldName));
            }
        }
        if (needVer)
            return data;
        else
            return null;
    }

    @Override
    public void updateUrgentInRefreshDetailsTimeToField(int cmid, HashMap<String, String> data) {

        Set<String> keys = data.keySet();
        keys.remove("community_member_id");
        keys.remove("password");
        for (String fieldName : keys) {
            dbController.updateUrgentInRefreshDetailsTime
                    (cmid, fieldName, 1);
        }

    }

    @Override
    public String getCurrentStatusOfPatient(int cmid) {
        HashMap<String, String> data = memberDetail.getUserByCmid(cmid);
        return memberDetail.getStatus(data);
    }

}
