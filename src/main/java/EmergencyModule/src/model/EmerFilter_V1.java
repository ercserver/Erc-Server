package EmergencyModule.src.model;

import DatabaseModule.src.api.IDbController;
import EmergencyModule.src.api.IEmerFilter_model;
import Utilities.ModelsFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by NAOR on 16/05/2015.
 */
public class EmerFilter_V1 implements IEmerFilter_model {

    private IDbController dbController = null;

    public EmerFilter_V1(){
        ModelsFactory models = new ModelsFactory();
        dbController = models.determineDbControllerVersion();
    }




    //filter list receied from "receiveUsersAroundLocation"
    @Override
    public HashMap<String,String> filterUsersByMatch(HashMap<String, String> listToFilter) {
        //TODO - Naor. Need to ask Michael
        return null;
    }


    //filter list received from "receiveUsersArrivalTimesAndApproach"
    @Override
    public HashMap<Integer, HashMap<String, String>> filterUsersByArrivalTime(HashMap<Integer,HashMap<String, String>> listToFilter) {
        //TODO - Maor
        return null;
    }



}
