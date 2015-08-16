package registratinModule;

import EmergencyModule.src.api.IEmerController;
import EmergencyModule.src.controller.EmerController_V1;
import RoutineModule.src.api.IRoutineController;
import RoutineModule.src.controller.RoutineController_V1;
import org.json.JSONArray;
import registrationModule.src.controller.RegController_V1;
import registrationModule.src.model.RegVerify_V2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

/**
 * Created by User on 06/05/2015.
 */

public class ShmulikTest {
    public static void main(String[] args) {
        //testNaor6("10042");
        //testNaor4("10005");
       //testNaor5();
       /* DbComm_V1 d = new DbComm_V1();
        testNaor1();

        RegVerify_V2 v2 = new RegVerify_V2();
        HashMap<String,String> h = new HashMap<String,String>();
        HashMap<String,String> h2 = new HashMap<String,String>();
        h = v2.getUserByCmid(1002);
        h2 = v2.getUserByCmid(1003);*/
        //testNaor2();
        /*
        RegController_V1 v = new RegController_V1();
        v2.changeStatusToVerifyDetailAndSendToApp(1002,h);
        //v2.proccesOfOkMember(1002);
        //v2.convertCodeToDefaultCallerSettings("0");
        //v2.checkCondForResendMail(details,, cmid)
        v2.filterDataForVerification(h);*/
///
//        test3();

    }

    private static void test3() {

        RegVerify_V2 v2 = new  RegVerify_V2();
        HashMap<String, String> s = v2.getUserByCmid(1002);
        System.out.println(s);
    }

    public static void test2()
    {
        HashMap<String,String> details = new HashMap<String,String>();
        details.put("status_num","1002");
        RegVerify_V2 v2 = new  RegVerify_V2();
        String s = v2.getStatus(details);
        System.out.println(s);

    }



    }
