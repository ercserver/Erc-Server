package EmergencyModule.src.api;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by NAOR on 16/05/2015.
 */
public interface IEmer_model {

    ArrayList<String> filterUsersByMatch(ArrayList<String> listToFilter);
    ArrayList<String> filterUsersByArrivalTime(HashMap<Integer,HashMap<String,String>> data);

}
