package registrationModule.src.model;

import DatabaseModule.src.api.IDbController;
import Utilities.ModelsFactory;
import registrationModule.src.api.IRegRequest_model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Created by NAOR on 06/04/2015.
 */
public class RegRequest_V1 implements IRegRequest_model {
    private final String userExistsMessage = "An active user with this mail already exists";

    private IDbController dbController = null;

    private Logger logger = Logger.getLogger(this.getClass().getName());

    public RegRequest_V1()
    {
        logger.log(Level.INFO, "In RegRequest_V1 ctor");
        ModelsFactory models = new ModelsFactory();
        dbController = models.determineDbControllerVersion();
        logger.log(Level.INFO, "exiting RegRequest_V1 ctor");
    }

    public String doesUserExist(HashMap<String, String> filledForm) {
        //search for an active user with that email - if not found return null, else return "userExistsMessage".
        HashMap<String,String> whereConditions = new HashMap<String, String>();
        whereConditions.put("P_CommunityMembers.email_address", filledForm.get("email_address"));
        HashMap<String,String> result = dbController.getUserByParameter(whereConditions);
        String message = null;
        if((null == result) || (result.get("status_name").equals("deleteAccount"))){
            return null;
        }
        return userExistsMessage;
    }

    public HashMap<String, String> filterFieldsForDoctorAuth(HashMap<String, String> fieldsToFilter) {
        HashMap<String,String> filteredResponse = new HashMap<String, String>();
        //Get the list of fields to filter
        ArrayList<String> listOfFieldsToFilter = decideFieldsToFilterForDoctorAuth();
        //One-by-one - retrieve the fields into the response
        for(String field : listOfFieldsToFilter){
            filteredResponse.put(field,fieldsToFilter.get(field));
        }
        filteredResponse.put("RequestID", "waitingPatients");
        return filteredResponse;
    }

    private ArrayList<String> decideFieldsToFilterForDoctorAuth() {
        ArrayList<String> decision = new ArrayList<String>();
        decision.add("first_name");
        decision.add("last_name");
        decision.add("community_member_id");
        decision.add("patient_id");
        decision.add("external_id");
        //TODO - More fields?
        //decision.add("medical_condition");
        //...
        //...
        return decision;
    }

}
