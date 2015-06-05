package EmergencyModule.src.model;

import DatabaseModule.src.api.IDbController;
import EmergencyModule.src.api.IEmerLogger_model;
import Utilities.ModelsFactory;

import java.util.HashMap;

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
    public void handleArrivalToDest(HashMap<String, String> data) {
        //TODO - Michael
    }

    @Override
    public void handleAssistantRespondsToApproach(HashMap<String, String> data) {
        //TODO - Michael
    }

    @Override
    public void handleApproveOrRejectMed(HashMap<String, String> data) {
        //TODO - Michael
    }

    @Override
    public void handleUpdatePatientStatus(HashMap<String, String> data) {
        //TODO - Michael
    }

    @Override
    public void handleHelpCalling(String eventId) {
        dbController.updateLogs(eventId, "'Patient called for help'");
    }

    @Override
    public void handleGettingCall(String eventId) {
        dbController.updateLogs(eventId, "'Server sended to patient's app call getting'");
    }

    @Override
    public void handleSearchingClosestEMS(String eventId) {
        dbController.updateLogs(eventId, "'Server asked GIS for the closest EMS'");
    }

    @Override
    public void handleGettingCmidOfEMS(String event_id) {
        dbController.updateLogs(event_id, "'EMS sended to the server his CMID'");
    }
}
