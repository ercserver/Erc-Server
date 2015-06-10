package DatabaseModule.src.controller;


import DatabaseModule.src.api.*;
import DatabaseModule.src.model.*;

import java.util.*;

/**
 * Created by NAOR on 06/04/2015.
 */
public class DbController_V1 implements IDbController {
    private final int dbInitVersion = 1;
    private final int dbCommVersion = 1;

    //holding the implementations chosen for the interface (composition)
    private IDbInit_model DB_initializer = determineDbInitVersion();
    private IDbComm_model DB_communicator = determineDbCommVersion();


    public void initializeAndConnect() {
        DB_initializer.initializeAndConnect();
    }

	//key = Email/InternalID vlaue = cmid do to string by class to  string/mail('') staring a  = "'aaaa'";
    public HashMap<Integer,HashMap<String,String>> getRegistrationFields(int userType) {
        System.out.println("userType");
        return DB_communicator.getRegistrationFields(userType);
    }

    public HashMap<String,String> getUserByParameter(HashMap<String,String> whereConditions){
        return DB_communicator.getUserByParameter(whereConditions);
    }

	//update personal detais 
    public void updateUserDetails(HashMap<String,String> updates){
        DB_communicator.updateUserDetails(updates);
    }

    public HashMap<Integer,HashMap<String,String>> getFrequency(HashMap<String,String> kindOfFrequency){
        return DB_communicator.getFrequency(kindOfFrequency);
    }

    public HashMap<Integer,HashMap<String,String>> getDefaultInEmergency(String state){
        return DB_communicator.getDefaultInEmergency(state);
    }

    public HashMap<String,String> getRejectCodes(){
        return DB_communicator.getRejectCodes();
    }

    public HashMap<Integer,HashMap<String,String>> getFromEnum(HashMap<String,String> cond){
        return DB_communicator.getFromEnum(cond);
    }

    public ArrayList<String> getWaitingPatientsCMID(int docCMID){
        return DB_communicator.getWaitingPatientsCMID(docCMID);
    }
	
    public void updateUrgentInRefreshDetailsTime(int CMID, String fieldName, int urgentBit){
        DB_communicator.updateUrgentInRefreshDetailsTime(CMID, fieldName, urgentBit);
    }

    public boolean isCommunityMemberExists(int cmid){
        return DB_communicator.isCommunityMemberExists(cmid);
    }

    public int addNewCommunityMember(HashMap<String,String> details){
        return DB_communicator.addNewCommunityMember(details);
    }

    public int getAuthenticationMethod(String state){
        return DB_communicator.getAuthenticationMethod(state);
    }

    public HashMap<String,String> getEmailOfDoctorsAuthorizer(String state){
        return DB_communicator.getEmailOfDoctorsAuthorizer(state);
    }

    public HashMap<String,String> getLoginDetails(String email){
        return DB_communicator.getLoginDetails(email);
    }

    public void updateLastRefreshTime(HashMap<String,String> params){
        DB_communicator.updateLastRefreshTime(params);
    }

    public HashMap<Integer,HashMap<String,String>> getAllRefreshTimes(){
        return DB_communicator.getAllRefreshTimes();
    }

    public void updateStatus(int cmid, String oldStatus, String newStatus) {
        DB_communicator.updateStatus(cmid,oldStatus,newStatus);
    }

    public HashMap<Integer,HashMap<String,String>> getRegIDsOfUser(int cmid)
    {
        return DB_communicator.getRegIDsOfUser(cmid);
    }

    public void deleteUser(int cmid){DB_communicator.deleteUser(cmid);}

    public int getUserType(String cmid){return DB_communicator.getUserType(cmid);}

    private IDbInit_model determineDbInitVersion(){
        switch (dbInitVersion) {
            //determine version of CommToUsers to use
            case 1: {
                return new DbInit_V1();
            }
            default: {
                return null;
            }
        }
    }

    private IDbComm_model determineDbCommVersion(){
        switch (dbCommVersion) {
            //determine version of CommToUsers to use
            case 1: {
                return new DbComm_V1();
            }
            default: {
                return null;
            }
        }
    }

    public void updateTable(String tableName, HashMap<String,String> whereConds,
                            String columnToUpdate, Object newValue)
    {
        DB_communicator.updateTable(tableName, whereConds, columnToUpdate, newValue);
    }

    public boolean doesDoctorExists(String docID){
        return DB_communicator.doesDoctorExists(docID);
    }

    public HashMap<Integer, HashMap<String, String>> getMedicationByNum(String medNum){
        return DB_communicator.getMedicationByNum(medNum);
    }

    public HashMap<Integer, HashMap<String, String>> getMedicalConditionByNum(String medConNum){
        return DB_communicator.getMedicalConditionByNum(medConNum);
    }

    public HashMap<Integer, HashMap<String, String>> getStatusByNum(String statusNum){
        return DB_communicator.getStatusByNum(statusNum);
    }

    public HashMap<Integer, HashMap<String, String>> getDoctor(HashMap<String,String> whereConditions){
        return DB_communicator.getDoctor(whereConditions);
    }

    public String getCmidByPatientID(String pID){
        return DB_communicator.getCmidByPatientID(pID);
    }


    @Override
    public HashMap<Integer, HashMap<String, String>> getAllCmidsByStatus(int status) {
        return DB_communicator.getAllCmidsByStatus(status);
    }

    @Override
    public HashMap<Integer, HashMap<String, String>> getRegistrationFieldsWithRefreshTime() {
        return null;
    }

    @Override
    public HashMap<Integer, HashMap<String, String>> getUnfinishedEvents() {
        return null;
    }

    @Override
    public HashMap<Integer, HashMap<String, String>> getEventsByEmsCmid(int cmid) {
        return DB_communicator.getEventsByEmsCmid(cmid);
    }

    public String getPatientIDByCmid(String cmid){
        return DB_communicator.getPatientIDByCmid(cmid);
    }

    @Override
    public List<Integer> getAllAssistantsByEventId(int eventId, int responseType) {
        HashMap<Integer, HashMap<String,String>> rv = DB_communicator.getAllAssistantsByEventId(eventId, responseType);

        // Build the list to return
        List<Integer> list = new ArrayList<>();
        for (Map.Entry<Integer, HashMap<String,String>> entry : rv.entrySet()){
            for (Map.Entry<String, String> pair : entry.getValue().entrySet()){
                list.add(Integer.parseInt(pair.getValue()));
            }
        }
        return list;
    }

    @Override
    public void closeEvent(int eventId, String newStatus) {
        DB_communicator.closeEvent(eventId, newStatus);
    }

    public HashMap<String, String> getEventDetails(String eventId){return DB_communicator.getEventDetails(eventId);}

    public void insertAssistant(HashMap<String, String> insert){DB_communicator.insertAssistant(insert);}

    public void updateEmerFirstResponse(HashMap<String, String> updates, HashMap<String, String> conds){DB_communicator.updateEmerFirstResponse(updates, conds);}

    public void updateArrivalDate(HashMap<String, String> data){DB_communicator.updateArrivalDate(data);}

    public void updateActivationDate(String cmid, String eventId){DB_communicator.updateActivationDate(cmid, eventId);}

    public void updateResult(String cmid, String eventId, String result){DB_communicator.updateResult(cmid, eventId, result);}

    public String getEventByCmid(String cmid){return DB_communicator.getEventByCmid(cmid);}

    public void updatePatientRemarks(String cmid, String eventID, String remark){DB_communicator.updatePatientRemarks(cmid, eventID, remark);}

    public ArrayList<String> getHelpersRegIds(String eventId){return DB_communicator.getHelpersRegIds(eventId);}

    public void insertMedicationUse(String proCmid, String eventId, String aproId, String medNum){DB_communicator.insertMedicationUse(proCmid, eventId, aproId, medNum);}

    public void updateAssistantArrivalTimesAndLocation(HashMap<String, String> data){DB_communicator.updateAssistantArrivalTimesAndLocation(data);}

    public HashMap<String, String>getAssistDetails(String cmid, String eventId){return DB_communicator.getAssistDetails(cmid, eventId);}

    public String getRegIDOfPatient(String patientId){return DB_communicator.getRegIDOfPatient(patientId);}

    public void removeAssistantFromEvent(String eventId, String patient_id){DB_communicator.removeAssistantFromEvent(eventId, patient_id);}

    @Override
    public ArrayList<Integer> filterAvailableMembers(ArrayList<Integer> cmidList, String eventId) {
        return DB_communicator.filterAvailableMembers(cmidList, eventId);
    }

    @Override
    public int startNewEmergencyEvent(HashMap<String, String> details) {
        return DB_communicator.startNewEmergencyEvent(details);
    }

    public void updateEventDetails(String eventId, String state, String regType, String radiud, String loc){DB_communicator.updateEventDetails(eventId, state, regType, radiud, loc);}

    public String getMedicalConditionOfPatient(String patientId){return DB_communicator.getMedicalConditionOfPatient(patientId);}

    public String getMedicationOfPatient(String cmid){return DB_communicator.getMedicationOfPatient(cmid);}

    public void updateEMSOfEvent(String cmid, String eventId){DB_communicator.updateEMSOfEvent(cmid, eventId);}

    public String getPrescNum(String cmid){return DB_communicator.getPrescNum(cmid);}

    public HashMap<String, String> getMedicalDetailsForPresenting(String cmid){return DB_communicator.getMedicalDetailsForPresenting(cmid);}

    public String getBirthDate(String cmid){return DB_communicator.getBirthDate(cmid);}

    public HashMap<Integer, HashMap<String, String>> getGoingAssistantsAndTimes(String eventId){return DB_communicator.getGoingAssistantsAndTimes(eventId);}

    public int getHowManySendToEvent(String state){return DB_communicator.getHowManySendToEvent(state);}

    @Override
    public void updateMedicineGiven(int cmid, int eventID) {
        DB_communicator.updateMedicineGiven(cmid, eventID);
    }

    @Override
    public void updateMedicineGiven(int cmid, int eventID, Date date) {
        DB_communicator.updateMedicineGiven(cmid, eventID, date);
    }

    public void updateLogs(String eventId, String actionTypeName){DB_communicator.updateLogs(eventId, actionTypeName);}

    public  HashMap<String, String> getFieldDetails(String name, String userType){return DB_communicator.getFieldDetails(name, userType);}

    public boolean isCmidStatusActive(String cmid){return DB_communicator.isCmidStatusActive(cmid);}

    public boolean doesMedicineMatch(String cmid, String eventId){return DB_communicator.doesMedicineMatch(cmid, eventId);}

    public boolean doesEventHasEMS(String eventId){return DB_communicator.doesEventHasEMS(eventId);}
}
