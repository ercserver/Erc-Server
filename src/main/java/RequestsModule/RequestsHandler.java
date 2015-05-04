package RequestsModule;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/requests")
public class RequestsHandler {

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.HEAD}, value = "welcome")
	public @ResponseBody String printWelcome() {
        return "Welcome to the erc-server";
	}

   /* @RequestMapping(method = {RequestMethod.GET, RequestMethod.HEAD}, value = "patients_reg")
    public @ResponseBody JSONObject handleRequest(JSONObject json){}*/
}