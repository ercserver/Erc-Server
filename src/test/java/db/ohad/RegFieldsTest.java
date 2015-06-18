package db.ohad;

import DatabaseModule.src.model.DbComm_V1;
import RequestsModule.utils.HashMapCreator;
import Utilities.ErcLogger;
import Utilities.HashMapBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import registrationModule.src.controller.RegController_V1;

import java.util.HashMap;

/**
 * Created by ohad on 10/6/2015.
 */
public class RegFieldsTest {
    public static void main(String[] args) {
        DbComm_V1 db= new DbComm_V1();
        db.deleteUser(1024);
    }



}
