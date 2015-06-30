package CommunicationModule.src.model;

import CommunicationModule.src.api.ICommToUsers_model;
import CommunicationModule.src.utilities.JSONResponseCreator;
import Utilities.ErcLogger;
import org.json.JSONArray;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by NAOR on 06/04/2015.
 */
public class CommToUsers_V1 implements ICommToUsers_model {
    //members
    protected JSONArray objToSend = null;
    protected ArrayList<String> targets = null;

    ErcLogger logger = new ErcLogger();

    public CommToUsers_V1(HashMap<Integer, HashMap<String, String>> data) {
        JSONResponseCreator responseCreator = new JSONResponseCreator();
        objToSend = responseCreator.establishResponse(data);
    }

    public JSONArray sendResponse () {
        logger.println("In CommToUsers_V1.sendResponse");
        return objToSend;
    }
}
