package EmergencyModule.src.api;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by NAOR on 16/05/2015.
 */
public interface IEmerController extends IEmer {

    //void requestUsersAroundLocation(HashMap<Integer,HashMap<String, String>> request);
    //void requestUsersArrivalTimes(HashMap<Integer,HashMap<String, String>> request);
    void approachAssistants(ArrayList<String> assistantsList);
    void addOrRemoveAssistant(HashMap<String,String> data);
    void approveOrRejectHelper(int patientID,int eventID);
}
