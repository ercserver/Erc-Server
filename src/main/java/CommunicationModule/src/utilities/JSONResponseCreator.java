package CommunicationModule.src.utilities;

import CommunicationModule.src.api.IResponseCreator;
import Utilities.ErcLogger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class JSONResponseCreator implements IResponseCreator {
    public JSONArray establishResponse(HashMap<Integer,HashMap<String,String>> data) {
        ErcLogger.println("In establishResponse: param = " + data);
        JSONArray objsToSend = new JSONArray();
        for (Map.Entry<Integer,HashMap<String,String>> objs : data.entrySet()){
            HashMap<String,String> obj = objs.getValue();
            JSONObject currJson = new JSONObject(obj);
            objsToSend.put(obj);
        }
        return objsToSend;
    }
}
