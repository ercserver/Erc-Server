package RequestsModule;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import registrationModule.src.api.IRegController;
import registrationModule.src.controller.RegController_V1;

@Controller
@RequestMapping("/requests")
public class RequestsHandler {
    private final String REQ_ID = "RequestID"; // This is the requests identifier's field name

    /*** Registration Process Requests Codes ***/
    private final String REGISTRATION = "registration";
    private final String SIGNUP = "signUp";
    private final String SIGNIN = "signIn";
    private final String RESENDEMAIL = "resendEmail";

    /*** Routine Requests Codes ***/
    private final String VERIFYDOCTOR = "verifyDoctor";
    private final String ASKWAITINGPATIENTS = "askWaitingPatients";
    private final String CURRENTLOCATION = "currentLocation";



    @RequestMapping(method = {RequestMethod.GET, RequestMethod.HEAD}, value = "/welcome")
	public @ResponseBody String printWelcome() {
        return "Welcome to the erc-server";
	}

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.HEAD}, value = "/patients_reg")
    public @ResponseBody String handleRequest(@RequestParam JSONObject requestJson){
        String reqId = "";
        try {
            IRegController rc = new RegController_V1();
            reqId = requestJson.getString(REQ_ID);

            // TODO - add the correct functions of IRegController
            switch (reqId) {
                case REGISTRATION:

                    break;
                case SIGNUP:
                    break;
                case SIGNIN:
                    break;
                case RESENDEMAIL:
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
}