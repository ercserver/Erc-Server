package EmergencyModule.src.model;

import DatabaseModule.src.api.IDbController;
import EmergencyModule.src.api.IEmer_model;
import Utilities.ModelsFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by NAOR on 16/05/2015.
 */
public class Emer_V1 implements IEmer_model {

    private IDbController dbController = null;

    Emer_V1(){
        ModelsFactory models = new ModelsFactory();
        dbController = models.determineDbControllerVersion();
    }

    @Override
    public void requestUsersAroundLocation(HashMap<String, String> request) {
        //TODO - Naor
    }


    @Override
    public void requestUsersArrivalTimes(HashMap<String, String> request) {
        //TODO - Maor
    }


    @Override
    public void approachAssistants(ArrayList<String> assistantsList) {
        //TODO - Maor
        //we approach from within "receiveUsersArrivalTimesAndApproach"
    }


    //filter list receied from "receiveUsersAroundLocation"
    @Override
    public ArrayList<String> filterUsersByMatch(ArrayList<String> listToFilter) {
        //TODO - Naor. Need to ask Michael
        return null;
    }


    //filter list received from "receiveUsersArrivalTimesAndApproach"
    @Override
    public ArrayList<String> filterUsersByArrivalTime(HashMap<Integer, HashMap<String, String>> data) {
        //TODO - Maor
        return null;
    }


    @Override
    public void addOrRemoveAssistant(HashMap<String, String> data) {
        //TODO - Naor
        //we call this function from "assistantRespondsToApproach" and from "rejectAssistantsByEMS"
    }

    @Override
    public void approveOrRejectHelper(int patientID, int eventID) {
        //TODO - Maor //we need to tell the helper not to give the medicine
        //TODO - Perhaps we just keep him waiting?? no need to really cancel...?

        //we call this function from "approveOrRejectMed"


    }

}
