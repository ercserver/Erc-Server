package EmergencyModule.src.api;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by NAOR on 16/05/2015.
 */
public interface IEmer_model {

    void requestUsersAroundLocation(HashMap<String,String> request);
    void requestUsersArrivalTimes(HashMap<String,String> request);
    void approachAssistants(ArrayList<String> assistantsList);
    ArrayList<String> filterUsersByMatch(ArrayList<String> listToFilter);
    ArrayList<String> filterUsersByArrivalTime(HashMap<Integer,HashMap<String,String>> data);
    void addOrRemoveAssistant(HashMap<String,String> data);
    void approveOrRejectHelper(int patientID,int eventID);

}
