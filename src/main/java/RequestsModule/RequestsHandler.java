package RequestsModule;

import RegistrationModule.src.api.*;
import RegistrationModule.src.controller.RegController_V1;
import RequestsModule.utils.HashMapCreator;
import RoutineModule.src.api.IRoutineController;
import RoutineModule.src.controller.RoutineController_V1;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
@RequestMapping("/server/requests")
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
    private final String ASKWAITINGPATIENTS = "askWaitingPatients";
    private final String CURRENTLOCATION = "routineLocation";



    @RequestMapping(method = {RequestMethod.GET, RequestMethod.HEAD}, value = "/welcome")
	public @ResponseBody String printWelcome() {
        return "Welcome to the erc-server";
	}

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.HEAD}, value = "/registration")
    public @ResponseBody String handleRegistrationRequests(@RequestParam JSONObject requestJson){
        HashMapCreator hmc = new HashMapCreator();
        HashMap<String, String> requestMap = hmc.jsonToMap(requestJson);
        String reqId = "";

        try {
            IRegController rc = new RegController_V1();
            reqId = requestJson.getString(REQ_ID);

            switch (reqId) {
                case REGISTRATION:
                    rc.getRegDetails(requestMap);
                    break;
                case SIGNUP:
                    rc.handleReg(requestMap);
                    break;
                case RESEND_EMAIL:
                    rc.resendAuth(requestMap);
                    break;
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
        //ToDo:do we really need to return something from here?
        return (new JSONObject().put("RequestAccepted", reqId).toString());
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.HEAD}, value = "/routine")
    public @ResponseBody String handleRoutineRequests(@RequestParam JSONObject requestJson){
        HashMapCreator hmc = new HashMapCreator();
        HashMap<String, String> requestMap = hmc.jsonToMap(requestJson);
        String reqId = "";

        try {
            IRegController rc = new RegController_V1();
            IRoutineController ruc = new RoutineController_V1();
            reqId = requestJson.getString(REQ_ID);

            switch (reqId) {
                case SIGNIN:
                    rc.signIn(requestMap);
                    break;
                case ASKWAITINGPATIENTS:
                    JSONArray respone  = (JSONArray) rc.getWaitingForDoctor(requestMap);
                    if (respone != null){
                        return respone.toString();
                    }
                case CURRENTLOCATION:
                    ruc.transferLocation(requestMap);
                default:
                    // Do nothing...
                    break;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return (new JSONObject().put("RequestAccepted", reqId).toString());
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