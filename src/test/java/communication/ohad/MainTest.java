package communication.ohad;

import CommunicationModule.src.model.GcmCommnication_V1;
import CommunicationModule.src.model.InitiatedHTTPCommunication_V1;
import DatabaseModule.src.model.DbComm_V1;
import RequestsModule.utils.HashMapCreator;
import Utilities.HashMapBuilder;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONWriter;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import registrationModule.src.controller.RegController_V1;
import sun.applet.Main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ohad on 10/6/2015.
 */
public class MainTest {
    public static void main(String[] args) {
        MainTest mt = new MainTest();
        mt.gcmTest();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
