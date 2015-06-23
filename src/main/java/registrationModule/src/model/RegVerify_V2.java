package registrationModule.src.model;

import DatabaseModule.src.api.IDbController;
import Utilities.ErcConfiguration;
import Utilities.ErcLogger;
import Utilities.PatientDetails;
import registrationModule.src.api.IRegVerify_model;
import Utilities.ModelsFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 29/04/2015.
 */
public class RegVerify_V2 implements IRegVerify_model {

    IDbController dbController = null;
    private PatientDetails patientD = null;

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
        if (status.equals("verifying details"))
        {
            HashMap<String, String> dataToPatient = new HashMap<String, String>();
            dataToPatient.put("RequestID", "wait");
            dataToPatient.put("message", "mail confirm wait for verify your medical details");
            responseToPatient.put(1, dataToPatient);
            return responseToPatient;
        }
        return null;
    }

    public HashMap<String,String> getPatientAndFillterDataToSendDoctor(int cmid,String code) {
        if(ifTypeISDoctor(code)) {
            HashMap<String, String> member = new HashMap<String, String>();
            member.put("P_CommunityMembers.community_member_id", new Integer(cmid).toString());
            HashMap<String, String> responseToDoctor = dbController.getUserByParameter(member);
            //ToDo:why do we need that?
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
            //return generateMailForVerificationDoctor(data, doctorsAuthorizer);
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

/*
    private ArrayList<String> generateMailForVerificationDoctor(HashMap<String, String> memberDetails,
                                                                HashMap<String, String> doctorsAuthorizer){

*/

        /*if (key.equals("specialization_description") || key.equals("org_house")
                || key.equals("organization_description") || key.equals("doc_license_number")
                || key.equals("web_site") || key.equals("org_city") ||
                key.equals("org_street") ||  key.equals("org_state") ||
                key.equals("remarks") || key.equals("organization_type_description") ||
                key.equals("position_description") || key.equals("org_phone_number") ||
                key.equals("org_city") || key.equals("email_address_of_organization")
                || key.equals("fax_number")
                )*/



/*
        String emailAddress = doctorsAuthorizer.get("email_address");
        String emailMessage  = "Dear authorizer,\n" +
                "Please confirm/reject the following doctor be a valid doctor:\n" +
                "First Name: " + firstName + ".\n" +
                "Last Name: " + lastName + ".\n" +
                "Licence Number: " + licenseNumber + ".\n\n" +
                "Workplace details: " + "\n" +
                "   organization description: " +
                    memberDetails.equals("organization_description") + ".\n" +
                "   organization type description" +
                    memberDetails.equals("organization_type_description") + ".\n" +
                "   position_description: " + memberDetails.equals("position_description") + "\n" +
                "   email address of organization: "  +
                    memberDetails.equals("email_address_of_organization") +  ".\n" +
                "   org phone number: " + memberDetails.equals("org_phone_number") +  ".\n" +
                "Thank you,\n" +
                "Socmed administration team.";
        String subject = "Doctor Authorization for Socmed App";

        ArrayList<String> emailDetails = new ArrayList<String>();
        emailDetails.add(emailAddress);
        emailDetails.add(emailMessage);
        emailDetails.add(subject);

        return emailDetails;
    }
*/
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

    /*
    public boolean statusIsEqualTo(String s,HashMap <String,String> details) {

        return details.get("status_num").equals(s);
    }*/

    /***********for func resendMail*********************/
    public String getRegId(int cmid)
    {
        HashMap<Integer, HashMap<String, String>> reg_id = dbController.getRegIDsOfUser(cmid);
        String reg = "0";
        for (Map.Entry<Integer,HashMap<String,String>> objs : reg_id.entrySet()){
            HashMap<String,String> obj = objs.getValue();
            reg = obj.get("reg_id");
        }
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
            state = "israel";
        }
        return state;
    }

    public HashMap<String, String> getUserByCmid(int cmid) {
        ErcLogger.println("In getUserByCmid. Parameters = " + cmid);
        HashMap<String, String> member = new HashMap<String, String>();
        member.put("P_CommunityMembers.community_member_id", new Integer(cmid).toString());
        HashMap<String, String> details = dbController.getUserByParameter(member);
        ErcLogger.println("details = " + details);
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
        HashMap<String,String> whereConditions = new HashMap<String, String>();
        whereConditions.put("doc_license_number", docLicence);
        HashMap<Integer, HashMap<String, String>> data = dbController.getDoctor(whereConditions);
        for (Map.Entry<Integer,HashMap<String,String>> objs : data.entrySet()){
            HashMap<String,String> obj = objs.getValue();
            int cmid =  new Integer(obj.get("community_member_id"));
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


/*
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
*/
    /***********for func responeDoctor********************/

    //ToDo need also to send cmid, password, and location frequency in emergency
    public HashMap<Integer,HashMap<String,String>> proccesOfOkMember(int cmid,String type,String password)
    {
        HashMap<Integer,HashMap<String,String>> responseToPatient =
                new HashMap<Integer,HashMap<String,String>>();



        HashMap<String,String> response = new HashMap<String, String>();
        response.put("community_member_id",new Integer(cmid).toString());
        response.put("password", password);


        response.put("RequestID", "active");

        responseToPatient.put(3,getFrequency("connect_server_frequency"));
        responseToPatient.put(4,getFrequency("times_to_connect_to_server"));

        responseToPatient.put(1, response);
        //if is a patient
        if (!type.equals("0")) {
            responseToPatient.put(2, getFrequency("location_frequency"));

            responseToPatient.put(5, getDefaultInEmergency(getState(cmid)));
        }
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
            res.put("RequestID", "accept");
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
        HashMap<String,String> details = dbController.getUserByParameter(member);
        return details.get("state");
    }

    private HashMap<String, String> getDefaultInEmergency(String state) {
        HashMap<Integer,HashMap<String,String>> defult
                = dbController.getDefaultInEmergency(state);
        for (Map.Entry<Integer,HashMap<String,String>> objs : defult.entrySet()){
            HashMap<String,String> obj = objs.getValue();
            String code = obj.get("default_caller");
            HashMap<String,String> response = convertCodeToDefaultCallerSettings(code);
            return response;

        }
        return null;
    }

    public String convertCodeToGender(String code) {
        HashMap<String,String> defalut = new HashMap<String,String>();
        defalut.put("column_name","'gender'");
        defalut.put("enum_code", "'" + code + "'");
        defalut.put("table_name","'P_CommunityMembers'");
        HashMap<Integer, HashMap<String, String>> data =
                dbController.getFromEnum(defalut);

        //System.out.println(response);
        for (Map.Entry<Integer,HashMap<String,String>> objs : data.entrySet()){
           HashMap<String,String> obj = objs.getValue();
           return obj.get("enum_value");
        }

        return null;
    }

    public HashMap<String, String> convertCodeToDefaultCallerSettings(String code) {
        HashMap<String,String> defalut = new HashMap<String,String>();
        defalut.put("column_name","'default_caller'");
        defalut.put("enum_code", "'" + code + "'");
        defalut.put("table_name","'DefaultCallerSettings'");
        HashMap<Integer, HashMap<String, String>> data =
                dbController.getFromEnum(defalut);

        //System.out.println(response);
        for (Map.Entry<Integer,HashMap<String,String>> objs : data.entrySet()){
            HashMap<String,String> response = new HashMap<String,String>();
            HashMap<String,String> obj = objs.getValue();
            response.put("name","default_caller");
            response.put("frequency",obj.get("enum_value")); // ???
            return response;
        }
        return null;
    }

    private HashMap<String,String> getFrequency(String code) {
        HashMap<String,String> response = new HashMap<String,String>();

        HashMap<String,String> kindOfFrequency = new HashMap<String,String>();
        kindOfFrequency.put("name",code);
        HashMap<Integer,HashMap<String,String>> freq
                = dbController.getFrequency(kindOfFrequency);
        if (freq == null)
            return null;
        for (Map.Entry<Integer,HashMap<String,String>> objs : freq.entrySet()){
            HashMap<String,String> obj = objs.getValue();

            response.put("name",obj.get("name"));
            response.put("frequency",obj.get("frequency"));
            return response;
        }
        return null;
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

    //TODO- Not for prototype for future releases
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
        ErcLogger.println("In mailVerification. parameters = " + data);
        String cmid = data.get("community_member_id");
        if(data.containsKey("confirmationOfDoctor"))
        {

        }//
        String rv = ErcConfiguration.VERIFY_EMAIL_URL;
        if (cmid != null){
            rv  += "?key=" + cmid;
        }
        return rv;

    }

    //TODO - Not for prototype for future releases only
    private HashMap<String,String> generateVerificationForSMS(HashMap<String, String> data){
        return null;
    }
}
