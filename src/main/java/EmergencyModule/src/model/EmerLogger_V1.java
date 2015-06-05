package EmergencyModule.src.model;

import DatabaseModule.src.api.IDbController;
import EmergencyModule.src.api.IEmerLogger_model;
import Utilities.ModelsFactory;

/**
 * Created by NAOR on 23/05/2015.
 */
public class EmerLogger_V1 implements IEmerLogger_model {

    private IDbController dbController = null;

    public EmerLogger_V1()
    {
        ModelsFactory models = new ModelsFactory();
        dbController = models.determineDbControllerVersion();
    }

    @Override
    public void handleArrivalToDest(String eventId, String community_member_id) {
        dbController.updateLogs(eventId, "'User " + community_member_id + " arrived to destination'");
    }

    @Override
    public void handleAssistantRespondsToApproach(String eventId, String community_member_id) {
        dbController.updateLogs(eventId, "'User with CMID " + community_member_id + " responded to the approach'");
    }

    @Override
    public void handleApproveOrRejectMed(String eventId) {
        dbController.updateLogs(eventId, "'EMS responded about medication giving'");
    }

    @Override
    public void handleUpdatePatientStatus(String eventId, String community_member_id) {
        dbController.updateLogs(eventId, "'Patient with CMID " + community_member_id +
        " updated about his status'");
    }

    @Override
    public void handleHelpCalling(String eventId) {
        dbController.updateLogs(eventId, "'Patient called for help'");
    }

    @Override
    public void handleGettingCall(String eventId) {
        dbController.updateLogs(eventId, "'Server sended to patient app call getting'");
    }

    @Override
    public void handleSearchingClosestEMS(String eventId) {
        dbController.updateLogs(eventId, "'Server asked GIS for the closest EMS'");
    }

    @Override
    public void handleGettingCmidOfEMS(String event_id) {
        dbController.updateLogs(event_id, "'EMS sended to the server his CMID'");
    }

    @Override
    public void handleReceivingUsersArrivalTimes(String eventId) {
        dbController.updateLogs(eventId, "'Server Gets from GIS users arrival times'");
    }

    @Override
    public void handleApproachAssistants(String eventId) {
        dbController.updateLogs(eventId, "'Server approach to the assistants for getting help'");
    }

    @Override
    public void handleRadiusUpdating(String eventId) {
        dbController.updateLogs(eventId, "'Server updated EMS with the radius'");
    }

    @Override
    public void handleSendingAssistant(String event_id, String community_member_id) {
        dbController.updateLogs(event_id, "'Server decided to send user " + community_member_id +
        " to the emergency event'");
    }

    @Override
    public void handleNotSendingAssistant(String event_id, String community_member_id) {
        dbController.updateLogs(event_id, "'Server decided not to send user " + community_member_id +
                " to the emergency event'");
    }

    @Override
    public void handleAskingGISFollowUser(String event_id, String community_member_id) {
        dbController.updateLogs(event_id, "'Server asked GIS to follow user " + community_member_id +
        "'");
    }

    @Override
    public void handleReceivingUserArrivalTime(String event_id, String community_member_id) {
        dbController.updateLogs(event_id, "'GIS updated arrival times of user " + community_member_id +
        " to the server'");
    }

    @Override
    public void handleAskEMSMedicationGivving(String event_id) {
        dbController.updateLogs(event_id, "'Server asked EMS for medication giving'");
    }

    @Override
    public void handleTellingAssistantAboutMedicationGiving(String event_id) {
        dbController.updateLogs(event_id, "'Server told assistant to give medication to the patint'");
    }

    @Override
    public void handlePopupMessage(String eventId) {
        dbController.updateLogs(eventId, "'Server sended to the assistants and the EMS of this event the new patient status'");
    }

    @Override
    public void handleStopFollowingUsers(String eventId) {
        dbController.updateLogs(eventId, "'Server told GIS to stop following part of the assistants'");
    }
}
