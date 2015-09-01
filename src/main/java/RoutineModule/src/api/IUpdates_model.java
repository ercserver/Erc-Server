package RoutineModule.src.api;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Maor on 02/05/2015.
 */
public interface IUpdates_model {
    HashMap<Integer,HashMap<String,String>> getFieldsForUpdate(HashMap<String, String> data);
    HashMap<String, String>  getCommunicationParameters(int cmid,String type);

    HashMap<String,String> buildBasicResponse(String message,String code, String newVal);
    void updateUserDetails(int cmid,String col,String value);

    boolean FieldneedRefresh(Map.Entry<Integer, HashMap<String, String>> objs);

    boolean checkIfWeFinishWithOnePatient(int i, int cmid, int tempCmid, Map.Entry<Integer, HashMap<String, String>> objs);

    HashMap<String, String> CheckIfNeedVerifyAndUpdateOrSendToVer(int cmid,HashMap<String, String> obj);

    void updateUrgentInRefreshDetailsTimeToField(int cmid, HashMap<String, String> obj);

    String getCurrentStatusOfPatient(int cmid);

    HashMap<String,String> forgotPassword(String email, HashMap<String, String> userD, int authMethod);
}
