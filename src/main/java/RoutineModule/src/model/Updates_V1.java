package RoutineModule.src.model;

import DatabaseModule.src.api.IDbController;
import RoutineModule.src.api.IUpdates_model;
import Utilities.ModelsFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Maor on 02/05/2015.
 */
public class Updates_V1 implements IUpdates_model {
    private IDbController dbController = null;

    public Updates_V1() {
        ModelsFactory models = new ModelsFactory();
        dbController = models.determineDbControllerVersion();
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


}
