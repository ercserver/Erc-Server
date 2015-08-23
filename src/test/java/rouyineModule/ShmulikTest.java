package rouyineModule;

import DatabaseModule.src.model.DbComm_V1;
import RoutineModule.src.controller.RoutineController_V1;
import Utilities.PatientDetails;

import java.util.HashMap;

/**
 * Created by User on 25/05/2015.
 */
public class ShmulikTest {
    public static void main(String[] args) {
        RoutineController_V1 r = new RoutineController_V1();
        PatientDetails d = new PatientDetails();
        DbComm_V1 c = new DbComm_V1();
       // HashMap<String, String> aa = c.getRejectCodes();
        //HashMap<String,String> data = new HashMap<String,String>();
        HashMap<String,String> data1 = new  HashMap<String,String>();//d.getUserByCmid(1083);
        //r.getUpdatesFields(data1);
        data1.put("city","doc");
        data1.put("contact_phone","054-2323232");
        data1.put("community_member_id","10027");
        data1.put("password","a1234");
        r.updateMemberDetails(data1);

    }
}
