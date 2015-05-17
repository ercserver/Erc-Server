package EmergencyModule.src.model;

import DatabaseModule.src.api.IDbController;
import EmergencyModule.src.api.IEmer_model;
import Utilities.ModelsFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by NAOR on 16/05/2015.
 */
public class EmerFilter_V1 implements IEmer_model {

    private IDbController dbController = null;

    public EmerFilter_V1(){
        ModelsFactory models = new ModelsFactory();
        dbController = models.determineDbControllerVersion();
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



}
