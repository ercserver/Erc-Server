package emergency.ohad;

import CommunicationModule.src.api.ICommController;
import DatabaseModule.src.api.IDbController;
import DatabaseModule.src.controller.DbController_V1;
import DatabaseModule.src.model.DbComm_V1;
import EmergencyModule.src.api.IEmerController;
import EmergencyModule.src.controller.EmerController_V1;
import RoutineModule.src.api.IRoutineController;
import RoutineModule.src.controller.RoutineController_V1;
import Utilities.HashMapBuilder;
import Utilities.ModelsFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import registrationModule.src.controller.RegController_V1;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ohad on 20/6/2015.
 */
public class MainTest {

    public static void main(String[] args) {
/*
        ArrayList<Integer> cmidList = new ArrayList<Integer>();
        cmidList.add(10042);
        String event_id = "1125";
        testNaor9(cmidList,event_id);
*/
        testNaor10("10042","1127");
      //  DbComm_V1 db = new DbComm_V1();
      //  db.closeEvent(1003, "FINISHED");
//      testNaor7("10017", "ff", "1077");
      /*  EmerController_V1 em = new EmerController_V1();
        em.receiveUsersAroundLocation(new HashMap<String, String>());*/
    //    DbController_V1 dbc = new DbController_V1();
    //    System.out.println(dbc.getEventDetails("1032"));
       /* EmerController_V1 ec = new EmerController_V1();
        ec.emergencyCall(new HashMapBuilder<String,String>().put("community_member_id","10005").build());*/
    }

    public static void testNaor1() {
        RegController_V1 rc = new RegController_V1();
        HashMap<String,String> fakeDetails = new HashMap<String,String>();
        fakeDetails.put("reg_id", "111111");
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
        fakeDetails.put("password", "a1234");
        fakeDetails.put("RequestID", "AroundLocation");
        fakeDetails.put("x", "34.729517");
        fakeDetails.put("y", "31.879038");
        fakeDetails.put("medical_condition_id", "1000");
        fakeDetails.put("reg_id", "0");

        controller.emergencyCall(fakeDetails);
    }
    public static void testNaor5() {
        HashMap<String, String> fakeDetails = new HashMap<String, String>();


        fakeDetails.put("state", "israel");
        fakeDetails.put("RequestID", "AroundLocation");
        fakeDetails.put("location_remark", "sitvanit");
        fakeDetails.put("radius", "5");
        fakeDetails.put("RequestID", "AroundLocation");
        fakeDetails.put("region_type", "1");
        fakeDetails.put("event_id", "1053");
        fakeDetails.put("10010", null);
        fakeDetails.put("6666", null);
        fakeDetails.put("7777", null);
        fakeDetails.put("112", null);
        fakeDetails.put("5555", null);

        IEmerController controller = new EmerController_V1();
        controller.receiveUsersAroundLocation(fakeDetails);


    }

    //test message of patient at risk
    public static void testNaor6(String cmid) {
        HashMap<String, String> fakeDetails = new HashMap<String, String>();

        fakeDetails.put("community_member_id", cmid);
        fakeDetails.put("message", "TEST MESSAGE");

        IEmerController controller = new EmerController_V1();
        controller.updatePatientStatus(fakeDetails);


    }
//10017 10018
    //simulate assistant responds to approach
    public static void testNaor7(String cmid,String password, String event_id) {

        HashMap<String, String> fakeDetails = new HashMap<String, String>();
        fakeDetails.put("community_member_id", cmid);
        fakeDetails.put("password", password);
        fakeDetails.put("event_id", event_id);
        fakeDetails.put("RequestID", "arrivalAcceptionMounted");
        fakeDetails.put("x", "31.523");
        fakeDetails.put("y", "33.111");
        fakeDetails.put("eta_by_foot", "33");
        fakeDetails.put("eta_by_car", "22");

        IEmerController controller = new EmerController_V1();
        controller.assistantRespondsToApproach(fakeDetails);
    }

    public static void testNaor9(ArrayList<Integer> cmidList, String event_id) {


        IDbController db = new DbController_V1();
        ArrayList<Integer> response = db.filterAvailableMembers(cmidList,event_id);
        for(Integer i : response){
            System.out.println(i.toString() + "\n");
        }
    }

    //approach assistant for help
    public static void testNaor10(String cmid,String event_id) {
        IDbController db = new DbController_V1();
        HashMap<String, String> fakeDetails = new HashMap<String, String>();
        ModelsFactory models = new ModelsFactory();
        ICommController commController = models.determineCommControllerVersion();
        //populate date
        fakeDetails.put("eta_by_car", "1");
        fakeDetails.put("eta_by_foot", "2");
        fakeDetails.put("location_remark", "Sitvanit Street 30, Yavne, Israel");
        fakeDetails.put("RequestID", "helpAssist");
        fakeDetails.put("event_id", event_id);
        fakeDetails.put("dosage", "6.0");
        fakeDetails.put("medication_name", "akamol4");
        fakeDetails.put("subRequest", "cmid");
        fakeDetails.put("x", "31.879038");
        fakeDetails.put("y", "34.72952");
        //TODO - if she really keeps insisting, just give it to her... =\
        //fakeDetails.put("community_member_id", cmid);


        //add reg_id of the relevant cmid to the list to send and execute sending
        ArrayList<String> target = new ArrayList<String>();
        HashMap<Integer,HashMap<String,String>> regId = db.getRegIDsOfUser(Integer.parseInt(cmid));
        target.add(regId.get(1).get("reg_id"));
        HashMap<Integer, HashMap<String, String>> request = new HashMap<Integer, HashMap<String, String>>();
        request.put(1, fakeDetails);
        commController.setCommToUsers(request, target, false);
        commController.sendResponse();


    }
}
