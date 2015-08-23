package DatabaseModule.src.model;


import DatabaseModule.src.api.IDbComm_model;
import Utilities.ErcConfiguration;
import Utilities.HashMapBuilder;
import net.sourceforge.jtds.jdbc.DateTime;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.logging.Logger;
import java.util.logging.Level;
/*
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.Level;
*/

//import com.sun.deploy.util.StringUtils;

/**
 * Created by NAOR on 06/04/2015.
 */
public class DbComm_V1 implements IDbComm_model {

    final String JDBC_DRIVER = ErcConfiguration.JDBC_DRIVER;
    final String DB_URL = ErcConfiguration.DB_URL;
    final String DBName = ErcConfiguration.DB_Name;
    final private String USERNAME = ErcConfiguration.DB_USERNAME;
    final private String PASS = ErcConfiguration.DB_PASS;
    private static Connection connection = null;
    private Statement statement = null;

    Logger logger = Logger.getLogger(this.getClass().getName());

    private void connect() throws SQLException {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASS);
            //connection.setAutoCommit(true);

            statement = connection.createStatement();

            statement.execute("USE " + DBName);

        } catch (ClassNotFoundException e) {
            logger.log(Level.WARNING, "Exception: ", e);
        }
    }

    private void releaseResources(Statement statement, Connection connection) {

        if (statement != null) {
            try {
                statement.close();
            } catch (Exception e) {
                logger.log(Level.WARNING, "Exception: ", e);
            }
        }
        // Discouraged
        /*if (connection != null)
        {
            try
            {
                connection.close();
            }
            catch (Exception e) {logger.log(Level.WARNING, "Exception: ", e);}
        }*/

    }

    /*For all of the methods:in each HashMap if the value represent value of column with type varchar,
      the value need to be in this format: 'value' */


    private HashMap<Integer, HashMap<String, String>>
    getRowsFromTable(HashMap<String, String> whereConditions, String tableName) {

        String conditions = "";

        if (whereConditions == null) {
            // No conditions - get all the rows from the table
            conditions = "1=1";
            whereConditions = new HashMap<String, String>();
        } else {
            int numOfConditions = whereConditions.size();
            Set<String> keys = whereConditions.keySet();
            Iterator<String> iter = keys.iterator();
            // creates the where condition for sql query
            String key = iter.next();
            conditions = key + "=?";
            for (int i = 1; i < numOfConditions; i++) {
                key = iter.next();
                conditions += " AND " + key + "=?";
            }

        }
        ResultSet rs = null;
        try {
            // Create the query
            if (!(connection != null && !connection.isClosed() /*&& connection.isValid*/))
                connect();
            String query = "SELECT * FROM " + tableName + " WHERE " + conditions;
            // Assign the values to the where clause
            PreparedStatement stmt = connection.prepareStatement(query);
            Set<String> keys = whereConditions.keySet();
            int parameterIndex = 1;
            for (String key : keys) {
                stmt.setObject(parameterIndex, whereConditions.get(key));
                parameterIndex++;
            }

            rs = stmt.executeQuery();
            HashMap<Integer, HashMap<String, String>> hash = resultSetToMap(rs);

            return hash;

        }
        // There was a fault with the connection to the server or with SQL
        catch (SQLException e) {
            logger.log(Level.WARNING, "Exception: ", e);
            return null;
        }
    }

    /* Debugging functions*/
    public int testDelete(String cmid) {

        try {
            if (!(connection != null && !connection.isClosed() /*&& connection.isValid*/))
                connect();
            ResultSet rs = null;
            String sql = "DELETE FROM RegIDs WHERE community_member_id=?;" +
                    "update dbo.P_CommunityMembers set email_address='bla' where community_member_id=?;" +
                    "update dbo.MembersLoginDetails set email_address='bla' where community_member_id=?";

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setObject(1, cmid);
            stmt.setObject(2, cmid);
            stmt.setObject(3, cmid);
            return stmt.executeUpdate();

        } catch (Exception ex) {
            logger.log(Level.WARNING, "Exception: ", ex);
        }
        return 0;
    }

    public HashMap<Integer, HashMap<String, String>> getRegistrationFields(int userType) {
        logger.log(Level.INFO, "In getRegistrationFields. userType = " + userType);
        HashMap<String, String> conds = new HashMap<String, String>();
        conds.put("user_type", Integer.toString(userType));
        // gets registration fields according to the givven usetType
        HashMap<Integer, HashMap<String, String>> ret = getRowsFromTable(conds, "RegistrationFields");
        /* gets for each registration field the possible values from the proper table, if
           the field is not "free text". we put a json object that converted to string */
        if (ret != null) {
            for (int i = 1; i <= ret.size(); i++) {
                HashMap<String, String> currentField = ret.get(i);
                if ((currentField.get("get_possible_values_from") == null) ||
                        (currentField.get("get_possible_values_from").equals("null")))
                    continue;
                String tableName = currentField.get("get_possible_values_from");
                JSONObject jo;
                // field that has few possible values from Enum table
                if (tableName.substring(0, 5).equals("Enum.")) {
                    ArrayList<String> l = new ArrayList<String>();
                    l.add("enum_value");
                    l.add("enum_code");
                    HashMap<String, String> conds1 = new HashMap<String, String>();
                    conds1.put("table_name", tableName.split("\\.")[1]);
                    conds1.put("column_name", tableName.split("\\.")[2]);
                    HashMap<Integer, HashMap<String, String>> h = selectFromTable("Enum", l, conds1);
                    JSONArray jarray = new JSONArray();

                    for (int j = 1; j <= h.size(); j++) {
                        jarray.put(new JSONObject().put("id", h.get(j).get("enum_code"))
                                .put("value", h.get(j).get("enum_value")));
                    }
                    // Add the array to the field
                    currentField.put("get_possible_values_from", jarray.toString());
                } else { // Not an enum - Just rows from a table
                    JSONArray jarray = new JSONArray();
                    HashMap<Integer, HashMap<String, String>> rowsMap = getRowsFromTable(null, tableName);
                    for (int rowNum = 1; rowNum <= rowsMap.size(); rowNum++) {
                        HashMap<String, String> row = rowsMap.get(rowNum);
                        JSONObject json = new JSONObject();
                        for (Map.Entry<String, String> entry : row.entrySet()) {
                            if (json.length() == 2) {
                                // Don't override existing values...
                                break;
                            }
                            String col = entry.getKey();
                            if (json.isNull("id") && (col.toLowerCase().contains("id") ||
                                    col.toLowerCase().contains("num")) &&
                                    !col.toLowerCase().contains("phone")
                                    && !col.toLowerCase().contains("fax")) {
                                // The primary key
                                json.put("id", entry.getValue());
                            } else if (json.isNull("value") && (col.toLowerCase().contains("description") ||
                                    col.toLowerCase().contains("name"))) {
                                // The value itself
                                json.put("value", entry.getValue());
                            }
                        }
                        jarray.put(json);
                    }
                    currentField.put("get_possible_values_from", jarray.toString());
                }

            }
        }
        logger.log(Level.INFO, "   exiting getRegistrationFields");
        return ret;
    }


    public HashMap<String, String> getUserByParameter(HashMap<String, String> whereConditions) {
        String conditions = "";
        int numOfConditions = whereConditions.size();
        Set<String> keys = whereConditions.keySet();
        Iterator<String> iter = keys.iterator();
        /* creates where coditions for sql query. each key in the input here should be in this format:
           table-name.column-name */
        String key = iter.next();
        conditions = key + "=?";
        for (int i = 1; i < numOfConditions; i++) {
            key = iter.next();
            conditions += " AND " + key + "=?";
        }
        ResultSet rs = null;
        try {
            if (!(connection != null && !connection.isClosed() /*&& connection.isValid*/))
                connect();
            // gets basic data about the user
            System.err.println("SELECT DISTINCT * FROM P_CommunityMembers INNER JOIN MembersLoginDetails " +
                    "ON P_CommunityMembers.community_member_id=MembersLoginDetails.community_member_id "
                    + "WHERE " + conditions);
            String query = "SELECT DISTINCT * FROM P_CommunityMembers INNER JOIN MembersLoginDetails " +
                    "ON P_CommunityMembers.community_member_id=MembersLoginDetails.community_member_id "
                    + "WHERE " + conditions;

            // Assign the values to the where clause
            PreparedStatement stmt = connection.prepareStatement(query);
            Set<String> keys1 = whereConditions.keySet();
            int parameterIndex = 1;
            for (String key1 : keys1) {
                logger.log(Level.INFO, "key: " + key1 + " val: " + whereConditions.get(key1));
                stmt.setObject(parameterIndex, whereConditions.get(key1));
                parameterIndex++;
            }
            rs = stmt.executeQuery();
            // no user exists for the given conditions
            if (!rs.next())
                return null;
            String cmid = rs.getObject("community_member_id").toString();
            // gets the userType by the user's cmid
            int userType = getUserType(cmid);
            // gets all important data about Patient user
            if (userType == 0) {
                query = "SELECT DISTINCT * FROM " + "P_CommunityMembers INNER JOIN "
                        + "P_Patients ON P_CommunityMembers.community_member_id=P_Patients.community_member_id "
                        + "INNER JOIN P_EmergencyContact ON P_Patients.community_member_id=P_EmergencyContact.community_member_id "
                        + "INNER JOIN MembersLoginDetails ON P_EmergencyContact.community_member_id=MembersLoginDetails.community_member_id "
                        + "INNER JOIN P_Supervision ON P_Patients.patient_id=P_Supervision.patient_id "
                        + "INNER JOIN P_Prescriptions ON P_Supervision.patient_id=P_Prescriptions.patient_id "
                        + "INNER JOIN P_Medications ON P_Medications.medication_num=P_Prescriptions.medication_num "
                        + "INNER JOIN P_Diagnosis ON P_Prescriptions.patient_id=P_Diagnosis.patient_id "
                        + "INNER JOIN M_MedicalConditions ON M_MedicalConditions.medical_condition_id=P_Diagnosis.medical_condition_id "
                        + "INNER JOIN Availability ON Availability.community_member_id=P_CommunityMembers.community_member_id "
                        + "INNER JOIN P_StatusLog ON MembersLoginDetails.community_member_id=P_StatusLog.community_member_id "
                        + "INNER JOIN P_Statuses ON P_StatusLog.status_num=P_Statuses.status_num " +
                        "WHERE " + conditions + " ORDER BY " + "P_StatusLog.date_from";
                stmt = connection.prepareStatement(query);
                keys1 = whereConditions.keySet();
                parameterIndex = 1;
                for (String key1 : keys1) {
                    stmt.setObject(parameterIndex, whereConditions.get(key1));
                    parameterIndex++;
                }
                rs = stmt.executeQuery();
            }
            // gets all important data about Doctor or ems user
            if (userType == 1) {
                query = "SELECT DISTINCT * FROM " + "P_CommunityMembers INNER JOIN "
                        + "P_Doctors ON P_CommunityMembers.community_member_id=P_Doctors.community_member_id "
                        + "INNER JOIN P_EmergencyContact ON P_Doctors.community_member_id=P_EmergencyContact.community_member_id "
                        + "INNER JOIN MembersLoginDetails ON P_EmergencyContact.community_member_id=MembersLoginDetails.community_member_id "
                        + "INNER JOIN P_StatusLog ON MembersLoginDetails.community_member_id=P_StatusLog.community_member_id "
                        + "INNER JOIN P_Statuses ON P_StatusLog.status_num=P_Statuses.status_num "
                        + "INNER JOIN MP_MedicalPersonnel ON MP_MedicalPersonnel.community_member_id=P_Doctors.community_member_id "
                        + "INNER JOIN MP_Certification ON MP_MedicalPersonnel.medical_personnel_id=MP_Certification.medical_personnel_id "
                        + "INNER JOIN MP_Specialization ON MP_Specialization.specialization_id=MP_Certification.specialization_id "
                        + "INNER JOIN MP_Affiliation ON MP_MedicalPersonnel.medical_personnel_id=MP_Affiliation.medical_personnel_id "
                        + "INNER JOIN MP_Positions ON MP_Positions.position_num=MP_Affiliation.position_num "
                        + "INNER JOIN MP_Organizations ON MP_Organizations.organization_id=MP_Affiliation.organization_id "
                        + "INNER JOIN MP_OrganizationTypes ON MP_Organizations.organization_type_num=MP_OrganizationTypes.organization_type_num "
                        + "INNER JOIN Availability ON Availability.community_member_id=P_CommunityMembers.community_member_id "
                        + "WHERE " + conditions + " ORDER BY " + "P_StatusLog.date_from";
                stmt = connection.prepareStatement(query);
                keys1 = whereConditions.keySet();
                parameterIndex = 1;
                for (String key1 : keys1) {
                    stmt.setObject(parameterIndex, whereConditions.get(key1));
                    parameterIndex++;
                }
                rs = stmt.executeQuery();
            }
            if (userType == 3){
                query = "SELECT DISTINCT * FROM " + "P_CommunityMembers "
                        + " INNER JOIN P_EmergencyContact ON P_CommunityMembers.community_member_id=P_EmergencyContact.community_member_id "
                        + " INNER JOIN MembersLoginDetails ON P_EmergencyContact.community_member_id=MembersLoginDetails.community_member_id "
                        + " INNER JOIN P_StatusLog ON MembersLoginDetails.community_member_id=P_StatusLog.community_member_id "
                        + " INNER JOIN P_Statuses ON P_StatusLog.status_num=P_Statuses.status_num "
                        + " INNER JOIN MP_MedicalPersonnel ON MP_MedicalPersonnel.community_member_id=P_CommunityMembers.community_member_id "
                        + " INNER JOIN MP_Affiliation ON MP_MedicalPersonnel.medical_personnel_id=MP_Affiliation.medical_personnel_id "
                        + " INNER JOIN MP_Positions ON MP_Positions.position_num=MP_Affiliation.position_num "
                        + " INNER JOIN MP_Organizations ON MP_Organizations.organization_id=MP_Affiliation.organization_id "
                        + " INNER JOIN MP_OrganizationTypes ON MP_Organizations.organization_type_num=MP_OrganizationTypes.organization_type_num "
                        + " INNER JOIN Availability ON Availability.community_member_id=P_CommunityMembers.community_member_id "
                        + " WHERE " + conditions + " ORDER BY " + "P_StatusLog.date_from";
                stmt = connection.prepareStatement(query);
                keys1 = whereConditions.keySet();
                parameterIndex = 1;
                for (String key1 : keys1) {
                    stmt.setObject(parameterIndex, whereConditions.get(key1));
                    parameterIndex++;
                }
                rs = stmt.executeQuery();
            }
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            ArrayList<String> columnNames = new ArrayList<String>();
            // gets all column names from the executed query
            for (int i = 1; i <= columnCount; i++)
                columnNames.add(rsmd.getColumnName(i));

            // no user for the given conditions  exists
            if (!rs.next())
                return null;
            else {
                HashMap<String, String> user = new HashMap<String, String>();
                // gets the data about the user:key=column-name, value=column-value-needs the most updated data
                do {
                    user.clear();
                    iter = columnNames.iterator();
                    for (int i = 0; i < columnCount; i++) {
                        String column = iter.next();
                        // no need for duplications or data about dates in this system
                        if ((!user.containsKey(column)) && (column != "date_from") && (column != "date_to")) {
                            if (rs.getObject(column) != null)
                                user.put(column, rs.getObject(column).toString());
                                // no data about the user in this column
                            else
                                user.put(column, "null");
                        }
                    }
                } while (rs.next());
                // for patient user-gets his doctor's license
                if (userType == 0) {
                    query = "SELECT DISTINCT P_Doctors.doc_license_number as sup,doc1.doc_license_number as pre,doc2.doc_license_number as dia FROM P_CommunityMembers INNER JOIN \n" +
                            "P_Patients ON P_CommunityMembers.community_member_id=P_Patients.community_member_id \n" +
                            "INNER JOIN P_Supervision ON P_Patients.patient_id=P_Supervision.patient_id\n" +
                            "  INNER JOIN P_Prescriptions ON P_Patients.patient_id=P_Prescriptions.patient_id\n" +
                            "  INNER JOIN P_Diagnosis ON P_Patients.patient_id=P_Diagnosis.patient_id\n" +
                            "  INNER JOIN P_Doctors ON P_Supervision.doctor_id=P_Doctors.doctor_id\n" +
                            "  INNER JOIN P_Doctors as doc1 ON P_Prescriptions.doctor_id=doc1.doctor_id\n" +
                            "  INNER JOIN P_Doctors as doc2 ON P_Diagnosis.doctor_id=doc2.doctor_id\n" +
                            "  INNER JOIN MembersLoginDetails ON P_CommunityMembers.community_member_id=MembersLoginDetails.community_member_id \n" +
                            "WHERE " + conditions + " and P_Supervision.date_to is null";
                    stmt = connection.prepareStatement(query);
                    keys1 = whereConditions.keySet();
                    parameterIndex = 1;
                    for (String key1 : keys1) {
                        stmt.setObject(parameterIndex, whereConditions.get(key1));
                        parameterIndex++;
                    }
                    rs = stmt.executeQuery();
                    if (rs.next()) {
                        if (rs.getObject("sup") != null)
                            user.put("P_supervision.doc_license_number", rs.getObject("sup").toString());
                        else
                            user.put("P_supervision.doc_license_number", "null");
                        if (rs.getObject("pre") != null)
                            user.put("P_prescriptions.doc_license_number", rs.getObject("pre").toString());
                        else
                            user.put("P_prescriptions.doc_license_number", "null");
                        if (rs.getObject("dia") != null)
                            user.put("P_diagnosis.doc_license_number", rs.getObject("dia").toString());
                        else
                            user.put("P_diagnosis.doc_license_number", "null");
                    }
                }
                return user;
            }
        }
        // There was a fault with the connection to the server or with SQL
        catch (SQLException e) {
            logger.log(Level.WARNING, "Exception: ", e);
            return null;
        }
        // Releases the resources of this method
        finally {
            releaseResources(statement, connection);
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    logger.log(Level.WARNING, "Exception: ", e);
                }
            }
        }
    }

    public int getUserType(String cmid) {
        ResultSet rs = null;
        try {
            if (!(connection != null && !connection.isClosed() /*&& connection.isValid*/))
                connect();
            statement = connection.createStatement();
            // gets the userType by cmid
            rs = statement.executeQuery("SELECT DISTINCT * FROM P_TypeLog " +
                    "WHERE community_member_id=" + cmid + " AND date_to IS NULL");
            if (rs.next()) {
                return rs.getInt("user_type");
            }
            return -1;
        }
        // There was a fault with the connection to the server or with SQL
        catch (SQLException e) {
            logger.log(Level.WARNING, "Exception: ", e);
            return -1;
        }
        // Releases the resources of this method
        finally {
            releaseResources(statement, connection);
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    logger.log(Level.WARNING, "Exception: ", e);
                }
            }
        }
    }

    public void updateUserDetails(HashMap<String, String> updates) {
        int CMID = Integer.parseInt(updates.get("community_member_id"));
        updates.remove("community_member_id");
        String Supdates = "";
        int numOfUpdates = updates.size();
        Set<String> keys = updates.keySet();
        Iterator<String> iter = keys.iterator();
        // gets personal updates for this user. the input format should be:key=column-name,value=column-new-value
        String key = iter.next();
        Supdates = key + "=" + updates.get(key);
        for (int i = 1; i < numOfUpdates; i++) {
            key = iter.next();
            Supdates += ", " + key + "=" + updates.get(key);
        }
        // update user's personal details
        try {
            if (!(connection != null && !connection.isClosed() /*&& connection.isValid*/))
                connect();
            statement = connection.createStatement();
            statement.execute("UPDATE " + "P_CommunityMembers SET " +
                    Supdates + " WHERE community_member_id=" + Integer.toString(CMID));
        }
        // There was a fault with the connection to the server or with SQL
        catch (SQLException e) {
            logger.log(Level.WARNING, "Exception: ", e);
        } finally {
            releaseResources(statement, connection);
        }
    }

    public HashMap<Integer, HashMap<String, String>> getFrequency(HashMap<String, String> kindOfFrequency) {
        // gets all data about specific frequency
        return selectFromTable("Frequencies", null, kindOfFrequency);
    }

    public HashMap<Integer, HashMap<String, String>> getDefaultInEmergency(String state) {
        HashMap<String, String> cond = new HashMap<String, String>();
        cond.put("state", state);
        ArrayList<String> select = new ArrayList<String>();
        select.add("default_caller");
        // gets the default caller in emergency event according to the givven state
        return selectFromTable("DefaultCallerSettings", select, cond);
    }

    public HashMap<String, String> getRejectCodes() {
        // gets all reject codes of patient that can givven by doctor
        HashMap<Integer, HashMap<String, String>> rejectCodes = getRowsFromTable(null, "RejectCodes");
        int numOfCodes = rejectCodes.size();
        HashMap<String, String> codes = new HashMap<String, String>();
        Collection<HashMap<String, String>> col = rejectCodes.values();
        Iterator<HashMap<String, String>> iter = col.iterator();
        // The returned HashMap will be in this format:key=id-of-reject-code, value=the-reject-code
        for (int i = 0; i < numOfCodes; i++) {
            HashMap<String, String> m = iter.next();
            codes.put(m.get("id"), m.get("description"));
        }
        return codes;
    }

    // gets value of enum by the enum number(or the opposite) from specific table and column
    public HashMap<Integer, HashMap<String, String>> getFromEnum(HashMap<String, String> cond) {
        return selectFromTable("Enum", null, cond);
    }

    public ArrayList<String> getWaitingPatientsCMID(int docCMID) {
        long s = System.currentTimeMillis();

        logger.log(Level.INFO, "In getWaitingPatientsCMID. param = " + docCMID);
        ResultSet rs = null;
        try {
            if (!(connection != null && !connection.isClosed() /*&& connection.isValid*/))
                connect();
            String sql = "select dbo.P_Patients.community_member_id from dbo.P_Patients INNER JOIN dbo.P_Supervision ON" +
                    "  dbo.P_Patients.patient_id = dbo.P_Supervision.patient_id INNER JOIN dbo.P_StatusLog ON" +
                    "   dbo.P_Patients.community_member_id=dbo.P_StatusLog.community_member_id INNER JOIN dbo.P_Doctors" +
                    "  on dbo.P_Doctors.doctor_id=dbo.P_Supervision.doctor_id INNER JOIN dbo.P_Statuses ON" +
                    "  dbo.P_StatusLog.status_num=dbo.P_Statuses.status_num" +
                    "  where dbo.P_Doctors.community_member_id=? AND dbo.P_StatusLog.date_to is NULL" +
                    " AND dbo.P_Statuses.status_name='verifying details'";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setObject(1, docCMID);

            rs = stmt.executeQuery();
            if (!rs.next()) {
                logger.log(Level.INFO, "In getWaitingPatientsCMID. No awaiting patients found");
                return null;
            } else {
                ArrayList<String> rv = new ArrayList<>();
                do {
                    rv.add(Integer.toString(rs.getInt("community_member_id")));
                } while (rs.next());

                return rv;
            }

        }
        // There was a fault with the connection to the server or with SQL
        catch (SQLException e) {
            logger.log(Level.WARNING, "Exception: ", e);
            return null;
        }
        // Releases the resources of this method
        finally {
            releaseResources(statement, connection);
            if (rs != null) {
                try {
                    rs.close();

                } catch (Exception e) {
                    logger.log(Level.WARNING, "Exception: ", e);
                }
            }
        }
    }



    public void updateUrgentInRefreshDetailsTime(int CMID, String fieldName, int urgentBit)
    {
        HashMap<String,String> cond = new HashMap<String,String>();
        cond.put("community_member_id", Integer.toString(CMID));
        cond.put("field_name", fieldName);
        // updates refresh of specific field of spesific user to be urgent
        updateTable("RefreshDetailsTime", cond, "urgent", Integer.toString(urgentBit));
        updateTable("RefreshDetailsTime", cond, "last_update_time", Calendar.getInstance());
    }

    private HashMap<Integer,HashMap<String,String>> resultSetToMap(ResultSet rs){
        HashMap<Integer,HashMap<String,String>> map = new HashMap<Integer,HashMap<String,String>>();
        try {
            int j = 1;
            while (rs.next()) {
                int total_rows = rs.getMetaData().getColumnCount();
                HashMap<String,String> obj = new HashMap<String,String>();
                for (int i = 1; i <= total_rows; i++) {
                    // Add pair (column, value) if value is not null
                    String column = rs.getMetaData().getColumnLabel(i);
                    Object val = rs.getObject(i);
                    if (val != null){
                        obj.put(column, val.toString());
                    }else{
                        obj.put(column, "null");
                    }
                }
                map.put(j, obj);
                j++;
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Exception: ", e);
            logger.log(Level.INFO, "exception in resultSetToMap");
        }
        return map;
    }

    private HashMap<Integer,HashMap<String,String>> selectFromTable
            (String tableName, List<String> columns, HashMap<String,String> whereConds){
        //logger.log(Level.INFO, "   In selectFromTable");
        // Create the select clause
        String selectString;
        if (columns == null) { //Select *
            selectString = "*";
        }else{
            selectString = StringUtils.join(columns, ",");
        }

        // Create the sql query
        String sql = String.format("SELECT %s FROM %s", selectString, tableName);

        // Create the where clause
        String whereString = "";
        if (whereConds != null) {
            Iterator<String> iter = whereConds.keySet().iterator();
            while (iter.hasNext()) {
                String key = iter.next();

                // Check if the condition is null. If so, use "IS" instead of "="
                if (whereConds.get(key).equalsIgnoreCase("null")){
                    whereString += String.format("%s IS NULL AND", key);
                    // Remove the condition from the set
                    whereConds.remove(key);
                }
               else {
                    whereString += String.format("%s=? AND ", key);
                }
            }
            // Remove the last "AND"
            whereString = whereString.substring(0, whereString.length() - 4);

            // Update the query string
            sql += " WHERE " + whereString;
        }
        ResultSet rs = null;
        // System.out.println(sql);
        try {
            //connect();
            if (!(connection != null && !connection.isClosed() /*&& connection.isValid*/))
                connect();
            PreparedStatement stmt = connection.prepareStatement(sql);
            int  parameterIndex = 1;
            Set<String> keys = whereConds.keySet();
            for (String key : keys){
                stmt.setObject(parameterIndex, whereConds.get(key));
                parameterIndex++;
            }
            rs = stmt.executeQuery();
            return resultSetToMap(rs);

        } catch (SQLException e) {
            logger.log(Level.WARNING, "Exception: ", e);
        }
        finally
        {
            releaseResources(statement, connection);
            if (rs != null)
            {
                try
                {
                    rs.close();
                }
                catch (Exception e) {logger.log(Level.WARNING, "Exception: ", e);}
            }
            //logger.log(Level.INFO, "   Existing selectFromTable");
        }
        return null;
    }

    public void updateTable(String tableName, HashMap<String,String> whereConds,
                             String columnToUpdate, Object newValue)
    {
        // Create the where clause
        Iterator<String> iter = whereConds.keySet().iterator();
        String whereString = iter.next() + "=?";
        while (iter.hasNext()){
            String key = iter.next();
           // String val = whereConds.get(key);
            whereString += " AND " + key + "=?";
        }

        // Create the sql query
        String sql = "UPDATE " + tableName + " SET " + columnToUpdate + "=? WHERE " + whereString;
        //System.out.println(sql);
        //logger.info("In updateTable. sql = " + sql);
        try {
            if (!(connection != null && !connection.isClosed() /*&& connection.isValid*/))
                connect();
            // Assign the values to the where and set clauses
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setObject(1, newValue);
            Set<String> keys = whereConds.keySet();
            int parameterIndex = 2;
            for (String key : keys) {
                stmt.setObject(parameterIndex, whereConds.get(key));
                parameterIndex++;
            }
            stmt.execute();

        } catch (SQLException e) {
            logger.log(Level.WARNING, "Exception: ", e);
        }
        finally
        {
            releaseResources(statement, connection);
        }

    }

    public boolean isCommunityMemberExists(int cmid) {
        HashMap<String,String> where = new HashMap<String,String>();
        where.put("community_member_id", Integer.toString(cmid));
        List<String> columns = Arrays.asList("community_member_id");
        HashMap<Integer,HashMap<String,String>> res =
                selectFromTable("P_CommunityMembers", columns, where);
        return (res.size() != 0);
    }

    public boolean isEmailMemberExists(String mail) {
        HashMap<String,String> where = new HashMap<String,String>();
        where.put("email_address", mail);
        List<String> columns = Arrays.asList("community_member_id");
        HashMap<Integer,HashMap<String,String>> res =
                selectFromTable("P_CommunityMembers", columns, where);
        return (res.size() != 0);
    }

    @Override
    public HashMap<Integer, HashMap<String, String>> getAllFrequencies() {
        return getRowsFromTable(null, "Frequencies");
    }


    public int addNewCommunityMember(HashMap<String,String> details) {
        /* - I'm assuming the fields 'doc_license_num' (from p_doctors) will
             be in the form 'TABLENAME.doc_license_num' to prevent ambiguity.
             e.g. p_supervision.doc_license_num

           - Because of the multiple occurrences of the field 'date_to' and
             mainly because the lack of actual meaning of it, regarding the registration
             process, a simple 'date_to' is expected

           - output: the created cmid
         */
        logger.log(Level.INFO, "In addNewCommunityMember. Parameters = " + details);
        int cmid = -1;
        try {
            // Validate connection
            if (!(connection != null && !connection.isClosed() /*&& connection.isValid*/))
                connect();

            // Insert basic details and get the new cmid
            PreparedStatement stmt = connection.prepareStatement("insert into P_CommunityMembers (external_id, external_id_type, first_name, last_name," +
                    "birth_date, gender, state, city, street, house_number, zip_code, home_phone_number, mobile_phone_number," +
                    "email_address) VALUES " +
                    "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, details.get("external_id"));
            stmt.setString(2, details.get("external_id_type"));
            stmt.setString(3, details.get("first_name"));
            stmt.setString(4, details.get("last_name"));
            stmt.setString(5, details.get("birth_date"));
            stmt.setString(6, details.get("gender"));
            stmt.setString(7, details.get("state"));
            stmt.setString(8, details.get("city"));
            stmt.setString(9, details.get("street"));
            stmt.setString(10, details.get("house_number"));
            stmt.setString(11, details.get("zip_code"));
            stmt.setString(12, details.get("home_phone_number"));
            stmt.setString(13, details.get("mobile_phone_number"));
            stmt.setString(14, details.get("email_address"));

            stmt.executeUpdate();

            // Get the new primary key
            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                cmid = rs.getInt(1);
                logger.log(Level.INFO, "New Generated CMID = " + cmid);
            } else{
                // There was a problem inserting the new member
                logger.log(Level.INFO, "Problem getting the newly generated cmid");
                return -1;
            }
            stmt.close();

            // Insert reg_id
            insertRegID(details.get("reg_id"), cmid);

            // Insert login details
            stmt = connection.prepareStatement("INSERT INTO MembersLoginDetails (community_member_id, password, email_address)" +
                    " VALUES (?,?,?)");
            stmt.setInt(1, cmid);
            stmt.setString(2, details.get("password"));
            stmt.setString(3, details.get("email_address"));
            stmt.executeUpdate();
            stmt.close();

            logger.log(Level.INFO, "Inserted: Login Details");

            // Insert contact info
            stmt = connection.prepareStatement("INSERT INTO P_EmergencyContact (community_member_id, contact_phone) VALUES (?,?)");
            stmt.setInt(1, cmid);
            stmt.setString(2, details.get("contact_phone"));
            stmt.executeUpdate();
            stmt.close();

            logger.log(Level.INFO, "Inserted: Emergency contact");


            // Insert availability hours
            stmt = connection.prepareStatement(" INSERT INTO Availability (hour_from, minutes_from, hour_to, minutes_to, community_member_id)" +
                    " VALUES (?,?,?,?,?)");
            stmt.setInt(1, Integer.parseInt(details.get("hour_from")));
            stmt.setInt(2, Integer.parseInt(details.get("minutes_from")));
            stmt.setInt(3, Integer.parseInt(details.get("hour_to")));
            stmt.setInt(4, Integer.parseInt(details.get("minutes_to")));
            stmt.setInt(5, cmid);
            stmt.executeUpdate();
            stmt.close();

            logger.log(Level.INFO, "Inserted: Availabilty");

            /* Insert the details based on the user type */
            int userType = Integer.parseInt(details.get("user_type"));

            // Insert to type log
            stmt = connection.prepareStatement("INSERT INTO P_TypeLog (user_type, community_member_id) VALUES (?,?)");

            stmt.setInt(1, Integer.parseInt(details.get("user_type")));
            stmt.setInt(2, cmid);

            stmt.executeUpdate();
            stmt.close();

            logger.log(Level.INFO, "Inserted: Type Log");

            switch(userType){
                case 0:
                    // Patient
                    stmt = connection.prepareStatement("INSERT INTO p_patients (community_member_id) VALUES (?)"
                            ,  Statement.RETURN_GENERATED_KEYS);
                    stmt.setInt(1, cmid);
                    stmt.executeUpdate();
                    // Get the new patient id
                    rs = stmt.getGeneratedKeys();
                    int patientID;
                    if (rs.next()) {
                        patientID = rs.getInt(1);
                    } else{
                        // There was a problem inserting the new member
                        return -1;
                    }
                    stmt.close();
                    logger.log(Level.INFO, "Inserted: Patient");
                    // Supervision

                    stmt = connection.prepareStatement("INSERT INTO P_Supervision (doctor_id, patient_id) " +
                            "VALUES (?,?)");
                    stmt.setInt(1, getDoctorIdByLicense(details.get("P_supervision.doc_license_number")));
                    stmt.setInt(2, patientID);
                    stmt.executeUpdate();
                    stmt.close();
                    logger.log(Level.INFO, "Inserted: Supervision");

                    stmt = connection.prepareStatement("INSERT INTO P_Prescriptions (medication_num, dosage," +
                            " doctor_id, date_to, patient_id) VALUES (?,?,?,?,?)");
                    stmt.setInt(1, Integer.parseInt(details.get("medication_num")));
                    stmt.setFloat(2, Float.parseFloat(details.get("dosage")));
                    stmt.setInt(3, getDoctorIdByLicense(details.get("P_prescriptions.doc_license_number")));
                    stmt.setString(4, details.get("date_to"));
                    stmt.setInt(5, patientID);
                    stmt.executeUpdate();
                    stmt.close();
                    logger.log(Level.INFO, "Inserted: Prescription");

                    stmt = connection.prepareStatement("INSERT INTO P_Diagnosis (patient_id, medical_condition_id," +
                            "doctor_id, date_to) VALUES (?,?,?,?)");
                    stmt.setInt(1, patientID);
                    stmt.setInt(2, Integer.parseInt(details.get("medical_condition_id")));
                    stmt.setInt(3, getDoctorIdByLicense(details.get("P_diagnosis.doc_license_number")));
                    stmt.setString(4, details.get("date_to"));
                    stmt.executeUpdate();
                    stmt.close();
                    logger.log(Level.INFO, "Inserted: Diagnosis");

                    break;

                case 1:
                    // Doctor or EMS
                    stmt = connection.prepareStatement("INSERT INTO P_Doctors (first_name, last_name, doc_license_number," +
                            "community_member_id) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                    stmt.setString(1, details.get("first_name"));
                    stmt.setString(2, details.get("last_name"));
                    stmt.setInt(3, Integer.parseInt(details.get("doc_license_number")));
                    stmt.setInt(4, cmid);
                    stmt.executeUpdate();
                    rs = stmt.getGeneratedKeys();
                    int doctorID;
                    if (rs.next()) {
                        doctorID = rs.getInt(1);
                    } else{
                        // There was a problem inserting the new member
                        return -1;
                    }
                    stmt.close();

                    // Create medical personnel
                    stmt = connection.prepareStatement("INSERT INTO dbo.MP_MedicalPersonnel (community_member_id)" +
                            " VALUES (?)", Statement.RETURN_GENERATED_KEYS);
                    stmt.setObject(1, cmid);
                    stmt.executeUpdate();
                    rs = stmt.getGeneratedKeys();
                    int mpid;
                    if (rs.next()) {
                        mpid = rs.getInt(1);
                    } else{
                        // There was a problem inserting the new member
                        return -1;
                    }
                    stmt.close();

                    stmt = connection.prepareStatement("INSERT INTO MP_Affiliation (organization_id, medical_personnel_id," +
                            "position_num, a_date_to) VALUES (?,?,?,?)");
                    stmt.setInt(1, Integer.parseInt(details.get("organization_id")));
                    stmt.setInt(2, mpid);
                    stmt.setInt(3, Integer.parseInt(details.get("position_num")));
                    stmt.setString(4, details.get("a_date_to"));
                    stmt.executeUpdate();
                    stmt.close();

                    stmt = connection.prepareStatement("INSERT INTO MP_Certification (certification_external_id," +
                            "medical_personnel_id, c_date_to, specialization_id) VALUES (?,?,?,?)");
                    stmt.setInt(1, Integer.parseInt(details.get("certification_external_id")));
                    stmt.setInt(2, mpid);
                    stmt.setString(3, details.get("c_date_to"));
                    stmt.setInt(4, Integer.parseInt(details.get("specialization_id")));
                    stmt.executeUpdate();
                    stmt.close();

                    break;
                case 3: // EMS
                    // Create medical personnel
                    stmt = connection.prepareStatement("INSERT INTO dbo.MP_MedicalPersonnel (community_member_id)" +
                            " VALUES (?)", Statement.RETURN_GENERATED_KEYS);
                    stmt.setObject(1, cmid);
                    stmt.executeUpdate();
                    rs = stmt.getGeneratedKeys();

                    if (rs.next()) {
                        mpid = rs.getInt(1);
                    } else{
                        // There was a problem inserting the new member
                        return -1;
                    }
                    stmt.close();

                    stmt = connection.prepareStatement("INSERT INTO MP_Affiliation (organization_id, medical_personnel_id," +
                            "position_num, a_date_to) VALUES (?,?,?,?)");
                    stmt.setInt(1, Integer.parseInt(details.get("organization_id")));
                    stmt.setInt(2, mpid);
                    stmt.setInt(3, Integer.parseInt(details.get("position_num")));
                    stmt.setString(4, details.get("a_date_to"));
                    stmt.executeUpdate();
                    stmt.close();
                    break;

            }

        } catch (SQLException e) {
            logger.log(Level.WARNING, "Exception: ", e);
        }
        return cmid;

    }

    private int getDoctorIdByLicense(String license) {
        HashMap whereClause = new HashMap();
        whereClause.put("doc_license_number", license);
        List select = Arrays.asList("doctor_id");
        HashMap<Integer, HashMap<String, String>> results = selectFromTable("p_doctors", select, whereClause);
        String docID = "-1";
        if (results.get(1) != null) {
            docID = results.get(1).get("doctor_id");
        }
        return Integer.parseInt(docID);
    }

    // expected format of the state:'state-name'
    public int getAuthenticationMethod(String state) {
        HashMap<String,String> cond = new HashMap<String,String>();
        cond.put("state", state);
        HashMap<Integer,HashMap<String,String>> res =
                selectFromTable("AuthenticationMethod", Arrays.asList("method"), cond);
        // returns authentication method in this state:mail,SMS...
        if (res.size() != 0){
            Collection<HashMap<String,String>> coll = res.values();
            return Integer.parseInt(coll.iterator().next().get("method"));
        }
        return -1;
    }

    // expected format of the state:'state-name'
    public HashMap<String,String> getEmailOfDoctorsAuthorizer(String state)
    {
        HashMap<String,String> cond = new HashMap<String,String>();
        cond.put("state", state);
        HashMap<Integer,HashMap<String,String>> res =
                selectFromTable("DoctorAuthorizers", Arrays.asList("email_address"), cond);
        // returns the mail of doctors authorizer
        if (res.size() != 0){
            Collection<HashMap<String,String>> coll = res.values();
            return coll.iterator().next();
        }
        return null;
    }

    // expected format of the email:'mail-address'
    public HashMap<String,String> getLoginDetails(String email) {
        HashMap<String,String> cond = new HashMap<String,String>();
        cond.put("email_address", email);
        HashMap<Integer,HashMap<String,String>> res =
                selectFromTable("MembersLoginDetails", null, cond);
        // returns login details of the givven mail if exists in the system
        if (res.size() != 0){
            Collection<HashMap<String,String>> coll = res.values();
            return coll.iterator().next();
        }
        return null;
    }

    // NOT TESTED
    public void updateLastRefreshTime(HashMap<String,String> params) {
        // Get all the relevant values from parms
        int cmid = Integer.parseInt(params.get("community_member_id"));
        String fieldToUpdate = params.get("field_name");
        String tsS = params.get("last_update_time");
        Timestamp ts =  Timestamp.valueOf(tsS);
        // Create the where clause json
        HashMap<String,String> whereMap = new HashMap<String,String>();
        whereMap.put("community_member_id", Integer.toString(cmid));
        whereMap.put("field_name", fieldToUpdate);
        // Update the datetime field
        updateTable("RefreshDetailsTime", whereMap, "last_update_time", ts);
    }

    public HashMap<Integer,HashMap<String,String>> getAllRefreshTimes() {
        return selectFromTable("RefreshDetailsTime", null, null);
    }

    // the status format should be:'status-name'
    public void updateStatus(int cmid, String oldStatus, String newStatus)
    {
        logger.log(Level.INFO, "In Update Status Parameters = " + cmid + ", " + oldStatus + ", " + newStatus);
        try
        {
            HashMap<String,String> cond = new HashMap<String,String>();
            HashMap<Integer, HashMap<String, String>> s;
            Collection<HashMap<String, String>> val;
            String statusNum;
            if(oldStatus != null)
            {
                cond.put("status_name", oldStatus);
                // gets status number of the givven old status
                s = getRowsFromTable(cond, "P_Statuses");
                val = s.values();
                statusNum = val.iterator().next().get("status_num");
                if (!(connection != null && !connection.isClosed() /*&& connection.isValid*/))
                    connect();
                // closed time of this user in the old status
                statement = connection.createStatement();
                statement.execute("UPDATE P_StatusLog SET date_to=CURRENT_TIMESTAMP" +
                        " WHERE" + " status_num=" + statusNum + " AND community_member_id="
                        + Integer.toString(cmid));
            }
            cond.clear();
            cond.put("status_name", newStatus);
            // gets status number of the givven new status
            logger.log(Level.INFO, "cond = " + cond);
            s = getRowsFromTable(cond, "P_Statuses");
            logger.log(Level.INFO, "s = " + s);
            val = s.values();
            logger.log(Level.INFO, "val = " + val);
            statusNum = val.iterator().next().get("status_num");
            if (!(connection != null && !connection.isClosed() /*&& connection.isValid*/))
                connect();
            statement = connection.createStatement();
            // updates the givven user with the givven new status
            statement.execute("INSERT INTO P_StatusLog (status_num,community_member_id) VALUES (" +
                    statusNum + "," + Integer.toString(cmid) + ")");
        }
        catch (SQLException e) {logger.log(Level.WARNING, "Exception: ", e);}
        finally
        {
            releaseResources(statement, connection);
        }
    }

    // expected regID format:'redID'
    public void insertRegID(String regId, int cmid)
    {
        if (regId.equals("0")){
            // No need to add reg id
            return;
        }
        try
        {
            if (!(connection != null && !connection.isClosed() /*&& connection.isValid*/))
                connect();
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO RegIDs (reg_id,community_member_id) VALUES " +
                    "(?,?)");
            stmt.setObject(1, regId);
            stmt.setObject(2, cmid);
            // inserts the regid to the regIDs table
            stmt.executeUpdate();
        }
        catch (SQLException e) {logger.log(Level.WARNING, "Exception: ", e);}
        finally
        {
            releaseResources(statement, connection);
        }
    }

    // return user's regID in a HashMap according to his cmid
    public HashMap<Integer,HashMap<String,String>> getRegIDsOfUser(int cmid)
    {
        HashMap<String,String> conds = new HashMap<String,String>();
        conds.put("community_member_id", Integer.toString(cmid));
        return getRowsFromTable(conds, "RegIDs");
    }

    public void deleteUser(int cmid)
    {
        // tables that relevant for each user in this community
        String[] tables =  {"P_StatusLog", "P_DeviceLog", "P_EmergencyContact", "P_TypeLog",
                            "MembersLoginDetails", "RefreshDetailsTime", "RegIDs"
                            , "P_Relations"};
        // tables that relevant for each Patient or Doctor user
        String[] pTables = {"P_Supervision", "P_Prescriptions", "P_Diagnosis", "P_Relations", "P_Patients", "P_Doctors"};
        // tables that relevant for each Doctor or ems user
        String[] mpTables = {"MP_Affiliation", "MP_Certification", "MP_MedicalPersonnel"};
        try
        {
            if (!(connection != null && !connection.isClosed() /*&& connection.isValid*/))
                connect();
            HashMap<String,String> cond = new HashMap<String,String>();
            cond.put("community_member_id", Integer.toString(cmid));
            // gets userTypeID according to the cmid, if exists
            HashMap<Integer,HashMap<String,String>> patientID = getRowsFromTable(cond, "P_Patients");
            HashMap<Integer,HashMap<String,String>> docID = getRowsFromTable(cond, "P_Doctors");
            HashMap<Integer,HashMap<String,String>> medPersonelID = getRowsFromTable(cond, "MP_MedicalPersonnel");
            // deletes the user from the data-base
            for(int i = 0; i < 8; i++)
            {
                statement = connection.createStatement();
                statement.execute("DELETE FROM " + tables[i] +
                        " WHERE community_member_id=" + Integer.toString(cmid));
            }
            statement = connection.createStatement();
            statement.execute("DELETE FROM P_Buddies" +
                    " WHERE community_member_id1=" + Integer.toString(cmid) +
                    " OR community_member_id2=" + Integer.toString(cmid));
            // deletes patient or doctor from relevant tables
            if((patientID != null) || (docID != null))
            {
                String id = patientID.get(1).get("patient_id");
                for(int i = 0; i < 6; i++)
                {
                    statement = connection.createStatement();
                    statement.execute("DELETE FROM " + pTables[i] +
                            " WHERE patient_id=" + id);
                }
            }
            // deletes ems or doctor from relevant tables
            if(medPersonelID != null)
            {
                String id = medPersonelID.get(1).get("medical_personnel_id");
                for(int i = 0; i < 3; i++) {
                    statement = connection.createStatement();
                    statement.execute("DELETE FROM " + mpTables[i] +
                            " WHERE medical_personnel_id=" + id);
                }
            }
            statement = connection.createStatement();
            statement.execute("DELETE FROM P_CommunityMembers" +
                    " WHERE community_member_id=" + Integer.toString(cmid));
        }
        catch (SQLException e) {logger.log(Level.WARNING, "Exception: ", e);}
        finally
        {
            releaseResources(statement, connection);
        }

    }

    // Checks if doctor is exist by his license number
    public boolean doesDoctorExists(String docLicense)
    {
        HashMap<String,String> whereConditions = new HashMap<String, String>();
        whereConditions.put("doc_license_number", docLicense);
        return (null != getDoctor(whereConditions));
    }

    public HashMap<Integer, HashMap<String, String>> getMedicationByNum(String medNum)
    {
        HashMap<String,String> whereConditions =  new HashMap<String, String>();
        whereConditions.put("medication_num", medNum);
        return getRowsFromTable(whereConditions, "P_Medications");

    }

    public HashMap<Integer, HashMap<String, String>> getMedicalConditionByNum(String medConNum)
    {
        HashMap<String,String> whereConditions =  new HashMap<String, String>();
        whereConditions.put("medical_condition_id", medConNum);
        return getRowsFromTable(whereConditions, "M_MedicalConditions");
    }

    public HashMap<Integer, HashMap<String, String>> getStatusByNum(String statusNum)
    {
        HashMap<String,String> whereConditions = new HashMap<String, String>();
        whereConditions.put("status_num", statusNum);
        return getRowsFromTable(whereConditions, "P_Statuses");
    }

    public String getStatusByName(String statusName)
    {
        HashMap<String,String> whereConditions = new HashMap<String, String>();
        whereConditions.put("status_name", statusName);
        return getRowsFromTable(whereConditions, "P_Statuses").get(1).get("status_num");
    }

    public HashMap<Integer, HashMap<String, String>> getDoctor(HashMap<String,String> whereConditions)
    {
        return getRowsFromTable(whereConditions, "P_Doctors");
    }

    public String getCmidByPatientID(String pID)
    {
        logger.info("in getCmidByPatientID. patient_id = " + pID);
        HashMap<String,String> cond = new HashMap<String,String>();
        cond.put("patient_id", pID);
        return getRowsFromTable(cond, "P_Patients").get(1).get("community_member_id");
    }

    public String getPatientIDByCmid(String cmid)
    {
        HashMap<String,String> cond = new HashMap<String,String>();
        cond.put("community_member_id", cmid);
        return getRowsFromTable(cond, "P_Patients").get(1).get("patient_id");
    }




    // TODO: get fields with values (intersection of registration fields and community_member)

    public HashMap<String, String> getEventDetails(String eventId)
    {
        HashMap<String, String> cond = new HashMap<String, String>();
        cond.put("event_id", eventId);
        HashMap<String, String> data = getRowsFromTable(cond, "O_EmergencyEvents").get(1);
        cond.clear();
        // Add the prescription num
        cond.put("patient_id", data.get("patient_id"));
        data.putAll(selectFromTable("P_Prescriptions", Arrays.asList("prescription_num"), cond).get(1));
        return data;
    }

    public void insertAssistant(HashMap<String, String> insert)
    {
        try
        {
            if (!(connection != null && !connection.isClosed() /*&& connection.isValid*/))
                connect();
           /* statement = connection.createStatement();
            statement.execute("INSERT INTO O_EmergencyEventResponse (community_member_id,event_id,prescription_num," +
                    "eta_by_foot,eta_by_car,location_remark) VALUES (" +
                    insert.get("community_member_id") + "," + insert.get("event_id") + "," +
                    insert.get("prescription_num") + "," +
                    insert.get("eta_by_foot") + "," + insert.get("eta_by_car") +
                    ",'" + insert.get("location_remark") + "')");*/
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO O_EmergencyEventResponse (community_member_id,event_id,prescription_num," +
                    "eta_by_foot,eta_by_car,location_remark) VALUES (?,?,?,?,?,?)");
            stmt.setObject(1, insert.get("community_member_id"));
            stmt.setObject(2, insert.get("event_id"));
            stmt.setObject(3, insert.get("prescription_num"));
            stmt.setObject(4, insert.get("eta_by_car"));
            stmt.setObject(5, insert.get("eta_by_foot"));
            stmt.setObject(6, insert.get("location_remark"));

            stmt.executeUpdate();


        }
        catch (SQLException e) {logger.log(Level.WARNING, "Exception: ", e);}
        finally
        {
            releaseResources(statement, connection);
        }
    }

    public void updateEmerFirstResponse(HashMap<String, String> updates, HashMap<String, String> conds)
    {
        updateTable("O_EmergencyEventResponse", conds, "response_type", updates.get("response_type"));
        if(updates.keySet().contains("transformation_mean"))
            updateTable("O_EmergencyEventResponse", conds, "transformation_mean", updates.get("transformation_mean"));
        // Create the where clause
        String whereString = "";
        Iterator<String> iter = conds.keySet().iterator();
        while (iter.hasNext()){
            String key = iter.next();
            String val = conds.get(key);
            whereString += String.format("%s=%s AND ", key, val);
        }
        // Remove the last "AND"
        whereString = whereString.substring(0, whereString.length() - 4);
        try
        {
            if (!(connection != null && !connection.isClosed() /*&& connection.isValid*/))
                connect();
            statement = connection.createStatement();
            statement.execute("UPDATE O_EmergencyEventResponse SET response_date=current_timestamp WHERE " + whereString);
        }
        catch (SQLException e) {logger.log(Level.WARNING, "Exception: ", e);}
        finally
        {
            releaseResources(statement, connection);
        }
    }

    public void updateArrivalDate(HashMap<String, String> data)
    {
        try
        {
            if (!(connection != null && !connection.isClosed() /*&& connection.isValid*/))
                connect();
            statement = connection.createStatement();
            statement.execute("UPDATE O_EmergencyEventResponse SET arrival_date=current_timestamp WHERE community_member_id="
                    + data.get("community_member_id") + " AND event_id=" + data.get("event_id"));
        }
        catch (SQLException e) {logger.log(Level.WARNING, "Exception: ", e);}
        finally
        {
            releaseResources(statement, connection);
        }
    }

    public void updateActivationDate(String cmid, String eventId)
    {
        try
        {
            if (!(connection != null && !connection.isClosed() /*&& connection.isValid*/))
                connect();
            statement = connection.createStatement();
            statement.execute("UPDATE O_EmergencyEventResponse SET activation_date=current_timestamp WHERE community_member_id="
                    + cmid + " AND event_id=" + eventId);
        }
        catch (SQLException e) {logger.log(Level.WARNING, "Exception: ", e);}
        finally
        {
            releaseResources(statement, connection);
        }
    }

    public void updateResult(String cmid, String eventId, String result)
    {
        try
        {
            if (!(connection != null && !connection.isClosed() /*&& connection.isValid*/))
                connect();
            statement = connection.createStatement();
            statement.execute("UPDATE O_EmergencyEventResponse SET result=" + result + " WHERE community_member_id="
                    + cmid + " AND event_id=" + eventId);
        }
        catch (SQLException e) {logger.log(Level.WARNING, "Exception: ", e);}
        finally
        {
            releaseResources(statement, connection);
        }
    }

    public String getEventByCmid(String cmid)
    {
        ResultSet rs = null;
        try {
            if (!(connection != null && !connection.isClosed() /*&& connection.isValid*/))
                connect();
            statement = connection.createStatement();
            // gets the userType by cmid
            rs = statement.executeQuery("SELECT DISTINCT * FROM O_EmergencyEvents " +
                    "WHERE create_by_member_id=" + cmid + " AND finished_date IS NULL order by created_date");
            String eventId = null;
            while(rs.next())
                eventId = rs.getObject("event_id").toString();
            return eventId;
        }
        // There was a fault with the connection to the server or with SQL
        catch (SQLException e) {logger.log(Level.WARNING, "Exception: ", e); return null;}
        // Releases the resources of this method
        finally
        {
            releaseResources(statement, connection);
            if (rs != null)
            {
                try
                {
                    rs.close();
                }
                catch (Exception e) {logger.log(Level.WARNING, "Exception: ", e);}
            }
        }
    }

    private String getCurrentDateTime(){
        java.util.Date date = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:MM:ss");

        return sdf.format(date);
    }

    @Override
    public void closeEvent(int eventId, String newStatus) {
        // status = {CANCELED, ACTIVE, FINISHED}
        logger.info("eventId = " + eventId + " newStatus = " + newStatus);
       try {
           HashMapBuilder<String, String> hmb = new HashMapBuilder<String, String>();
            // Get the status code from O_EventStatuses
            String statusNum = selectFromTable("O_EventStatuses",
                    Arrays.asList("status_num"),
                    hmb.put("status_name", newStatus).build()).get(1).get("status_num");

            // Update the finish date and the status
           String sql = "UPDATE O_EmergencyEvents SET finished_date=current_timestamp" +
                   ", status_num=? WHERE event_id=?";
           if (connection == null || connection.isClosed()){
               connect();
           }
           PreparedStatement stmt = connection.prepareStatement(sql);
           stmt.setObject(1, statusNum);
           stmt.setObject(2, eventId);
            stmt.executeUpdate();

        }catch(Exception ex){
            logger.log(Level.WARNING, "Exception: ", ex);
        }



    }

    @Override
    public ArrayList<Integer> filterAvailableMembers(ArrayList<Integer> cmidList, String eventId) {
        ArrayList<Integer> cmids = new ArrayList<Integer>();
        ResultSet rs = null;
        try {
            if (!(connection != null && !connection.isClosed() /*&& connection.isValid*/))
                connect();
            String sql = "SELECT P_StatusLog.community_member_id FROM P_StatusLog JOIN " +
                    "Availability ON P_StatusLog.community_member_id=Availability.community_member_id " +
                    "JOIN P_Statuses ON P_Statuses.status_num=P_StatusLog.status_num WHERE status_name='active' AND " +
                    "P_StatusLog.community_member_id IN ";
            // Add the string "(cmid1, cmid2,...)"
            sql += cmidList.toString().replace('[','(').replace(']', ')');

            // Check availabilty by hours and minutes
            Date currentTime = new Date();
            sql += " AND hour_from<=? AND hour_to>?";//AND minutes_from<=? AND minutes_to>? ;";

            // Prepare the statement
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, currentTime.getHours());
            stmt.setInt(2, currentTime.getHours());

            rs = stmt.executeQuery();
            // Extract list
            while (rs.next()) {
                int i = rs.getInt("community_member_id");

                if(doesMedicineMatch(Integer.toString(i), eventId))
                    cmids.add(i);
            }

        }
        // There was a fault with the connection to the server or with SQL
        catch (SQLException e) {logger.log(Level.WARNING, "Exception: ", e); return null;}
        // Releases the resources of this method
        finally
        {
            releaseResources(statement, connection);
            if (rs != null)
            {
                try
                {
                    rs.close();
                }
                catch (Exception e) {logger.log(Level.WARNING, "Exception: ", e);}
            }
            logger.info("cmids after filter = " + cmids);
            return cmids;
        }

    }

    public void updatePatientRemarks(String cmid, String eventID, String remark)
    {
        ResultSet rs = null;
        try {
            if (!(connection != null && !connection.isClosed() /*&& connection.isValid*/))
                connect();
            statement = connection.createStatement();
            // gets the userType by cmid
            rs = statement.executeQuery("SELECT DISTINCT * FROM O_EmergencyEvents " +
                    "WHERE create_by_member_id=" + cmid + " AND event_id=" + eventID);
            rs.next();
            String re = (String)rs.getObject("patient_condition_remarks");
            if(re == null)
                re = "";
            re = "'" + re + "," + remark + "'";
            statement.execute("UPDATE O_EmergencyEvents SET patient_condition_remarks=" + re + " WHERE create_by_member_id="
                    + cmid + " AND event_id=" + eventID);
            statement.execute("UPDATE O_EmergencyEvents SET last_action_time=CURRENT_TIMESTAMP" + " WHERE create_by_member_id="
                    + cmid + " AND event_id=" + eventID);
        }
        // There was a fault with the connection to the server or with SQL
        catch (SQLException e) {logger.log(Level.WARNING, "Exception: ", e);}
        // Releases the resources of this method
        finally
        {
            releaseResources(statement, connection);
            if (rs != null)
            {
                try
                {
                    rs.close();
                }
                catch (Exception e) {logger.log(Level.WARNING, "Exception: ", e);}
            }
        }
    }

    @Override
    public int startNewEmergencyEvent(HashMap<String, String> details) {

        /*
        output : event_id
        input:
            create_by_member_id
            patient_id
            created_date
            finished_date - optional
            medical_condition_id
            ems_member_id
            status_num
            x
            y
            location_remark - optional
            patient_condition_remarks - optional
            last_action_time - optional
            time_to_next_reminder - optional
            memo - optional
        */

        logger.log(Level.INFO, "   In startNewEmergencyEvent. details = " + details);
        ResultSet rs = null;
        int eventId = -1;
        try {
            if (!(connection != null && !connection.isClosed() /*&& connection.isValid*/))
                connect();

            // Prepare the statement
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO O_EmergencyEvents " +
                    "(create_by_member_id, patient_id, medical_condition_id," +
                    " status_num, x, y) " +
                    "VALUES (?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
            HashMap<String, String> cond = new HashMap<String, String>();
            cond.put("status_name", "active");
            String statusNum = getRowsFromTable(cond, "O_EventStatuses").get(1).get("status_num");
            stmt.setInt(1, Integer.parseInt(details.get("create_by_member_id")));
            stmt.setInt(2, Integer.parseInt(details.get("patient_id")));
            stmt.setInt(3, Integer.parseInt(details.get("medical_condition_id")));
            stmt.setInt(4, Integer.parseInt(statusNum));
            stmt.setFloat(5, Float.parseFloat(details.get("x")));
            stmt.setFloat(6, Float.parseFloat(details.get("y")));
            //stmt.setInt(7, Integer.parseInt(details.get("time_to_next_reminder")));

            stmt.executeUpdate();

            // Get the new primary key
            rs = stmt.getGeneratedKeys();


            if (rs.next()) {
                eventId = rs.getInt(1);
                logger.log(Level.INFO, "   new eventid = " + eventId);
            } else{
                // There was a problem inserting the new event
                logger.log(Level.INFO, "   error creating eventid");
                return -1;
            }
            stmt.close();
        }
        // There was a fault with the connection to the server or with SQL
        catch (SQLException e) {logger.log(Level.WARNING, "Exception: ", e);}
        // Releases the resources of this method
        finally
        {
            releaseResources(statement, connection);
            if (rs != null)
            {
                try
                {
                    rs.close();
                }
                catch (Exception e) {logger.log(Level.WARNING, "Exception: ", e);}
            }
            logger.log(Level.INFO, "   Exiting startNewEmergencyEvent");
            return eventId;
        }

    }

    public void insertMedicationUse(String proCmid, String eventId, String aproId, String medNum)
    {
        try
        {
            if (!(connection != null && !connection.isClosed() /*&& connection.isValid*/))
                connect();
            statement = connection.createStatement();
            statement.execute("INSERT INTO O_EmergencyMedicationUse (event_id,providing_member_id,approved_by_id,medication_num) VALUES (" +
                    eventId + "," + proCmid + "," + aproId + "," + medNum + ")");
        }
        catch (SQLException e) {logger.log(Level.WARNING, "Exception: ", e);}
        finally
        {
            releaseResources(statement, connection);
        }
    }

    @Override
    public void updateAssistantArrivalTimesAndLocation(HashMap<String, String> data) {
        HashMap<String, String> conds = new HashMap<String, String>();
        conds.put("community_member_id", data.get("community_member_id"));
        conds.put("event_id", data.get("event_id"));
        updateTable("O_EmergencyEventResponse", conds, "location_remark", data.get("location_remark"));
        updateTable("O_EmergencyEventResponse", conds, "eta_by_foot", data.get("eta_by_foot"));
        updateTable("O_EmergencyEventResponse", conds, "eta_by_car", data.get("eta_by_car"));
    }

    @Override
    public HashMap<Integer, HashMap<String, String>> getAllAssistantsByEventId(int eventId, int responseType) {
        HashMapBuilder<String, String> hma = new HashMapBuilder<String, String>();
        hma.put("event_id", Integer.toString(eventId));

        // Check if response type is relevant
        if (responseType != -1){
            hma. put("response_type", Integer.toString(responseType));
        }

        return selectFromTable("O_EmergencyEventResponse",
                Arrays.asList("community_member_id"),
                hma.build());

    }

    public HashMap<Integer, HashMap<String, String>> getGoingAssistantsAndTimes(String eventId)
    {
        HashMap<Integer, HashMap<String, String>> assistnats = getAllAssistantsByEventId(Integer.parseInt(eventId), 1);
        for(int i = 1; i <= assistnats.size(); i++)
        {
            String cmid = assistnats.get(i).get("community_member_id");
            HashMap<String, String> det = getAssistDetails(cmid, eventId);
            if(det.get("transformation_mean").equals("0"))
                assistnats.get(i).put("eta", det.get("eta_by_foot"));
            else
                assistnats.get(i).put("eta", det.get("eta_by_car"));
        }
        return assistnats;
    }

    public ArrayList<String> getHelpersRegIds(String eventId)
    {
        HashMap<Integer, HashMap<String, String>> assistents = getAllAssistantsByEventId(Integer.parseInt(eventId), 1);
        Iterator<HashMap<String, String>> iter = assistents.values().iterator();
        ArrayList<String> regs = new ArrayList<String>();
        while(iter.hasNext())
        {
            HashMap<Integer, HashMap<String, String>> r = getRegIDsOfUser(Integer.parseInt(iter.next().get("community_member_id")));
            Iterator<HashMap<String, String>> iter1 = r.values().iterator();
            while(iter1.hasNext())
                regs.add(iter1.next().get("reg_id"));
        }
        return regs;
    }

    public HashMap<Integer, HashMap<String, String>> getEventsByEmsCmid(int cmid){
        HashMapBuilder<String, String> hma = new HashMapBuilder<String, String>();
        return selectFromTable("O_EmergencyEvents", null /*all*/,
                hma.put("ems_member_id", Integer.toString(cmid)).build());
    }

    @Override
    public HashMap<Integer, HashMap<String, String>> getRegistrationFieldsWithRefreshTime() {
        //Integer need to be cmid
        return null;

    }

    @Override
    public HashMap<Integer, HashMap<String, String>> getUnfinishedEvents() {
        return null;
    }

    @Override
    public HashMap<Integer, HashMap<String, String>>  getAllCmidsByStatus(int status) {
        HashMapBuilder<String, String> cond = new HashMapBuilder<String, String>();
        return selectFromTable("P_StatusLog",Arrays.asList("community_member_id"),
                cond.put("date_to", "null").put("status_num", Integer.toString(status)).build());
    }

    public HashMap<String, String> getAssistDetails(String cmid, String eventId)
    {
        HashMap<String, String> conds = new HashMap<String, String>();
        conds.put("community_member_id", cmid);
        conds.put("event_id", eventId);
        return getRowsFromTable(conds, "O_EmergencyEventResponse").get(1);
    }

    public String getRegIDOfPatient(String patientId)
    {
        return getRegIDsOfUser(Integer.parseInt(getCmidByPatientID(patientId))).get(1).get("reg_id");
    }

    public void removeAssistantFromEvent(String eventId, String cmid)
    {
        //String cmid = getCmidByPatientID(patient_id);
        HashMap<String, String> conds = new HashMap<String, String>();
        conds.put("community_member_id", cmid);
        conds.put("event_id", eventId);
        updateTable("O_EmergencyEventResponse", conds, "response_type", "3");
    }

    public void updateEventDetails(String eventId, String state, String regType, String radiud, String loc)
    {
        HashMap<String, String> cond = new HashMap<String, String>();
        cond.put("event_id", eventId);
        if(loc != null)
            updateTable("O_EmergencyEvents", cond, "location_remark", loc);
        if(state != null)
            updateTable("O_EmergencyEvents", cond, "state", state);
        if(radiud != null)
            updateTable("O_EmergencyEvents", cond, "radius", radiud);
        if(regType != null)
            updateTable("O_EmergencyEvents", cond, "region_type", regType);
        try {
            if (!(connection != null && !connection.isClosed() /*&& connection.isValid*/))
                connect();
            statement = connection.createStatement();
            statement.execute("UPDATE O_EmergencyEvents SET last_action_time=CURRENT_TIMESTAMP" + " WHERE event_id=" + eventId);
        }
        // There was a fault with the connection to the server or with SQL
        catch (SQLException e) {logger.log(Level.WARNING, "Exception: ", e);}
        // Releases the resources of this method
        finally
        {
            releaseResources(statement, connection);
        }
    }

    public String getMedicalConditionOfPatient(String patientId)
    {
        HashMap<String, String>cond = new HashMap<String, String>();
        cond.put("patient_id", patientId);
        return getRowsFromTable(cond, "P_Diagnosis").get(1).get("medical_condition_id");
    }

    public String getMedicationOfPatient(String cmid)
    {
        ResultSet rs = null;
        try
        {
            if (!(connection != null && !connection.isClosed() /*&& connection.isValid*/))
                connect();
            statement = connection.createStatement();
            // gets all related patients for this doctor
            rs = statement.executeQuery("SELECT DISTINCT * FROM " + "P_Patients INNER JOIN "+
                    "P_Prescriptions ON P_Patients.patient_id=P_Prescriptions.patient_id "
                    + "WHERE P_Patients.community_member_id=" + cmid);
            // no patient related for this doctor
            if(!rs.next())
                return null;
            else
                return rs.getObject("medication_num").toString();
        }
        // There was a fault with the connection to the server or with SQL
        catch (SQLException e) {logger.log(Level.WARNING, "Exception: ", e); return null;}
        // Releases the resources of this method
        finally
        {
            releaseResources(statement, connection);
            if (rs != null)
            {
                try {
                    rs.close();
                }
                catch (Exception e) {logger.log(Level.WARNING, "Exception: ", e);}
            }
        }
    }

    public void updateEMSOfEvent(String cmid, String eventId)
    {
        logger.info("in updateEMSOfEvent. cmid = " + cmid + " eventID = " + eventId);
        HashMap<String, String> cond = new HashMap<String, String>();
        cond.put("event_id", eventId);
        updateTable("O_EmergencyEvents", cond, "ems_member_id", cmid);
        try {
            if (!(connection != null && !connection.isClosed() /*&& connection.isValid*/))
                connect();
            statement = connection.createStatement();
            statement.execute("UPDATE O_EmergencyEvents SET last_action_time=CURRENT_TIMESTAMP" + " WHERE event_id=" + eventId);
        }
        // There was a fault with the connection to the server or with SQL
        catch (SQLException e) {logger.log(Level.WARNING, "Exception: ", e);}
        // Releases the resources of this method
        finally
        {
            releaseResources(statement, connection);
        }
    }

    public String getPrescNum(String cmid)
    {
        ResultSet rs = null;
        try
        {
            if (!(connection != null && !connection.isClosed() /*&& connection.isValid*/))
                connect();
            statement = connection.createStatement();
            // gets all related patients for this doctor
            rs = statement.executeQuery("SELECT DISTINCT * FROM " + "P_Patients INNER JOIN "+
                    "P_Prescriptions ON P_Patients.patient_id=P_Prescriptions.patient_id "
                    + "WHERE P_Patients.community_member_id=" + cmid);
            // no patient related for this doctor
            if(!rs.next())
                return null;
            else
                return rs.getObject("prescription_num").toString();
        }
        // There was a fault with the connection to the server or with SQL
        catch (SQLException e) {logger.log(Level.WARNING, "Exception: ", e); return null;}
        // Releases the resources of this method
        finally
        {
            releaseResources(statement, connection);
            if (rs != null)
            {
                try {
                    rs.close();
                }
                catch (Exception e) {logger.log(Level.WARNING, "Exception: ", e);}
            }
        }
    }

    public HashMap<String, String> getMedicalDetailsForPresenting(String cmid)
    {
        ResultSet rs = null;
        try
        {
            if (!(connection != null && !connection.isClosed() /*&& connection.isValid*/))
                connect();
            statement = connection.createStatement();
            // gets all related patients for this doctor
            rs = statement.executeQuery("SELECT DISTINCT * FROM " + "P_Patients INNER JOIN "+
                    "P_Prescriptions ON P_Patients.patient_id=P_Prescriptions.patient_id " +
                    "INNER JOIN P_Medications ON P_Medications.medication_num=P_Prescriptions.medication_num "
                    + "WHERE P_Patients.community_member_id=" + cmid);
            // no patient related for this doctor
            if(!rs.next())
                return null;
            else
            {
                HashMap<String, String> res = new HashMap<String, String>();
                res.put("medication_name", rs.getObject("medication_name").toString());
                res.put("dosage", rs.getObject("dosage").toString());
                return res;
            }
        }
        // There was a fault with the connection to the server or with SQL
        catch (SQLException e) {logger.log(Level.WARNING, "Exception: ", e); return null;}
        // Releases the resources of this method
        finally
        {
            releaseResources(statement, connection);
            if (rs != null)
            {
                try {
                    rs.close();
                }
                catch (Exception e) {logger.log(Level.WARNING, "Exception: ", e);}
            }
        }
    }

    public String getBirthDate(String cmid)
    {
        HashMap<String, String> cond = new HashMap<String, String>();
        cond.put("community_member_id", cmid);
        return getRowsFromTable(cond, "P_CommunityMembers").get(1).get("birth_date");
    }

    public int getHowManySendToEvent(String state)
    {
        HashMap<String, String> cond = new HashMap<String, String>();
        cond.put("state", state);
        return Integer.parseInt(getRowsFromTable(cond, "HowManyToSendInEmerEvent").get(1).get("how_much"));
    }

    @Override
    public void updateMedicineGiven(int cmid, int eventID) {
        HashMapBuilder<String, String> hmb = new HashMapBuilder<String, String>();
        try {
            if (connection == null || connection.isClosed()){
                connect();
            }
            String sql = "UPDATE dbo.O_EmergencyMedicationUse SET providing_member_id=?, " +
                    "medication_provision_date=CURRENT_TIMESTAMP WHERE event_id=?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setObject(1, cmid); // providing_member_id
            stmt.setObject(2, eventID);

            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Exception: ", e);
        }

    }

    @Override
    public void updateMedicineGiven(int cmid, int eventID, Date date) {
        HashMapBuilder<String, String> hmb = new HashMapBuilder<String, String>();
        try {
            if (connection == null || connection.isClosed()){
                connect();
            }
            String sql = "UPDATE dbo.O_EmergencyMedicationUse SET providing_member_id=?, " +
                    "medication_provision_date=? WHERE event_id=?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setObject(1, cmid); // providing_member_id
            stmt.setDate(2, new java.sql.Date(date.getTime()));
            stmt.setObject(3, eventID);

            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Exception: ", e);
        }
    }

    public void updateLogs(String eventId, String actionTypeName, String descr)
    {
        //logger.log(Level.INFO, "In updateLogs");
        HashMap<String, String> cond = new HashMap<String, String>();
        cond.put("action_type_name", actionTypeName);

        try
        {
            if (!(connection != null && !connection.isClosed() /*&& connection.isValid*/))
                connect();

            int num = -1;
            try{
               num = Integer.parseInt(getRowsFromTable(cond, "O_ActionTypes").get(1).get("action_type_num"));
            } catch (NullPointerException ex){
                //pass
            }


            if (num < -1){
                // Insert new action type
                PreparedStatement stmt = connection.prepareStatement("insert dbo.O_ActionTypes values (?)", Statement.RETURN_GENERATED_KEYS);
                stmt.executeUpdate();

                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()){
                    num = rs.getInt(1);
                }

            }
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO O_EmergencyEventActions  " +
                    "(event_id, action_type_num, more_description) VALUES (?,?,?)");
            stmt.setObject(1, Integer.parseInt(eventId));
            stmt.setObject(2, num);
            stmt.setObject(3, descr);

            stmt.executeUpdate();
        }
        catch (SQLException e) {logger.log(Level.WARNING, "Exception: ", e);}
        finally
        {
            releaseResources(statement, connection);
        }
    }

    public HashMap<String, String> getFieldDetails(String name, String userType)
    {
        HashMap<String, String> conds = new HashMap<String, String>();
        conds.put("field_name", name);
        conds.put("user_type", userType);
        return getRowsFromTable(conds, "RegistrationFields").get(1);
    }

    public boolean isCmidStatusActive(String cmid)
    {
        ResultSet rs = null;
        try {
            if (!(connection != null && !connection.isClosed() /*&& connection.isValid*/))
                connect();
            statement = connection.createStatement();
            // gets the userType by cmid
            rs = statement.executeQuery("SELECT DISTINCT * FROM P_StatusLog INNER JOIN P_Statuses " +
                    "ON P_StatusLog.status_num=P_Statuses.status_num " +
                    "WHERE P_StatusLog.community_member_id=" + cmid + " AND date_to IS NULL");
            if(!rs.next())
                return false;
            return rs.getString("status_name").equals("active");
        }
        // There was a fault with the connection to the server or with SQL
        catch (SQLException e) {logger.log(Level.WARNING, "Exception: ", e); return false;}
        // Releases the resources of this method
        finally
        {
            releaseResources(statement, connection);
            if (rs != null)
            {
                try
                {
                    rs.close();
                }
                catch (Exception e) {logger.log(Level.WARNING, "Exception: ", e);}
            }
        }
    }

    // cmid of assistant
    public boolean doesMedicineMatch(String cmid, String eventId)
    {
        ResultSet rs = null;
        try {
            if (!(connection != null && !connection.isClosed() /*&& connection.isValid*/))
                connect();
            statement = connection.createStatement();
            // gets the userType by cmid
            rs = statement.executeQuery("SELECT DISTINCT * FROM P_Patients INNER JOIN P_Prescriptions " +
                    "ON P_Patients.patient_id=P_Prescriptions.patient_id " +
                    "INNER JOIN M_BrandNames ON M_BrandNames.medication_num=P_Prescriptions.medication_num " +
                    "INNER JOIN M_Indications ON M_BrandNames.brand_name_id=M_Indications.brand_name_id " +
                    "INNER JOIN O_EmergencyEvents ON O_EmergencyEvents.medical_condition_id=M_Indications.medical_condition_id " +
                    "WHERE P_Patients.community_member_id=" + cmid + " AND O_EmergencyEvents.event_id=" + eventId);
            return rs.next();
        }
        // There was a fault with the connection to the server or with SQL
        catch (SQLException e) {logger.log(Level.WARNING, "Exception: ", e); return false;}
        // Releases the resources of this method
        finally
        {
            releaseResources(statement, connection);
            if (rs != null)
            {
                try
                {
                    rs.close();
                }
                catch (Exception e) {logger.log(Level.WARNING, "Exception: ", e);}
            }
        }
    }

    public boolean doesEventHasEMS(String eventId)
    {
        ResultSet rs = null;
        try {
            if (!(connection != null && !connection.isClosed() /*&& connection.isValid*/))
                connect();
            statement = connection.createStatement();
            // gets the userType by cmid
            rs = statement.executeQuery("SELECT DISTINCT * FROM O_EmergencyEvents " +
                    "WHERE event_id=" + eventId + " AND ems_member_id IS NULL");
            return (!rs.next());
        }
        // There was a fault with the connection to the server or with SQL
        catch (SQLException e) {logger.log(Level.WARNING, "Exception: ", e); return false;}
        // Releases the resources of this method
        finally
        {
            releaseResources(statement, connection);
            if (rs != null)
            {
                try
                {
                    rs.close();
                }
                catch (Exception e) {logger.log(Level.WARNING, "Exception: ", e);}
            }
        }
    }

    public HashMap<String, String> getMedicationNameAndDosage(String prescriptionNum)
    {
        ResultSet rs = null;
        try {
            if (!(connection != null && !connection.isClosed() /*&& connection.isValid*/))
                connect();
            statement = connection.createStatement();
            // gets the userType by cmid
            rs = statement.executeQuery("SELECT DISTINCT * FROM P_Prescriptions INNER JOIN P_Medications " +
                    "ON P_Prescriptions.medication_num=P_Medications.medication_num " +
                    " WHERE P_Prescriptions.prescription_num=" + prescriptionNum);
            if(!rs.next())
                return null;
            HashMap<String, String> res = new HashMap<String, String>();
            res.put("medication_name", rs.getString("medication_name"));
            res.put("dosage", rs.getString("dosage"));
            return res;
        }
        // There was a fault with the connection to the server or with SQL
        catch (SQLException e) {logger.log(Level.WARNING, "Exception: ", e); return null;}
        // Releases the resources of this method
        finally
        {
            releaseResources(statement, connection);
            if (rs != null)
            {
                try
                {
                    rs.close();
                }
                catch (Exception e) {logger.log(Level.WARNING, "Exception: ", e);}
            }
        }
    }

    public static void main(String[] args) {
        DbComm_V1 db = new DbComm_V1();
        db.getRowsFromTable(new HashMapBuilder<String, String>().put("event_id","1003").build(),"O_emergencyevents");
    }

}
