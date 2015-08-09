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
        testNaor3("10005");
        testNaor4("10005");
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
    public static void testNaor1() {
        RegController_V1 rc = new RegController_V1();
        HashMap<String,String> fakeDetails = new HashMap<String,String>();
        fakeDetails.put("reg_id","111111");
        fakeDetails.put("user_type", "0");
        JSONArray objToSend = (JSONArray) rc.getRegDetails(fakeDetails);
        System.out.println(objToSend.toString());
    }



    public static void testNaor2() {
        RegController_V1 rc = new RegController_V1();
        HashMap<String, String> fakeDetails = new HashMap<String, String>();
        /*DbComm_V1 db = new DbComm_V1();
        db.deleteUser(1030);*/

        fakeDetails.put("email_address", "'arbelax@gail.com'");
        fakeDetails.put("user_type", "1");
        fakeDetails.put("P_supervision.doc_licence_num", "10054");
        fakeDetails.put("P_prescriptions.doc_licence_num", "10054");
        fakeDetails.put("P_diagnosis.doc_licence_num", "10054");
        fakeDetails.put("external_id", "1234");
        fakeDetails.put("external_id_type", "0");
        fakeDetails.put("first_name", "Arbel");
        fakeDetails.put("last_name", "Axelrod");
        fakeDetails.put("birth_date", "1900-10-27");
        fakeDetails.put("doc_license_number", "123456");
        fakeDetails.put("gender", "1");
        fakeDetails.put("state", "israel");
        fakeDetails.put("city", "Nahariyya");
        fakeDetails.put("street", "HaZamir");
        fakeDetails.put("house_number", "8");
        fakeDetails.put("zip_code", "00000");
        fakeDetails.put("mobile_phone_number", "052222222222");
        fakeDetails.put("contact_phone", "0521123456");
        fakeDetails.put("reg_id", "9999");
        fakeDetails.put("hour_from", "12");
        fakeDetails.put("minutes_from", "56");
        fakeDetails.put("hour_to", "16");
        fakeDetails.put("minutes_to", "52");
        fakeDetails.put("date_to", "2018-12-12");
        fakeDetails.put("medication_num", "1000");
        fakeDetails.put("dosage", "1.456");
        fakeDetails.put("medical_condition_id", "1000");
        fakeDetails.put("password", "1111111");


        JSONArray objToSend = (JSONArray) rc.handleReg(fakeDetails);
        System.out.println(objToSend.toString());

    }
    //Transfer fake location to GIS
    public static void testNaor3(String cmid) {
        IRoutineController controller = new RoutineController_V1();
        HashMap<String, String> fakeDetails = new HashMap<String, String>();
        fakeDetails.put("community_member_id", cmid);
        fakeDetails.put("RequestID", "routineLocation");
        fakeDetails.put("x", "34.729817");
        fakeDetails.put("y", "31.879638");
        controller.transferLocation(fakeDetails);
    }
    //start emergency process
    public static void testNaor4(String cmid) {
        IEmerController controller = new EmerController_V1();
        HashMap<String, String> fakeDetails = new HashMap<String, String>();
        fakeDetails.put("community_member_id", cmid);
        fakeDetails.put("RequestID", "AroundLocation");
        fakeDetails.put("x", "34.729517");
        fakeDetails.put("y", "31.879038");
        fakeDetails.put("medical_condition_id", "1000");
        fakeDetails.put("reg_id", "0");

        controller.emergencyCall(fakeDetails);
    }



    }
