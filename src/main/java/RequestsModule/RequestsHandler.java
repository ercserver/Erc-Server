package RequestsModule;


import DatabaseModule.src.model.DbComm_V1;
import EmergencyModule.src.api.IEmerController;
import EmergencyModule.src.controller.EmerController_V1;
import Utilities.HashMapBuilder;
import org.springframework.web.bind.annotation.*;
import registrationModule.src.api.*;
import registrationModule.src.controller.RegController_V1;
import RequestsModule.utils.HashMapCreator;
import RoutineModule.src.api.IRoutineController;
import RoutineModule.src.controller.RoutineController_V1;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

@Controller
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



    // TODO - Create a constructor that starts the scheduler ( a singleton - Schdeuler)
    public RequestsHandler(){
        System.out.println("constructor");
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.HEAD}, value = "/welcome")
	public @ResponseBody String printWelcome() {
        return "Welcome to the erc-server";
	}

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.HEAD}, value = "/db")
    public @ResponseBody String initDB()
    {
        DbComm_V1 db = new DbComm_V1();
        HashMapBuilder<String, String> hashMapBuilder = new HashMapBuilder<>();

        return db.getUserByParameter(hashMapBuilder.put("community_member_id","1024").build()).toString();
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.HEAD}, value = "/test", consumes = "application/json")
    public @ResponseBody String returnJson(@RequestBody String request) {
        JSONObject reqJson = new JSONObject(request);


        return reqJson.toString();
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.HEAD}, value = "/registration")
    public @ResponseBody String handleRegistrationRequests(@RequestBody String data){
        HashMapCreator hmc = new HashMapCreator();
        JSONObject json = new JSONObject(data);
        HashMap<String, String> requestMap = hmc.jsonToMap(json);
        String reqId = "";
        System.out.println("in registration");
        System.out.println("data:");
        System.out.println(data);


        try {
            IRegController rc = new RegController_V1();
            reqId = requestMap.get(REQ_ID);

            switch (reqId) {
                case REGISTRATION:
                    String s = rc.getRegDetails(requestMap).toString();
                    PrintWriter writer = null;
                    try {
                        writer = new PrintWriter("log.txt", "UTF-8");
                        writer.print(s);

                        writer.close();
                        return s;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                break;
                case SIGNUP:
                    return rc.handleReg(requestMap).toString();

                case RESEND_EMAIL:
                    return rc.resendAuth(requestMap).toString();

                case CONFIRM_PATIENT:
                    rc.responeByDoctor(requestMap);
                    break;
                case REJECT_PATIENT:
                    rc.responeByDoctor(requestMap);
                    break;
                default:
                    // Do nothing...
                    break;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return (new JSONObject().put("Error processing: ", reqId).toString());
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.HEAD}, value = "/routine")
    public @ResponseBody String handleRoutineRequests(@RequestBody String request){
        JSONObject data = new JSONObject(request);
        HashMapCreator hmc = new HashMapCreator();
        HashMap<String, String> requestMap = hmc.jsonToMap(data);
        String reqId = "";
        String rv = "Received Request : ";
        try {
            IRegController rc = new RegController_V1();
            IRoutineController ruc = new RoutineController_V1();
            reqId = data.getString(REQ_ID);

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
        HashMapCreator hmc = new HashMapCreator();
        HashMap<Integer, HashMap<String, String>> requestMap = hmc.jsonArrayToMap(data);
        IEmerController ec = new EmerController_V1();
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
    public @ResponseBody String handleEmergencyRequests(@RequestBody String request){
        JSONObject data = new JSONObject(request);
        HashMapCreator hmc = new HashMapCreator();
        HashMap<String, String> requestMap = hmc.jsonToMap(data);
        String reqId = "";
        String rv = "";
        try {
            IEmerController ec = new EmerController_V1();
            reqId = data.getString(REQ_ID);

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
            IRegController rc = new RegController_V1();
            rc.verifyDetail(key);


        }catch (Exception ex){
            ex.printStackTrace();
        }
        return "Email Verified";
    }


    //ToDo:need links for confirmation and rejection of doctor
}