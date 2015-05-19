package EmergencyModule.src.controller;

import CommunicationModule.src.api.ICommController;
import DatabaseModule.src.api.IDbController;
import EmergencyModule.src.api.IEmerController;
import EmergencyModule.src.api.IEmerFilter_model;
import Utilities.AssistantFunctions;
import Utilities.ModelsFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by NAOR on 16/05/2015.
 */
public class EmerController_V1 implements IEmerController {

    private static final String GIS_URL = null;
    private static final String EMS_URL = null;

    private IEmerFilter_model emergencyFilter = null;
    private IDbController dbController = null;
    private ICommController commController = null;
    private AssistantFunctions assistentFuncs = null;

    EmerController_V1(){
        ModelsFactory models = new ModelsFactory();
        commController = models.determineCommControllerVersion();
        dbController = models.determineDbControllerVersion();
        emergencyFilter = models.determineEmerVersion();
        assistentFuncs = new AssistantFunctions();
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
    // we use "filterUsersByArrivalTime" and "approachAssistants" here
    @Override
    public void receiveUsersArrivalTimesAndApproach(HashMap<Integer, HashMap<String, String>> data) {
        //filter
        HashMap<Integer, HashMap<String, String>> filteredData = emergencyFilter.filterUsersByArrivalTime(data);
        approachAssistants(filteredData);
    }

    @Override
    public Object assistantRespondsToApproach(HashMap<String, String> response) {
        //TODO - Maor // we use "addOrRemoveAssistant" here
        //1. If approves - we need to send him the map or something(??)
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
        //TODO - will need to do here things with logs
        if (!assistentFuncs.checkCmidAndPassword(data.get("password"), Integer.parseInt(data.get("community_member_id"))))
        {
            return;
        }
        HashMap<String,String> h = new HashMap<String,String>();
        h.put("event_id", data.get("event_id"));
        HashMap<String,String> cond = new HashMap<String,String>();
        cond.put("P_CommunityMembers.community_member_id", data.get("community_member_id"));
        h.put("patient_id", dbController.getUserByParameter(cond).get("patient_id"));
        h.put("RequestID", "needConfirmMedicationGivving");
        HashMap<Integer,HashMap<String,String>> response = new HashMap<Integer,HashMap<String,String>>();
        response.put(1, h);
        commController.setCommToUsers(response, null, false);
        commController.sendResponse();
    }

    @Override
    public void approveOrRejectMed(HashMap<String, String> data) {
        //TODO - will need to do here things with logs
        HashMap<String,String> h = new HashMap<String, String>();
        h.put("event_id", data.get("event_id"));
        h.put("RequestID", data.get("RequestID"));
        String cmid = dbController.getCmidByPatientID(data.get("patient_id"));
        String regid = dbController.getRegIDsOfUser(Integer.parseInt(cmid)).get(1).get("reg_id");
        ArrayList<String> target = new ArrayList<String>();
        target.add(regid);
        HashMap<Integer,HashMap<String,String>> response = new HashMap<Integer,HashMap<String,String>>();
        response.put(1, h);
        commController.setCommToUsers(response, target, false);
        commController.sendResponse();
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
        //TODO - will need to do here things with logs
        if (!assistentFuncs.checkCmidAndPassword(data.get("password"), Integer.parseInt(data.get("community_member_id"))))
        {
            return;
        }
        //ToDo:need from DB
       // String eventId = dbController.getEventByCmid(data.get("community_member_id"));
        HashMap<String, String> response = new HashMap<String, String>();
        //response.put("event_id", eventId);
        response.put("message", data.get("message"));
        response.put("RequestID", "newInfo");
        //ToDo:need from DB
        //ArrayList<String> regIds = dbController.getHelpersRegIds(eventId);
        HashMap<Integer,HashMap<String,String>> h = new HashMap<Integer,HashMap<String,String>>();
        h.put(1, response);
        //popUpMessage(h, regIds, true);
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

    private void popUpMessage(HashMap<Integer,HashMap<String,String>> response, ArrayList<String> regIds, boolean sendToEms){
        // we call this from "updatePatientStatus"
        if(sendToEms)
        {
            if(null != regIds)
            {
                commController.setCommToUsers(response, null, false);
                commController.sendResponse();
                commController.setCommToUsers(response, regIds, false);
                commController.sendResponse();
            }
            commController.setCommToUsers(response, null, false);
            commController.sendResponse();
        }
        if(null != regIds)
        {
            commController.setCommToUsers(response, regIds, false);
            commController.sendResponse();
        }
        //TODO - Perhaps we do that from another module? maybe the comm?
    }

    private void initiatedOneObjectRequest(HashMap<String, String> request,ArrayList<String> URLs){
        HashMap<Integer,HashMap<String,String>> dataToSend = new HashMap<Integer,HashMap<String,String>>();
        dataToSend.put(1, request);
        //initiated comm - true
        //ToDo:Naor pay your atension that by this you can only comunicate with EMS of GIS
        commController.setCommToUsers(dataToSend, URLs,true);
        //send request
        commController.sendResponse();
    }

    //we approach from within "receiveUsersArrivalTimesAndApproach"
    private void approachAssistants(HashMap<Integer, HashMap<String, String>> assistantsList)
    {
        String eventId = getEventId(assistantsList);
        //ToDo:need form db
        //HashMap<String, String> eventDetails = dbController.getEventDetails(eventId);
        Iterator<HashMap<String, String>> iter = assistantsList.values().iterator();
        while(iter.hasNext())
        {
            HashMap<String, String> user = iter.next();
            if(!user.keySet().contains("subRequest"))
                continue;
            //user.put("x", eventDetails.get("x"));
            //user.put("y", eventDetails.get("y"));
            //ToDo:need to put patient's address and get in some way proper medication that the cmid has
            user.put("RequestID", "helpAssist");
            user.put("event_id", eventId);
            HashMap<Integer, HashMap<String, String>> response = new HashMap<Integer, HashMap<String, String>>();
            response.put(1, user);
            HashMap<Integer,HashMap<String,String>> regId = dbController.getRegIDsOfUser(Integer.parseInt(user.get("community_member_id")));
            ArrayList<String> target = new ArrayList<String>();
            target.add(regId.get(1).get("reg_id"));
            commController.setCommToUsers(response, target, false);
            commController.sendResponse();
        }
    }

    private String getEventId(HashMap<Integer, HashMap<String, String>> assistantsList)
    {
        Iterator<HashMap<String, String>> iter = assistantsList.values().iterator();
        while(iter.hasNext())
        {
            HashMap<String, String> user = iter.next();
            if(!user.keySet().contains("subRequest"))
                return user.get("event_id");
        }
        return null;
    }


    private void addOrRemoveAssistant(String patientID,String eventID,boolean action) {
        //TODO - Naor
        //we call this function from "assistantRespondsToApproach" and from "rejectAssistantsByEMS"
    }

    //we call this function from "approveOrRejectMed"
    /*private void approveOrRejectHelper(int patientID, int eventID) {
        // in case of reject, do we need to tell the helper not to give the medicine
        //Perhaps we just keep him waiting?? no need to really cancel...?
    }*/

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
