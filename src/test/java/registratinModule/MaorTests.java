package registratinModule;

import DatabaseModule.src.model.DbComm_V1;

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

   /* public Object updatedFromPatient(HashMap<String, String> filledForm)
    {
        if checkCmidAndPassword(filledForm.get("password"), Integer.parseInt(filledForm.get("community_member_id"))
        {

        }
        else

    }*/
}
