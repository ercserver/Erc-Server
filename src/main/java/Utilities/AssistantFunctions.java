package Utilities;

import DatabaseModule.src.api.IDbController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by מאור on 18/05/2015.
 */
public class AssistantFunctions {
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



}
