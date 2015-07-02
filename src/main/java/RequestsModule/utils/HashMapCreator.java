package RequestsModule.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ohad on 5/5/2015.
 */
public class HashMapCreator {
    private Logger logger = Logger.getLogger(this.getClass().getName());

    public  HashMap<String, String> jsonToMap(JSONObject jObject) throws JSONException {
        logger.log(Level.INFO, "In jsonToMap");
        HashMap<String, String> map = new HashMap<String, String>();
        Set<String> keys = jObject.keySet();

        for (String key : keys){
            try {
                map.put(key, jObject.get(key).toString());
                logger.log(Level.INFO, "map <-- " + key);
            }catch (Exception ex){
                System.err.println("Error in key : " + key);
            }

        }
        logger.log(Level.INFO, "After while");
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
