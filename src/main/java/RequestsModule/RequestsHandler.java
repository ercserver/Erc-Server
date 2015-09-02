package RequestsModule;


import DatabaseModule.src.model.DbComm_V1;
import EmergencyModule.src.controller.EmerController_V1;
import RequestsModule.utils.HashMapCreator;
import RequestsModule.utils.JsonValidator;
import RequestsModule.utils.TestGCM;
import RequestsModule.utils.TestNewDB;
import RoutineModule.src.api.IRoutineController;
import RoutineModule.src.controller.RoutineController_V1;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import registrationModule.src.api.IRegController;
import registrationModule.src.controller.RegController_V1;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


@Controller
@Scope("session")
@RequestMapping("/requests")
public class RequestsHandler {


    private static final String EMSID = "EMSId";
    private static final String ASSISTANT_DETAILS = "AssistantDetails";
    private static final String UPDATE_PATIENT_AT_RISK = "updatePatientAtRisk";
    private static final String CANCEL_ARRIVAL = "cancelArrival";
    private static final String GAVE_MEDICATION = "gaveMedication";
    private static final String CANCEL_PATIENT_AT_RISK = "cancelPatientAtRisk";
    private final String REQ_ID = "RequestID"; // This is the requests identifier's field name

    /*** Registration Process Requests Codes ***/
    private final String REGISTRATION = "registration";
    private final String SIGNUP = "signUp";
    private final String SIGNIN = "signIn";
    private final String RESEND_EMAIL = "resendAuth";
    private final String CONFIRM_PATIENT = "confirmPatient";
    private final String REJECT_PATIENT = "rejectPatient";
    private final String FORGOT_PASSWORD = "forgotPassword";

    /*** Routine Requests Codes ***/
    private final String ASKWAITING_PATIENTS = "askWaitingPatients";
    private final String CURRENT_LOCATION = "routineLocation";

    /*** Routine Requests Codes ***/
    private final String AROUND_LOCATION = "AroundLocation";
    private final String USERS_ARRIVAL_TIMES = "UsersArrivalTimes"; // Arrival times
    private final String CLOSEST_EMS = "closestEMS";
    private final String FOLLOW_USER = "followUser";
    private final String UPDATE_REQ = "updateReq";
    private final String UPDATE_DETAILS = "updateDetails";
    private final String EMS_EVENT = "EMSEvents";
    private final String UPDATE_STATUS = "updateStatus";

    /*** Emergency Requests Codes ***/
    private final String HELP = "help";
    private static final String ARRIVAL_ACCEPTION_ON_FOOT = "arrivalAcceptionOnFoot";
    private static final String ARRIVAL_ACCEPTION_MOUNTED = "arrivalAcceptionMounted";
    private static final String ARRIVAL_REJECTION = "arrivalRejection";
    private static final String EMSTAKE_OVER = "EMStakeOver";
    private static final String CANCEL_ASSIST = "cancelAssist";
    private static final String I_AM_HERE = "iAmHere";
    private static final String CONFIRM_MEDICATION = "confirmMedication";
    private static final String REJECT_MEDICATION = "rejectMedication";

    /*** Class Members */

    HashMapCreator hmc = new HashMapCreator();
    JsonValidator jv = new JsonValidator();

    /****** Controlers ******/
    final static Logger logger = Logger.getLogger(RequestsHandler.class.getName());
    IRegController rc = new RegController_V1();
    IRoutineController ruc = new RoutineController_V1();
    EmerController_V1 ec = new EmerController_V1();


    // TODO - Create a constructor that starts the scheduler ( a singleton - Schdeuler)
    public RequestsHandler(){
        logger.log(Level.INFO, "Controller Ctor");

    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.HEAD}, value = "/welcome")
	public @ResponseBody String printWelcome() {
        return "Welcome to the erc-server";
	}

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.HEAD}, value = "/db")
    public @ResponseBody String initDB(@RequestParam String url)
    {
        try {
            TestNewDB.test(url);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Exception: ", e);
        }
        return "db";
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.HEAD}, value = "/stuck")
    public @ResponseBody String checkStuck()
    {
      return "Not stuck :)";
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.HEAD}, value = "/delete/{cmid}")
    public @ResponseBody String deleteUser(@PathVariable String cmid ){
        DbComm_V1 db = new DbComm_V1();
        if (db.testDelete(cmid) > 0) {
            return cmid + " deleted";
        }else{
            return "Email not found";
        }
    }


    @RequestMapping(method = {RequestMethod.POST, RequestMethod.HEAD}, value = "/test", consumes = "application/json")
    public @ResponseBody String returnJson(@RequestBody String request) {
        JSONObject reqJson = new JSONObject(request);


        return reqJson.toString();
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.HEAD}, value = "/test-gcm")
    public @ResponseBody String returnGCM(@RequestParam int cmid) {
        new TestGCM().gcmTest(cmid);
        return "gcm sent";
    }


    @RequestMapping(method = {RequestMethod.POST, RequestMethod.HEAD}, value = "/registration")
    public @ResponseBody String handleRegistrationRequests(@RequestBody String data){
        logger.log(Level.INFO, "In Registration. params = " + data);
        JSONObject json = jv.createJSON(data);
        HashMap<String, String> requestMap = hmc.jsonToMap(json);
        String reqId = "";
        logger.log(Level.INFO, "After parsing JSONFile");
        try {

            reqId = requestMap.get(REQ_ID);
            //logger.log(Level.INFO, "Before switch. reqID = " + reqId);
            switch (reqId) {
                case REGISTRATION:
                    return rc.getRegDetails(requestMap).toString();
                case SIGNUP:
                    return rc.handleReg(requestMap).toString();

                case RESEND_EMAIL:
                    return rc.resendAuth(requestMap).toString();

                case CONFIRM_PATIENT:
                    return rc.responseByDoctor(requestMap, true).toString();

                case REJECT_PATIENT:
                    return rc.responseByDoctor(requestMap, false).toString();


                default:
                    // Do nothing...
                    break;
            }
        }catch (Exception ex){
            logger.log(Level.WARNING, "Exception: ", ex);
            logger.log(Level.INFO, "No response");
        }

        return (new JSONObject().put("Error processing: ", reqId).toString());
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.HEAD}, value = "/emergency")
    public @ResponseBody String handleEmergencyRequests(@RequestBody String data) {
        logger.log(Level.INFO, "In Emergency. params = " + data);
        JSONObject json = jv.createJSON(data);
        logger.log(Level.INFO, "After json object");
        HashMap<String, String> requestMap = hmc.jsonToMap(json);
        logger.log(Level.INFO, "requestMap = " + requestMap);
        String reqId = "";
        logger.log(Level.INFO, "After parsing JSONFile");
        try {
            reqId = requestMap.get(REQ_ID);
            logger.log(Level.INFO, "Before switch. reqID = " + reqId);
            switch (reqId) {
                case HELP:
                    ec.emergencyCall(requestMap);
                    break;
                case ARRIVAL_ACCEPTION_ON_FOOT:
                case ARRIVAL_ACCEPTION_MOUNTED:
                case ARRIVAL_REJECTION:
                    ec.assistantRespondsToApproach(requestMap);
                    break;
                case EMSTAKE_OVER:
                    ec.emsTakeover(requestMap);
                    break;
                case CANCEL_ASSIST:
                    ec.rejectAssistantsByEMS(requestMap);
                    break;
                case I_AM_HERE: // from application
                    ec.arrivalToDestination(requestMap);
                    break;

                case CONFIRM_MEDICATION:
                case REJECT_MEDICATION:
                    ec.approveOrRejectMed(requestMap);
                    break;
                case GAVE_MEDICATION:
                    ec.assistantGaveMed(requestMap);
                    break;
                case EMSID:
                    ec.getCmidOfEms(requestMap);
                    break;
                case ASSISTANT_DETAILS:
                    ec.requestAssistantDetails(requestMap);
                    break;
                case UPDATE_PATIENT_AT_RISK:
                    ec.updatePatientStatus(requestMap);
                    break;
                case CANCEL_ARRIVAL:
                    ec.assistantCancelsArrival(requestMap);
                    break;
                case CANCEL_PATIENT_AT_RISK:
                    ec.patientCancelledEvent(requestMap);
                    break;


                default:
                    logger.log(Level.INFO, " default...");
                    return new JSONArray().put(new JSONObject().put("status", "failed")).toString();
            }
        } catch (Exception ex) {
            logger.log(Level.WARNING, "Exception: ", ex);
        }
        return new JSONArray().put(new JSONObject().put("status", "success")).toString();
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.HEAD}, value = "/routine")
    public @ResponseBody String handleRoutineRequests(@RequestBody String request){
        logger.log(Level.INFO, "In routine requests. param = " + request);
        JsonValidator jv = new JsonValidator();
        JSONObject data = jv.createJSON(request);
        HashMapCreator hmc = new HashMapCreator();
        HashMap<String, String> requestMap = hmc.jsonToMap(data);
        String reqId = "";
        String rv = "Received Request : ";
        try {
            IRegController rc = new RegController_V1();
            IRoutineController ruc = new RoutineController_V1();
            reqId = data.optString(REQ_ID, "");

            switch (reqId) {
                case SIGNIN:
                    rv = rc.signIn(requestMap).toString();
                    System.out.println("rv = " + rv);
                    break;
                case ASKWAITING_PATIENTS:
                    JSONArray response  = (JSONArray) rc.getWaitingForDoctor(requestMap);
                    if (response != null){
                        return response.toString();
                    }
                    break;
                case CURRENT_LOCATION:
                    rv = ruc.transferLocation(requestMap).toString();
                    break;
                case UPDATE_REQ:
                    rv = ruc.getUpdatesFields(requestMap).toString();
                    break;
                case UPDATE_DETAILS:
                    rv = ruc.updateMemberDetails(requestMap).toString();
                    break;
                case FORGOT_PASSWORD:
                    rv = ruc.forgotPassword(requestMap).toString();
                    break;
                case EMS_EVENT:
                    rv = ruc.getEmsEventsByDispatcherCmid(requestMap).toString();
                case UPDATE_STATUS:
                    rv = ruc.updateStatus(requestMap).toString();

                default:
                    // Do nothing...
                    rv = "Wrong JSONFile id";
                    break;//
            }
        }catch (Exception ex){
            logger.log(Level.WARNING, "Exception: ", ex);
        }
        return rv;
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.HEAD}, value = "/emergency-gis-times")
    public @ResponseBody String handleMembersArrivalTimes(@RequestParam String JSONFile){
        logger.info("in handleMembersArrivalTimes. json = " + JSONFile);
        JSONArray data = new JSONArray(JSONFile);
        HashMap<Integer, HashMap<String, String>> requestMap = hmc.jsonArrayToMap(data);
        logger.info("in map = " + requestMap);
        String rv = "Received Request id : ";
        // Get JSONFile id
        try{
            HashMap<String, String> details = requestMap.get(1);
            String reqId;
            if (details != null){
                reqId = details.get(REQ_ID);
                switch (reqId){
                    case USERS_ARRIVAL_TIMES:
                        logger.info("in  USERS_ARRIVAL_TIMES");
                        ec.receiveUsersArrivalTimesAndApproach(requestMap);
                        rv += reqId;
                        break;

                    default:
                        rv = "Wrong JSONFile id";
                        break;
                }
            }
        }catch(Exception ex){
           logger.log(Level.WARNING, "Exception: ", ex);
        }
        return rv;
    }

     @RequestMapping(method = {RequestMethod.POST, RequestMethod.HEAD}, value = "/emergency-gis")
    public @ResponseBody String handleEmergencyGISRequests(@RequestParam String JSONFile){
        logger.log(Level.INFO, "JSONFile = " + JSONFile);
         JSONObject data = jv.createJSON(JSONFile);
        HashMap<String, String> requestMap = hmc.jsonToMap(data);
         logger.log(Level.INFO, "requestMap = " + requestMap);
        String reqId = "";
        String rv = "";
        try {
            reqId = requestMap.get(REQ_ID);
            logger.log(Level.INFO, "reqId = " + reqId);
            switch (reqId) {
                case AROUND_LOCATION:
                    ec.receiveUsersAroundLocation(requestMap);
                    break;
                case CLOSEST_EMS:
                    ec.receiveClosestEmsAndApproach(requestMap);
                    break;
                case FOLLOW_USER:
                    ec.receiveArrivalTime(requestMap);
                    break;

                default:
                    // Do nothing...
                    break;
            }
        }catch (Exception ex){
            logger.log(Level.WARNING, "Exception: ", ex);
        }
        return rv;
    }




    @RequestMapping(method = {RequestMethod.GET, RequestMethod.HEAD}, value = "/verify_email")
    public @ResponseBody String handleEmailVerification(@RequestParam String key /*CMID right now*/){
        String reqId = "";

        try {
            rc.verifyDetail(key);


        }catch (Exception ex){
            logger.log(Level.WARNING, "Exception: ", ex);
        }
        return "Email Verified";
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.HEAD}, value = "/auth_doctor")
    public @ResponseBody String handleDoctorAuth(@RequestParam String key , @RequestParam boolean accept/*CMID right now*/){
        String reqId = "";

        try {
            rc.responeToDoctorAturization(key, accept);
        }catch (Exception ex){
            logger.log(Level.WARNING, "Exception: ", ex);
        }
        return "Email Verified";
    }


    //ToDo:need links for confirmation and rejection of doctor
}