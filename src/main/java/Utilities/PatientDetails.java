package Utilities;

import DatabaseModule.src.api.IDbController;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.apache.log4j.Level;

/**
 * Created by User on 21/05/2015.
 */
public class PatientDetails {

    private IDbController dbController = null;
    Logger logger = Logger.getLogger(this.getClass().getName());



    public PatientDetails()
    {
        logger.log(Level.INFO, "In PatientDetails ctor");
        ModelsFactory models = new ModelsFactory();
        dbController = models.determineDbControllerVersion();
        logger.log(Level.INFO, "Exiting PatientDetails");
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

    public boolean ifTypeISDoctor(String regid) {
        if (regid.equals("0"))
            return true;
        else
            return false;
    }

    public String getState(int cmid) {
        HashMap<String,String> member = new HashMap<String,String>();
        member.put("P_CommunityMembers.community_member_id",new Integer(cmid).toString());
        HashMap<String,String> details = dbController.getUserByParameter(member);
        return details.get("state");
    }


    public boolean ifTypeISPatientOrGuardian(String code) {
        if (code.equals("0"))
            return false;
        else
            return true;
    }

    public HashMap<String, String> getUserByCmid(int cmid) {

        logger.log(Level.INFO, "In getUserByCmid. Parameters = " + cmid);
        HashMap<String, String> member = new HashMap<String, String>();
        member.put("P_CommunityMembers.community_member_id", new Integer(cmid).toString());
        HashMap<String, String> details = dbController.getUserByParameter(member);
        logger.log(Level.INFO, "details = " + details);
        HashMap<Integer, HashMap<String, String>> reg_id = dbController.getRegIDsOfUser(cmid);
        String reg = "0";
        for (Map.Entry<Integer,HashMap<String,String>> objs : reg_id.entrySet()){
            HashMap<String,String> obj = objs.getValue();
            reg = obj.get("reg_id");
        }
        details.put("reg_id",reg);
        return details;
    }

}
