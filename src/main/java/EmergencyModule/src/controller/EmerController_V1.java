package EmergencyModule.src.controller;

import CommunicationModule.src.api.ICommController;
import CommunicationModule.src.model.InitiatedHTTPCommunication_V1;
import DatabaseModule.src.api.IDbController;
import EmergencyModule.src.api.IEmerController;
import EmergencyModule.src.api.IEmer_model;
import Utilities.ModelsFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by NAOR on 16/05/2015.
 */
public class EmerController_V1 implements IEmerController {

    private static final String GIS_URL = null;
    private IEmer_model emergency = null;
    private IDbController dbController = null;
    private ICommController commController = null;

    EmerController_V1(){
        ModelsFactory models = new ModelsFactory();
        commController = models.determineCommControllerVersion();
        dbController = models.determineDbControllerVersion();
        emergency = models.determineEmerVersion();
    }

    //this methos will be called from the emergency event initiation
    @Override
    public void receiveUsersAroundLocation(HashMap<String, String> data) {

        //TODO - Naor //we use "filterUsersByMatch" and "requestUserArrivalTimes" here

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

    private void requestFromGIS(HashMap<Integer,HashMap<String, String>> request){
        //determine where and how to send the request
        ArrayList<String> sendTo = new ArrayList<String>();
        sendTo.add(GIS_URL);
        //initiated comm - true
        commController.setCommToUsers(request,sendTo,true);
        //send request
        commController.sendResponse();
    }

    @Override
    public void approachAssistants(ArrayList<String> assistantsList) {
        //TODO - Maor
        //we approach from within "receiveUsersArrivalTimesAndApproach"
    }


    @Override
    public void addOrRemoveAssistant(HashMap<String, String> data) {
        //TODO - Naor
        //we call this function from "assistantRespondsToApproach" and from "rejectAssistantsByEMS"
    }

    @Override
    public void approveOrRejectHelper(int patientID, int eventID) {
        //TODO - Maor //we need to tell the helper not to give the medicine
        //TODO - Perhaps we just keep him waiting?? no need to really cancel...?

        //we call this function from "approveOrRejectMed"

    }

    /*
    @Override
    public void requestUsersArrivalTimes(HashMap<Integer,HashMap<String, String>> request) {
        //TODO - Maor // we don't need this - "requestFromGIS does this
    }
    */
    /*
    @Override
    public void requestUsersAroundLocation(HashMap<Integer,HashMap<String, String>> request) {
        //TODO - Naor // we don't need this - "requestFromGIS does this
    }
  */
}
