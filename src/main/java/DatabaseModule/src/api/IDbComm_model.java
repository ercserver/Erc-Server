package DatabaseModule.src.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by NAOR on 06/04/2015.
 */
public interface IDbComm_model {
    HashMap<Integer, HashMap<String, String>> getRegistrationFields(int userType);

    HashMap<String, String> getUserByParameter(HashMap<String, String> whereConditions);

    void updateUserDetails(HashMap<String, String> updates);

    HashMap<Integer, HashMap<String, String>> getFrequency(HashMap<String, String> kindOfFrequency);

    HashMap<Integer, HashMap<String, String>> getDefaultInEmergency(String state);

    HashMap<String, String> getRejectCodes();

    HashMap<Integer, HashMap<String, String>> getFromEnum(HashMap<String, String> cond);

    ArrayList<String> getWaitingPatientsCMID(int docCMID);

    void updateUrgentInRefreshDetailsTime(int CMID, String fieldName, int urgentBit);

    boolean isCommunityMemberExists(int cmid);

    int addNewCommunityMember(HashMap<String, String> details);

    int getAuthenticationMethod(String state);

    HashMap<String, String> getEmailOfDoctorsAuthorizer(String state);

    HashMap<String, String> getLoginDetails(String email);

    void updateLastRefreshTime(HashMap<String, String> params);

    HashMap<Integer, HashMap<String, String>> getAllRefreshTimes();

    void updateStatus(int cmid, String oldStatus, String newStatus);

    void deleteUser(int cmid);

    int getUserType(String cmid);

    HashMap<Integer, HashMap<String, String>> getRegIDsOfUser(int cmid);

    void updateTable(String tableName, HashMap<String, String> whereConds,
                     String columnToUpdate, Object newValue);

    boolean doesDoctorExists(String docID);

    HashMap<Integer, HashMap<String, String>> getMedicationByNum(String medNum);

    HashMap<Integer, HashMap<String, String>> getMedicalConditionByNum(String medConNum);

    HashMap<Integer, HashMap<String, String>> getStatusByNum(String statusNum);

    HashMap<Integer, HashMap<String, String>> getDoctor(HashMap<String, String> whereConditions);

    String getCmidByPatientID(String pID);

    HashMap<Integer, HashMap<String, String>> getEventsByEmsCmid(int cmid);


    // TODO: Ohad
    HashMap<Integer, HashMap<String, String>> getRegistrationFieldsWithRefreshTime();

    HashMap<Integer, HashMap<String, String>> getUnfinishedEvents();

    HashMap<Integer, HashMap<String, String>> getAllCmidsByStatus(int status);

    String getPatientIDByCmid(String cmid);

    HashMap<Integer, HashMap<String, String>> getAllAssistantsByEventId(int eventId, int responseType);

    HashMap<String, String> getEventDetails(String eventId);

    void insertAssistant(HashMap<String, String> insert);

    void updateArrivalDate(HashMap<String, String> data);

    void updateEmerFirstResponse(HashMap<String, String> updates, HashMap<String, String> conds);

    void updateActivationDate(String cmid, String eventId);

    void updateResult(String cmid, String eventId, String result);

    String getEventByCmid(String cmid);

    void closeEvent(int eventId, String newStatus); // status={CANCELED, ACTIVE, FINISHED}

    ArrayList<Integer> filterAvailableMembers(ArrayList<Integer> cmidList, String eventId);

    void updatePatientRemarks(String cmid, String eventID, String remark);

    int startNewEmergencyEvent(HashMap<String, String> details);


    void insertMedicationUse(String proCmid, String eventId, String aproId, String medNum);

    void updateAssistantArrivalTimesAndLocation(HashMap<String, String> data);

    ArrayList<String> getHelpersRegIds(String eventId);

    HashMap<String, String> getAssistDetails(String cmid, String eventId);

    String getRegIDOfPatient(String patientId);

    void removeAssistantFromEvent(String eventId, String patient_id);

    void updateEventDetails(String eventId, String state, String regType, String radiud, String loc);

    String getMedicalConditionOfPatient(String patientId);

    String getMedicationOfPatient(String cmid);

    void updateEMSOfEvent(String cmid, String eventId);

    String getPrescNum(String cmid);

    HashMap<String, String> getMedicalDetailsForPresenting(String cmid);

    String getBirthDate(String cmid);

    HashMap<Integer, HashMap<String, String>> getGoingAssistantsAndTimes(String eventId);

    int getHowManySendToEvent(String state);
    void updateLogs(String eventId, String actionTypeName, String descr);
    HashMap<String, String> getFieldDetails(String name, String userType);


    void updateMedicineGiven(int cmid, int eventID);

    void updateMedicineGiven(int cmid, int eventID, Date date);
    boolean isCmidStatusActive(String cmid);
    boolean doesMedicineMatch(String cmid, String eventId);
    boolean doesEventHasEMS(String eventId);
    String getStatusByName(String statusName);
    HashMap<String, String> getMedicationNameAndDosage(String prescriptionNum);
    boolean isEmailMemberExists(String mail);

    HashMap<Integer,HashMap<String,String>> getAllFrequencies();
}