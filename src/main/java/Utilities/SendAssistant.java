package Utilities;

import java.util.HashMap;

/**
 * Created by User on 21/05/2015.
 */
public class SendAssistant {

    //basic respone include only message and Request ID
   public HashMap<String,String> buildBasicRespone(String message,
                                                                        String code)
    {

        HashMap<String,String> dataToSend = new HashMap<String, String>();
        dataToSend.put("RequestID", code);
        dataToSend.put("message", message);
        return dataToSend;
    }
}
