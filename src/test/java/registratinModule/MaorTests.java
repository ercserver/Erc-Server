package registratinModule;

import RoutineModule.src.controller.RoutineController_V1;
import RoutineModule.src.model.EmsRoutine_V1;
import registrationModule.src.controller.RegController_V1;

import java.util.HashMap;

/**
 * Created by מאור on 10/05/2015.
 */
public class MaorTests {
    public static void main(String[] args) {
        System.out.println(new EmsRoutine_V1().getEmsEventsByDispatcherCmid(10010));
        RoutineController_V1 cont = new RoutineController_V1();
        //controller.askGisToFollow("1053", "10010");
       // ArrayList<String> cmids = new ArrayList<String>();
        //cmids.add("10010");
        //controller.stopFollow("1053", cmids);
        HashMap<String, String>h = new HashMap<String,String>();
        h.put("state", "Israel");
        h.put("RequestID", "resendAuth");
        h.put("email_address", "shirap64@gmail.com");
        RegController_V1 c = new RegController_V1();
        c.resendAuth(h);

        /*h.put("event_id", "1321");
        h.put("status", "FINISH");
        controller.emsTakeover(h);*/
        /*h.put("event_id", "1027");
        h.put("RequestID", "UsersArrivalTimes");
        h.put("radius", "5");
        HashMap<String,String> h1 = new HashMap<String,String>();
        h1.put("subRequest", "cmid");
        h1.put("community_member_id", "10019");
        h1.put("location_remark", "Sitvanit Street 17-31, Yavne, Israel");
        h1.put("eta_by_car", "1");
        h1.put("eta_by_foot", "1");
        HashMap<Integer, HashMap<String,String>> hh = new HashMap<Integer, HashMap<String,String>>();
        hh.put(1, h);
        hh.put(2, h1);
        controller.receiveUsersArrivalTimesAndApproach(hh);*/
        //controller.updateRadiusToEMS("10", "1003");
        /*h.put("event_id", "1003");
        h.put("RequestID", "followUser");
        h.put("location_remark", "Sitvanit Street 17-31, Yavne, Israel");
        h.put("community_member_id", "10014");
        h.put("eta_by_car", "1");
        h.put("eta_by_foot", "1");
        h.put("x", "34.728817");
        h.put("y", "31.878638");
        controller.receiveArrivalTime(h);*/

        /*h.put("community_member_id", "10019");
        h.put("password", "ff");
        h.put("event_id", "1027");
        h.put("RequestID", "arrivalAcceptionMounted");
        h.put("eta_by_car", "1");
        h.put("eta_by_foot", "1");
        h.put("x", "34.729817");
        h.put("y", "31.879638");
       // h.put("reg_id","APA91bHIuXMXn2Y4uermQmcpmJSqkHGGjo38UloJDqGt2jBB6w1Ok79B7FZXgRiqrmwhbf6gAFnMYu4JllAeFL7RIEyGB44E_HNfMgja9rR2piOFZfdCOXtSycFOlyEp6nkrshPw33Sq");
        h.put("reg_id","APA91bEP314fADLSbwAt6lHdRm3G9TUl5vHxjE1JJi2kRzjPUo0ODqkG8xdTkV8YQ0OIv-mP2eCRFpaykZLelWSTBBFEzdWVuMAFqcfpoKi_064mvSxq6o1sXVz1FoZQOr-Pcu8ofnC2");
        controller.assistantRespondsToApproach(h);*/

        /*h.put("password", "a123");
        h.put("event_id", "1003");
        h.put("community_member_id", "10010");
        h.put("RequestID", "EMSId");
        controller.getCmidOfEms(h);*/

       /* h.put("community_member_id", "10010");
        h.put("password", "a123");
        Object obj = cont.getEmsEventsByDispatcherCmid(h);
        System.out.print(obj);*/

        /*h.put("password", "a1234");
        h.put("community_member_id", "10042");
        h.put("event_id", "1027");
        controller.assistantCancelsArrival(h);*/

        /*h.put("password", "ff");
        h.put("community_member_id", "10019");
        h.put("event_id", "1027");
        h.put("reg_id","APA91bEP314fADLSbwAt6lHdRm3G9TUl5vHxjE1JJi2kRzjPUo0ODqkG8xdTkV8YQ0OIv-mP2eCRFpaykZLelWSTBBFEzdWVuMAFqcfpoKi_064mvSxq6o1sXVz1FoZQOr-Pcu8ofnC2");
        controller.arrivalToDestination(h);*/

        /*h.put("community_member_id", "10005");
        h.put("message", "blablabla");
        controller.updatePatientStatus(h);*/

        /*h.put("community_member_id", "10005");
        h.put("password", "a1234");
        controller.patientCancelledEvent(h);*/

       /*h.put("community_member_id", "10042");
        h.put("password", "a1234");
        h.put("event_id", "1027");
        controller.assistantGaveMed(h);*/

        /*h.put("password", "a123");
        h.put("event_id", "1027");
        h.put("community_member_id", "10010");
        h.put("RequestID", "confirmMedication");
        h.put("patient_id", "10010");
        controller.approveOrRejectMed(h);*/
    }

    /*public Object updatedFromPatient(HashMap<String, String> filledForm)
    {
        if (!checkCmidAndPassword(filledForm.get("password"), Integer.parseInt(filledForm.get("community_member_id"))))
        {
            return null;
        }
        String eventId = dbController.getEventByCmid(filledForm.get("community_member_id"));
        HashMap<String, String> response = new HashMap<String, String>();
        response.put("event_id", eventId);
        response.put("message", filledForm.get("message"));
        response.put("RequestID", "newInfo");
        ArrayList<String> regIds = dbController.getHelpersRegIds(eventId);
        HashMap<Integer,HashMap<String,String>> h = new HashMap<Integer,HashMap<String,String>>();
        h.put(1, response);
        return popUpMessage(h, regIds, true);
    }

    private Object popUpMessage(HashMap<Integer,HashMap<String,String>> response, ArrayList<String> regIds, boolean sendToEms)
    {
        if(sendToEms)
        {
            if(null != regIds)
            {
                commController.setCommToUsers(response, null, false);
                commController.sendResponse();
                commController.setCommToUsers(response, regIds, false);
                return commController.sendResponse();
            }
            commController.setCommToUsers(response, null, false);
            return commController.sendResponse();
        }
        if(null != regIds)
        {
            commController.setCommToUsers(response, regIds, false);
            return commController.sendResponse();
        }
        return null;
    }

    public Object getResponseFromEmsAboutgivingMed(HashMap<String, String> filledForm)
    {
        HashMap<String,String> h = new HashMap<String,String>();
        h.put("event_id", filledForm.get("event_id"));
        h.put("RequestID", filledForm.get("RequestID"));
        String cmid = dbController.getCmidByPatientID(filledForm.get("patient_id"));
        String regid = dbController.getRegIDsOfUser(Integer.parseInt(cmid)).get(1).get("reg_id");
        ArrayList<String> target = new ArrayList<String>();
        target.add(regid);
        HashMap<Integer,HashMap<String,String>> response = new HashMap<Integer,HashMap<String,String>>();
        response.put(1, h);
        commController.setCommToUsers(response, target, false);
        return commController.sendResponse();
    }

    public Object someoneCame(HashMap<String, String> filledForm)
    {
        if (!checkCmidAndPassword(filledForm.get("password"), Integer.parseInt(filledForm.get("community_member_id"))))
        {
            return null;
        }
        HashMap<String,String> h = new HashMap<String,String>();
        h.put("event_id", filledForm.get("event_id"));
        HashMap<String,String> cond = new HashMap<String,String>();
        cond.put("P_CommunityMembers.community_member_id", filledForm.get("community_member_id"));
        h.put("patient_id", dbController.getUserByParameter(cond).get("patient_id"));
        h.put("RequestID", "confirmMedicationGiving");
        HashMap<Integer,HashMap<String,String>> response = new HashMap<Integer,HashMap<String,String>>();
        response.put(1, h);
        commController.setCommToUsers(response, null, false);
        return commController.sendResponse();
    }*/
}
