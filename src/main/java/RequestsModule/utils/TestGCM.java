package RequestsModule.utils;

import CommunicationModule.src.model.GcmCommnication_V1;
import DatabaseModule.src.model.DbComm_V1;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by naor on 1/7/2015.
 */
public class TestGCM {
    public void gcmTest(int cmid){
        DbComm_V1 db = new DbComm_V1();
        HashMap<Integer, HashMap<String, String>> data = new HashMap<>();
        HashMapCreator hmc = new HashMapCreator();


        data.put(1, hmc.jsonToMap(new org.json.JSONObject().put("message", "Hello World!")));


        ArrayList target = new ArrayList();

        target.add(db.getRegIDsOfUser(cmid).get(1).get("reg_id"));

        GcmCommnication_V1 gcm = new GcmCommnication_V1(data, target);
        gcm.sendResponse();
    }
}
