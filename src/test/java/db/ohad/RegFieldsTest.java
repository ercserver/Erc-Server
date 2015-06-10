package db.ohad;

import DatabaseModule.src.model.DbComm_V1;
import Utilities.HashMapBuilder;

/**
 * Created by ohad on 10/6/2015.
 */
public class RegFieldsTest {
    public static void main(String[] args) {
        DbComm_V1 dbc = new DbComm_V1();
        System.out.println(dbc.getUserByParameter(new HashMapBuilder<String, String>().put("P_communityMembers." +
                "email_address","ohadgur@gmail.com").build()));
    }



}
