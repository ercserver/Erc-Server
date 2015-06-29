package registrationModule.src.api;

import java.util.HashMap;

/**
 * Created by NAOR on 06/04/2015.
 */
public interface IRegVerify {
//bla
    //after member accept his mail we apply this function
    Object verifyDetail(String communityMemberId);


    Object resendAuth(HashMap<String, String> data);

    // if doctor reject we send reason in string reason
    //else we send null
    Object responeByDoctor(HashMap<String, String> data, boolean isConfirmation);


    //TODO
    Object responeToDoctorAturization(String cmid,boolean isAccept);

    Object signIn(HashMap<String,String> details);

    // TODO: Ohad
    Object handleForgotPassword(HashMap<String, String> data);

}
