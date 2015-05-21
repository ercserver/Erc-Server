package RoutineModule.src.api;

import java.util.HashMap;

/**
 * Created by ohad on 19/5/2015.
 */
public interface IEmsRoutine {
    public Object getEmsEventsByDispatcherCmid(HashMap<String, String> data); // Send a list of events that the dispatcher was invloved in
}
