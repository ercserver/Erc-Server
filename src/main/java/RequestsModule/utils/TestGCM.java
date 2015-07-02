package RequestsModule.utils;

import CommunicationModule.src.model.GcmCommnication_V1;
import DatabaseModule.src.model.DbComm_V1;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ohad on 1/7/2015.
 */
public class TestGCM {
    public void gcmTest(){
        DbComm_V1 db = new DbComm_V1();
        HashMap<Integer, HashMap<String, String>> data = new HashMap<>();
        HashMapCreator hmc = new HashMapCreator();
        JSONArray jsonArray = new JSONArray("[{'hello':'world}]");
        for (int i=0; i < jsonArray.length(); i++){
            data.put(i+1, hmc.jsonToMap(jsonArray.getJSONObject(i)));
        }

        ArrayList target = new ArrayList();
        target.add("APA91bEP314fADLSbwAt6lHdRm3G9TUl5vHxjE1JJi2kRzjPUo0ODqkG8xdTkV8YQ0OIv-mP2eCRFpaykZLelWSTBBFEzdWVuMAFqcfpoKi_064mvSxq6o1sXVz1FoZQOr-Pcu8ofnC2");

        GcmCommnication_V1 gcm = new GcmCommnication_V1(data, target);
        gcm.sendResponse();
    }
}
