package RoutineModule.src.api;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Maor on 02/05/2015.
 */
public interface IUpdates_model {
    HashMap<Integer,HashMap<String,String>> getFieldsForUpdate(HashMap<String, String> data);
    HashMap<String, String>  getCommunicationParameters(int cmid,String type);

    HashMap<String,String> buildBasicResponse(String message,String code);
    void updateUserDetails(int cmid,String col,String value);

    boolean FieldneedRefresh(Map.Entry<Integer, HashMap<String, String>> objs);
}
