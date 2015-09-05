package CommunicationModule.src.model;

import CommunicationModule.src.api.ICommToUsers_model;
import CommunicationModule.src.utilities.JSONResponseCreator;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 * Created by NAOR on 06/04/2015.
 */
public class CommToUsers_V1 implements ICommToUsers_model {
    //members
    protected JSONArray objToSend = null;
    protected ArrayList<String> targets = null;

    Logger logger = Logger.getLogger(this.getClass().getName());

    public CommToUsers_V1(HashMap<Integer, HashMap<String, String>> data) {
        JSONResponseCreator responseCreator = new JSONResponseCreator();
        objToSend = responseCreator.establishResponse(data);
    }

    public JSONArray sendResponse () {
        //logger.log(Level.INFO, "In CommToUsers_V1.sendResponse");
        return objToSend;
    }
}
