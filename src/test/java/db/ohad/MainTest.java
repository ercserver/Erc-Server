package db.ohad;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ohad on 29/5/2015.
 */
public class MainTest {
    public static void main(String[] args) {

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:MM:ss");

        System.out.println(sdf.format(date));
    }

}
