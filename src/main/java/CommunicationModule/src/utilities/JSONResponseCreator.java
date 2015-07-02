package CommunicationModule.src.utilities;

import CommunicationModule.src.api.IResponseCreator;
import Utilities.ErcLogger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class JSONResponseCreator implements IResponseCreator {

    private ErcLogger logger = new ErcLogger(this.getClass().getName());

    public JSONArray establishResponse(HashMap<Integer,HashMap<String,String>> data) {
        logger.println("In establishResponse: param = " + data);
        JSONArray objsToSend = new JSONArray();
        for (Map.Entry<Integer,HashMap<String,String>> objs : data.entrySet()){
            HashMap<String,String> obj = objs.getValue();
            JSONObject currJson = new JSONObject(obj);
            objsToSend.put(obj);
        }
        return objsToSend;
    }
}
