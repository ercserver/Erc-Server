package RoutineModule.src.model;

import DatabaseModule.src.api.IDbController;
import RoutineModule.src.api.IEmsRoutine_model;
import Utilities.ModelsFactory;

import java.util.HashMap;

/**
 * Created by NAOR on 28/04/2015.
 */
public class EmsRoutine_V1 implements IEmsRoutine_model {


    IDbController dbController = null;

    public EmsRoutine_V1() {
        ModelsFactory models = new ModelsFactory();
        dbController = models.determineDbControllerVersion();
    }

    public HashMap<Integer, HashMap<String, String>> getEmsEventsByDispatcherCmid(int cmid) {
        return dbController.getEventsByEmsCmid(cmid);
    }
}
