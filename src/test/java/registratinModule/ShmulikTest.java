package registratinModule;

import registrationModule.src.controller.RegController_V1;
import registrationModule.src.model.RegVerify_V2;

import java.util.HashMap;

/**
 * Created by User on 06/05/2015.
 */
public class ShmulikTest {
    public static void main(String[] args) {


        RegVerify_V2 v2 = new RegVerify_V2();
        HashMap<String,String> h = new HashMap<String,String>();
        HashMap<String,String> h2 = new HashMap<String,String>();
        h = v2.getUserByCmid(1002);
        h2 = v2.getUserByCmid(1003);
        /*
        RegController_V1 v = new RegController_V1();
        v2.changeStatusToVerifyDetailAndSendToApp(1002,h);*/
        //v2.proccesOfOkMember(1002);
        //v2.convertCodeToDefaultCallerSettings("0");
        //v2.checkCondForResendMail(details,, cmid)
        v2.filterDataForVerification(h);
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
    public static void testNaor1() {
        RegController_V1 rc = new RegController_V1();
    }





}
