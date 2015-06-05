package EmergencyModule.src.api;

import java.util.HashMap;

/**
 * Created by NAOR on 23/05/2015.
 */
public interface IEmerLogger_model {
    void handleArrivalToDest(HashMap<String, String> data);

    void handleAssistantRespondsToApproach(HashMap<String, String> data);
    void handleApproveOrRejectMed(HashMap<String, String> data);

    void handleUpdatePatientStatus(HashMap<String, String> data);

    void handleHelpCalling(String eventId);

    void handleGettingCall(String eventId);

    void handleSearchingClosestEMS(String eventId);

    void handleGettingCmidOfEMS(String event_id);
}
