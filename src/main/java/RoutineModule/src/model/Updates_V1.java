package RoutineModule.src.model;

import DatabaseModule.src.api.IDbController;
import RoutineModule.src.api.IUpdates_model;
import Utilities.CommunicationParameters;
import Utilities.ModelsFactory;
import Utilities.PatientDetails;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Maor on 02/05/2015.
 */
public class Updates_V1 implements IUpdates_model {

    private IDbController dbController = null;
    private CommunicationParameters comParam = null;
    private PatientDetails memberDetail = null;

    public Updates_V1() {
        ModelsFactory models = new ModelsFactory();
        dbController = models.determineDbControllerVersion();
        comParam = new CommunicationParameters();
        memberDetail = new PatientDetails();
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

        HashMap<String, String> ret = new HashMap<String, String>();
        ret.putAll(comParam.getFrequency("'connect_server_frequency'"));
        ret.putAll(comParam.getFrequency("'times_to_connect_to_server'"));
        String reg = memberDetail.getRegId(cmid);
        if (memberDetail.ifTypeISPatientOrGuardian(reg)) {
            ret.putAll(comParam.getFrequency("'location_frequency'"));
            ret.putAll(comParam.getDefaultInEmergency(memberDetail.getState(cmid)));
        }
        return ret;
    }

}
