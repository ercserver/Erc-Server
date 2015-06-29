package CommunicationModule.src.model;

import Utilities.ErcLogger;
import org.json.JSONArray;
import org.jsoup.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by NAOR on 05/04/2015..
 */
public class InitiatedHTTPCommunication_V1 extends CommToUsers_V1 {
    private ErcLogger logger = new ErcLogger();

    public InitiatedHTTPCommunication_V1(HashMap<Integer,HashMap<String,String>> data, ArrayList<String> target) {
        super(data);
        targets = target;
    }

    public JSONArray sendResponse () {

        //communicate the JSON file to each target URL provided
        for(int i = 0; i < targets.size(); i+=3) {
            try {
                logger.println("In sendResponse. params(" + i + ") = " + targets.get(i));
                logger.println("In sendResponse. objToSend = " + objToSend);
                Jsoup.connect(targets.get(i))
                        .data("username", targets.get(i+1))
                        .data("password", targets.get(i+2))
                        .data("JSONFile", objToSend.toString())
                        .header("Content-Type", "Application/json")
                        .timeout(10 * 1000 * 60) // milliseconds
                        .method(Connection.Method.POST)
                        .execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
