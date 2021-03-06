package registrationModule.src.model;

import DatabaseModule.src.api.IDbController;
import Utilities.ErcConfiguration;
import Utilities.HashMapBuilder;
import Utilities.ModelsFactory;
import Utilities.PatientDetails;
import registrationModule.src.api.IRegVerify_model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by User on 29/04/2015.
 */
public class RegVerify_V2 implements IRegVerify_model {

    IDbController dbController = null;
    private PatientDetails patientD = null;
    Logger logger = Logger.getLogger(this.getClass().getName());

    public RegVerify_V2() {
        ModelsFactory models = new ModelsFactory();
        dbController = models.determineDbControllerVersion();
        patientD = new PatientDetails();
    }

    /***********for func verifyDetail*********************/
    public HashMap<Integer, HashMap<String, String>> changeStatusToVerifyDetailAndSendToApp(
            HashMap<String, String> data) {

        HashMap<Integer, HashMap<String, String>> responseToPatient =
                new HashMap<Integer, HashMap<String, String>>();
        String status = getStatus(data);
        HashMap<String, String> dataToPatient = new HashMap<String, String>();
        dataToPatient.put("RequestID", "wait");
        dataToPatient.put("message", "mail confirm wait for verify your medical details");
        responseToPatient.put(1, dataToPatient);
        return responseToPatient;
    }

    public HashMap<String,String> getPatientAndFillterDataToSendDoctor(int cmid,String code) {
        if(ifTypeISDoctor(code)) {
            HashMap<String, String> member = new HashMap<String, String>();
            member.put("P_CommunityMembers.community_member_id", new Integer(cmid).toString());
            HashMap<String, String> responseToDoctor = dbController.getUserByParameter(member);
            responseToDoctor.put("RequestID", "verifyPatient");
            return filterDataForVerification(responseToDoctor);
        }
        return null;
    }

    public HashMap<String,String> getdoctorsAuthorizer(String code,HashMap<String,String> data) {
        // We could just use the method of patient type and check with 'no'
        if (ifTypeISDoctor(code)) {
            //return doctorsAuthorizer details
            return dbController.getEmailOfDoctorsAuthorizer(data.get("state"));
        }
        return null;
    }


    public String generateMessgeForVerfictionDoctor(HashMap<String, String> memberDetails)
    {
        String firstName = memberDetails.get("first_name");
        String lastName = memberDetails.get("last_name");
        String licenseNumber = memberDetails.get("doc_license_number");

        return "Please confirm/reject the following doctor be a valid doctor:\n" +
                "First Name: " + firstName + ".\n" +
                "Last Name: " + lastName + ".\n" +
                "Licence Number: " + licenseNumber + ".\n\n" +
                "Workplace details: " + "\n" +
                "   organization description: " +
                memberDetails.get("organization_description") + ".\n" +
                "   organization type description: " +
                memberDetails.get("organization_type_description") + ".\n" +
                "   position_description: " + memberDetails.get("position_description") + "\n" +
                "   email address of organization: "  +
                memberDetails.get("email_address_of_organization") +  ".\n" +
                "   org phone number: " + memberDetails.get("org_phone_number") +  ".\n" +
                "Thank you,\n" +
                "Socmed administration team.";
    }

    private boolean ifTypeISDoctor(String regid) {
        return regid.equals("0");
    }

    public boolean ifTypeISPatientOrGuardian(String code) {
        return !code.equals("0");
    }

    // need to be pravite
    public HashMap<String,String> filterDataForVerification(HashMap<String, String> data)
    {

        HashMap<String, String> fillter = new  HashMap<String, String>();

        //add medical condition description
        String medicalConditionDescription =
                getMedicalConditionDescription(data.get("medical_condition_id"));
        fillter.put("medical_condition_description", medicalConditionDescription);


        //add medication name
        String medicationName = getMedicationName( data.get("medication_num"));

        fillter.put("medication_name", medicationName);

        for (String key : data.keySet()) {
            if (key.equals("first_name") || key.equals("last_name") || key.equals("street") ||
                    key.equals("home_phone_number") || key.equals("email_address")
                    || key.equals("house_number") || key.equals("contact_phone") ||
                    key.equals("zip_code") || key.equals("birth_date") || key.equals("city") ||
                    key.equals("mobile_phone_number") || key.equals("state")
                    || key == "dosage")
                fillter.put(key,data.get(key));

            if (key.equals("gender"))
                fillter.put(key,convertCodeToGender(data.get(key)));
        }
        return fillter;
    }

    private String getMedicationName(String medicationNum) {
        HashMap<Integer, HashMap<String, String>> data = dbController.getMedicationByNum(medicationNum);
        for (Map.Entry<Integer,HashMap<String,String>> objs : data.entrySet()){
            HashMap<String,String> obj = objs.getValue();
            return obj.get("medication_name");
        }
        return null;
    }

    private String getMedicalConditionDescription(String medicalConditionId) {
        HashMap<Integer, HashMap<String, String>> data = dbController.getMedicalConditionByNum(medicalConditionId);
        for (Map.Entry<Integer,HashMap<String,String>> objs : data.entrySet()){
            HashMap<String,String> obj = objs.getValue();
            return obj.get("medical_condition_description");
        }
        return null;
    }

    /***********for func resendMail*********************/
    public String getRegId(int cmid)
    {
        HashMap<Integer, HashMap<String, String>> reg_id = dbController.getRegIDsOfUser(cmid);
        String reg = "0";
        for (Map.Entry<Integer,HashMap<String,String>> objs : reg_id.entrySet()){
            HashMap<String,String> obj = objs.getValue();
            //return reg id from data
            reg = obj.get("reg_id");
        }
        //send "0"
        return reg;
    }

    @Override
    public String getUserPassword(String cmid) {
        HashMap<String, String> data = patientD.getUserByCmid(Integer.parseInt(cmid));
        return data.get("password");
    }

    @Override

    public String initState(String state) {

        if (null == state)
        {
            //init difult state to israel
            state = "israel";
        }
        return state;
    }

    public HashMap<String, String> getUserByCmid(int cmid) {
        logger.log(Level.INFO, "In getUserByCmid. Parameters = " + cmid);
        HashMap<String, String> member = new HashMap<String, String>();
        member.put("P_CommunityMembers.community_member_id", new Integer(cmid).toString());
        HashMap<String, String> details = dbController.getUserByParameter(member);
        logger.log(Level.INFO, "details = " + details);
        HashMap<Integer, HashMap<String, String>> reg_id = dbController.getRegIDsOfUser(cmid);
        String reg = "0";
        for (Map.Entry<Integer,HashMap<String,String>> objs : reg_id.entrySet()){
            HashMap<String,String> obj = objs.getValue();
            reg = obj.get("reg_id");
        }
        details.put("reg_id",reg);
        return details;
    }

    public HashMap<String, String> getUserByMail(String mail) {

        //Prepares the data to use the function getUserByParameter
        HashMap<String, String> member = new HashMap<String, String>();
        member.put("P_CommunityMembers.email_address",  mail );
        HashMap<String, String> details = dbController.getUserByParameter(member);
        return details;
    }

    public String getStatus(HashMap<String, String> details)
    {
        String status_num = details.get("status_num");
        HashMap<Integer, HashMap<String, String>> data = dbController.getStatusByNum(status_num);

        for (Map.Entry<Integer,HashMap<String,String>> objs : data.entrySet()){
            HashMap<String,String> obj = objs.getValue();
            return obj.get("status_name");
        }
        return null;
    }

    public HashMap<String, String> fillterDoctorData(HashMap<String, String> details) {

        HashMap<String,String> fillter = new HashMap<String,String>();
        for (String key : details.keySet()) {

            if (key.equals("first_name") || key.equals("last_name") || key.equals("street") ||
                    key.equals("home_phone_number") || key.equals("email_address")
                    || key.equals("house_number") || key.equals("contact_phone") ||
                    key.equals("zip_code") || key.equals("birth_date") || key.equals("city") ||
                    key.equals("mobile_phone_number") || key.equals("state")
                    )
                fillter.put(key, details.get(key));
            //data that relevant only to doctor
            if (key.equals("specialization_description") || key.equals("org_house")
                    || key.equals("organization_description") || key.equals("doc_license_number")
                    || key.equals("web_site") || key.equals("org_city") ||
                    key.equals("org_street") ||  key.equals("org_state") ||
                    key.equals("remarks") || key.equals("organization_type_description") ||
                    key.equals("position_description") || key.equals("org_phone_number") ||
                    key.equals("org_city") || key.equals("email_address_of_organization")
                    || key.equals("fax_number")
                    )
            {
                fillter.put(key, details.get(key));
            }
            if (key.equals("gender"))
                 fillter.put(key,convertCodeToGender(details.get(key)));

        }
        //need to add this
        // //mybey  depend on doctor_id
        return fillter;
    }

    @Override
    public HashMap<String, String> getSupervision(String docLicence) {

        // Prepares the data to use the function getDoctor
        HashMap<String,String> whereConditions = new HashMap<String, String>();
        whereConditions.put("doc_license_number", docLicence);
        HashMap<Integer, HashMap<String, String>> data = dbController.getDoctor(whereConditions);

        for (Map.Entry<Integer,HashMap<String,String>> objs : data.entrySet()){
            HashMap<String,String> obj = objs.getValue();
            // get cmid of Supervision
            int cmid =  new Integer(obj.get("community_member_id"));
            //Get all the data on it and return
            HashMap<String,String> doctor = getUserByCmid(cmid);
            return doctor;
        }
        return null;
    }






    //not for prototype
    private HashMap<String,String> generateVerificationOfPatientForSMS(HashMap<String, String> doctorData) {
        return null;
    }



    public boolean checkCondForResendMail(HashMap<String, String> details, String email, int cmid) {
        String status = getStatus(details);
        String currentEmail = details.get("email_address");

        //if we  resend mail to hos current mail
        //we need to check that this mail dont approval
        if (currentEmail.equals(email)) {
            return status.equals("verifying email");
        }
        //if member change his mail we need to check that
        //is mail not exist in another user
        else {
            return getUserByMail(email) == null;
        }



    }

    /***********for func responeDoctor********************/

    public HashMap<Integer,HashMap<String,String>> proccesOfOkMember(int cmid/*,String type,*/, String password)
    {
        HashMap<Integer,HashMap<String,String>> responseToPatient =
                new HashMap<Integer,HashMap<String,String>>();

        HashMap<String,String> response = new HashMap<String, String>();
        response.put("community_member_id",new Integer(cmid).toString());
        response.put("password", password);
        response.put("RequestID", "active");

        responseToPatient.put(1, response);
        responseToPatient.putAll(getAllFrequencies());

        return responseToPatient;
    }


    public int checkIfDoctorIsaccept(String email)
    {
        HashMap<String, String> member = new HashMap<String, String>();
        member.put("P_CommunityMembers.email_address", email);
        HashMap<String, String> details = dbController.getUserByParameter(member);
        String status = getStatus(details);
        if (status.equals("reject by authentication"))
            return 0;
        if (status.equals("Active"))
            return 1;
        else
            // his status equal to verify email or details
            return 2;
    }

    public HashMap<Integer,HashMap<String,String>> verifySignIn(HashMap<String,String> details)
    {
        HashMap<String,String> conds = new HashMap<String,String>();
        //conds.put("P_CommunityMembers.community_member_id", details.get("community_member_id"));
        conds.put("MembersLoginDetails.password", details.get("password") );
        conds.put("MembersLoginDetails.email_address", details.get("email_address") );
        // gets the user according to the givven sign-in data
        HashMap<String,String> user = dbController.getUserByParameter(conds);
        HashMap<Integer,HashMap<String,String>> response = new HashMap<Integer,HashMap<String,String>>();
        HashMap<String,String> res = new HashMap<String,String>();
        // wrong details(probably password)-rejects sign-in
        if (user == null)
            res.put("RequestID", "reject");
        // correct log-in details-accept sign-in
        else
        {
            res.put("RequestID", "accept");
            res.put("community_member_id", user.get("community_member_id"));
            res.put("status_name", user.get("status_name"));

            int userType = dbController.getUserType(user.get("community_member_id"));
            if (userType == 1 || userType == 3 ){
                // Add the organization name and id
                res.put("organization_id", user.get("organization_id"));
                res.put("organization_description", user.get("organization_description"));
            }
        }
        response.put(1, res);
        return response;
    }

    public ArrayList<String> verifyFilledForm(HashMap<String, String> filledForm) {
        ArrayList<String> errorMessages = new ArrayList<String>();

        int userType = Integer.parseInt(filledForm.get("user_type"));
        switch(userType){
            //patient
            case 0:{
                if(!doesDoctorExist(filledForm)){
                    errorMessages.add("One of the specified doctors does not exist in the system!");
                    //if{....}
                    //more things to verify....
                    //
                }
                break;
            }
            //doctor
            case 1:{
                //need to verify something in this stage?
                break;
            }
            //guardian
            case 2:{
                //need to verify something in this stage?
                break;
            }
            //EMS
            case 3:{
                //need to verify something in this stage?
                break;
            }
            default:{
                return null;
            }
        }
        return errorMessages;
    }

    public HashMap<String, String> generateDataForAuth(HashMap<String, String> filledForm, int authMethod) {
        //Generate data for the authentication object to be created according to the authentication method
        switch(authMethod){
            case 0:{
                return generateVerificationForMail(filledForm);
        }
            case 1:{
                return generateVerificationForSMS(filledForm);
            }/*
            case x:{
                return generateVerificationForXXXXX();
            }
            */
            default:{
                return null;
            }
        }
    }

    public HashMap<String, String> generateDataForAuthD(String access, String message, String subject, int authMethod) {
        //Generate data for the authentication object to be created according to the authentication method
        switch(authMethod){
            case 0:{
                return generateVerificationForMailD(access, message, subject);
            }
            case 1:{
                return generateVerificationForSMSD(access, message, subject);
            }/*
            case x:{
                return generateVerificationForXXXXX();
            }
            */
            default:{
                return null;
            }
        }
    }

    private String getState(int cmid) {
        HashMap<String,String> member = new HashMap<String,String>();
        member.put("P_CommunityMembers.community_member_id",new Integer(cmid).toString());
        //Receiving data on the user
        HashMap<String,String> details = dbController.getUserByParameter(member);
        //Pull the country from all data
        return details.get("state");
    }

    private HashMap<String, String> getDefaultInEmergency(String state) {

        //get code of DefaultInEmergency
        HashMap<Integer,HashMap<String,String>> defult
                = dbController.getDefaultInEmergency(state);

        for (Map.Entry<Integer,HashMap<String,String>> objs : defult.entrySet()){
            HashMap<String,String> obj = objs.getValue();
            String code = obj.get("default_caller");
            HashMap<String,String> response = convertCodeToDefaultCallerSettings(code);
            //return value of who default in emergency
            return response;

        }
        return null;
    }

    public String convertCodeToGender(String code) {
        //Prepares the data to use the function convertCodeToGender
        HashMap<String,String> defalut = new HashMap<String,String>();
        defalut.put("column_name","gender");
        defalut.put("enum_code", code );
        defalut.put("table_name","P_CommunityMembers");
        //get gender
        HashMap<Integer, HashMap<String, String>> data =
                dbController.getFromEnum(defalut);

        //teturn gender type as a string
        for (Map.Entry<Integer,HashMap<String,String>> objs : data.entrySet()){
           HashMap<String,String> obj = objs.getValue();
           return obj.get("enum_value");
        }
        return null;
    }

    public HashMap<String, String> convertCodeToDefaultCallerSettings(String code) {
        //Prepares the data to use the function getFromEnum
        HashMap<String,String> defalut = new HashMap<String,String>();
        defalut.put("column_name","default_caller");
        defalut.put("enum_code", code );
        defalut.put("table_name","DefaultCallerSettings");

        //get DefaultCallerSettings value
        HashMap<Integer, HashMap<String, String>> data =
                dbController.getFromEnum(defalut);


        for (Map.Entry<Integer,HashMap<String,String>> objs : data.entrySet()){
            HashMap<String,String> response = new HashMap<String,String>();
            HashMap<String,String> obj = objs.getValue();
            response.put("name","default_caller");
            response.put("frequency",obj.get("enum_value")); // ???
            //Returns a map containing the name default caller and its value
            return response;
        }
        return null;
    }

    //Get the value of the frequency according to its name
    private HashMap<String,String> getFrequency(String code) {
        HashMap<String,String> response = new HashMap<String,String>();

        HashMap<String,String> kindOfFrequency = new HashMap<String,String>();
        kindOfFrequency.put("name",code);
        //Get the value of the frequency according to its name
        HashMap<Integer,HashMap<String,String>> freq
                = dbController.getFrequency(kindOfFrequency);
        if (freq == null)
            return null;
        for (Map.Entry<Integer,HashMap<String,String>> objs : freq.entrySet()){
            HashMap<String,String> obj = objs.getValue();

            response.put("name",obj.get("name"));
            response.put("frequency",obj.get("frequency"));
            //Returns a map containing the name frequency and its value
            return response;
        }
        return null;
    }

    private HashMap<Integer, HashMap<String,String>> getAllFrequencies() {
        //Get all the frequency needed to send in the registration process
        HashMap<Integer,HashMap<String,String>> freq
                = dbController.getAllFrequencies();
        if (freq == null)
            return null;
        HashMap<Integer, HashMap<String, String>> response = new HashMap<Integer, HashMap<String, String>>();
        int i = 2;
        //Passing on any frequency and creates a message to send
        for (Map.Entry<Integer,HashMap<String,String>> objs : freq.entrySet()){
            HashMap<String,String> obj = objs.getValue();

            response.put(i, new HashMapBuilder<String, String>().put("name", obj.get("name"))
                    .put("frequency",obj.get("frequency")).build());
            i++;
        }
        return response;
    }

    private boolean doesDoctorExist(HashMap<String,String> data) {
        //generate list of doctors licence fields to be checked against the database
        ArrayList<String> doctorsFields = doctorFields();
        //check each field against the database
        for (String field : doctorsFields){
            //if one of the doctors does not exists in the database - return false
            //TODO - For prototype we assume only one country exists - for later versions probably a country will also have to integrated ito this method
            if(!dbController.doesDoctorExists(data.get(field))){
                return false;
            }
        }
        //all doctors exist - return true
        return true;
    }
    private ArrayList<String> doctorFields(){
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("P_supervision.doc_licence_num");
        fields.add("P_prescriptions.doc_licence_num");
            fields.add("P_diagnosis.doc_licence_num");

        return fields;
    }

    private HashMap<String, String>  generateVerificationForSMSD(String access, String message, String subject) {
        return null;
    }

    private HashMap<String,String> generateVerificationForMailD(String access, String message, String subject)
    {
        HashMap<String,String> generatedAuthMail = new HashMap<String, String>();
        generatedAuthMail.put("Subject", subject);
        generatedAuthMail.put("Message", message);
        generatedAuthMail.put("Email", access);

        return generatedAuthMail;
    }

    private HashMap<String,String> generateVerificationForMail(HashMap<String, String> data){
        String firstName = data.get("first_name");
        String lastName = data.get("last_name");
        String emailAddress = data.get("email_address");
        String emailMessage = "Dear " + firstName + "  " + lastName + ",\n\n" +
                data.get("Message") + generateMailLinkForVerifications(data);

        String emailSubject =  data.get("Subject");

        HashMap<String,String> generatedAuthMail = new HashMap<String, String>();
        generatedAuthMail.put("Subject", emailSubject);
        generatedAuthMail.put("Message", emailMessage);
        generatedAuthMail.put("Email", emailAddress);

        return generatedAuthMail;
    }

    private String generateMailLinkForVerifications(HashMap<String, String> data) {
        if(data.keySet().contains("noLinks"))
            return "";
        logger.log(Level.INFO, "In mailVerification. parameters = " + data);
        String cmid = data.get("community_member_id");
        String rv = "server_error";
        if (cmid != null){
            String key = "?key=" + cmid;
            if(data.containsKey("confirmationOfDoctor")){
                rv = String.format("%s%s&accept=true\n%s%s&accept=false",
                        ErcConfiguration.AUTH_DOCTOR_URL,
                        key,
                        ErcConfiguration.AUTH_DOCTOR_URL,
                        key);

            }else{
                // Mail authorization
                rv = String.format("%s%s", ErcConfiguration.VERIFY_EMAIL_URL, key);
            }
        }
        return rv;
    }


    //TODO - Not for prototype for future releases only
    private HashMap<String,String> generateVerificationForSMS(HashMap<String, String> data){
        return null;
    }

    public boolean isPatientOrGuardian(String cmid)
    {
        int type = dbController.getUserType(cmid);
        return ((type == 0) || (type == 2));
    }

    public boolean isDoctorOrEMS(String cmid)
    {
        int type = dbController.getUserType(cmid);
        return ((type == 1) || (type == 3));
    }
}
