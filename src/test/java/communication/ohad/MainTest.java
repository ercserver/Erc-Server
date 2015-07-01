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
        JSONArray jsonArray = new JSONArray("[{\"needs_verification\":\"true\",\"Request_ID\":\"registration\",\"type\":\"3\",\"serial_num\":\"3\",\"refresh_time\":\"null\",\"field_name\":\"birth_date\",\"insert_data_to\":\"p_communityMembers\",\"user_type\":\"1\",\"fields_group\":\"0\",\"is_required\":\"true\",\"get_possible_values_from\":\"null\",\"gui_description\":\"Birth Date\",\"max_length\":\"50\"},{\"needs_verification\":\"true\",\"insert_data_to\":\"null\",\"user_type\":\"1\",\"fields_group\":\"1\",\"is_required\":\"true\",\"get_possible_values_from\":\"null\",\"type\":\"1\",\"gui_description\":\"Certification ID\",\"serial_num\":\"10\",\"refresh_time\":\"null\",\"field_name\":\"certification_external_id\",\"max_length\":\"50\"},{\"needs_verification\":\"true\",\"insert_data_to\":\"p_communityMembers\",\"user_type\":\"1\",\"fields_group\":\"0\",\"is_required\":\"true\",\"get_possible_values_from\":\"null\",\"type\":\"0\",\"gui_description\":\"City\",\"serial_num\":\"4\",\"refresh_time\":\"null\",\"field_name\":\"city\",\"max_length\":\"50\"},{\"needs_verification\":\"true\",\"insert_data_to\":\"P_EmergencyContact\",\"user_type\":\"1\",\"fields_group\":\"0\",\"is_required\":\"true\",\"get_possible_values_from\":\"null\",\"type\":\"0\",\"gui_description\":\"Emergency Contact Phone Number\",\"serial_num\":\"4\",\"refresh_time\":\"null\",\"field_name\":\"contact_phone\",\"max_length\":\"50\"},{\"needs_verification\":\"true\",\"insert_data_to\":\"null\",\"user_type\":\"1\",\"fields_group\":\"1\",\"is_required\":\"true\",\"get_possible_values_from\":\"null\",\"type\":\"1\",\"gui_description\":\"Doctor license\",\"serial_num\":\"10\",\"refresh_time\":\"null\",\"field_name\":\"doc_license_number\",\"max_length\":\"50\"},{\"needs_verification\":\"true\",\"insert_data_to\":\"p_communityMembers\",\"user_type\":\"1\",\"fields_group\":\"0\",\"is_required\":\"true\",\"get_possible_values_from\":\"null\",\"type\":\"0\",\"gui_description\":\"Email Address\",\"serial_num\":\"0\",\"refresh_time\":\"null\",\"field_name\":\"email_address\",\"max_length\":\"50\"},{\"needs_verification\":\"true\",\"insert_data_to\":\"p_communityMembers\",\"user_type\":\"1\",\"fields_group\":\"0\",\"is_required\":\"true\",\"get_possible_values_from\":\"null\",\"type\":\"0\",\"gui_description\":\"ID\",\"serial_num\":\"0\",\"refresh_time\":\"null\",\"field_name\":\"external_id\",\"max_length\":\"50\"},{\"needs_verification\":\"true\",\"insert_data_to\":\"null\",\"user_type\":\"1\",\"fields_group\":\"1\",\"is_required\":\"true\",\"get_possible_values_from\":\"null\",\"type\":\"1\",\"gui_description\":\"ID type\",\"serial_num\":\"10\",\"refresh_time\":\"null\",\"field_name\":\"external_id_type\",\"max_length\":\"50\"},{\"needs_verification\":\"true\",\"insert_data_to\":\"p_communityMembers\",\"user_type\":\"1\",\"fields_group\":\"0\",\"is_required\":\"true\",\"get_possible_values_from\":\"null\",\"type\":\"0\",\"gui_description\":\"First Name\",\"serial_num\":\"1\",\"refresh_time\":\"null\",\"field_name\":\"first_name\",\"max_length\":\"50\"},{\"needs_verification\":\"true\",\"insert_data_to\":\"p_communityMembers\",\"user_type\":\"1\",\"fields_group\":\"0\",\"is_required\":\"true\",\"get_possible_values_from\":\"{\\\"1\\\":{\\\"enum_value\\\":\\\"male\\\"},\\\"2\\\":{\\\"enum_value\\\":\\\"female\\\"},\\\"3\\\":{\\\"enum_value\\\":\\\"male\\\"},\\\"4\\\":{\\\"enum_value\\\":\\\"female\\\"}}\",\"type\":\"1\",\"gui_description\":\"Gender - 1 or 2\",\"serial_num\":\"3\",\"refresh_time\":\"null\",\"field_name\":\"gender\",\"max_length\":\"50\"},{\"needs_verification\":\"true\",\"insert_data_to\":\"p_communityMembers\",\"user_type\":\"1\",\"fields_group\":\"0\",\"is_required\":\"true\",\"get_possible_values_from\":\"null\",\"type\":\"0\",\"gui_description\":\"Last Name\",\"serial_num\":\"2\",\"refresh_time\":\"null\",\"field_name\":\"last_name\",\"max_length\":\"50\"},{\"needs_verification\":\"true\",\"insert_data_to\":\"p_communityMembers\",\"user_type\":\"1\",\"fields_group\":\"0\",\"is_required\":\"true\",\"get_possible_values_from\":\"null\",\"type\":\"0\",\"gui_description\":\"Mobile Phone Number\",\"serial_num\":\"4\",\"refresh_time\":\"null\",\"field_name\":\"mobile_phone_number\",\"max_length\":\"50\"},{\"needs_verification\":\"true\",\"insert_data_to\":\"null\",\"user_type\":\"1\",\"fields_group\":\"1\",\"is_required\":\"true\",\"get_possible_values_from\":\"{\\\"1\\\":{\\\"org_street\\\":\\\"binyamin\\\",\\\"org_city\\\":\\\"Ramat-Gan\\\",\\\"organization_description\\\":\\\"macabi\\\",\\\"org_phone_number\\\":\\\"038574685\\\",\\\"organization_id\\\":\\\"1000\\\",\\\"email_address_of_organization\\\":\\\"mac@gmail.com\\\",\\\"org_house\\\":\\\"4\\\",\\\"organization_type_num\\\":\\\"1000\\\",\\\"fax_number\\\":\\\"034857636\\\",\\\"org_state\\\":\\\"Israel\\\",\\\"web_site\\\":\\\"mac.org.il\\\",\\\"remarks\\\":\\\"best\\\"}}\",\"type\":\"1\",\"gui_description\":\"Organization ID\",\"serial_num\":\"10\",\"refresh_time\":\"null\",\"field_name\":\"organization_id\",\"max_length\":\"50\"},{\"needs_verification\":\"true\",\"insert_data_to\":\"p_communityMembers\",\"user_type\":\"1\",\"fields_group\":\"0\",\"is_required\":\"true\",\"get_possible_values_from\":\"null\",\"type\":\"0\",\"gui_description\":\"Password\",\"serial_num\":\"5\",\"refresh_time\":\"null\",\"field_name\":\"password\",\"max_length\":\"50\"},{\"needs_verification\":\"true\",\"insert_data_to\":\"null\",\"user_type\":\"1\",\"fields_group\":\"1\",\"is_required\":\"true\",\"get_possible_values_from\":\"null\",\"type\":\"1\",\"gui_description\":\"Position Num\",\"serial_num\":\"10\",\"refresh_time\":\"null\",\"field_name\":\"position_num\",\"max_length\":\"50\"},{\"needs_verification\":\"true\",\"insert_data_to\":\"null\",\"user_type\":\"1\",\"fields_group\":\"1\",\"is_required\":\"true\",\"get_possible_values_from\":\"{\\\"1\\\":{\\\"specialization_id\\\":\\\"1000\\\",\\\"specialization_description\\\":\\\"head\\\"}}\",\"type\":\"1\",\"gui_description\":\"Specialization ID\",\"serial_num\":\"10\",\"refresh_time\":\"null\",\"field_name\":\"specialization_id\",\"max_length\":\"50\"},{\"needs_verification\":\"true\",\"insert_data_to\":\"p_communityMembers\",\"user_type\":\"1\",\"fields_group\":\"0\",\"is_required\":\"true\",\"get_possible_values_from\":\"null\",\"type\":\"0\",\"gui_description\":\"State\",\"serial_num\":\"4\",\"refresh_time\":\"null\",\"field_name\":\"state\",\"max_length\":\"50\"},{\"needs_verification\":\"true\",\"insert_data_to\":\"p_communityMembers\",\"user_type\":\"1\",\"fields_group\":\"0\",\"is_required\":\"true\",\"get_possible_values_from\":\"null\",\"type\":\"0\",\"gui_description\":\"Street\",\"serial_num\":\"4\",\"refresh_time\":\"null\",\"field_name\":\"street\",\"max_length\":\"50\"}]");
        for (int i=0; i < jsonArray.length(); i++){
            data.put(i+1, hmc.jsonToMap(jsonArray.getJSONObject(i)));
        }

        ArrayList target = new ArrayList();
        target.add("APA91bEP314fADLSbwAt6lHdRm3G9TUl5vHxjE1JJi2kRzjPUo0ODqkG8xdTkV8YQ0OIv-mP2eCRFpaykZLelWSTBBFEzdWVuMAFqcfpoKi_064mvSxq6o1sXVz1FoZQOr-Pcu8ofnC2");

        GcmCommnication_V1 gcm = new GcmCommnication_V1(data, target);
        gcm.sendResponse();
    }

}
