package registrationModule.src.controller;

import CommunicationModule.src.api.ICommController;
import DatabaseModule.src.api.IDbController;
import Utilities.AssistantFunctions;
import Utilities.ModelsFactory;
import Utilities.PatientDetails;
import org.json.JSONArray;
import org.json.JSONObject;
import registrationModule.src.api.IRegController;
import registrationModule.src.api.IRegRequest_model;
import registrationModule.src.api.IRegVerify_model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Created by NAOR on 06/04/2015.dd
 */
public class RegController_V1 implements IRegController {
    private IRegRequest_model registrator = null;
    private IRegVerify_model verification = null;
    private IDbController dbController = null;
    private ICommController commController = null;
    private HashMap<Integer, HashMap<String, String>> response = null;
    private AssistantFunctions assistantFuncs = null;
    private PatientDetails patientD = null;

    Logger logger = Logger.getLogger(this.getClass().getName());

    public RegController_V1(){


        ModelsFactory models = new ModelsFactory();
        commController = models.determineCommControllerVersion();
        dbController = models.determineDbControllerVersion();
        registrator = models.determineRegRequestVersion();
        verification = models.determineRegVerifyVersion();
        assistantFuncs = new AssistantFunctions();
        patientD = new PatientDetails();
    }

    public Object getRegDetails(HashMap<String,String> request) {
        //logger.log(Level.INFO, "In getRegDetails. params = " + request);
        //generate data to send
        HashMap<Integer,HashMap<String,String>> dataToSend =
                dbController.getRegistrationFields(Integer.parseInt(request.get("user_type")));
        dataToSend.get(1).put("RequestID", "registration");
        //determine how to send the data
        commController.setCommToUsers(dataToSend, null, false);

        //send the data

        return commController.sendResponse();
    }

    public Object handleReg(HashMap<String, String> filledForm) {
        //logger.log(Level.INFO, "In handleReg. params = " + filledForm);
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
                //set the message and code
                responseCode = setResponseCodeForRejection(filledForm.get("user_type"));
                message = appendAllMessages(messages);
            }
            //Legit form
            else{
                //set the message and code
                responseCode = "wait";
                //Get authorization method from db
                int authMethod = dbController.getAuthenticationMethod(filledForm.get("state"));
                logger.log(Level.INFO, "authMethod = " + authMethod);
                String method = (0 == authMethod) ? "mail" : "sms";
                message = "Form filled successfully. A verification " + method + " was sent to you. Please verify your registration.";
                //Add the new community member (a new CmID is generated)
                // Something was wronge in inserting new CMID
                int newCmid = dbController.addNewCommunityMember(filledForm);
                if(newCmid < 0)
                    return null;
                logger.log(Level.INFO, "After adding a new community member. cmid = " + newCmid);
                //Update status to "Verifying Email"
                dbController.updateStatus(newCmid, null, "verifying email");
                logger.log(Level.INFO, "After updating status to verifying email");
                //Generate data for the authorization message
                filledForm.put("Message", generateMessageForAuth(newCmid,filledForm.get("password")));
                filledForm.put("Subject","Confirm your email for Socmed App");
                filledForm.put("community_member_id", Integer.toString(newCmid));
                HashMap<String, String> data = verification.generateDataForAuth(filledForm,authMethod);
                //Create authorization comm
                ICommController commAuthMethod = new ModelsFactory().determineCommControllerVersion();
                commAuthMethod.setCommOfficial(data,authMethod);
                //Communicate authorization (email/sms/...)
                commAuthMethod.sendMessage();
                }
        }
        HashMap<Integer,HashMap<String,String>> dataToSend = buildResponeWithOnlyRequestID(message, responseCode);
        //determine how to send the data. Initiated communication - so use "false"
        commController.setCommToUsers(dataToSend,null,false);
        //send the data
        return commController.sendResponse();
    }

    private String generateMessageForAuth(int cmid, String password) {
        return "Thank you for registering to SocMed.\n" +
                "CMID: " + Integer.toString(cmid) + "\n" +
                "Password: " + password + "\n" +
                "Please click the following link to complete your registration:\n";
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

    private ArrayList<String> sendTo(HashMap<String,String> data){
        String regID = data.get("reg_id");
        ArrayList<String> sendTo = null;
        if (null != regID){
            sendTo = new ArrayList<String>();
            sendTo.add(regID);
            logger.log(Level.INFO, "In snedTo, regID = " + regID);
        }
        return sendTo;
    }


    public Object verifyDetail(String communityMemberId) {
        //data contain onlt cmid as string
        if (communityMemberId.isEmpty()){
            return null;
        }
        int cmid = Integer.parseInt(communityMemberId);

        //ArrayList<String> mail = null;
        HashMap<String,String> data = null;
        HashMap<String, String> details =  verification.getUserByCmid(cmid);
        String regid = details.get("reg_id");
        //method how to send data mail/sms
        int authMethod =
                dbController.getAuthenticationMethod(details.get("state"));
        ArrayList<String> target = new ArrayList<String>();
        target.add(regid);
        HashMap<String,String> dataFilter = null;

        //send details to app or website that say wait, your details checking
        changeStatusToVerifyDetailAndSendToApp(cmid,regid, target,details);

        if (verification.isPatientOrGuardian(communityMemberId)) {

            //dataFilter = verification.getPatientAndFillterDataToSendDoctor(cmid,regid);
            HashMap<String,String> doctorData = verification.getSupervision(details.get("P_supervision.doc_license_number"));
            doctorData.put("Subject", "Confirm wating patient");
            doctorData.put("Message", "you have new wating patient for your verification" + ".\n"
                    + " please enter to your website! ");
            doctorData.put("noLinks", "");
            data =
                    verification.generateDataForAuth(doctorData, authMethod);

        }
        //if is a doctor
        else {
            dataFilter = verification.fillterDoctorData(details); //need to implement
            //data = Authorizer;
            HashMap<String,String> dataAuthorizer = null;
            dataAuthorizer = verification.getdoctorsAuthorizer(regid,dataFilter);
            dataAuthorizer.put("Subject","Doctor Authorization for Socmed App");
            dataAuthorizer.put("first name", "doctors");
            dataAuthorizer.put("last_name", "authorizer");
            dataAuthorizer.put("community_member_id", communityMemberId);
            dataAuthorizer.put("confirmationOfDoctor", "");
            dataAuthorizer.put("Message",verification.generateMessgeForVerfictionDoctor(dataFilter/*data*/) );
            data =
                    verification.generateDataForAuth(dataAuthorizer, authMethod);

        }
        ICommController commAuthMethod = new ModelsFactory().determineCommControllerVersion();
        commAuthMethod.setCommOfficial(data,authMethod);
        //Communicate authorization (email/sms/...)
        commAuthMethod.sendMessage();
        return null;
    }

    private void changeStatusToVerifyDetailAndSendToApp(int cmid, String code,
                                                        ArrayList<String> target,
                                                        HashMap<String, String> data) {
        String status = verification.getStatus(data);
        if (status.equals("verifying email"))
        {
            dbController.updateStatus(cmid, "verifying email", "verifying details");
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



    //-----------------------------------------------------------------------
    //confirmtion or rejection by doctor
    public Object responseByDoctor(HashMap<String, String> data, boolean isConfirmation) {
        HashMap<Integer,HashMap<String,String>> response;
        String reason = null;
        String explanation = null;
        int cmidDoctor = Integer.parseInt(data.get("community_member_id"));
        String password = data.get("password");
        if (data.keySet().contains("reason") || data.keySet().contains("explanation")) {
            reason = data.get("reason");
            explanation = data.get("explanation");
        }


        String patientId = data.get("patient_id");

        String communityMemberId = dbController.getCmidByPatientID(patientId);
        HashMap<String,String> patientDet =
                verification.getUserByCmid(new Integer(communityMemberId ));


        String regid = patientDet.get("reg_id");
        int cmidPatient = Integer.parseInt(patientDet.get("community_member_id"));


        ArrayList<String> target = new ArrayList<String>();
        target.add(regid);
        //int cm = 0; //change
        if (checkCmidAndPassword(password, cmidDoctor)) {
            if (isConfirmation) {
                dbController.updateStatus(cmidPatient, "verifying details", "active");
                //we send regid != 0 to say that type is patient
                response =  verification.proccesOfOkMember(new Integer(communityMemberId), password);
                commController.setCommToUsers(response, target, false);
                commController.sendResponse();

            }
            else
            {
                response = buildRejectMessage(cmidPatient, reason, explanation);
                commController.setCommToUsers(response, target, false);
                commController.sendResponse();
            }
        }
        return new JSONArray().put(new JSONObject().put("status", "ok"));
    }
//--------------------------------------------------------------
    private boolean checkCmidAndPassword(String password, int cmid) {
        HashMap<String,String> data = verification.getUserByCmid(cmid);
        String email = data.get("email_address");
        data = dbController.getLoginDetails(email);
        String pas = data.get("password");
        return pas.equals(password);
    }
//-----------------------------------------------------------------------------



    public Object responeToDoctorAturization(String cmid,boolean isAccept) {
        //get user password
        String password = verification.getUserPassword(cmid);
        ArrayList<String> sendTo = new ArrayList<String>();
        sendTo = assistantFuncs.addReceiver("EMS", sendTo);
        if (isAccept) {
            dbController.updateStatus(new Integer(cmid), "verifying details", "active");
            //0 say that type is doctor
            response =  verification.proccesOfOkMember(new Integer(cmid),password);
            commController.setCommToUsers(response, sendTo, true);
        } else {
            response = buildRejectMessage(new Integer(cmid),"doctor Authorization reject you",
                    "ask Authorization");
            commController.setCommToUsers(response, sendTo, true);
        }
        return commController.sendResponse();
    }


//-------------------------------------------

    public Object resendAuth(HashMap<String, String> getData) {
        String requestID = null;
        String message = null;
        HashMap<String,String> details = null;
        //init state if get null init to israel
        String state = verification.initState(getData.get("state"));
        //get auth method
        int authMethod = dbController.getAuthenticationMethod(state);

        switch(authMethod) {
            case 0: {
                String email = getData.get("email_address");
                //get all user details
                details = verification.getUserByMail(email);
                //checks  that there is a user with this email
                if (null == details)
                {
                    requestID = "rejectResend";
                    message = "Invalid email! Please try again...";
                    break;
                }
                break;
            }
            case 1: {
                //TODO - To be implemented in later versions
                //String phone = data.get("phone_number");
                break;
            }
            default: {
                //throw some nasty error?
                return null;
            }
        }

        //get and send the auth mail/sms/...
        if  (null == requestID) {
            getData.put("Message", generateMessageForAuth(Integer.parseInt(details.get("community_member_id")),
                    details.get("password")));
            getData.put("Subject", "Resend email:\n\nConfirm your email for Socmed App");
            HashMap<String, String> dataForAuth = verification.generateDataForAuth(getData, authMethod);
            ICommController commAuthMethod = new ModelsFactory().determineCommControllerVersion();
            commAuthMethod.setCommOfficial(dataForAuth, authMethod);
            commAuthMethod.sendMessage();
            requestID = "waitResend";
            message = "Resend successful!";
        }
        //send
        commController.setCommToUsers(buildResponeWithOnlyRequestID(message, requestID), null, false);
        return commController.sendResponse();
    }

    //TODO- Not for prototype for future releases
    private Object resendSMS(HashMap<String, String> data) {
        return null;
    }

    public Object getWaitingForDoctor(HashMap<String,String> request) {
        logger.log(Level.INFO, "In getWaitingForDoctor. param = " + request);
        String password = request.get("password");
        int cmid = Integer.parseInt(request.get("community_member_id"));
        /*if(checkCmidAndPassword(password,cmid)){

        }*/
        logger.log(Level.INFO, "In getWaitingForDoctor. cmid = " + cmid);
        HashMap<Integer,HashMap<String,String>> response = new HashMap<Integer,HashMap<String,String>>();
        //Pull from the db the list of patient that are pending the doctor's confirmation
        ArrayList<String> listOfPatients = dbController.getWaitingPatientsCMID(cmid);
        logger.log(Level.INFO, "In getWaitingForDoctor: listOfPatients = " + listOfPatients);
        //user isn't a doctor - we don't serve this request. Return null
        if(null == listOfPatients || listOfPatients.isEmpty()){
            return null;
        }
        //for each cmid in the list received - filter fields and add to the response
        int index = 1;
        for(String currCmid : listOfPatients){
            logger.log(Level.INFO, "In getWaitingForDoctor: patient cmid: " + currCmid);
            HashMap<String,String> whereConditions = new HashMap<String, String>();
            whereConditions.put("P_CommunityMembers.community_member_id", currCmid);
            response.put(index,registrator.filterFieldsForDoctorAuth(dbController.getUserByParameter(whereConditions)));
            index++;
        }
        logger.log(Level.INFO, "In getWaitingForDoctor: response = " + response);
        //adding reject reasons object (with "subRequest" to make it identifiable)
        HashMap<String,String> rejectCodes = dbController.getRejectCodes();
        rejectCodes.put("RequestID","waitingPatients");
        rejectCodes.put("subRequest","rejectReasons");
        response.put(0,rejectCodes);
        //determine how to send the data - not initiated communication so use "false"
        commController.setCommToUsers(response,null,false);
        //send the data
        return commController.sendResponse();
    }

    public Object signIn(HashMap<String,String> details)
    {
        // verify log-in details
        HashMap<Integer,HashMap<String,String>> response = verification.verifySignIn(details);
        commController.setCommToUsers(response, null, false);
        // Sends response to the proper user
        return commController.sendResponse();
    }

    @Override
    public Object handleForgotPassword(HashMap<String, String> data) {
        return null;
    }

    private HashMap<Integer,HashMap<String,String>> buildRejectMessage(int cmid, String reason,
                                                                       String explanation) {
        logger.log(Level.INFO, String.format("In buildRejectMessage. params = cmid=%d, Reason=%s, explanation=%s",
                cmid, reason, explanation));
        dbController.updateStatus(cmid, "verifying details", "not authorized");
        HashMap<Integer,HashMap<String,String>> responseToPatient =
                new HashMap<Integer,HashMap<String,String>>();
        HashMap<String,String> response = new HashMap<String, String>();
        response.put("RequestID", "rejectReg");
        response.put("reason", reason);
        response.put("explanation", explanation);
        responseToPatient.put(1, response);
        return responseToPatient;
    }

    private void updateUserMail(String mail, int cmid) {

        HashMap<String, String> member = new HashMap<String, String>();
        member.put("email_address", mail);
        member.put("community_member_id", Integer.toString(cmid));
        dbController.updateUserDetails(member);
        member.remove("email_address");
        dbController.updateTable("MembersLoginDetails", member, "email_address", mail);

    }
}
