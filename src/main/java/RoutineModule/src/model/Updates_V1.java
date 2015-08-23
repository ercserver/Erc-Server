package RoutineModule.src.model;

import DatabaseModule.src.api.IDbController;
import RoutineModule.src.api.IUpdates_model;
import Utilities.CommunicationParameters;
import Utilities.ModelsFactory;
import Utilities.PatientDetails;
import Utilities.SendAssistant;

import java.util.*;

/**
 * Created by Maor on 02/05/2015.
 */
public class Updates_V1 implements IUpdates_model {

    private IDbController dbController = null;
    private CommunicationParameters comParam = null;
    private PatientDetails memberDetail = null;
    private SendAssistant sendAssist = null;

    public Updates_V1() {
        ModelsFactory models = new ModelsFactory();
        dbController = models.determineDbControllerVersion();
        comParam = new CommunicationParameters();
        memberDetail = new PatientDetails();
        sendAssist = new SendAssistant();
    }

    public HashMap<Integer,HashMap<String,String>> getFieldsForUpdate(HashMap<String, String> data)
    {

                //new HashMap<Integer,HashMap<String,String>>();
        int cmid = Integer.parseInt(data.get("community_member_id"));

        HashMap<Integer,HashMap<String,String>> dataToSend =
                dbController.getRegistrationFields(dbController.getUserType(data.get("community_member_id")));

        HashMap<String,String> userD = memberDetail.getUserByCmid(cmid);
        dataToSend.get(1).put("RequestID", "updateDetails");
        for (Map.Entry<Integer,HashMap<String,String>> fields : dataToSend.entrySet())
        {
            HashMap<String, String> field = fields.getValue();
            //field.containsKey()
            String name = field.get("field_name");
            String value = data.get(name);
            field.put("value",value);
        }
        return dataToSend;
    }

    @Override
    public HashMap<String, String> getCommunicationParameters(int cmid,String type) {
       // HashMap<Integer,HashMap<String, String>> ret =
       //         new HashMap<Integer,HashMap<String, String>>();

        HashMap<String, String> ret = new HashMap<String, String>();


        if (type.equals("setConnectServerFrequency"))
        {
            ret.putAll(comParam.getFrequency("'connect_server_frequency'"));
        }

        if (type.equals("setTimesToConnectToServer")) {
            ret.putAll(comParam.getFrequency("'times_to_connect_to_server'"));
        }

        String reg = memberDetail.getRegId(cmid);
        if (memberDetail.ifTypeISPatientOrGuardian(reg)) {
            if (type.equals("setLocationFrequency")) {
                ret.putAll(comParam.getFrequency("'location_frequency'"));
            }
            if (type.equals("setCallingDefault")) {
                ret.putAll(comParam.getDefaultInEmergency(memberDetail.getState(cmid)));
            }
        }
        return ret;
    }

    @Override
    public HashMap<String, String> buildBasicResponse(String message,
                                                      String code) {
        return sendAssist.buildBasicRespone(message,code);
    }


    public void updateUserDetails(int cmid,String col,String value) {
            HashMap<String, String> member = new HashMap<String, String>();
            member.put(col, "'" + value + "'");
            member.put("community_member_id", Integer.toString(cmid));
            dbController.updateUserDetails(member);
    }


    private boolean compareDate(Date d1,Date d2,int refreseFreq )
    {
        long diff = d1.getTime() - d2.getTime();
        int diffDays =  (int) (diff / (24* 1000 * 60 * 60));
        return  diffDays >= refreseFreq;
    }

    @Override
    public boolean FieldneedRefresh(Map.Entry<Integer, HashMap<String, String>> objs) {
        HashMap<String, String> obj = objs.getValue();
        Calendar timeRef = Calendar.getInstance();
        Date d1 = new Date(String.valueOf(timeRef.getTime()));
        String lastUpdate = obj.get("last_update_time");
        Date d2 = new Date(lastUpdate);
        int timeToRefresh = Integer.parseInt(obj.get("refresh_time"));
        return compareDate(d1,d2,timeToRefresh);
    }

    @Override
    public boolean checkIfWeFinishWithOnePatient(int i, int cmid, int tempCmid,Map.Entry<Integer, HashMap<String, String>> objs) {
        if (i == 1) {
            cmid = objs.getKey();
            tempCmid = cmid;
        }
        else
        {
            tempCmid = objs.getKey();
        }
        return tempCmid == cmid;
    }

    @Override
    public HashMap<String, String> CheckIfNeedVerifyAndUpdateOrSendToVer(int cmid,HashMap<String, String> data) {
        boolean needVer = false;
        String reg = data.get("redId");
        //TODO- how i get user type
        int type = dbController.getUserType(String.valueOf(cmid));

        data.remove("user_type");// ?????
        data.remove("redId"); // ???
        Set<String> keys = data.keySet();
        for (String fieldName : keys) {
            //set  to urget to 0
            dbController.updateUrgentInRefreshDetailsTime
                    (cmid, data.get(fieldName), 0);
            HashMap<String, String> filedData = dbController.getFieldDetails(fieldName,
                    String.valueOf(type) );
            if (filedData.get("needs_verification").equals("1")) {
                needVer = true;
            } else {
                //update in table
                updateUserDetails(cmid, fieldName,data.get(fieldName));
            }
        }
        if (needVer)
            return data;
        else
            return null;
    }

    @Override
    public void updateUrgentInRefreshDetailsTimeToField(int cmid, HashMap<String, String> data) {

        Set<String> keys = data.keySet();
        keys.remove("community_member_id");
        keys.remove("password");
        for (String fieldName : keys) {
            dbController.updateUrgentInRefreshDetailsTime
                    (cmid, fieldName, 1);
        }

    }

    @Override
    public String getCurrentStatusOfPatient(int cmid) {
        HashMap<String, String> data = memberDetail.getUserByCmid(cmid);
        return memberDetail.getStatus(data);
    }

    @Override
    public HashMap<String,String> forgotPassword(String email, HashMap<String, String> userD,int authMethod) {
        String first = userD.get("first_name");
        String last =  userD.get("last_name");
        HashMap<String,String> data = new HashMap<String,String>();
        data.put("Subject","Your password for Socmed App");
        data.put("first name", first);
        data.put("last_name", last);
        data.put("Email",email);
        data.put("Message", generateMessgeForForgotPass(userD) );
        return data;

    }

    private String generateMessgeForForgotPass(HashMap<String, String> userD) {
        String first = userD.get("first_name");
        String last =  userD.get("last_name");
        return "Hi " + first + " " + last + "\n" + "Your password in Socmed App is: " + userD.get("password") +
        "\n" + "Thank you,\n" +
        "Socmed administration team.";
    }


    private String generateMessgeForVerfictionDoctor(HashMap<String, String> memberDetails)
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

}
