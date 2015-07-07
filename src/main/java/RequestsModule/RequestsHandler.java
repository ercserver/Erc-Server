package RequestsModule;


import DatabaseModule.src.model.DbComm_V1;
import EmergencyModule.src.api.IEmerController;
import EmergencyModule.src.controller.EmerController_V1;
import RequestsModule.utils.HashMapCreator;
import RequestsModule.utils.JsonValidator;
import RequestsModule.utils.TestGCM;
import RequestsModule.utils.TestNewDB;
import RoutineModule.src.api.IRoutineController;
import RoutineModule.src.controller.RoutineController_V1;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import registrationModule.src.api.IRegController;
import registrationModule.src.controller.RegController_V1;

import java.util.HashMap;


@Controller
@Scope("session")
@RequestMapping("/requests")
public class RequestsHandler {
    private final String REQ_ID = "RequestID"; // This is the requests identifier's field name

    /*** Registration Process Requests Codes ***/
    private final String REGISTRATION = "registration";
    private final String SIGNUP = "signUp";
    private final String SIGNIN = "signIn";
    private final String RESEND_EMAIL = "resendAuth";
    private final String CONFIRM_PATIENT = "confirmPatient";
    private final String REJECT_PATIENT = "rejectPatient";

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


    /*** Emergency Requests Codes ***/
    private final String HELP = "help";

    /*** Class Members */

    HashMapCreator hmc = new HashMapCreator();
    JsonValidator jv = new JsonValidator();

    /****** Controlers ******/
    final static Logger logger = Logger.getLogger(RequestsHandler.class);
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
    public @ResponseBody String initDB()
    {
        try {
            TestNewDB.test();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "db";
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
    public @ResponseBody String returnGCM() {
        new TestGCM().gcmTest();
        return "gcm sent";
    }


    @RequestMapping(method = {RequestMethod.POST, RequestMethod.HEAD}, value = "/registration")
    public @ResponseBody String handleRegistrationRequests(@RequestBody String data){
        logger.log(Level.INFO, "In Registration. params = " + data);
        JSONObject json = jv.createJSON(data);
        HashMap<String, String> requestMap = hmc.jsonToMap(json);
        String reqId = "";
        logger.log(Level.INFO, "After parsing request");
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
                    rc.responseByDoctor(requestMap, true);
                    break;
                case REJECT_PATIENT:
                    rc.responseByDoctor(requestMap, false);
                    break;
                default:
                    // Do nothing...
                    break;
            }
        }catch (Exception ex){
            ex.printStackTrace();
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
        logger.log(Level.INFO, "After parsing request");
        try {
            reqId = requestMap.get(REQ_ID);
            logger.log(Level.INFO, "Before switch. reqID = " + reqId);
            switch (reqId) {
                case HELP:
                    logger.log(Level.INFO, " requestID = " + reqId);
                    ec.emergencyCall(requestMap);
                    break;
                default:
                    logger.log(Level.INFO, " default...");
                    return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
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
                    rv = rc.getRegDetails(requestMap).toString();
                    break;
                case UPDATE_DETAILS:
                    rv = ruc.updateMemberDetails(requestMap).toString();
                    break;
                default:
                    // Do nothing...
                    rv = "Wrong request id";
                    break;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return rv;
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.HEAD}, value = "/emergency-gis-times")
    public @ResponseBody String handleMembersArrivalTimes(@RequestBody String request){
        JSONArray data = new JSONArray(request);
        HashMap<Integer, HashMap<String, String>> requestMap = hmc.jsonArrayToMap(data);
        String rv = "Received Request id : ";
        // Get request id
        try{
            HashMap<String, String> details = requestMap.get(1);
            String reqId;
            if (details != null){
                reqId = details.get(REQ_ID);
                switch (reqId){
                    case USERS_ARRIVAL_TIMES:
                        ec.receiveUsersArrivalTimesAndApproach(requestMap);
                        rv += reqId;
                        break;

                    default:
                        rv = "Wrong request id";
                        break;
                }
            }
        }catch(Exception ex){
           ex.printStackTrace();
        }
        return rv;
    }

     @RequestMapping(method = {RequestMethod.POST, RequestMethod.HEAD}, value = "/emergency-gis")
    public @ResponseBody String handleEmergencyGISRequests(@RequestBody String request){
         JSONObject data = jv.createJSON(request);
        HashMap<String, String> requestMap = hmc.jsonToMap(data);
        String reqId = "";
        String rv = "";
        try {
            reqId = requestMap.get(REQ_ID);

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
            ex.printStackTrace();
        }
        return rv;
    }




    @RequestMapping(method = {RequestMethod.GET, RequestMethod.HEAD}, value = "/verify_email")
    public @ResponseBody String handleEmailVerification(@RequestParam String key /*CMID right now*/){
        String reqId = "";

        try {
            rc.verifyDetail(key);


        }catch (Exception ex){
            ex.printStackTrace();
        }
        return "Email Verified";
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.HEAD}, value = "/auth_doctor")
    public @ResponseBody String handleDoctorAuth(@RequestParam String key , @RequestParam boolean accept/*CMID right now*/){
        String reqId = "";

        try {
            rc.responeToDoctorAturization(key, accept);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return "Email Verified";
    }


    //ToDo:need links for confirmation and rejection of doctor
}