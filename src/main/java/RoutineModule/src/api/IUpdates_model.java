package RoutineModule.src.api;

import java.util.HashMap;

/**
 * Created by Maor on 02/05/2015.
 */
public interface IUpdates_model {
    HashMap<Integer,HashMap<String,String>> getFieldsForUpdate(HashMap<String, String> data);
}
