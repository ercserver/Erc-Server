package registratinModule;

import org.json.JSONObject;
import registrationModule.src.model.RegVerify_V2;

import java.util.HashMap;

/**
 * Created by User on 06/05/2015.
 */

public class ShmulikTest {
    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>();
        map.put("message", "hello world!");
        JSONObject json = new JSONObject(map);
        System.out.println(json);

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
