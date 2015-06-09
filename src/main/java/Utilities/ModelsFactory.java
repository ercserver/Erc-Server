package Utilities;

import CommunicationModule.src.api.ICommController;
import CommunicationModule.src.controller.CommController_V1;
import DatabaseModule.src.api.IDbController;
import DatabaseModule.src.controller.DbController_V1;

import EmergencyModule.src.api.IEmerFilter_model;
import EmergencyModule.src.api.IEmerLogger_model;
import EmergencyModule.src.model.EmerFilter_V1;
import EmergencyModule.src.model.EmerLogger_V1;

import RoutineModule.src.api.IEmsRoutine_model;
import RoutineModule.src.api.IUpdates_model;
import RoutineModule.src.model.EmsRoutine_V1;
import RoutineModule.src.model.Updates_V1;
import registrationModule.src.api.IRegRequest_model;
import registrationModule.src.api.IRegVerify_model;
import registrationModule.src.model.RegRequest_V1;
import registrationModule.src.model.RegVerify_V2;

/**
 * Created by Maor on 30/04/2015.
 */
public class ModelsFactory {
        private final int commControllerVersion = 1;
        private final int dbControllerVersion = 1;
        private final int regRequestVersion = 1;
        private final int regVerifyVersion = 2;
        private final int emerFilterVersion = 1;
        private final int emerLoggerVersion = 1;
        private final int emsRoutineVersion = 1;

        public ICommController determineCommControllerVersion(){
            switch (commControllerVersion) {
                //Communicate the DB to retrieve the data
                case 1: {
                    return new CommController_V1();
                }
                default: {
                    return null;
                }
            }
        }

        public IDbController determineDbControllerVersion(){
            switch (dbControllerVersion) {
                //Communicate the DB to retrieve the data
                case 1: {
                    return new DbController_V1();
                }
                default: {
                    return null;
                }
            }
        }
        public IRegRequest_model determineRegRequestVersion(){
            switch (regRequestVersion) {
                //Communicate the DB to retrieve the data
                case 1: {
                    return new RegRequest_V1();
                }
                default: {
                    return null;
                }
            }
        }
        public IRegVerify_model determineRegVerifyVersion(){
            switch (regVerifyVersion) {
                //Communicate the DB to retrieve the data
                case 1: {
                   // return new RegVerify_V1();
                    return null;
                }
                case 2: {
                    return new RegVerify_V2();
                }
                default: {
                    return null;
                }
            }
        }

    public IUpdates_model determineIUpdatesVersion(){
        switch (dbControllerVersion) {
            //Communicate the DB to retrieve the data
            case 1: {
                return new Updates_V1();
            }
            default: {
                return null;
            }
        }
    }
    public IEmsRoutine_model determineIEmsRoutineVersion(){
        switch (emsRoutineVersion) {
            //Communicate the DB to retrieve the data
            case 1: {
                return new EmsRoutine_V1();
            }
            default: {
                return null;
            }
        }
    }

   public IEmerFilter_model determineEmerFilterVersion() {
        switch (emerFilterVersion) {
            //Communicate the DB to retrieve the data
            case 1: {
                return new EmerFilter_V1();
            }
            default: {
                return null;
            }
        }
    }
    public IEmerLogger_model determineEmerLoggerVersion() {
        switch (emerLoggerVersion) {
            //Communicate the DB to retrieve the data
            case 1: {
                return new EmerLogger_V1();
            }
            default: {
                return null;
            }
        }
    }
}
