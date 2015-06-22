package RequestsModule.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by ohad on 5/5/2015.
 */
public class HashMapCreator {

    public  HashMap<String, String> jsonToMap(JSONObject jObject) throws JSONException {
        HashMap<String, String> map = new HashMap<String, String>();
        Iterator<?> keys = jObject.keys();

        while( keys.hasNext() ){
            String key = (String)keys.next();
            String value = jObject.getString(key);
            map.put(key, value);
        }
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
