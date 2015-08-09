package emergency.ohad;

import DatabaseModule.src.controller.DbController_V1;
import EmergencyModule.src.controller.EmerController_V1;
import Utilities.HashMapBuilder;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by ohad on 20/6/2015.
 */
public class MainTest {

    public static void main(String[] args) {
      /*  EmerController_V1 em = new EmerController_V1();
        em.receiveUsersAroundLocation(new HashMap<String, String>());*/
        DbController_V1 dbc = new DbController_V1();
        dbc.getPatientIDByCmid("10005");

        EmerController_V1 ec = new EmerController_V1();
        ec.emergencyCall(new HashMapBuilder<String,String>().put("community_member_id","10005").build());
    }
}
