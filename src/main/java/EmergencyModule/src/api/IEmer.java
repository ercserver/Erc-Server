package EmergencyModule.src.api;

import java.util.HashMap;

/**
 * Created by NAOR on 16/05/2015.
 */
public interface IEmer {

    //From GIS
    void receiveUsersAroundLocation(HashMap<String,String> data);
    //From GIS
    void receiveUsersArrivalTimesAndApproach(HashMap<Integer,HashMap<String,String>> data);
    //From app
    void assistantRespondsToApproach(HashMap<String, String> response);
    //From EMS
    void rejectAssistantsByEMS (HashMap<String,String> toReject);
    //From app
    void arrivalToDestination(HashMap<String,String> data);
    //From EMS
    void approveOrRejectMed(HashMap<String,String> data);
    //From app
    void assistantGaveMed (HashMap<String,String> data);
    //From app
    void assistantCancelsArrival(HashMap<String,String> data);
    //From app
    void updatePatientStatus(HashMap<String,String> data);
    //From EMS
    void emsTakeover(HashMap<String,String> data);
    //From GIS
    void receiveArrivalTime(HashMap<String,String> data);
    //From app
    void emergencyCall(HashMap<String, String> data);
    //TODO From ?
    void getCmidOfEms(HashMap<String, String> data);
    //From GIS
    void receiveClosestEmsAndApproach (HashMap<String, String> data);
}
