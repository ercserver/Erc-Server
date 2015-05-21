package RoutineModule.src.api;

import java.util.HashMap;

/**
 * Created by NAOR on 28/04/2015.
 */
public interface IEmsRoutine_model {

    public HashMap<Integer, HashMap<String, String>> getEmsEventsByDispatcherCmid(int cmid);

}
