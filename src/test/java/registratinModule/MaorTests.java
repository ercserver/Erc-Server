package registratinModule;

import DatabaseModule.src.model.DbComm_V1;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by מאור on 10/05/2015.
 */
public class MaorTests {
    public static void main(String[] args) {
        DbComm_V1 d = new DbComm_V1();
        HashMap<String,String> h = new HashMap<String,String>();
        h.put("P_CommunityMembers.community_member_id","1003");
        HashMap<String,String> h1 = d.getUserByParameter(h);
        System.out.println(h1);
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
        return popUpMessage(response, regIds, true);
    }

    private Object popUpMessage(HashMap<String, String> response, ArrayList<String> regIds, boolean sendToEms)
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
    }*/
}
