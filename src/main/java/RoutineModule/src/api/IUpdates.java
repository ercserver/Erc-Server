package RoutineModule.src.api;

import java.util.HashMap;

/**
 * Created by Maor on 02/05/2015.
 */
public interface IUpdates {
    public Object getUpdatesFields(HashMap<String, String> data);

    public Object updateCommunicationParameters(); // Send to all users the most recent comm. params.

    public Object updateMemberDetails(HashMap<String, String> data); // Update the details in the db

    public Object handleRefreshDetails(); /* Get all the fields of all the users
                                    and check if they need a refresh. If so, send those useres a message */

    public Object handleRefreshResponse(HashMap<Integer, HashMap<String, String>> data);

    public Object updateStatus(HashMap<String, String> data);

    // TODO: Ohad
    public Object logoff(HashMap<String, String> data);

    // TODO: Shmulik
    public Object deleteMember(HashMap<String, String> data);
}
