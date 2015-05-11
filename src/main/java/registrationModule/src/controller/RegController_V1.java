package registrationModule.src.controller;

import CommunicationModule.src.api.ICommController;
import DatabaseModule.src.api.IDbController;
import registrationModule.src.api.IRegController;
import registrationModule.src.api.IRegRequest_model;
import registrationModule.src.api.IRegVerify_model;
import registrationModule.src.utilities.ModelsFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by NAOR on 06/04/2015.dd
 */
public class RegController_V1 implements IRegController {
    private IRegRequest_model registrator = null;
    private IRegVerify_model verification = null;
    private IDbController dbController = null;
    private ICommController commController = null;

    public RegController_V1(){
        ModelsFactory models = new ModelsFactory();
        commController = models.determineCommControllerVersion();
        dbController = models.determineDbControllerVersion();
        registrator = models.determineRegRequestVersion();
        verification = models.determineRegVerifyVersion();
    }

    public Object getRegDetails(HashMap<String,String> request) {
        //generate data to send
        HashMap<Integer,HashMap<String,String>> dataToSend =
                dbController.getRegistrationFields(Integer.parseInt(request.get("userType")));
        dataToSend.get(1).put("RequestID", "registration");
        ArrayList<String> sendTo = sendTo(request);
        //determine how to send the data
        commController.setCommToUsers(dataToSend, sendTo, false);
        //send the data
        return commController.sendResponse();
    }

    public Object handleReg(HashMap<String, String> filledForm) {

        //if the user exists (registration model decides how to determine that)
        String message = registrator.doesUserExist(filledForm);
        String responseCode = null;
        if(null != message){
            //set the message and code
            responseCode = setResponseCodeForRejection(filledForm.get("user_type"));

            message = "A user with such email already exists! Please try again.";
        }
        //User does not exist
        else {
            //Separate according to user type and handle accordingly.... use the Verification Module for verification processes
            ArrayList<String> messages = verification.verifyFilledForm(filledForm);

            if(!messages.isEmpty()){
                //TODO - We need to have such code....
                //set the message and code
                responseCode = setResponseCodeForRejection(filledForm.get("user_type"));
                message = appendAllMessages(messages);
            }
            //Legit form
            else{
                //set the message and code
                responseCode = "wait";
                //Get authorization method from db
                //ToDo:we should take the state from filledForm
                int authMethod = dbController.getAuthenticationMethod("'israel'");
                String method = (0 == authMethod) ? "mail" : "sms";
                message = "Form filled successfully. A verification " + method + " was sent to you. Please verify your registration.";
                //Add the new community member (a new CmID is generated)
                int newCmid = dbController.addNewCommunityMember(filledForm);
                //Update status to "Verifying Email"
                dbController.updateStatus(newCmid, null,"'verifying email'");
                //insert the newly created cmid to the form for mail purposes
                filledForm.put("community_member_id", Integer.toString(newCmid));
                //Generate data for the authorization message
                HashMap<String, String> data = verification.generateDataForAuth(filledForm,authMethod);
                //Create authorization comm
                ICommController commAuthMethod = new ModelsFactory().determineCommControllerVersion();
                commAuthMethod.setCommOfficial(data,authMethod);
                //Communicate authorization (email/sms/...)
                commAuthMethod.sendMessage();
            }
        }
        //
        HashMap<Integer,HashMap<String,String>> dataToSend = buildResponeWithOnlyRequestID(message,responseCode);
        ArrayList<String> sendTo = sendTo(filledForm);
        //determine how to send the data. Initiated communication - so use "false"
        commController.setCommToUsers(dataToSend,sendTo,false);
        //send the data
        return commController.sendResponse();
    }

    private String setResponseCodeForRejection(String user_type) {
        //set response code according to user type
        switch(Integer.parseInt(user_type)){
            case 0:{
                return "rejectPatient";
            }
            case 1:{
                return "rejectDoctor";
            }
            case 2:{
                return "rejectGuardian";
            }
            case 3:{
                return "rejectEms";
            }
            default:{
                //throw some nasty error
                return null;
            }
        }
    }

    private String appendAllMessages(ArrayList<String> messages) {
        //Title for the main message
        String message = "An error has occurred!\n";
        //list messages
        int index = 1;
        for(String msg : messages){
            message += Integer.toString(index) + ". " + msg + "\n";
        }
        //close the main message and return it
        message += "Please correct the above fields and re-submit the registrations form.\n";
        return message;
    }

    //TODO - Sendto always returns one string (or null)...An ArrayList here is probably a bad implementation =\
    private ArrayList<String> sendTo(HashMap<String,String> data){
        String regID = data.get("reg_id");
        ArrayList<String> sendTo = null;
        if (null != regID){
            sendTo = new ArrayList<String>();
            sendTo.add(regID);
        }
        return sendTo;
    }


    public Object verifyDetail(String communityMemberId) {
        //data contain onlt cmid as string
        int cmid = Integer.parseInt(communityMemberId);
        //ArrayList<String> mail = null;
        HashMap<String,String> data = null;
        HashMap<String, String> details =  verification.getUserByCmid(cmid);
        String regid = details.get("reg_id");
        //method how to send data mail/sms
        int authMethod= authMethod =
                dbController.getAuthenticationMethod("'" + details.get("state")+ "'");;
        ArrayList<String> target = new ArrayList<String>();
        target.add(regid);
        HashMap<String,String> dataFilter = null;
        changeStatusToVerifyDetailAndSendToApp(cmid,regid, target,details);

        if (verification.ifTypeISPatientOrGuardian(regid)) {

            //dataFilter = verification.getPatientAndFillterDataToSendDoctor(cmid,regid);
            HashMap<String,String> doctorData = verification.getSupervision(details.get("P_supervision.doc_license_number"));
            doctorData.put("email_subject", "Confirm wating patient");
            doctorData.put("message", "you have new wating patient for your verification" + ".\n"
                    + " please enter to your website! ");

            data =
                    verification.generateDataForAuth(doctorData, authMethod);

        }
        //if is a doctor
        else {
            dataFilter = verification.fillterDoctorData(details); //need to implement
            //data = Authorizer;
            HashMap<String,String> dataAuthorizer = null;
            dataAuthorizer = verification.getdoctorsAuthorizer(regid,dataFilter);
            dataAuthorizer.put("email_subject","Doctor Authorization for Socmed App");
            dataAuthorizer.put("first name", "doctors");
            dataAuthorizer.put("last_name", "authorizer");
            dataAuthorizer.put("message",verification.generateMessgeForVerfictionDoctor(data) );

            data =
                    verification.generateDataForAuth(dataAuthorizer, authMethod);

        }
        ICommController commAuthMethod = new ModelsFactory().determineCommControllerVersion();
        commAuthMethod.setCommOfficial(data,authMethod);
        //Communicate authorization (email/sms/...)
        commAuthMethod.sendMessage();
        return null;
    }

    private void sendMailD(String emailAddress, String emailMessage, String subject) {
        //ToDo:need to get in generic way the state
        //we  dont need state here
        int authMethod = dbController.getAuthenticationMethod("'israel'");
        HashMap<String, String> mail =  verification.generateDataForAuthD(emailAddress, emailMessage, subject, authMethod);
        ICommController commAuthMethod = new ModelsFactory().determineCommControllerVersion();
        commAuthMethod.setCommOfficial(mail,authMethod);
        //Communicate authorization (email/sms/...)
        commAuthMethod.sendMessage();
    }

    private void changeStatusToVerifyDetailAndSendToApp(int cmid, String code,
                                                        ArrayList<String> target,
                                                        HashMap<String, String> data) {
        String status = verification.getStatus(data);
        if (status.equals("verifying email"))
        {
            dbController.updateStatus(cmid, "'verifying email'", "'verifying details'");
            // ToDo:why do we need again to send 'wait' message?
            //if (verification.ifTypeISPatientOrGuardian(code)) {
                HashMap<Integer, HashMap<String, String>> send =
                        verification.changeStatusToVerifyDetailAndSendToApp(data);
            if (verification.ifTypeISPatientOrGuardian(code)) {
                commController.setCommToUsers(send,
                        target, false);
            }
            else
            {
                //send to doctor
                commController.setCommToUsers(send,
                        null, false);
            }
            commController.sendResponse();
            //}
        }
    }

//-----------------------------------------------------------------------
    private HashMap<Integer,HashMap<String,String>> buildResponeWithOnlyRequestID(String message,
                                                                                  String code)
    {
        HashMap<Integer,HashMap<String,String>> res = new HashMap<Integer,HashMap<String,String>>();
        HashMap<String,String> dataToSend = new HashMap<String, String>();
        dataToSend.put("RequestID", code);
        dataToSend.put("message", message);
        res.put(1, dataToSend);
        return res;
    }



    //need to do
    public Object responeByDoctor(HashMap<String, String> data) {
        HashMap<Integer,HashMap<String,String>> response;

        int cmid = Integer.parseInt(data.get("community_member_id"));
        String reason = data.get("reason");
        String password = data.get("password");
        String regid = data.get("reg_id");
        ArrayList<String> target = new ArrayList<String>();
        target.add(regid);
        if (checkCmidAndPassword(password, cmid)) {
            if (reason == null) {
                dbController.updateStatus(cmid, "'verifying details'", "'Active'");
                if (verification.ifTypeISPatientOrGuardian(regid)) {
                    response =  verification.proccesOfOkMember(cmid);
                    commController.setCommToUsers(response, target, false);
                    commController.sendResponse();
                }
            }
            else
            {
                //if is a doctor
                if (!verification.ifTypeISPatientOrGuardian(regid))
                {
                    dbController.updateStatus(cmid, "'verifying details'", "'reject by authentication'");
                    return null;
                }
                else {
                    response = buildRejectMessage(cmid, reason);
                    commController.setCommToUsers(response, target, false);
                    commController.sendResponse();
                }
            }
        }   //verification.responeDoctor(cmid, reason,regid);
        return null;
    }
//--------------------------------------------------------------
    private boolean checkCmidAndPassword(String password, int cmid) {
        HashMap<String,String> data = verification.getUserByCmid(cmid);
        String email = data.get("email_address");
        data = dbController.getLoginDetails("'" +email + "'");
        String pas = data.get("password");
        return pas.equals(password);
    }
//-----------------------------------------------------------------------------
    public Object responeToDoctorIfHeAccept(HashMap<String,String> details)
    {
        HashMap<Integer,HashMap<String,String>> response = new
                HashMap<Integer,HashMap<String,String>>();

        String email = details.get("email_address");
        //reject
        int type = verification.checkIfDoctorIsaccept(email);
        if (0 == type) {
            int cmid = Integer.parseInt(details.get("community_member_id"));
            dbController.deleteUser(cmid);
            response = buildRejectMessage(cmid, "reject_by_authentication");
            commController.setCommToUsers(response, null, false);
        }
        //accept
        if (1 == type)
        {
            response.get(1).put("RequestID", "Active");
            commController.setCommToUsers(response, null, false);
        }
        //in other status
        else
        {
            // is equal to 2
            response.get(1).put("RequestID", "wait");
            commController.setCommToUsers(response, null, false);
        }
        return commController.sendResponse();
    }

//-------------------------------------------
    public Object resendAuth(HashMap<String, String> data) {

        int cmid = Integer.parseInt(data.get("community_member_id"));
        String password = data.get("password");
        String regid = data.get("reg_id");
        String requestID = null;
        String message = null;
        //verify cmid and password
        if(checkCmidAndPassword(password, cmid)){
            //get auth method
            String state = data.get("state");
            int authMethod = dbController.getAuthenticationMethod("'" + state + "'");
            //get all useer details
            HashMap<String,String> details = verification.getUserByCmid(cmid);
            switch(authMethod){
                case 0:{
                    String email = data.get("email_address");
                    //verify mail doesn't already exist in the system
                    if (!verification.checkCondForResendMail(details, email, cmid)){
                        requestID = "rejectResend";
                        message = "Invalid email! Please try again...";
                        break;
                    }
                    //if the user's email in the db isn't the same as specified in the request
                    if (!details.get("email_address").equals(email)) {
                        //change in the db
                        updateUserMail(email, cmid);
                        //change in the curr func
                        details.put("email_address",email);
                    }
                    break;
                }
                case 1:{
                    //TODO - To be implemented in later versions
                    //String phone = data.get("phone_number");
                    break;
                }
                default:{
                    //throw some nasty error?
                    return null;
                }
            }
            //get and send the auth mail/sms/...
            //ToDo:we need to check if we need to resend....
            HashMap<String, String> dataForAuth = verification.generateDataForAuth(details, authMethod);
            ICommController commAuthMethod = new ModelsFactory().determineCommControllerVersion();
            commAuthMethod.setCommOfficial(dataForAuth, authMethod);
            commAuthMethod.sendMessage();

            requestID = "waitResend";
            message = "Resend successful!";
        }
        //failed to verify credentials - communicate that failure
        else {
            //TODO - Do we have a request code for invalid credentials?? If not - we need one!!
            requestID = "invalidCredentials";
            message = "Invalid credentials! Please try again...";
        }
        //determine who to send to
        ArrayList<String> target = new ArrayList<String>();
        target.add(regid);
        //send
        commController.setCommToUsers(buildResponeWithOnlyRequestID(message, requestID), target, false);
        return commController.sendResponse();
    }
//------------------------------------------------------

    private boolean ifTypeISPatientOrGuardian(String regid) {
        return !regid.equals("0");
    }
    //------------------------------------------------------------------
    //TODO- Not for prototype for future releases
    private Object resendSMS(HashMap<String, String> data) {
        return null;
    }

    //ToDo:don't we need to check if this cmid is of a doctor?
    public Object getWaitingForDoctor(int doctorCmid) {
        //Pull from the db the list of patient that are pending the doctor's confirmation
        ArrayList<String> listOfPatients = dbController.getWaitingPatientsCMID(doctorCmid);

        HashMap<Integer,HashMap<String,String>> response = new HashMap<Integer,HashMap<String,String>>();
        //for each cmid in the list received - filter fields and add to the response
        int index = 1;
        for(String currCmid : listOfPatients){
            HashMap<String,String> whereConditions = new HashMap<String, String>();
            whereConditions.put("P_CommunityMembers.community_member_id", currCmid);
            response.put(index,registrator.filterFieldsForDoctorAuth(dbController.getUserByParameter(whereConditions)));
            //TODO -Shmulik we receive an integer here!
            //shmulik- this is not my function
            index++;
        }
        //ToDo:we need to add reject codes for doctors....
        //determine how to send the data - initiated communication so use "false"
        commController.setCommToUsers(response,null,false);
        //send the data
        return commController.sendResponse();
    }

    public Object signIn(HashMap<String,String> details)
    {
        // verify log-in details
        HashMap<Integer,HashMap<String,String>> response = verification.verifySignIn(details);

        // Sign in of doctor/ems
        if(details.get("reg_id") == "0")
        {
            commController.setCommToUsers(response, null, false);
        }
        // Sign-in of patient
        else
        {
            ArrayList<String> target = new ArrayList<String>();
            target.add(details.get("reg_id"));
            commController.setCommToUsers(response, target, false);
        }
        // Sends response to the proper user
        return commController.sendResponse();
    }
    //------------------------------------

    private HashMap<Integer,HashMap<String,String>> buildRejectMessage(int cmid, String Reason) {
        dbController.updateStatus(cmid, "'verifying details'", "'active'");
        HashMap<Integer,HashMap<String,String>> responseToPatient =
                new HashMap<Integer,HashMap<String,String>>();
        HashMap<String,String> response = new HashMap<String, String>();
        response.put("RequestID", "Reject");
        response.put("reason", Reason);
        responseToPatient.put(1, response);
        return responseToPatient;
    }
//-----------------------------------------------------
    private void updateUserMail(String mail, int cmid) {

        HashMap<String, String> member = new HashMap<String, String>();
        member.put("email_address", "'" + mail + "'");
        member.put("community_member_id", Integer.toString(cmid));
        dbController.updateUserDetails(member);
        member.remove("email_address");
        dbController.updateTable("MembersLoginDetails", member, "email_address", "'" + mail + "'");

    }
}
