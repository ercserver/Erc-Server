package db.ohad;

import DatabaseModule.src.controller.DbController_V1;
import DatabaseModule.src.model.DbComm_V1;
import registrationModule.src.controller.RegController_V1;
import registrationModule.src.model.RegVerify_V2;

/**
 * Created by ohad on 10/6/2015.
 */


public class RegFieldsTest {


    public static void main(String[] args) {


        RegController_V1 rc = new RegController_V1();
        DbController_V1 dbController = new DbController_V1();
        DbComm_V1 db = new DbComm_V1();
        RegVerify_V2 rv = new RegVerify_V2();
        //System.out.println(rc.getWaitingForDoctor(1083));

        /*String cmid = "1081";
        HashMap<String, String> member = new HashMap<String, String>();
        member.put("P_CommunityMembers.community_member_id", new Integer(cmid).toString());
        HashMap<String, String> details = dbController.getUserByParameter(member);
        System.out.println(rc.getWaitingForDoctor(new HashMap<String, String>()));*/
    }



}
