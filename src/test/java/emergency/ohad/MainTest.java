package emergency.ohad;

import EmergencyModule.src.controller.EmerController_V1;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by ohad on 20/6/2015.
 */
public class MainTest {

    public static void main(String[] args) {
      /*  EmerController_V1 em = new EmerController_V1();
        em.receiveUsersAroundLocation(new HashMap<String, String>());*/
        HashMap<String, String[]> map = new HashMap<>();
        map.put("first", new String[] {"Hello", "Hi"});
        JSONObject json = new JSONObject(map);
        System.out.println(json);
    }
}
