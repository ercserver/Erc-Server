package EmergencyModule.src.controller;

import CommunicationModule.src.api.ICommController;
import DatabaseModule.src.api.IDbController;
import EmergencyModule.src.api.IEmerController;
import EmergencyModule.src.api.IEmer_model;
import Utilities.ModelsFactory;

import java.util.HashMap;

/**
 * Created by NAOR on 16/05/2015.
 */
public class EmerController_V1 implements IEmerController {

    private IEmer_model emergency = null;
    private IDbController dbController = null;
    private ICommController commController = null;

    EmerController_V1(){
        ModelsFactory models = new ModelsFactory();
        commController = models.determineCommControllerVersion();
        dbController = models.determineDbControllerVersion();
        emergency = models.determineEmerVersion();
    }

    @Override
    public void receiveUsersAroundLocation(HashMap<String, String> data) {
        //TODO - Naor //we call "filterUsersByMatch" and "requestUserArrivalTimes" here

    }

    //This function is called from "receiveUsersAroundLocation"
    @Override
    public void receiveUsersArrivalTimesAndApproach(HashMap<Integer, HashMap<String, String>> data) {
        //TODO - Maor // we use "filterUsersByArrivalTime" and "approachAssistants" here
    }

    @Override
    public Object assistantRespondsToApproach(HashMap<String, String> response) {
        //TODO - Maor // we use "addOrRemoveAssistant" here
        // I believe we do not need to return anything here:
        //1. If approves - we need to send him the map or something(?)
        //2. If not - it just exits "event mode" in the app
        //TODO - Maor
        return null;
    }

    @Override
    public void rejectAssistantsByEMS(HashMap<String, String> toReject) {
        //TODO - Naor // we use "addOrRemoveAssistant" here
    }

    @Override
    public void arrivalToDestination(HashMap<String, String> data) {
        //TODO - Maor
        // I think we don't need to return anything here because the app will
        // always return "Wait", as we concluded together
        //TODO - Maor
    }

    @Override
    public void approveOrRejectMed(HashMap<String, String> data) {
        //TODO - Maor
    }

    @Override
    public void assistantGaveMed(HashMap<String, String> data) {
        //TODO - Naor
    }

    @Override
    public void assistantCancelsArrival(HashMap<String, String> data) {
        //TODO - Naor
    }

    @Override
    public void updatePatientStatus(HashMap<String, String> data) {
        //TODO - Maor
    }

    @Override
    public void emsTakeover(HashMap<String, String> data) {
        //TODO - Naor
    }

    private void cancelMessageAssistant(int patientID){
        //TODO - Naor // this PRIVATE function is called from "rejectAssistantsByEMS"
    }

    private void popUpMessage(HashMap<String,String> data){
        //TODO - Maor // we call this from "updatePatientStatus"
        //TODO - Perhaps we do that from another module? maybe the comm?
    }


}
