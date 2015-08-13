package emergency.ohad;

import DatabaseModule.src.controller.DbController_V1;
import DatabaseModule.src.model.DbComm_V1;
import EmergencyModule.src.api.IEmerController;
import EmergencyModule.src.controller.EmerController_V1;
import Utilities.HashMapBuilder;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by ohad on 20/6/2015.
 */
public class MainTest {

    public static void main(String[] args) {
        DbComm_V1 db = new DbComm_V1();
        db.closeEvent(1003, "FINISHED");
      //testNaor6("10042");
      /*  EmerController_V1 em = new EmerController_V1();
        em.receiveUsersAroundLocation(new HashMap<String, String>());*/
    //    DbController_V1 dbc = new DbController_V1();
    //    System.out.println(dbc.getEventDetails("1032"));
       /* EmerController_V1 ec = new EmerController_V1();
        ec.emergencyCall(new HashMapBuilder<String,String>().put("community_member_id","10005").build());*/
    }



    //test message of patient at risk
    public static void testNaor6(String cmid) {
        HashMap<String, String> fakeDetails = new HashMap<String, String>();

        fakeDetails.put("community_member_id", cmid);
        fakeDetails.put("message", "TEST MESSAGE");

        IEmerController controller = new EmerController_V1();
        controller.updatePatientStatus(fakeDetails);


    }
}
