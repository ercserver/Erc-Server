package EmergencyModule.src.controller;

import CommunicationModule.src.api.ICommController;
import DatabaseModule.src.api.IDbController;
import EmergencyModule.src.api.IEmerController;
import EmergencyModule.src.api.IEmerFilter_model;
import EmergencyModule.src.api.IEmerLogger_model;
import Utilities.AssistantFunctions;
import Utilities.HashMapBuilder;
import Utilities.ModelsFactory;
import Utilities.ParametersVerifier;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Created by NAOR on 16/05/2015.
 */
public class EmerController_V1 implements IEmerController {



    private IEmerFilter_model emergencyFilter = null;
    private IEmerLogger_model emergencyLogger = null;
    private IDbController dbController = null;
    private ICommController commController = null;
    private AssistantFunctions assistantFuncs = null;

    Logger logger = Logger.getLogger(this.getClass().getName());

    public EmerController_V1(){
        ModelsFactory models = new ModelsFactory();
        commController = models.determineCommControllerVersion();
        dbController = models.determineDbControllerVersion();
        emergencyFilter = models.determineEmerFilterVersion();
        emergencyLogger = models.determineEmerLoggerVersion();
        assistantFuncs = new AssistantFunctions();
    }
//
    public void emergencyCall(HashMap<String, String> data)
    {
        logger.log(Level.INFO, "In emergencyCall");
        ParametersVerifier verifier = new ParametersVerifier(data);
        if (!verifier.verify("community_member_id", "medical_condition_id", "x", "y", "reg_id")){
            return;
        }

        // Checks that this is a real user
        if (!assistantFuncs.checkCmidAndPassword(data.get("password"), Integer.parseInt(data.get("community_member_id"))))
            return;
        // Creates the event_id
        HashMap<String, String> details = new HashMap<String, String>() ;

        details.put("create_by_member_id", data.get("community_member_id"));
        details.put("patient_id", dbController.getPatientIDByCmid(data.get("community_member_id")));
        details.put("medical_condition_id", data.get("medical_condition_id"));
        details.put("x", data.get("x"));
        details.put("y", data.get("y"));

        int event = dbController.startNewEmergencyEvent(details);

        emergencyLogger.handleHelpCalling(Integer.toString(event));
        //Updates the patient's app
        ArrayList<String> sendTo = new ArrayList<String>();
        sendTo.add(data.get("reg_id"));
        HashMap<String, String> response = new HashMap<String, String>();
        response.put("RequestID", "emergencyAccepted");
        HashMap<Integer, HashMap<String, String>> res = new HashMap<Integer, HashMap<String, String>>();
        res.put(1, response);
        commController.setCommToUsers(res, sendTo, false);
        commController.sendResponse();
        emergencyLogger.handleGettingCall(Integer.toString(event));
        data.put("event_id", Integer.toString(event));
        logger.info("Before closest EMS");
        askForClosestEMS(data);
        emergencyLogger.handleSearchingClosestEMS(Integer.toString(event));
        data.put("medical_condition_description",
                dbController.getMedicalConditionByNum(details.get("medical_condition_id")).get(1).get("medical_condition_description"));
        askForUsersAroundLocation(data);
        logger.log(Level.INFO, "Exiting emergencyCall");
    }
//
    private void askForUsersAroundLocation(HashMap<String, String> data)
    {
        String age = turnBirthDateIntoAge(dbController.getBirthDate(data.get("community_member_id")));
        data.put("age",age);
        data.put("RequestID","AroundLocation");
        //add the GIS URL to the receivers
        ArrayList<String> sendTo = new ArrayList<String>();
        sendTo = assistantFuncs.addReceiver("GIS", sendTo);
        //initiate request
        initiatedOneObjectRequest(data, sendTo);
    }

    private String turnBirthDateIntoAge(String stringedBirth) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            //parse and get birth date
            long birth = format.parse(stringedBirth).getTime();
            //get current date
            long now = new java.util.Date().getTime();
            //Subtract and return stringed years
            int yearsBetween = (int)(((now-birth)/(1000*60*60*24))/365);
            return Integer.toString(yearsBetween);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    //called from emergencyCall
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
    public void receiveClosestEmsAndApproach(HashMap<String, String> data){
        //generate required data and approach the EMS
        HashMap<String,String> eventDetails = dbController.getEventDetails(data.get("event_id"));
        data.put("event_id", eventDetails.get("event_id"));
        data.put("location_remark",eventDetails.get("location_remark"));
        data.put("x", eventDetails.get("x"));
        data.put("y", eventDetails.get("y"));
        String cmid = dbController.getCmidByPatientID(eventDetails.get("patient_id"));
        HashMap<String,String> dbRequest = new HashMap<String,String>();
        dbRequest.put("P_CommunityMembers.community_member_id",cmid);
        HashMap<String,String> userDetails = dbController.getUserByParameter(dbRequest);
        data.put("full_name", userDetails.get("first_name") + " " + userDetails.get("last_name"));
        data.put("age",userDetails.get("age"));
        data.put("gender",userDetails.get("gender"));
        data.put("external_id",userDetails.get("external_id"));
        data.put("external_id_type",userDetails.get("external_id_type"));
        data.put("home_phone_number",userDetails.get("home_phone_number"));
        data.put("mobile_phone_number",userDetails.get("mobile_phone_number"));
        HashMap<String,String> medicationDetails = dbController.getMedicationNameAndDosage(eventDetails.get("prescription_num"));
        data.put("medication_name",medicationDetails.get("medication_name"));
        data.put("dosage",medicationDetails.get("dosage"));
        //add the required command to the request
        data.put("RequestID", "start");
        //add the EMS URL to the receivers
        ArrayList<String> sendTo = new ArrayList<String>();
        sendTo = assistantFuncs.addReceiver("EMS", sendTo);
        //initiate request
        initiatedOneObjectRequest(data, sendTo);
        emergencyLogger.handleReceivalOfClosestEms(eventDetails.get("event_id"));
    }

    public void getCmidOfEms(HashMap<String, String> data)
    {
        logger.info("entered getCmidOfEms");
        if (!assistantFuncs.checkCmidAndPassword(data.get("password"), Integer.parseInt(data.get("community_member_id"))))
            return;
        dbController.updateEMSOfEvent(data.get("community_member_id"), data.get("event_id"));
        emergencyLogger.handleGettingCmidOfEMS(data.get("event_id"));
        logger.info("exit getCmidOfEms");
    }

    //this method will be called from the emergency event initiation
    //-1 for all 0 Not responded 1 - approved 2 - rejected - 3 -  cancelled
    @Override
    public void receiveUsersAroundLocation(HashMap<String,String> data) {
        logger.log(Level.INFO, "data = " + data);
        data.remove("RequestID");
        //pop the event data
        String state = data.get("state");
        String location_remark = data.get("location_remark");
        String region_type = data.get("region_type");
        String radius = data.get("radius");
        String eventID = data.get("event_id");
//
        int eventIDInted = Integer.parseInt(eventID);
        data.remove("event_id");
        data.remove("state");
        data.remove("region_type");
        data.remove("radius");
        data.remove("location_remark");
        data.remove("x");
        data.remove("y");
        dbController.updateEventDetails(eventID, state, region_type, radius, location_remark);
        //add the radius to the EMS
        boolean isEmsInEvent = (!(dbController.getEventDetails(eventID)).get("ems_member_id").equals("null"));
        logger.info("ems_member_id != null : " + isEmsInEvent);
       //TODO: Check with the EMS if they support those lines
       /* if (isEmsInEvent) {
            updateRadiusToEMS(radius, eventID);
        }*/
        //filter

        HashMap<String,String> filteredData = emergencyFilter.filterUsersByMatch(data,eventID,region_type,radius);
        logger.info("After filter");
        /*get all of the users to which a request was sent for the event
        and did not reject or cancelled (either approved or not yet responded)*/
        HashMap<String,String> allHelpersRequested = turnIntListIntoHashMap(dbController.getAllAssistantsByEventId(eventIDInted, -1));
        //if this list is not empty - we need to handle cancellations and filter the users to be approached
        //*Note that this necessarily suggests that the radius was changed by the EMS, but the opposite is not necessarily true
        //(Radius change does not necessarily mean that this list will be non-empty)
        if(!allHelpersRequested.isEmpty()){
            filteredData = handleRadiusChange(allHelpersRequested,filteredData,eventID,isEmsInEvent);
        }
        //Prepare the times request for the GIS with the reduced list
        filteredData.put("RequestID", "Times");
        filteredData.put("event_id",eventID);
        //add the GIS URL to the receivers
        ArrayList<String> sendTo = new ArrayList<String>();
        sendTo = assistantFuncs.addReceiver("GIS", sendTo);
        //initiate request
        initiatedOneObjectRequest(filteredData, sendTo);
    }

    private HashMap<String, String> handleRadiusChange(HashMap<String, String> allHelpersRequested, HashMap<String, String> filteredData,String eventID,boolean isEmsInEvent) {
        int eventIDInted = Integer.parseInt(eventID);
        HashMap<String,String> notNeededHelpers = new HashMap<String,String>();
        notNeededHelpers.putAll(allHelpersRequested);
        HashMap<String,String> cancelledAndRejectedAssistants = turnIntListIntoHashMap(dbController.getAllAssistantsByEventId(eventIDInted, 3));
        HashMap<String,String> approvedArrivalAssistants = turnIntListIntoHashMap(dbController.getAllAssistantsByEventId(eventIDInted, 1));
        //assemble no longer required helpers
        //Remove cancelled and rejected assistants
        for(String helper : notNeededHelpers.keySet()){
            if (cancelledAndRejectedAssistants.containsKey(helper)){
                notNeededHelpers.remove(helper);
            }
        }
        //Isolate the list of undeeded assistants to assistants that were previously
        //requested to help but are no longer needed
        for(String helper : notNeededHelpers.keySet()){
            //only keep helpers that are not needed
            if(filteredData.containsKey(helper)){
                notNeededHelpers.remove(helper);
            }
        }
        //inform these no longer needed assistants of the cancellation
        rejectAssistants(notNeededHelpers,eventID,"Assistance is no longer required.");
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
        //handle cases that the EMS is involved
        if (isEmsInEvent) {
            //remove the unneeded helpers from EMS and inform. "0" represents 'inform the EMS'.
            for (String helper : notNeededHelpers.keySet()) {
                removeAssistant(helper, eventID, 0, "Assistance is no longer required.");
            }
        }
        //remove those helpers from the GIS
        ArrayList<String> cmidsToStopFollow = new ArrayList<String>();
        for(String helper : notNeededHelpers.keySet()){
            cmidsToStopFollow.add(helper);
        }
        stopFollow(eventID, cmidsToStopFollow);

        //only stay in "filteredData" with the new helpers to approach
        //we do not approach a cancelled/rejected helper more than once. Approved by Michael
        for(String helper : filteredData.keySet()){
            if(allHelpersRequested.containsKey(helper)){
                filteredData.remove(helper);
            }
        }

        return filteredData;
    }

    private void updateRadiusToEMS(String radius,String eventID) {
        logger.info("radius = " + radius);
        HashMap<String,String> data = new HashMap<>();
        data.put("event_id",eventID);
        data.put("radius",radius);
        data.put("RequestID","SetEventSearchRadius");
        ArrayList<String> sendTo = new ArrayList<String>();
        sendTo = assistantFuncs.addReceiver("EMS", sendTo);
        initiatedOneObjectRequest(data, sendTo);
    }

    //This function is called from "receiveUsersAroundLocation"
    // we use "filterUsersByArrivalTime" and "approachAssistants" here
    @Override
    public void receiveUsersArrivalTimesAndApproach(HashMap<Integer, HashMap<String, String>> data) {
        logger.log(Level.INFO, "in receiveUsersArrivalTimesAndApproach. data = " + data);
        String eventId = getEventId(data);
        emergencyLogger.handleReceivingUsersArrivalTimes(eventId);
        //filter
        HashMap<Integer, HashMap<String, String>> filteredData = emergencyFilter.filterUsersByArrivalTime(data);
        approachAssistants(filteredData);
        emergencyLogger.handleApproachAssistants(eventId);
        // Sends to EMS the radius with that the GIS searches for assistants
        String radius = getRadius(filteredData);
        HashMap<String, String> response = new HashMap<String, String>();
        response.put("RequestID", "getRadius");
        response.put("radius", radius);
        response.put("event_id", eventId);
        ArrayList<String> sendTo = new ArrayList<String>();
        sendTo = assistantFuncs.addReceiver("EMS", sendTo);
        initiatedOneObjectRequest(response, sendTo);
        emergencyLogger.handleRadiusUpdating(eventId);
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
        if (!assistantFuncs.checkCmidAndPassword(response.get("password"), Integer.parseInt(response.get("community_member_id"))))
            return;
        emergencyLogger.handleAssistantRespondsToApproach(response.get("event_id"), response.get("community_member_id"));
        //Not in prototype
        String EMSArrivalTime = null;
        // How much time will take this assistant
        String eta = null;
        // Tells if we want to send this assistant or not
        boolean send = false;
        HashMap<String, String> updates = new HashMap<String, String>();
        // This assistant accepted arrival
        if(!response.get("RequestID").equals("arrivalRejection")) {
            eta = (response.get("RequestID").equals("arrivalAcceptionOnFoot")) ?
                    response.get("eta_by_foot") : response.get("eta_by_car");
            if (EMSArrivalTime != null) {
            }
            HashMap<String, String> res = new HashMap<String, String>();
            // The assistants that are going to the event now and their proper arrival times
            HashMap<Integer, HashMap<String, String>> relevantAssistants = dbController.getGoingAssistantsAndTimes(response.get("event_id"));
            // Tells how much assistants we want in this state
            int howManyToSend = dbController.getHowManySendToEvent("Israel");
            // We want to send this assistant-Sends proper message to app
            if (toSend(relevantAssistants, howManyToSend, eta)) {
                updates.put("response_type", "1");
                send = true;
                res.put("RequestID", "go");
                String message = "Thank you for your respond! You can go to the patient at risk!";
                res.put("message", message);
            }
            // We don't want to send this assistant-Sends proper message to app
            else
            {
                updates.put("response_type", "4");
                res.put("RequestID", "noNeed");
                String message = "We have enough assistants that closer than you and confirmed their arrival.";
                message += "\n Thank you anyway and have a good day!";
                res.put("message", message);
            }
            HashMap<Integer, HashMap<String, String>> re = new HashMap<Integer, HashMap<String, String>>();
            re.put(1, res);
            ArrayList<String> target = new ArrayList<String>();
            if (null != response.get("reg_id")){
                target.add(response.get("reg_id"));
                commController.setCommToUsers(re, target, false);
                commController.sendResponse();
            }
            if(send)
                emergencyLogger.handleSendingAssistant(response.get("event_id"), response.get("community_member_id"));
            else
                emergencyLogger.handleNotSendingAssistant(response.get("event_id"), response.get("community_member_id"));
            // This assistant accepted arrival by foot
            if (response.get("RequestID").equals("arrivalAcceptionOnFoot"))
                updates.put("transformation_mean", "0");
            // This assistant accepted arrival by car
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
        if(send) {
            HashMap<String, String> assistantDetails = dbController.getAssistDetails(response.get("community_member_id"), response.get("event_id"));
            //TODO - FOR DEBUG PURPOSES, WE NEED TO REMOVE THIS IF
            if (null == assistantDetails){
                assistantDetails = new HashMap<String,String>();
                assistantDetails.put("location_remark","SomeLocation");
            }
            updateOrAddAssistantToEMS(dbController.getPatientIDByCmid(response.get("community_member_id")),
                    response.get("event_id"), eta, assistantDetails.get("location_remark"), response.get("x"), response.get("y"));
            askGisToFollow(response.get("event_id"), response.get("community_member_id"));
            emergencyLogger.handleAskingGISFollowUser(response.get("event_id"), response.get("community_member_id"));
        }
    }

    // Tells if we want to send an assistant to emergency event by his proper arrival time
    private boolean toSend(HashMap<Integer, HashMap<String, String>> relevantAssistants, int howManyToSend, String eta)
    {
        // We didn't get a full stack yet-sends the assistant
        if(relevantAssistants.size() < howManyToSend)
            return true;
        double dEta = Double.parseDouble(eta);
        // Counts how many actives assistant are better by their arrival times
        int betterFromAssist = 0;
        for(int i = 1; i <= relevantAssistants.size(); i++)
            if(Double.parseDouble(relevantAssistants.get(i).get("eta")) <= dEta)
                betterFromAssist++;
        return betterFromAssist < howManyToSend;
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
        logger.log(Level.INFO, "in receiveArrivalTime. data = " + data);
        emergencyLogger.handleReceivingUserArrivalTime(data.get("event_id"), data.get("community_member_id"));
        // Updates the data base about the location and arrival times of an assistant
        dbController.updateAssistantArrivalTimesAndLocation(data);
        // Updates the EMS
        HashMap<String,String> assistDetails = dbController.getAssistDetails(data.get("community_member_id"), data.get("event_id"));
        String eta = (assistDetails.get("transformation_mean").equals("0")) ?
                data.get("eta_by_foot") : data.get("eta_by_car");
        updateOrAddAssistantToEMS(dbController.getPatientIDByCmid(data.get("community_member_id")),
                data.get("event_id"), eta, data.get("location_remark"), data.get("x"), data.get("y"));
    }

    //This function will be called by "receiveArrivalTime" - for updates and by
    // "assistantRespondsToApproach" - for addition.
    //Division to different cases logic is done on the EMS side:
    // If they don't have that patient in
    //that event - it's an addition. If they do - it's an update.
    private void updateOrAddAssistantToEMS(String patientId, String eventId, String eta, String locationRemark, String x, String y) {
        //generate
        HashMap<String,String> updateOrAddToEms = new HashMap<String,String>();
        String patientCmid = dbController.getCmidByPatientID(patientId);
        HashMap<String, String> user = dbController.getUserByParameter(
                new HashMapBuilder<String, String>().put("P_communityMembers.community_member_id", patientCmid).build());

        updateOrAddToEms.put("RequestID", "updateOrAddAssistant");
        updateOrAddToEms.put("first_name", user.get("first_name"));
        updateOrAddToEms.put("last_name", user.get("last_name"));

        // Get mediaction name
        String medName = dbController.getMedicationByNum(
                user.get("medication_num")).get(1).get("medication_name");

        updateOrAddToEms.put("medication_name",medName);
        updateOrAddToEms.put("dosage",user.get("dosage"));
        updateOrAddToEms.put("mobile_phone_number",user.get("mobile_phone_number"));
        updateOrAddToEms.put("patient_id",patientId);
        updateOrAddToEms.put("event_id", eventId);
        updateOrAddToEms.put("eta", eta);
        updateOrAddToEms.put("location_remark", locationRemark);
        updateOrAddToEms.put("x", x);
        updateOrAddToEms.put("y", y);

        //send
        ArrayList<String> sendTo = new ArrayList<>();
        sendTo = assistantFuncs.addReceiver("EMS", sendTo);
        initiatedOneObjectRequest(updateOrAddToEms, sendTo);
    }

    @Override
    public void requestAssistantDetails(HashMap<String, String> data){
        //append the required data to the request
        String cmid = dbController.getCmidByPatientID(data.get("patient_id"));
        HashMap<String,String> dbRequest = new HashMap<>();
        dbRequest.put("community_member_id",cmid);
        dbController.getCmidByPatientID(data.get("patient_id"));
        HashMap<String,String> userDetails = dbController.getUserByParameter(dbRequest);
        data.put("full_name", userDetails.get("first_name") + " " + userDetails.get("last_name"));
        data.put("mobile_phone_number", userDetails.get("mobile_phone_number"));
        //respond
        HashMap<Integer,HashMap<String,String>> dataToSend = new HashMap<>();
        dataToSend.put(1,data);
        commController.setCommToUsers(dataToSend, null, false);
        commController.sendResponse();
    }

    //Inserts assistant to the data base
    private void insertAssistantToDB(HashMap<String, String> user, String locationRemark, String eventId, String date)
    {
        HashMap<String, String> insert = new HashMap<String, String>();
        insert.put("location_remark",locationRemark);
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
        // Verify parameters
        ParametersVerifier verifier = new ParametersVerifier(toReject);
        if (!verifier.verify("password", "community_member_id", "RequestID", "event_id")){
            return;
        }
        //Verify credentials
        if (assistantFuncs.checkCmidAndPassword(toReject.get("password"), Integer.parseInt(toReject.get("community_member_id")))) {
            //remove unrequired keys
            toReject.remove("password");
            toReject.remove("community_member_id");
            toReject.remove("RequestID");
            String eventID = toReject.get("event_id");
            toReject.remove("event_id");
            //Reject the assistants
            rejectAssistants(toReject, eventID,"EMS services decided help is no longer required");
            //stop following
            ArrayList<String> cmidsToStopFollow = generateCmidListFromPatientsHashMap(toReject);
            stopFollow(eventID,cmidsToStopFollow);
        }
    }

    private ArrayList<String> generateCmidListFromPatientsHashMap(HashMap<String, String> toStopFollow) {
        ArrayList<String> cmidsList = new ArrayList<>();
        //convert patients hashmap to cmids list
        for(String currentPatient : toStopFollow.keySet()) {
            cmidsList.add(dbController.getCmidByPatientID(currentPatient));
        }
        return cmidsList;
    }

    private void rejectAssistants(HashMap<String, String> toReject,String eventID,String reason){
        //remove every assistant from the database and inform. "1" to inform the assistants here.
        for(String patientID : toReject.keySet()){
            //Update the assistant's status on the DB
            dbController.removeAssistantFromEvent(eventID, patientID);//
            //inform. "1" to inform helpers here.
            removeAssistant(patientID, eventID, 1, reason);

        }
    }

    //we call this function from "rejectAssistantsByEMS", "cancelEvent" and receiveArrivalTimes
    private void removeAssistant(String patientId, String eventId, int inform,String reason) {
        //Handling rejection logs
        emergencyLogger.updateAssistantRemovalFromEvent(patientId, eventId, inform);

        HashMap<String,String> data = new HashMap<String,String>();
        data.put("RequestID", "cancelAssist");
        data.put("event_id", eventId);
        data.put("reason",reason);
        //Notify the EMS of the removal
        if(0 == inform){
            data.put("patient_id", patientId);
            ArrayList<String> sendTo = new ArrayList<String>();
            sendTo = assistantFuncs.addReceiver("EMS", sendTo);
            initiatedOneObjectRequest(data, sendTo);
        }
        //Notify the assistant of the removal - GCM
        else{
            //Get the regID of the user to be aborted
            String regId = dbController.getRegIDOfPatient(patientId);
            HashMap<Integer,HashMap<String,String>> request = new HashMap<>();
            request.put(1, data);
            ArrayList<String> sendTo = new ArrayList<String>();
            sendTo.add(regId);
            commController.setCommToUsers(request, sendTo, false);
            commController.sendResponse();
        }
    }

    // Assistant arrival to the destination in emergency event
    @Override
    public void arrivalToDestination(HashMap<String, String> data) {
        //verify credentials
        if (!assistantFuncs.checkCmidAndPassword(data.get("password"), Integer.parseInt(data.get("community_member_id"))))
        {
            return;
        }
        emergencyLogger.handleArrivalToDest(data.get("event_id"), data.get("community_member_id"));
        // Updates the data base
        dbController.updateArrivalDate(data);
        // This events hasn't have EMS member yet
        if(!dbController.doesEventHasEMS(data.get("event_id")))
        {
            // Sends message to the assistant
            HashMap<String, String> h = new HashMap<String, String>();
            h.put("event_id", data.get("event_id"));
            h.put("message", "We don't have connection with EMS now-do what you think!");
            h.put("RequestID", "noEMS");
            HashMap<Integer,HashMap<String,String>> request = new HashMap<Integer,HashMap<String,String>>();
            request.put(1, h);
            ArrayList<String> sendTo = new ArrayList<String>();
            sendTo.add(data.get("reg_id"));
            commController.setCommToUsers(request, sendTo, false);
            commController.sendResponse();
            emergencyLogger.handleDontHaveEMS(data.get("event_id"), data.get("community_member_id"));
            return;
        }
        // Sends the news to EMS and asks for giving the medication
        HashMap<String,String> h = new HashMap<String,String>();
        h.put("event_id", data.get("event_id"));
        h.put("patient_id", dbController.getPatientIDByCmid(data.get("community_member_id")));
        h.put("RequestID", "confirmMedicationGiving");
        ArrayList<String> sendTo = new ArrayList<String>();
        sendTo = assistantFuncs.addReceiver("EMS", sendTo);
        initiatedOneObjectRequest(h, sendTo);
        emergencyLogger.handleAskEMSMedicationgiving(data.get("event_id"));
    }

    @Override
    public void approveOrRejectMed(HashMap<String, String> data) {
        logger.info("In approveOrRejectMed. data = " + data);
        if (!assistantFuncs.checkCmidAndPassword(data.get("password"), Integer.parseInt(data.get("community_member_id"))))
            return;
        emergencyLogger.handleApproveOrRejectMed(data.get("event_id"));
        HashMap<String, String> h = new HashMap<String, String>();
        h.put("event_id", data.get("event_id"));
        h.put("RequestID", data.get("RequestID"));
        String cmid = dbController.getCmidByPatientID(data.get("patient_id"));
        // Ems accept medication giving-updates the data base
        if (data.get("RequestID").equals("confirmMedication")) {
            dbController.updateActivationDate(cmid, data.get("event_id"));
            dbController.insertMedicationUse(cmid, data.get("event_id"), data.get("community_member_id"),
                    dbController.getMedicationOfPatient(cmid));
        }
        // Ems reject medication giving-updates the data base
        else
            dbController.updateResult(cmid, data.get("event_id"), "'EMS rejected medication giving'");
        // Sends EMS response to the assistant
        String regid = dbController.getRegIDsOfUser(Integer.parseInt(cmid)).get(1).get("reg_id");
        logger.info("regid = " + regid);
        ArrayList<String> target = new ArrayList<String>();
        target.add(regid);
        HashMap<Integer,HashMap<String,String>> response = new HashMap<Integer, HashMap<String, String>>();
        response.put(1, h);
        commController.setCommToUsers(response, target, false);
        commController.sendResponse();
        emergencyLogger.handleTellingAssistantAboutMedicationGiving(data.get("event_id"));
        logger.info("exiting approveOrRejectMed");
    }

    @Override
    public void assistantGaveMed(HashMap<String, String> data) {
        String cmid = data.get("community_member_id");
        if (!assistantFuncs.checkCmidAndPassword(data.get("password"), Integer.parseInt(cmid)))
            return;
        String eventID = data.get("event_id");
        dbController.updateMedicineGiven(Integer.parseInt(cmid), Integer.parseInt(eventID));
        data.put("patient_id", dbController.getPatientIDByCmid(cmid));
        data.remove("RequestID");
        data.put("RequestID", "AssistantGaveMed");
        //add the EMS URL to the receivers
        ArrayList<String> sendTo = new ArrayList<String>();
        sendTo = assistantFuncs.addReceiver("EMS", sendTo);
        //initiate request
        initiatedOneObjectRequest(data, sendTo);

        emergencyLogger.handleMedicationGiving(eventID, cmid);
    }

    @Override
    public void assistantCancelsArrival(HashMap<String, String> data) {
        if (!assistantFuncs.checkCmidAndPassword(data.get("password"), Integer.parseInt(data.get("community_member_id"))))
            return;
        String patientID = dbController.getPatientIDByCmid(data.get("community_member_id"));
        String eventID = data.get("event_id");
        //Update the assistant's status on the DB and inform. "0" to inform EMS here.
        dbController.removeAssistantFromEvent(eventID, patientID);//
        removeAssistant(patientID, eventID, 0, null);
    }

    @Override
    public void updatePatientStatus(HashMap<String, String> data) {
       if (!assistantFuncs.checkCmidAndPassword(data.get("password"), Integer.parseInt(data.get("community_member_id"))))
        {
            return;
        }
        String eventId = dbController.getEventByCmid(data.get("community_member_id"));
        emergencyLogger.handleUpdatePatientStatus(eventId, data.get("community_member_id"));
        dbController.updatePatientRemarks(data.get("community_member_id"), eventId, data.get("message"));
        // Sends the patient's message to EMS and the assistants

        ArrayList<String> regIds = dbController.getHelpersRegIds(eventId);
        popUpMessage(eventId, data.get("message"), regIds, true);
        emergencyLogger.handlePopupMessage(eventId);
    }

    // we call this from "updatePatientStatus"
    /*If we want to send to EMS we set "sendToEms" to true.
     To whoever app we want to send to, we add the reg id of the app to the regId's list*/
    private void popUpMessage(String eventId, String message, ArrayList<String> regIds, boolean sendToEms){
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("RequestID", "newInfo");
        data.put("message",message);
        // Sends message to apps
        if(null != regIds && !regIds.isEmpty())
        {
            HashMap<Integer,HashMap<String,String>> response = new HashMap<Integer, HashMap<String,String>>();
            response.put(1, data);
            commController.setCommToUsers(response, regIds, false);
            commController.sendResponse();
        }
        // Sends message to EMS
        if(sendToEms)
        {
            data.put("event_id",eventId);
            ArrayList<String> sendTo = new ArrayList<String>();
            sendTo = assistantFuncs.addReceiver("EMS", sendTo);
            initiatedOneObjectRequest(data, sendTo);
        }
    }
    @Override
    public void emsTakeover(HashMap<String, String> data) {
        // Check params
        if(null != data.get("status")){
            cancelEvent(data.get("event_id"), "FINISHED");
            //close the event within the GIS
            cancelEventOnGISorEMS(data.get("event_id"), "GIS");
            // Close the event within the app of the patient
            sendEventEndingToApp(data.get("event_id"));
            return;
        }
        //If the EMS wants to change the radius - ask for locations for GIS with the new radius
        //The Logic of the receiving of locations should handle the
        // addition/removal of users from the EMS and DB
        else if(null != data.get("radius")){
            //generate request
            HashMap<String,String>  request = new HashMap<String,String>();
            request.put("radius",data.get("radius"));
            request.put("event_id",data.get("event_id"));
            //Handle radius change logs
            emergencyLogger.changedRadius(request);
            request.put("RequestID","AroundLocation");

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

    // By this we let the app of the patient know about the event ending
    private void sendEventEndingToApp(String eventId)
    {
        String cmid = dbController.getEventDetails(eventId).get("create_by_member_id");

        ArrayList<String> sendTo = new ArrayList<String>();
        sendTo.add(dbController.getRegIDsOfUser(Integer.parseInt(cmid)).get(1).get("reg_id"));
        HashMap<String, String> response = new HashMap<String, String>();
        response.put("RequestID", "done");
        HashMap<Integer, HashMap<String, String>> res = new HashMap<Integer, HashMap<String, String>>();
        res.put(1, response);
        commController.setCommToUsers(res, sendTo, false);
        commController.sendResponse();
    }

    private HashMap<String,String> turnIntListIntoHashMap(List<Integer> eventHelpers){
        HashMap<String,String> toReturn = new HashMap<String,String>();

        for(Integer current : eventHelpers){
            toReturn.put(current.toString(),null);
        }
        return toReturn;
    }

    //called from EMSTakeover or patientCancelledEvent
    private void cancelEvent(String eventID,String status) {
        //get all helpers and cancel them
        List<Integer> eventHelpers = dbController.getAllAssistantsByEventId(Integer.parseInt(eventID),-1);
        HashMap<String,String> rejectRequest = turnIntListIntoHashMap(eventHelpers);
        rejectAssistants(rejectRequest, eventID, "Event is over");
        //close the event within the DB
        dbController.closeEvent(Integer.parseInt(eventID), status);
        //Hanle event termination logs
        emergencyLogger.terminateEvent(eventID, status);
    }

    public void patientCancelledEvent(HashMap<String,String> data){
        if (!assistantFuncs.checkCmidAndPassword(data.get("password"), Integer.parseInt(data.get("community_member_id"))))
            return;
        String eventID = dbController.getEventByCmid(data.get("community_member_id"));
        //cancel with assistants and db
        cancelEvent(eventID, "canceled");
        //cancel with EMS
        cancelEventOnGISorEMS(eventID, "EMS");
        //cancel with GIS
        cancelEventOnGISorEMS(eventID, "GIS");
    }

    private void cancelEventOnGISorEMS(String eventID,String where) {
        //generate request
        HashMap<String,String> request = new HashMap<String,String>();
        request.put("RequestID", "cancelEvent");
        request.put("event_id",eventID);
        //send to GIS
        ArrayList<String> sendTo = new ArrayList<String>();
        sendTo = assistantFuncs.addReceiver(where, sendTo);
        initiatedOneObjectRequest(request, sendTo);
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
            String loc = user.get("location_remark");
            user.remove("location_remark");
            // Inserts the assistant to the data base
            insertAssistantToDB(user, loc, eventId, eventDetails.get("created_date"));
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
            req.put(cmids.get(i), "null");
        response.put(1, req);
        ArrayList<String> sendTo = new ArrayList<String>();
        sendTo = assistantFuncs.addReceiver("GIS",sendTo);
        commController.setCommToUsers(response, sendTo, true);
        //send request
        commController.sendResponse();
        emergencyLogger.handleStopFollowingUsers(eventId);
    }


    //we call this function from "approveOrRejectMed"
    /*private void approveOrRejectHelper(int patientID, int eventID) {
        // in case of reject, we just need to tell the helper not to give the medicine and stay put
        //We allow the EMS to change their mind, but this logic is handled over their side using the instructions below
    }*/
    //EMS Needs to know after first time someone said he arrived - they should allow the Mokdan
    //to change his mind after rejecting. They should keep a boolean field in the table indicating "arrival"
}
