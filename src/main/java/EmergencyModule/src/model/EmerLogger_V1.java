package EmergencyModule.src.model;

import EmergencyModule.src.api.IEmerLogger_model;

import java.util.HashMap;

/**
 * Created by NAOR on 23/05/2015.
 */
public class EmerLogger_V1 implements IEmerLogger_model {
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
}
