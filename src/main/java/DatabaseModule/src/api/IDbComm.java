package DatabaseModule.src.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by NAOR on 06/04/2015.
 */
public interface IDbComm {
    //TODO - lots of methods to query the DB
    HashMap<Integer,HashMap<String,String>> getRegistrationFields(int userType);
    HashMap<String,String> getUserByParameter(HashMap<String,String> whereConditions);
    void updateUserDetails(HashMap<String,String> updates);
    HashMap<Integer,HashMap<String,String>> getFrequency(HashMap<String,String> kindOfFrequency);
    HashMap<Integer,HashMap<String,String>> getDefaultInEmergency(String state);
    HashMap<String,String> getRejectCodes();
    HashMap<Integer,HashMap<String,String>> getFromEnum(HashMap<String,String> cond);
    ArrayList<String> getWaitingPatientsCMID(int docCMID);
    void updateUrgentInRefreshDetailsTime(int CMID, String fieldName, int urgentBit);
    boolean isCommunityMemberExists(int cmid);
    int addNewCommunityMember(HashMap<String,String> details);
    int getAuthenticationMethod(String state);
    HashMap<String,String> getEmailOfDoctorsAuthorizer(String state);
    HashMap<String,String> getLoginDetails(String email);
    void updateLastRefreshTime(HashMap<String,String> params);
    HashMap<Integer,HashMap<String,String>> getAllRefreshTimes();
    void updateStatus(int cmid, String oldStatus, String newStatus);
    void deleteUser(int cmid);
    int getUserType(String cmid);
    HashMap<Integer,HashMap<String,String>> getRegIDsOfUser(int cmid);
    void updateTable(String tableName, HashMap<String,String> whereConds,
                            String columnToUpdate, Object newValue);
    boolean doesDoctorExists(String docID);
    HashMap<Integer, HashMap<String, String>> getMedicationByNum(String medNum);
    HashMap<Integer, HashMap<String, String>> getMedicalConditionByNum(String medConNum);
    HashMap<Integer, HashMap<String, String>> getStatusByNum(String statusNum);
    HashMap<Integer, HashMap<String, String>> getDoctor(HashMap<String,String> whereConditions);
    String getCmidByPatientID(String pID);
    // TODO: Ohad
    HashMap<Integer, HashMap<String, String>> getRegistrationFieldsWithRefreshTime();
    HashMap<Integer, HashMap<String, String>> getUnfinishedEvents();

    String getPatientIDByCmid(String cmid);

}
