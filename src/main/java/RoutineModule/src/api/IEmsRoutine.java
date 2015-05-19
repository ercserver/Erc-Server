package RoutineModule.src.api;

/**
 * Created by ohad on 19/5/2015.
 */
public interface IEmsRoutine {
    public Object getEmsEventsByDispatcherCmid(int cmid); // Send a list of events that the dispatcher was invloved in
}
