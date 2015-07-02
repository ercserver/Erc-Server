package RequestsModule.utils;

import Utilities.ErcLogger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by ohad on 5/5/2015.
 */
public class HashMapCreator {
    ErcLogger logger = new ErcLogger(this.getClass().getName());

    public  HashMap<String, String> jsonToMap(JSONObject jObject) throws JSONException {
        logger.println("In jsonToMap");
        HashMap<String, String> map = new HashMap<String, String>();
        Set<String> keys = jObject.keySet();

        for (String key : keys){
            try {
                map.put(key, jObject.get(key).toString());
                logger.println("map <-- " + key);
            }catch (Exception ex){
                System.err.println("Error in key : " + key);
            }

        }
        logger.println("After while");
        return map;
    }

    public  HashMap<Integer, HashMap<String, String>> jsonArrayToMap(JSONArray jArray) throws JSONException {
        HashMap<Integer, HashMap<String, String>> map = new HashMap<>();
        for (int i = 0; i < jArray.length(); i++){
            JSONObject json = jArray.getJSONObject(i);
            map.put(i+1, jsonToMap(json));
        }

        return map;
    }


}
