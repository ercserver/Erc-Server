package CommunicationModule.src.controller;

import CommunicationModule.src.api.*;
import CommunicationModule.src.model.CommOfficialFactory_V1;
import CommunicationModule.src.model.CommToUsersFactory_V1;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;


/**
 * Created by NAOR on 06/04/2015.
 */
public class CommController_V1 implements ICommController {
    //version to use - change this to change version - edit decision methos in accordance
    private static final int commToUsersVersion = 1;
    private static final int commOfficialVersion = 1;
    private Logger logger = Logger.getLogger(getClass().getName());

    //holding the implementations chosen for the interface (composition)
    private ICommToUsers_model commToUsers = null;
    private ICommOfficial_model commOfficial = null;

    public Object sendResponse() {
        if(null != commToUsers) {
            return commToUsers.sendResponse();
        }
        else{
            //throw some kind of alert?
            return null;
        }
    }

    public void sendMessage()  {
        if (null != commOfficial) {
            commOfficial.sendMessage();
        }
        else{
            //throw some kind of alert?
        }
    }

    //set methods for the members
    public void setCommOfficial(HashMap<String,String> data,int type){
        ICommOfficialFactory commOfficialFact = determineCommOfficialVersion();
        commOfficial = commOfficialFact.createComm(data,type);
    }
    public void setCommToUsers(HashMap<Integer, HashMap<String, String>> data,
                               ArrayList<String> target,boolean initiatedComm){
        logger.log(Level.INFO, "In setCommToUsers. data = " + data + ", target = " + target +
        ", initiatedComm = " + initiatedComm);
        ICommToUsersFactory commToUsersFact = determineCommToUsersVersion();
        commToUsers = commToUsersFact.createComm(data,target,initiatedComm);
        logger.log(Level.INFO, "exiting setCommToUsers");
    }


    private ICommToUsersFactory determineCommToUsersVersion(){
        switch (commToUsersVersion) {
            //determine version of CommToUsers
            case 1: {
                return new CommToUsersFactory_V1();
            }
            default: {
                return null;
            }
        }
    }

    private ICommOfficialFactory determineCommOfficialVersion(){
        switch (commOfficialVersion) {
            //determine version of CommToMail to use
            case 1: {
                return new CommOfficialFactory_V1();
            }
            default: {
                return null;
            }
        }
    }
}
