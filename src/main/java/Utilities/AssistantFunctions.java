package Utilities;

import DatabaseModule.src.api.IDbController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Created by מאור on 18/05/2015.
 */
public class AssistantFunctions {
    private final String GIS_URL = ErcConfiguration.GIS_SERVER_URL;
    private final String GIS_UNAME = "";
    private final String GIS_PW = "";
    private final String EMS_URL = ErcConfiguration.EMS_SERVER_URL;
    private final String EMS_UNAME = "";
    private final String EMS_PW = "";

    private IDbController dbController = null;

    private Logger logger = Logger.getLogger(this.getClass().getName());

    public AssistantFunctions()
    {

        ModelsFactory models = new ModelsFactory();
        dbController = models.determineDbControllerVersion();

    }

    public boolean checkCmidAndPassword(String password, int cmid)
    {
        logger.log(Level.INFO, "In checkCmidAndPassword. params = " + password + ", " + cmid);
        HashMap<String, String> conds = new HashMap<String, String>();
        conds.put("P_CommunityMembers.community_member_id", Integer.toString(cmid));
        conds.put("MembersLoginDetails.password", password);
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
