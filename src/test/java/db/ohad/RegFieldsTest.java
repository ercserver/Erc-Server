package db.ohad;

import DatabaseModule.src.model.DbComm_V1;
import Utilities.HashMapBuilder;
import registrationModule.src.controller.RegController_V1;

import java.util.HashMap;

/**
 * Created by ohad on 10/6/2015.
 */
public class RegFieldsTest {
    public static void main(String[] args) {
        RegController_V1 rc = new RegController_V1();
        DbComm_V1 db = new DbComm_V1();
        HashMap<Integer, HashMap<String, String>> map = db.getRegistrationFields(0);
    }



}
