package EmergencyModule.src.controller;

import CommunicationModule.src.api.ICommController;
import DatabaseModule.src.api.IDbController;
import EmergencyModule.src.api.IEmerController;
import EmergencyModule.src.api.IEmerFilter_model;
import Utilities.ModelsFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by NAOR on 16/05/2015.
 */
public class EmerController_V1 implements IEmerController {

    private static final String GIS_URL = null;
    private static final String EMS_URL = null;

    private IEmerFilter_model emergencyFilter = null;
    private IDbController dbController = null;
    private ICommController commController = null;

    EmerController_V1(){
        ModelsFactory models = new ModelsFactory();
        commController = models.determineCommControllerVersion();
        dbController = models.determineDbControllerVersion();
        emergencyFilter = models.determineEmerVersion();
    }

    //this methos will be called from the emergency event initiation
    @Override
    public void receiveUsersAroundLocation(HashMap<String, String> data) {
        data.remove("RequestID");
        //filter
        HashMap<String,String> filteredData = emergencyFilter.filterUsersByMatch(data);
        //prepare and send "Times" request to the GIS
        data.put("RequestID", "Times");
        //add the GIS URL to the receivers
        ArrayList<String> sendTo = new ArrayList<String>();
        sendTo.add(GIS_URL);
        //initiate request
        initiatedOneObjectRequest(data,sendTo);
    }

    //This function is called from "receiveUsersAroundLocation"
    @Override
    public void receiveUsersArrivalTimesAndApproach(HashMap<Integer, HashMap<String, String>> data) {
        //TODO - Maor // we use "filterUsersByArrivalTime" and "approachAssistants" here
    }

    @Override
    public Object assistantRespondsToApproach(HashMap<String, String> response) {
        //TODO - Maor // we use "addOrRemoveAssistant" here
        //1. If approves - we need to send him the map or something(?)
        //2. If not - it just exits "event mode" in the app
        //TODO - Maor
        return null;
    }

    @Override
    public void rejectAssistantsByEMS(HashMap<String, String> toReject) {
        toReject.remove("RequestID");
        String eventID = toReject.get("EventID");
        toReject.remove("EventID");
        //run over the list and remove every assistant ("false" indicates removal)
        for(String patientID : toReject.values()){
            addOrRemoveAssistant(patientID,eventID,false);
        }
        //TODO - Naor
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
        //TODO - Maor // we use "approveOrRejectHelper" here

    }

    @Override
    public void assistantGaveMed(HashMap<String, String> data) {
        String eventID = data.get("eventID");
        String cmid = data.get("community_member_id");
        //dbController.updateMedicideGiven(cmid,eventID); //TODO - Ohad
        data.put("RequestID", "AssistantGaveMed");
        //add the EMS URL to the receivers
        ArrayList<String> sendTo = new ArrayList<String>();
        sendTo.add(EMS_URL);
        //initiate request
        initiatedOneObjectRequest(data,sendTo);
    }

    @Override
    public void assistantCancelsArrival(HashMap<String, String> data) {
        String patientID = data.get("PatientID");
        String eventID = data.get("EventID");
        //"false" for removal
        addOrRemoveAssistant(patientID,eventID,false);
    }

    @Override
    public void updatePatientStatus(HashMap<String, String> data) {
        //TODO - Maor
    }

    @Override
    public void emsTakeover(HashMap<String, String> data) {

        if(null != data.get("Status")){
            cancelEvent(data.get("EventID"));
            return;
        }
        //if()
        //TODO - Naor
    }

    //called from EMSTakeover
    private void cancelEvent(String eventID) {
    /*
        HashMap<String,String> eventHelpers = dbController.getAllEventHelpers(eventID);//TODO-Ohad
        rejectAssistantsByEMS(eventHelpers);
        dbController.closeEvent(eventID); //TODO - Ohad
    */
        //TODO - Naor
    }

    private void cancelMessageAssistant(int patientID){
        //TODO - Naor // this PRIVATE function is called from "rejectAssistantsByEMS"
    }

    private void popUpMessage(HashMap<String,String> data){
        //TODO - Maor // we call this from "updatePatientStatus"
        //TODO - Perhaps we do that from another module? maybe the comm?
    }

    private void initiatedOneObjectRequest(HashMap<String, String> request,ArrayList<String> URLs){
        HashMap<Integer,HashMap<String,String>> dataToSend = new HashMap<Integer,HashMap<String,String>>();
        dataToSend.put(1, request);
        //initiated comm - true
        commController.setCommToUsers(dataToSend, URLs,true);
        //send request
        commController.sendResponse();
    }

    private void approachAssistants(ArrayList<String> assistantsList) {
        //TODO - Maor
        //we approach from within "receiveUsersArrivalTimesAndApproach"
    }


    private void addOrRemoveAssistant(String patientID,String eventID,boolean action) {
        //TODO - Naor
        //we call this function from "assistantRespondsToApproach" and from "rejectAssistantsByEMS"
    }

    //we call this function from "approveOrRejectMed"
    private void approveOrRejectHelper(int patientID, int eventID) {
        //TODO - Maor
        // in case of reject, do we need to tell the helper not to give the medicine
        //Perhaps we just keep him waiting?? no need to really cancel...?
        //TODO


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
