package db.ohad;

import DatabaseModule.src.model.DbComm_V1;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by ohad on 29/5/2015.
 */
public class MainTest {
    public static void main(String[] args) {
        DbComm_V1 db = new DbComm_V1();
        System.out.println(db.getRegistrationFields(1));
    }

}
