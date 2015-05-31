package EmergencyModule.src.controller;

import CommunicationModule.src.api.ICommController;
import DatabaseModule.src.api.IDbController;
import EmergencyModule.src.api.IEmerController;
import EmergencyModule.src.api.IEmerFilter_model;
import EmergencyModule.src.api.IEmerLogger_model;
import EmergencyModule.src.model.EmerFilter_V1;
import Utilities.AssistantFunctions;
import Utilities.ModelsFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by NAOR on 16/05/2015.
 */
public class EmerController_V1 implements IEmerController {

    private static final String GIS_URL = null;
    private static final String GIS_UNAME = null;
    private static final String GIS_PW = null;
    private static final String EMS_URL = null;
    private static final String EMS_UNAME = null;
    private static final String EMS_PW = null;

    private IEmerFilter_model emergencyFilter = null;
    private IEmerLogger_model emergencyLogger = null;
    private IDbController dbController = null;
    private ICommController commController = null;
    private AssistantFunctions assistantFuncs = null;

    EmerController_V1(){
        ModelsFactory models = new ModelsFactory();
        commController = models.determineCommControllerVersion();
        dbController = models.determineDbControllerVersion();
        emergencyFilter = models.determineEmerFilterVersion();
        emergencyLogger = models.determineEmerLoggerVersion();
        assistantFuncs = new AssistantFunctions();
    }

    public void emergencyCall(HashMap<String, String> data)
    {
        // Checks that this is a real user
        if (!assistantFuncs.checkCmidAndPassword(data.get("password"), Integer.parseInt(data.get("community_member_id"))))
            return;
        // Creates the event_id
        HashMap<String, String> details = new HashMap<String, String>();
        details.put("create_by_member_id", data.get("community_member_id"));
        details.put("patient_id", dbController.getPatientIDByCmid(data.get("community_member_id")));
        details.put("medical_condition_id", dbController.getMedicalConditionOfPatient(details.get("patient_id")));
        details.put("x", data.get("x"));
        details.put("y", data.get("y"));
        int event = dbController.startNewEmergencyEvent(details);
        // Updates the patient's app
        ArrayList<String> sendTo = new ArrayList<String>();
        sendTo.add(data.get("reg_id"));
        HashMap<String, String> response = new HashMap<String, String>();
        response.put("RequestID", "emergencyAccepted");
        HashMap<Integer, HashMap<String, String>> res = new HashMap<Integer, HashMap<String, String>>();
        res.put(1, response);
        commController.setCommToUsers(res, sendTo, false);
        commController.sendResponse();
        askForClosestEMS(data);
        askForUsersAroundLocation(data, Integer.toString(event),
                dbController.getMedicalConditionByNum(details.get("medical_condition_id")).get(1).get("medical_condition_description"));
    }

    //ToDo:Naor. you need to get here also the age...
    private void askForUsersAroundLocation(HashMap<String, String> data, String eventId, String medConditionMed) {
    }

    // called from emergencyCall
    private void askForClosestEMS(HashMap<String, String> data) {
        // Asks from GIS the closest EMS to the emergency event
        HashMap<String, String> response = new HashMap<String, String>();
        response.put("RequestID", "closestEMS");
        response.put("event_id", data.get("event_id"));
        response.put("x", data.get("x"));
        response.put("y", data.get("y"));
        //add the GIS URL to the receivers
        ArrayList<String> sendTo = new ArrayList<String>();
        sendTo = assistantFuncs.addReceiver("GIS", sendTo);
        //initiate request
        initiatedOneObjectRequest(response, sendTo);
    }

    public void getCmidOfEms(HashMap<String, String> data)
    {
        dbController.updateEMSOfEvent(data.get("community_member_id"), data.get("event_id"));
    }

    //this methos will be called from the emergency event initiation
    //-1 for all 0 Not responded 1 - approved 2 - rejected - 3 -  cancelled
    //ToDo:Naor, I think we should get here HashMap<Integer,HashMap<String,String> because we get a list of users...
    //TODO - Maor: we only get the plain list and one object called event_id, no real need for us to handle the complicated form... see the implementation below and respond with your opinion:
    @Override
    public void receiveUsersAroundLocation(HashMap<String,String> data) {
        data.remove("RequestID");
        //ToDo:need to update the DB about location_remark of the patient with the following method of DB:
        // public void updateLocationRemarkOfPatient(String eventId, String loc)
        //TODO - Naor:you didn't understand me. This is the location remark of the patient of the emergency event' and not of the assistants



        //pop the event id
        String eventID = data.get("event_id");
        data.remove("event_id");
        //filter
        HashMap<String,String> filteredData = emergencyFilter.filterUsersByMatch(data);
        //prepare to send a "Times" request to the GIS

        /*get all of the users to which a request was sent for the event
        and did not reject (either approved or not yet responded)*/
        //TODO - Ohad: Please just return a Hash of String,String. Also - please accept the event ID parameter as a string rather than as an int
        HashMap<String,String> allHelpersRequested = dbController.getAllAssistantsByEventId(eventID, -1);
        HashMap<String,String> notNeededHelpers = new HashMap<String,String>();
        notNeededHelpers.putAll(allHelpersRequested);
        HashMap<String,String> cancelledAndRejectedAssistants = dbController.getAllAssistantsByEventId(eventID,3);
        cancelledAndRejectedAssistants.putAll(dbController.getAllAssistantsByEventId(eventID,3));
        HashMap<String,String> approvedArrivalAssistants = dbController.getAllAssistantsByEventId(eventID,1);
        //assemble no longer required helpers
        //Remove cancelled and rejected assistants
        for(String helper : notNeededHelpers.keySet()){
            if (cancelledAndRejectedAssistants.containsKey(helper)){
                notNeededHelpers.remove(helper);
            }
        }
        //Remove assistants out of range
        for(String helper : notNeededHelpers.keySet()){
            //only keep helpers that are not needed
            if(filteredData.containsKey(helper)){
                notNeededHelpers.remove(helper);
            }
        }
        //inform the unneeded assistants of a cancellation
        notNeededHelpers.put("event_id",eventID);
        rejectAssistants(notNeededHelpers);
        notNeededHelpers.remove("event_id");
        //Update the DB of the cancelled assistants
        for(String helper : notNeededHelpers.keySet()) {
            dbController.removeAssistantFromEvent(eventID, helper);//
        }

        //reduce the list only to the unneeded assistants that are on the way
        for(String helper : notNeededHelpers.keySet()){
            if (!approvedArrivalAssistants.containsKey(helper)){
                notNeededHelpers.remove(helper);
            }
        }
        //remove the unneeded helpers from EMS and inform. "0" reperesents inform the EMS.
        for(String helper : notNeededHelpers.keySet()) {
            removeAssistant(helper, eventID, 0);
        }
        //remove those helpers from the GIS
        ArrayList<String> cmidsToStopFollow = new ArrayList<String>();
        for(String helper : notNeededHelpers.keySet()){
            cmidsToStopFollow.add(helper);
        }
        //TODO - Naor - did you mean to check empty list about the cmidsToStopFollow list?
        stopFollow(eventID, cmidsToStopFollow);

        //only stay in "filteredData" with the new helpers to approach
        //TODO - Michael: Please notice that by this logic - we do not approach a cancelled/rejected helper more than once. Is the the intention?
        for(String helper : filteredData.keySet()){
            if(allHelpersRequested.containsKey(helper)){
                filteredData.remove(helper);
            }
        }
        //Prepare the times request for the GIS with the reduced list
        filteredData.put("RequestID", "Times");
        //add the GIS URL to the receivers
        ArrayList<String> sendTo = new ArrayList<String>();
        sendTo = assistantFuncs.addReceiver("GIS", sendTo);
        //initiate request
        initiatedOneObjectRequest(filteredData,sendTo);
    }

    //This function is called from "receiveUsersAroundLocation"
    // we use "filterUsersByArrivalTime" and "approachAssistants" here
    @Override
    public void receiveUsersArrivalTimesAndApproach(HashMap<Integer, HashMap<String, String>> data) {
        //filter
        HashMap<Integer, HashMap<String, String>> filteredData = emergencyFilter.filterUsersByArrivalTime(data);
        approachAssistants(filteredData);
        // Sends to EMS the radius with that the GIS searches for assistants
        String radius = getRadius(filteredData);
        HashMap<String, String> response = new HashMap<String, String>();
        response.put("RequestID", "getRadius");
        response.put("radius", radius);
        response.put("event_id", getEventId(filteredData));
        ArrayList<String> sendTo = new ArrayList<String>();
        sendTo = assistantFuncs.addReceiver("EMS", sendTo);
        initiatedOneObjectRequest(response, sendTo);
    }

    // Searches for the map with the arguments that are not a possible assistant and returns the radius
    private String getRadius(HashMap<Integer, HashMap<String, String>> data) {
        Iterator<HashMap<String, String>> iter = data.values().iterator();
        while(iter.hasNext())
        {
            HashMap<String, String> user = iter.next();
            if(!user.keySet().contains("subRequest"))
                return user.get("radius");
        }
        return null;
    }

    // we use "addAssistant" here
    @Override
    public void assistantRespondsToApproach(HashMap<String, String> response) {
        //TODO - will need to do here things with logs
        emergencyLogger.handleAssistantRespondsToApproach(response);
        if (!assistantFuncs.checkCmidAndPassword(response.get("password"), Integer.parseInt(response.get("community_member_id"))))
            return;
        HashMap<String, String> updates = new HashMap<String, String>();
        // This assistant accepted arrival
        if(!response.get("RequestID").equals("arivalRejection")) {
            updates.put("response_type", "1");
            // This assistant will arrival by foot
            if (response.get("RequestID").equals("arivalAcceptionOnFoot"))
                updates.put("transformation_mean", "0");
            // This assistant will arrival by car
            else
                updates.put("transformation_mean", "1");
        }
        // The assistant reject arrival
        else
            updates.put("response_type", "2");

        // Updates the data base about the assistant's response
        HashMap<String, String> conds = new HashMap<String, String>();
        conds.put("event_id", response.get("event_id"));
        conds.put("community_member_id", response.get("community_member_id"));
        dbController.updateEmerFirstResponse(updates, conds);

        // Adds this assistant to EMS, and to the following emergency of GIS
        if(!response.get("RequestID").equals("arivalRejection")) {
            String eta = (response.get("RequestID").equals("arivalAcceptionOnFoot")) ?
                               response.get("eta_by_foot") : response.get("eta_by_car");
            HashMap<String, String>h = dbController.getAssistDetails(response.get("community_member_id"), response.get("event_id"));
            updateOrAddAssistantToEMS(dbController.getPatientIDByCmid(response.get("community_member_id")),
                    response.get("event_id"), eta, h.get("x"), h.get("y"));
            askGisToFollow(response.get("event_id"), response.get("community_member_id"));
        }
    }

    private void askGisToFollow(String eventId, String cmid)
    {
        HashMap<String, String> request = new HashMap<String, String>();
        request.put("community_member_id", cmid);
        request.put("event_id", eventId);
        request.put("RequestID", "followUser");
        ArrayList<String> sendTo = new ArrayList<String>();
        sendTo = assistantFuncs.addReceiver("GIS", sendTo);
        initiatedOneObjectRequest(request, sendTo);
    }

    public void receiveArrivalTime(HashMap<String,String> data)
    {
        // Updates the data base about the location and arrival times of an assistant
        dbController.updateAssistantArrivalTimesAndLocation(data);
        // Updates the EMS
        HashMap<String,String> assistDetails = dbController.getAssistDetails(data.get("community_member_id"), data.get("event_id"));
        String eta = (assistDetails.get("transformation_mean").equals("0")) ?
                data.get("eta_by_foot") : data.get("eta_by_car");
        updateOrAddAssistantToEMS(dbController.getPatientIDByCmid(data.get("community_member_id")),
                data.get("event_id"), eta, data.get("x"), data.get("y"));
    }

    //This function will be called by "receiveArrivalTime" - for updates and by
    // "assistantRespondsToApproach" - for addition.
    //Division to different cases logic is done on the EMS side:
    // If they don't have that patient in
    //that event - it's an addition. If they do - it's an update.
    private void updateOrAddAssistantToEMS(String patientId, String eventId, String eta, String xCoord, String yCoord) {
        //generate
        HashMap<String,String> updateOrAddToEms = new HashMap<String,String>();
        updateOrAddToEms.put("RequestID", "updateOrAddAssistant");
        updateOrAddToEms.put("patient_id",patientId);
        updateOrAddToEms.put("event_id",eventId);
        updateOrAddToEms.put("eta",eta);
        updateOrAddToEms.put("x",xCoord);
        updateOrAddToEms.put("y", yCoord);
        //send
        ArrayList<String> sendTo = new ArrayList<String>();
        sendTo = assistantFuncs.addReceiver("EMS", sendTo);
        initiatedOneObjectRequest(updateOrAddToEms, sendTo);
    }

    //Inserts assistant to the data base
    private void insertAssistantToDB(HashMap<String, String> user, String x, String y, String eventId, String date)
    {
        HashMap<String, String> insert = new HashMap<String, String>();
        insert.put("x", x);
        insert.put("y", y);
        insert.put("event_id", eventId);
        insert.put("community_member_id", user.get("community_member_id"));
        insert.put("eta_by_foot", user.get("eta_by_foot"));
        insert.put("eta_by_car", user.get("eta_by_car"));
        insert.put("created_date", date);
        insert.put("prescription_num", dbController.getPrescNum(user.get("community_member_id")));
        dbController.insertAssistant(insert);
    }

    @Override
    public void rejectAssistantsByEMS(HashMap<String, String> toReject) {
        if (assistantFuncs.checkCmidAndPassword(toReject.get("password"), Integer.parseInt(toReject.get("community_member_id")))) {
            rejectAssistants(toReject);
        }
    }
    private void rejectAssistants(HashMap<String, String> toReject){
        toReject.remove("RequestID");
        String eventID = toReject.get("EventID");
        toReject.remove("EventID");
        //remove every assistant from the database and inform. "1" to inform the assistants here.
        for(String patientID : toReject.values()){
            //Update the assistant's status on the DB and and inform. "1" to inform helpers here.
            dbController.removeAssistantFromEvent(eventID, patientID);//
            removeAssistant(patientID,eventID, 1);
        }
    }

    //we call this function from "rejectAssistantsByEMS"
    private void removeAssistant(String patientId, String eventId, int inform) {

        //TODO - Logs
        //emergencyLogger.handleAssistantRemovalFromEvent(eventId,patientId)

        HashMap<String,String> data = new HashMap<String,String>();
        data.put("RequestID", "cancelAssist");
        data.put("event_id", eventId);
        //Notify the EMS of the removal
        if(0 == inform){
            data.put("patient_id", patientId);
            ArrayList<String> sendTo = new ArrayList<String>();
            sendTo = assistantFuncs.addReceiver("EMS", sendTo);
            initiatedOneObjectRequest(data, sendTo);
        }
        //Notify the assistant of the removal - GCM
        else{
            //get the regID of the user to be aborted
            String regId = dbController.getRegIDOfPatient(patientId);
            HashMap<Integer,HashMap<String,String>> request = new HashMap<Integer,HashMap<String,String>>();
            request.put(1, data);
            ArrayList<String> sendTo = new ArrayList<String>();
            sendTo.add(regId);
            commController.setCommToUsers(request, sendTo, false);
        }
    }

    // Assistant arrival to the destination in emergency event
    @Override
    public void arrivalToDestination(HashMap<String, String> data) {
        //TODO - will need to do here things with logs
        emergencyLogger.handleArrivalToDest(data);
        if (!assistantFuncs.checkCmidAndPassword(data.get("password"), Integer.parseInt(data.get("community_member_id"))))
        {
            return;
        }
        // Updates the data base
        dbController.updateArrivalDate(data);
        // Sends the news to EMS and asks for givving the medication
        HashMap<String,String> h = new HashMap<String,String>();
        h.put("event_id", data.get("event_id"));
        h.put("patient_id", dbController.getPatientIDByCmid(data.get("community_member_id")));
        h.put("RequestID", "needConfirmMedicationGivving");
        ArrayList<String> sendTo = new ArrayList<String>();
        sendTo = assistantFuncs.addReceiver("EMS", sendTo);
        initiatedOneObjectRequest(h, sendTo);
    }

    @Override
    public void approveOrRejectMed(HashMap<String, String> data) {
        if (!assistantFuncs.checkCmidAndPassword(data.get("password"), Integer.parseInt(data.get("community_member_id"))))
            return;
        //TODO - will need to do here things with logs
        emergencyLogger.handleApproveOrRejectMed(data);
        HashMap<String, String> h = new HashMap<String, String>();
        h.put("event_id", data.get("event_id"));
        h.put("RequestID", data.get("RequestID"));
        String cmid = dbController.getCmidByPatientID(data.get("patient_id"));
        // Ems accept medication givving-updates the data base
        if (data.get("RequestID").equals("confirmMedication")) {
            dbController.updateActivationDate(cmid, data.get("event_id"));
            dbController.insertMedicationUse(cmid, data.get("event_id"), data.get("community_member_id"),
                    dbController.getMedicationOfPatient(cmid));
        }
        // Ems reject medication givving-updates the data base
        else
            dbController.updateResult(cmid, data.get("event_id"), "'EMS rejected medication givving'");
        // Sends EMS response to the assistant
        String regid = dbController.getRegIDsOfUser(Integer.parseInt(cmid)).get(1).get("reg_id");
        ArrayList<String> target = new ArrayList<String>();
        target.add(regid);
        HashMap<Integer,HashMap<String,String>> response = new HashMap<Integer, HashMap<String, String>>();
        response.put(1, h);
        commController.setCommToUsers(response, target, false);
        commController.sendResponse();
    }

    @Override
    public void assistantGaveMed(HashMap<String, String> data) {
        String eventID = data.get("event_id");
        String cmid = data.get("community_member_id");
        //ToDo:Naor: the assistant can't know the cmid of the approver, and also you don't really need this
        String approverID = data.get("approver_id");
        String medNum = null;
        /* Why do we need "medNum" here? it can be inferred from the DB. Don't forget
        this method is intiated by the app - we can't get the med num here...
        We can also consider holding a table of approvals in the DB (If we don't already own such)
         */
        //TODO - Naor:you are using the wrong method!(maby the method's name is confusing) you just need to call to method that updates the date of giving:method that Ohad need to implement
        dbController.insertMedicationUse(cmid, eventID, approverID, medNum);
        //dbController.updateMedicineGiven(cmid,eventID); //TODO - Ohad
        data.put("RequestID", "AssistantGaveMed");
        //add the EMS URL to the receivers
        ArrayList<String> sendTo = new ArrayList<String>();
        sendTo = assistantFuncs.addReceiver("EMS", sendTo);
        //initiate request
        initiatedOneObjectRequest(data, sendTo);
    }

    @Override
    public void assistantCancelsArrival(HashMap<String, String> data) {
        String patientID = dbController.getPatientIDByCmid(data.get("community_member_id"));
        String eventID = data.get("event_id");
        //Update the assistant's status on the DB and inform. "0" to inform EMS here.
        dbController.removeAssistantFromEvent(eventID, patientID);//
        removeAssistant(patientID, eventID, 0);
    }

    @Override
    public void updatePatientStatus(HashMap<String, String> data) {
        //TODO - will need to do here things with logs
        emergencyLogger.handleUpdatePatientStatus(data);
        if (!assistantFuncs.checkCmidAndPassword(data.get("password"), Integer.parseInt(data.get("community_member_id"))))
        {
            return;
        }
        String eventId = dbController.getEventByCmid(data.get("community_member_id"));
        dbController.updatePatientRemarks(data.get("community_member_id"), eventId, data.get("message"));
        // Sends the patient's message to EMS and the assistents
        HashMap<String, String> response = new HashMap<String, String>();
        response.put("event_id", eventId);
        response.put("message", data.get("message"));
        response.put("RequestID", "newInfo");
        ArrayList<String> regIds = dbController.getHelpersRegIds(eventId);
        HashMap<Integer,HashMap<String,String>> h = new HashMap<Integer, HashMap<String,String>>();
        h.put(1, response);
        popUpMessage(h, regIds, true);
    }

    @Override
    public void emsTakeover(HashMap<String, String> data) {

        if(null != data.get("status")){
            cancelEvent(data.get("event_id"));
            return;
        }
        //If the EMS wants to change the radius - ask for locations for GIS with the new radius
        //The Logic of the receiving of locations should handle the
        // addition/removal of users from the EMS and DB
        if(null != data.get("radius")){
            //generate request
            HashMap<String,String>  request = new HashMap<String,String>();
            request.put("radius",data.get("radius"));
            request.put("event_id",data.get("event_id"));
            request.put("RequestID","Locations");
            //send request to GIS
            ArrayList<String> sendTo = new ArrayList<String>();
            sendTo = assistantFuncs.addReceiver("GIS",sendTo);
            //initiate request
            initiatedOneObjectRequest(request, sendTo);
        }


        /* TODO - Not in prototype I think
        if(null != data.get("Dosage")) {

        }
        */
    }


    //called from EMSTakeover
    private void cancelEvent(String eventID) {
        //get all helpers and cancel them
        //TODO - This error is explained before in the fucntion "receiveArrivalTimes"
        HashMap<String,String> eventHelpers = dbController.getAllAssistantsByEventId(eventID,-1);//
        rejectAssistants(eventHelpers);
        //close the event within the GIS
        cancelEventOnGIS(eventID);
        //close the event within the DB
        //TODO - Ohad: I am not sure why we need another paramter in this function. Please explain Also, please receive the EventID param as a string.
        dbController.closeEvent(eventID);

        //TODO - Logs
        //emergencyLogger.closeEventByEMS(eventID);
    }

    private void cancelEventOnGIS(String eventID) {
        //generate request
        HashMap<String,String> request = new HashMap<String,String>();
        request.put("RequestID", "cancelEvent");
        request.put("event_id",eventID);
        //send to GIS
        ArrayList<String> sendTo = new ArrayList<String>();
        sendTo = assistantFuncs.addReceiver("GIS", sendTo);
        initiatedOneObjectRequest(request, sendTo);
    }


    private void popUpMessage(HashMap<Integer,HashMap<String,String>> response, ArrayList<String> regIds, boolean sendToEms){
        // we call this from "updatePatientStatus"
        if(sendToEms)
        {
            ArrayList<String> sendTo = new ArrayList<String>();
            sendTo = assistantFuncs.addReceiver("EMS", sendTo);
            initiatedOneObjectRequest(response.get(1), sendTo);
            // Sends message to apps and to EMS
            if(null != regIds)
            {
                commController.setCommToUsers(response, regIds, false);
                commController.sendResponse();
                return;
            }
        }
        // Sends message only to apps
        if(null != regIds)
        {
            commController.setCommToUsers(response, regIds, false);
            commController.sendResponse();
        }
    }

    private void initiatedOneObjectRequest(HashMap<String, String> request,ArrayList<String> URLs){
        HashMap<Integer,HashMap<String,String>> dataToSend = new HashMap<Integer,HashMap<String,String>>();
        dataToSend.put(1, request);
        //initiated comm - true
        commController.setCommToUsers(dataToSend, URLs,true);
        //send request
        commController.sendResponse();
    }



    //we approach from within "receiveUsersArrivalTimesAndApproach"
    private void approachAssistants(HashMap<Integer, HashMap<String, String>> assistantsList)
    {
        String eventId = getEventId(assistantsList);
        HashMap<String, String> eventDetails = dbController.getEventDetails(eventId);
        Iterator<HashMap<String, String>> iter = assistantsList.values().iterator();
        while(iter.hasNext())
        {
            //possible assistant
            HashMap<String, String> user = iter.next();
            //This is the map of the other arguments from GIS
            if(!user.keySet().contains("subRequest"))
                continue;
            //Assistant location
            String x = user.get("x");
            String y = user.get("y");
            user.remove("x");
            user.remove("y");
            // Inserts the assistant to the data base
            insertAssistantToDB(user, x, y, eventId, eventDetails.get("created_date"));
            HashMap<String, String> medicalDetForPresenting = dbController.getMedicalDetailsForPresenting(user.get("community_member_id"));
            user.put("medication_name", medicalDetForPresenting.get("medication_name"));
            user.put("dosage", medicalDetForPresenting.get("dosage"));
            user.put("x", eventDetails.get("x"));
            user.put("y", eventDetails.get("y"));
            user.put("location_remark", eventDetails.get("location_remark"));
            user.put("RequestID", "helpAssist");
            user.put("event_id", eventId);
            // Sends the approach to the assistant
            HashMap<Integer, HashMap<String, String>> response = new HashMap<Integer, HashMap<String, String>>();
            response.put(1, user);
            HashMap<Integer,HashMap<String,String>> regId = dbController.getRegIDsOfUser(Integer.parseInt(user.get("community_member_id")));
            ArrayList<String> target = new ArrayList<String>();
            target.add(regId.get(1).get("reg_id"));
            commController.setCommToUsers(response, target, false);
            commController.sendResponse();
        }
    }


    // Searches for the map with the arguments that are not a possible assistant and returns the eventID
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

    // Gets eventId and list of users, and tells to GIS to stop following these users
    private void stopFollow(String eventId, ArrayList<String> cmids)
    {
        // Checks that we have user that we realy need to stop following
        if(cmids.isEmpty())
            return;
        HashMap<String, String> req = new HashMap<String, String>();
        req.put("RequestID", "stopFollow");
        req.put("event_id", eventId);
        HashMap<Integer, HashMap<String, String>> response = new  HashMap<Integer, HashMap<String, String>>();
        for(int i = 0; i < cmids.size(); i++)
        {
            HashMap<String, String> user = new HashMap<String, String>();
            user.put("community_member_id", cmids.get(i));
            user.put("subRequest", "cmid");
            response.put(i + 1, user);
        }
        response.put(cmids.size() + 1, req);
        ArrayList<String> sendTo = new ArrayList<String>();
        sendTo = assistantFuncs.addReceiver("GIS",sendTo);
        commController.setCommToUsers(response, sendTo, true);
        //send request
        commController.sendResponse();
    }


    //we call this function from "approveOrRejectMed"
    /*private void approveOrRejectHelper(int patientID, int eventID) {
        // in case of reject, we just need to tell the helper not to give the medicine and stay put
        //We allow the EMS to change their mind, but this logic is handled over their side using the instructions below
    }*/
    //EMS Needs to know after first time someone said he arrived - they should allow the Mokdan
    //to change his mind after rejecting. They should keep a boolean field in the table indicating "arrival"
}
