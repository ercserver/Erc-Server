package EmergencyModule.src.api;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by NAOR on 16/05/2015.
 */
public interface IEmerFilter_model {

    HashMap<String,String> filterUsersByMatch(HashMap<String, String> listToFilter);
    HashMap<String,String> filterUsersByArrivalTime(HashMap<Integer,HashMap<String, String>> data);

}
