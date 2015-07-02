package RequestsModule.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ohad on 2/7/2015.
 */
public class JsonValidator{

        public JSONObject createJSON(String jsonString){
            return isJSONValid(jsonString) ? new JSONObject(jsonString) : new JSONObject();
        }

        private boolean isJSONValid(String test) {
            try {
                new JSONObject(test);
            } catch (JSONException ex) {
               return false;
            }
            return true;
        }

}
