package DatabaseModule.src.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by User on 20/04/2015.
 */

public interface IDbController  extends IDbInit, IDbComm {
    HashMap<Integer, HashMap<String, String>> getAllCmidsByStatus(int status);
    HashMap<Integer, HashMap<String, String>> getRegistrationFieldsWithRefreshTime();
    HashMap<Integer, HashMap<String, String>> getUnfinishedEvents();
    HashMap<Integer, HashMap<String, String>> getEventsByEmsCmid(int cmid);
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
    void initializeAndConnect();
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
    String getPatientIDByCmid(String cmid);
    HashMap<String, String> getEventDetails(String eventId);
    void insertAssistant(HashMap<String, String> insert);
    void updateEmerFirstResponse(HashMap<String, String> updates, HashMap<String, String> conds);
    void updateArrivalDate(HashMap<String, String> data);
    void updateActivationDate(String cmid, String eventId);
    void updateResult(String cmid, String eventId, String result);
    String getEventByCmid(String cmid);
    void updatePatientRemarks(String cmid, String eventID, String remark);
    void insertMedicationUse(String proCmid, String eventId, String aproId, String medNum);
    void updateAssistantArrivalTimesAndLocation(HashMap<String, String> data);
    ArrayList<String> getHelpersRegIds(String eventId);
    HashMap<String, String>getAssistDetails(String cmid, String eventId);
    String getRegIDOfPatient(String patientId);
    void removeAssistantFromEvent(String eventId, String patient_id);
    HashMap<Integer, HashMap<String, String>> filterAvailableMembers(List<Integer> cmidList);
    int startNewEmergencyEvent(HashMap<String, String> details);
    void updateLocationRemarkOfPatient(String eventId, String loc);
    String getMedicalConditionOfPatient(String patientId);
    String getMedicationOfPatient(String cmid);
    void updateEMSOfEvent(String cmid, String eventId);
}
