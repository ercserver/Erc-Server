package CommunicationModule.src.model;

import org.json.JSONArray;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;
import java.util.logging.Level;
import org.jsoup.helper.HttpConnection;

/**
 * Created by NAOR on 05/04/2015..
 */
public class InitiatedHTTPCommunication_V1 extends CommToUsers_V1 {
    Logger logger = Logger.getLogger(this.getClass().getName());

    public InitiatedHTTPCommunication_V1(HashMap<Integer,HashMap<String,String>> data, ArrayList<String> target) {
        super(data);
        targets = target;
    }

    public JSONArray sendResponse () {
        logger.log(Level.INFO, "In HTTP.sendResponse");
        Connection.Response response = null;
        //communicate the JSON file to each target URL provided
        for(int i = 0; i < targets.size(); i+=3) {
            try {

                response = Jsoup.connect(targets.get(i))
                        .data("username", targets.get(i + 1))
                        .data("password", targets.get(i + 2))
                        .data("JSONFile", objToSend.toString())
                        .ignoreContentType(true)
                        .userAgent("Mozilla")
                        .timeout(20 * 1000) // milliseconds
                        .method(Connection.Method.POST)
                        .execute();

            } catch (Exception e) {
                logger.info(e.getMessage());
            }
        }
        if (response == null){
            return new JSONArray();
        }
        logger.log(Level.INFO, "exiting HTTP.sendResponse. Response = " + response.body());

        try {
            return new JSONArray(response.body());
        }catch (Exception ex){

            ex.printStackTrace();
        }
        return new JSONArray();

    }
}
