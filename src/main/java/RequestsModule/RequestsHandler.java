package RequestsModule;

import RequestsModule.utils.HashMapCreator;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import registrationModule.src.api.IRegController;
import registrationModule.src.controller.RegController_V1;

import java.util.HashMap;

@Controller
@RequestMapping("/server/requests")
public class RequestsHandler {
    private final String REQ_ID = "RequestID"; // This is the requests identifier's field name

    /*** Registration Process Requests Codes ***/
    private final String REGISTRATION = "registration";
    private final String SIGNUP = "signUp";
    private final String SIGNIN = "signIn";
    private final String RESEND_EMAIL = "resendEmail";

    /*** Routine Requests Codes ***/
    private final String ASKWAITINGPATIENTS = "askWaitingPatients";
    private final String CURRENTLOCATION = "currentLocation";



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
                default:
                    // Do nothing...
                    break;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return (new JSONObject().put("RequestAccepted", reqId).toString());
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.HEAD}, value = "/routine")
    public @ResponseBody String handleRoutineRequests(@RequestParam JSONObject requestJson){
        HashMapCreator hmc = new HashMapCreator();
        HashMap<String, String> requestMap = hmc.jsonToMap(requestJson);
        String reqId = "";

        try {
            IRegController rc = new RegController_V1();
            reqId = requestJson.getString(REQ_ID);

            switch (reqId) {
                case SIGNIN:
                    rc.signIn(requestMap);
                    break;
                case ASKWAITINGPATIENTS:
                    JSONArray respone  = (JSONArray) rc.getWaitingForDoctor(Integer.parseInt(requestMap.get("cmid")));
                    if (respone != null){
                        return respone.toString();
                    }
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
            rc.verifyDetail(null); // TODO - insert the key


        }catch (Exception ex){
            ex.printStackTrace();
        }
        return "Email Verified";
    }
}