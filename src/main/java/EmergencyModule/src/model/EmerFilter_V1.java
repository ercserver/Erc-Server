package EmergencyModule.src.model;

import DatabaseModule.src.api.IDbController;
import EmergencyModule.src.api.IEmerFilter_model;
import Utilities.ModelsFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

//import org.apache.log4j.Logger;


// Created by NAOR on 16/05/2015.


public class EmerFilter_V1 implements IEmerFilter_model {

    private IDbController dbController = null;

    //private Logger logger = Logger.getLogger(this.getClass().getName());

    public EmerFilter_V1(){
        ModelsFactory models = new ModelsFactory();
        dbController = models.determineDbControllerVersion();
    }

    //filter list received from "receiveUsersAroundLocation"
   // @Override
    public HashMap<String,String> filterUsersByMatch(HashMap<String, String> listToFilter,String eventID,String region_type,String radius) {
        /**region type and radius are not used for filtering in this version of the filter class**/
     //   logger.info("Assistant to filter = " + listToFilter + " region_type = " + region_type + " radius = " + radius);
        // Creates arrayList of possible assistants
        ArrayList<Integer> intedList = new ArrayList<Integer>();
        Iterator<String> iter = listToFilter.keySet().iterator();
        while(iter.hasNext()) {
            int cur = Integer.parseInt(iter.next());
            // Check if user has regid
            if (!dbController.getRegIDsOfUser(cur).isEmpty() && !intedList.contains(cur)) {
                intedList.add(cur);
            }
        }
     //   logger.info("intedList = " + intedList);
        // Gets filter by the data base
        ArrayList<Integer> filterL = dbController.filterAvailableMembers(intedList, eventID);
        HashMap<String, String> filter = new HashMap<String, String>();
        for(int i = 0; i < filterL.size(); i++) {
            filter.put(Integer.toString(filterL.get(i)), "null");
        }
      //  logger.info("filtered = " + filter);
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
