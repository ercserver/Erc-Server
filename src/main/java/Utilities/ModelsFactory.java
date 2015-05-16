package Utilities;

import CommunicationModule.src.api.ICommController;
import CommunicationModule.src.controller.CommController_V1;
import DatabaseModule.src.api.IDbController;
import DatabaseModule.src.controller.DbController_V1;
import EmergencyModule.src.api.IEmer_model;
import EmergencyModule.src.model.Emer_V1;
import RoutineModule.src.api.IUpdates_model;
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
        private final int emerVersion = 1;


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

    public IEmer_model determineEmerVersion() {
        switch (emerVersion) {
            //Communicate the DB to retrieve the data
            case 1: {
                return new Emer_V1();
            }
            default: {
                return null;
            }
        }
    }
}
