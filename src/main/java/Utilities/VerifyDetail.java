package Utilities;

import CommunicationModule.src.api.ICommController;
import DatabaseModule.src.api.IDbController;
import registrationModule.src.api.IRegVerify_model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by User on 21/05/2015.
 */
public class VerifyDetail {


    private IRegVerify_model verification = null;
    private IDbController dbController = null;
    private PatientDetails memberDetail = null;
    private ICommController commController = null;
    //private HashMap<Integer, HashMap<String, String>> response = null;

    public VerifyDetail(){
        ModelsFactory models = new ModelsFactory();
        //commController = models.determineCommControllerVersion();
        dbController = models.determineDbControllerVersion();
        //registrator = models.determineRegRequestVersion();
        verification = models.determineRegVerifyVersion();
        memberDetail = new PatientDetails();
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
                dbController.getAuthenticationMethod("'" + details.get("state")+ "'");
        ArrayList<String> target = new ArrayList<String>();
        target.add(regid);
        HashMap<String,String> dataFilter = null;
        changeStatusToVerifyDetailAndSendToApp(cmid,regid, target,details);

        if (verification.ifTypeISPatientOrGuardian(regid)) {

            //dataFilter = verification.getPatientAndFillterDataToSendDoctor(cmid,regid);
            HashMap<String,String> doctorData = verification.getSupervision(details.get("P_supervision.doc_license_number"));
            doctorData.put("Subject", "Confirm wating patient");
            doctorData.put("Message", "you have new wating patient for your verification" + ".\n"
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
            dataAuthorizer.put("Subject","Doctor Authorization for Socmed App");
            dataAuthorizer.put("first name", "doctors");
            dataAuthorizer.put("last_name", "authorizer");
            dataAuthorizer.put("Message",verification.generateMessgeForVerfictionDoctor(data) );

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
        dbController.updateStatus(cmid, status , "verifying details");
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
