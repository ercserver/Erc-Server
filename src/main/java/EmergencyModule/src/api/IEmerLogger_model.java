package EmergencyModule.src.api;

import java.util.HashMap;

/**
 * Created by NAOR on 23/05/2015.
 */
public interface IEmerLogger_model {
    void handleArrivalToDest(String eventId, String community_member_id);

    void handleAssistantRespondsToApproach(String eventId, String community_member_id);
    void handleApproveOrRejectMed(String eventId);

    void handleUpdatePatientStatus(String eventId, String community_member_id);

    void handleHelpCalling(String eventId);

    void handleGettingCall(String eventId);

    void handleSearchingClosestEMS(String eventId);

    void handleGettingCmidOfEMS(String event_id);

    void handleReceivingUsersArrivalTimes(String eventId);

    void handleApproachAssistants(String eventId);

    void handleRadiusUpdating(String eventId);

    void handleSendingAssistant(String event_id, String community_member_id);

    void handleNotSendingAssistant(String event_id, String community_member_id);

    void handleAskingGISFollowUser(String event_id, String community_member_id);

    void handleReceivingUserArrivalTime(String event_id, String community_member_id);

    void handleAskEMSMedicationGivving(String event_id);

    void handleTellingAssistantAboutMedicationGiving(String event_id);

    void handlePopupMessage(String eventId);

    void handleStopFollowingUsers(String eventId);

    void handleDontHaveEMS(String event_id, String community_member_id);
}
