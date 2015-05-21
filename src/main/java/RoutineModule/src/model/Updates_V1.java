package RoutineModule.src.model;

import DatabaseModule.src.api.IDbController;
import RoutineModule.src.api.IUpdates_model;
import Utilities.CommunicationParameters;
import Utilities.ModelsFactory;
import Utilities.PatientDetails;
import Utilities.SendAssistant;

import java.util.ArrayList;
import java.util.HashMap;

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
    public HashMap<String, String> getCommunicationParameters(int cmid) {
       // HashMap<Integer,HashMap<String, String>> ret =
       //         new HashMap<Integer,HashMap<String, String>>();
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


}
