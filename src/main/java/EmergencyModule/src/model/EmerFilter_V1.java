package EmergencyModule.src.model;

import DatabaseModule.src.api.IDbController;
import EmergencyModule.src.api.IEmerFilter_model;
import Utilities.ModelsFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


// Created by NAOR on 16/05/2015.


public class EmerFilter_V1 implements IEmerFilter_model {

    private IDbController dbController = null;

    public EmerFilter_V1(){
        ModelsFactory models = new ModelsFactory();
        dbController = models.determineDbControllerVersion();
    }




    //filter list receied from "receiveUsersAroundLocation"
   // @Override
    public HashMap<String,String> filterUsersByMatch(HashMap<String, String> listToFilter,String eventID) {
        // Creates arrayList of possible assistants
        ArrayList<Integer> l = new ArrayList<Integer>();
        Iterator<String> iter = listToFilter.keySet().iterator();
        while(iter.hasNext())
            l.add(Integer.parseInt(iter.next()));
        // Gets filter by the data base
        ArrayList<Integer> filterL = dbController.filterAvailableMembers(l, eventID);
        HashMap<String, String> filter = new HashMap<String, String>();
        for(int i = 0; i < filterL.size(); i++)
            filter.put(Integer.toString(filterL.get(i)), null);

        return filter;

    }

    //filter list received from "receiveUsersArrivalTimesAndApproach"
    @Override
    public HashMap<Integer, HashMap<String, String>> filterUsersByArrivalTime(HashMap<Integer,HashMap<String, String>> listToFilter) {
        //Not in prototype
        String EMSArrivalTime = null;
        if(EMSArrivalTime != null){}
        return listToFilter;
    }



}
