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
        HashMap<String,String> data = new HashMap<String,String>();
        HashMap<String,String> data1 = d.getUserByCmid(10018);
        //r.getUpdatesFields(data1);
        data.put("email_address","shbe77@gmail.com");
        r.forgotPassword(data);
        //r.updateCommunicationParameters("dasd");
                //r.handleRefreshDetails();
    }
}
