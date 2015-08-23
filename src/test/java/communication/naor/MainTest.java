package communication.naor;

import CommunicationModule.src.model.CommOfficial_V1;
import CommunicationModule.src.model.CommToMail_V1;
import CommunicationModule.src.model.GcmCommnication_V1;
import DatabaseModule.src.model.DbComm_V1;
import RequestsModule.utils.HashMapCreator;
import Utilities.HashMapBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by naor on 10/6/2015.
 */
public class MainTest {

    private  Logger logger = Logger.getLogger(this.getClass().getName());

    public static void main(String[] args) {
        /*MainTest mt = new MainTest();
        mt.gcmTest();*/
        CommOfficial_V1 mail = new CommToMail_V1(new HashMapBuilder<String,String>().
                put("Message","blabla").put("Subject", "subject").put("Email","ohadgur@gmail.com").build() );
        mail.sendMessage();
    }
    private void emsTest(){
        // THIS WORKS!!!!
        HashMap<Integer, HashMap<String, String>> map = new HashMap<>();
        map.put(1, new HashMapBuilder<String, String>().put("hello", "world").build());
        ArrayList<String> target = new ArrayList<>();
        target.add("http://mba4.ad.biu.ac.il/gisWebProject/Mapping");

        // target.add("http://mba4.ad.biu.ac.il/Erc-Server/requests/test");
        target.add("un");
        target.add("pwd");
        try {

            Connection.Response res = Jsoup.connect(target.get(0)).

                    data("data",new JSONObject().put("hello","world").toString())

                    .header("content-type", "application/json")
                    .method(Connection.Method.POST)

                    .execute();


            System.out.println(new JSONObject(res.body()));
        } catch (IOException e) {
            logger.log(Level.WARNING, "Exception: ", e);
            System.out.println(e.getMessage());
        }

    }
    private void gisTest(){
        HashMap<Integer, HashMap<String, String>> data = new HashMap<>();
        data.put(1, new HashMapBuilder<String, String>().put("hello", "world").build());
        ArrayList<String> target = new ArrayList<>();
       target.add("http://mba4.ad.biu.ac.il//gisWebProject/test");
       // target.add("http://mba4.ad.biu.ac.il/Erc-Server/requests/test");
        target.add("un");
        target.add("pwd");
        try {
            Connection.Response res = Jsoup.connect(target.get(0)).
                    data("data",new JSONObject().put("hello","world").toString())
                    .ignoreContentType(true)
                    .method(Connection.Method.POST)
                    .execute();
            System.out.println(res.body());

        } catch (IOException e) {
            logger.log(Level.WARNING, "Exception: ", e);
            System.out.println(e.getMessage());
        }
    }

    public void gcmTest(){
        DbComm_V1 db = new DbComm_V1();
        HashMap<Integer, HashMap<String, String>> data = new HashMap<>();
        HashMapCreator hmc = new HashMapCreator();
        JSONArray jsonArray = new JSONArray("[{'password':'asdf', 'community_member_id':'1165', 'RequestID':'active'}, {'name':'connect_server_frequency', 'frequency':'49.0'},  {'name':'location_frequency', 'frequency':'43.0'}]");
        for (int i=0; i < jsonArray.length(); i++){
            data.put(i+1, hmc.jsonToMap(jsonArray.getJSONObject(i)));
        }

        ArrayList target = new ArrayList();
        target.add("APA91bEP314fADLSbwAt6lHdRm3G9TUl5vHxjE1JJi2kRzjPUo0ODqkG8xdTkV8YQ0OIv-mP2eCRFpaykZLelWSTBBFEzdWVuMAFqcfpoKi_064mvSxq6o1sXVz1FoZQOr-Pcu8ofnC2");

        GcmCommnication_V1 gcm = new GcmCommnication_V1(data, target);
        gcm.sendResponse();
    }

}
