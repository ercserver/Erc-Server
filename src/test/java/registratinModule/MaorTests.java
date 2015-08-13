package registratinModule;

import DatabaseModule.src.model.DbComm_V1;
import EmergencyModule.src.api.IEmerController;
import EmergencyModule.src.controller.EmerController_V1;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by מאור on 10/05/2015.
 */
public class MaorTests {
    public static void main(String[] args) {
        IEmerController controller = new EmerController_V1();
        //controller.askGisToFollow("1053", "10010");
       // ArrayList<String> cmids = new ArrayList<String>();
        //cmids.add("10010");
        //controller.stopFollow("1053", cmids);
        HashMap<String, String>h = new HashMap<String,String>();
        h.put("event_id", "1053");
        h.put("RequestID", "UsersArrivalTimes");
        h.put("radius", "5");
        HashMap<String,String> h1 = new HashMap<String,String>();
        h1.put("subRequest", "cmid");
        h1.put("community_member_id", "10010");
        h1.put("location_remark", "Sitvanit Street 17-31, Yavne, Israel");
        h1.put("eta_by_car", "1");
        h1.put("eta_by_foot", "1");
        HashMap<Integer, HashMap<String,String>> hh = new HashMap<Integer, HashMap<String,String>>();
        hh.put(1, h);
        hh.put(2, h1);
        controller.receiveUsersArrivalTimesAndApproach(hh);
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
