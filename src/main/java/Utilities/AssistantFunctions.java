package Utilities;

import DatabaseModule.src.api.IDbController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by מאור on 18/05/2015.
 */
public class AssistantFunctions {
    private static final String GIS_URL = null;
    private static final String GIS_UNAME = null;
    private static final String GIS_PW = null;
    private static final String EMS_URL = null;
    private static final String EMS_UNAME = null;
    private static final String EMS_PW = null;

    private IDbController dbController = null;

    public AssistantFunctions()
    {
        ModelsFactory models = new ModelsFactory();
        dbController = models.determineDbControllerVersion();
    }

    public boolean checkCmidAndPassword(String password, int cmid)
    {
        HashMap<String, String> conds = new HashMap<String, String>();
        conds.put("P_CommunityMembers.community_member_id", Integer.toString(cmid));
        conds.put("MembersLoginDetails.password", "'" + password + "'");
        return(null != dbController.getUserByParameter(conds));
    }

    public ArrayList<String> addReceiver(String receiver,ArrayList<String> sendTo) {
        if(receiver.equals("EMS")){
            sendTo.add(EMS_URL);
            sendTo.add(EMS_UNAME);
            sendTo.add(EMS_PW);
        }
        else if (receiver.equals("GIS")){
            sendTo.add(GIS_URL);
            sendTo.add(GIS_UNAME);
            sendTo.add(GIS_PW);
        }
        return sendTo;
    }


}
