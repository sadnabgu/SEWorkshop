package org.bgu.service;

import org.apache.log4j.Logger;
import org.bgu.domain.facades.ForumFacade;
import org.bgu.domain.facades.UserFacade;
import org.bgu.service.ServiceObjects.Result;
import org.bgu.service.ServiceObjects.RetObj;

import java.util.UUID;

/**
 * one object per admin connection/session
 *
 * Created by hodai on 4/20/15.
 */
public class AdminService {
    static Logger logger = Logger.getLogger(AdminService.class);

    /**
     * first init routine  - initial administrator details
     * //TODO - make sure that "initialization" can be made by several "super admins" using UserFacade.addSuperAdmin()
     * @param adminName - initial
     * @param adminPass - initial
     * @return true upon success. exception upon failure.
     */
   public synchronized static RetObj<Object> initializeSystem(String adminName, String adminPass){
       logger.debug(String.format("Going to initialize system admin: %s, pass: %s", adminName, adminPass));

       if (!UserFacade.initSystem(adminName, adminPass)) {
           logger.error(String.format("Cannot to initialize system admin: %s, pass: %s", adminName, adminPass));
           return new RetObj<>(Result.REINITIALIZE_SYSTEM);
       }

       logger.info(String.format("initialize system success admin: %s, pass: %s", adminName, adminPass));
       return new RetObj<>(Result.SUCCESS);
   }

    /**
     * login to the whole damn system as the super admin
     * @param adminName - registered(initialized) admin name
     * @param adminPass - registered(initialized) admin password
     * @return true upon success. exception of Result.FAIL upon failure.
     */
    public synchronized static RetObj<UUID> loginSystem(String adminName, String adminPass){
        logger.debug(String.format("Going to login system admin: %s, pass: %s", adminName, adminPass));

        if (!UserFacade.isInitializedSystem()) {
            logger.warn(String.format("login failed - uninitialized system: %s, pass: %s", adminName, adminPass));
            return new RetObj<>(Result.UNINITIALIZED_SYSTEM);
        }
        if (!UserFacade.validateNamePassSuperAdmin(adminName, adminPass)) {
            logger.warn(String.format("login failed - wrong credentials: %s, pass: %s", adminName, adminPass));
            return new RetObj<>(Result.WRONG_USER_NAME_OR_PASS);
        }

        UUID sId = UserFacade.addSuperAdminSession(adminName);
        if (sId == null){
            logger.warn(String.format("login failed - sid is null : %s, pass: %s", adminName, adminPass));
            return new RetObj<>(Result.WRONG_USER_NAME_OR_PASS);
        }

        logger.info(String.format("login failed - uninitialized system: %s, pass: %s", adminName, adminPass));
        return new RetObj<>(Result.SUCCESS, sId);
    }

    /**
     *
     * @param sId
     * @param ForumName
     * @param managerName
     * @param managerPass
     * @return
     */
    public synchronized static RetObj<Object> createForum(UUID sId, String ForumName, String managerName, String managerPass){
        logger.debug(String.format("creating forum... sid: %s, name: %s, manager: %s, pass: %s", sId, ForumName, managerName, managerPass));
        if (!UserFacade.isLoggedInSuperAdmin(sId)) {
            // only logged in admin can create new forum
            logger.debug(String.format("create forum failed: not logged in as admin... sid: %s, name: %s, manager: %s, pass: %s", sId, ForumName, managerName, managerPass));
            return new RetObj<>(Result.NOT_LOGGEDIN_SYSTEM);
        }
        if (null == managerName || null == managerPass) {
            logger.debug(String.format("create forum failed: user name or password are missing... sid: %s, name: %s, manager: %s, pass: %s", sId, ForumName, managerName, managerPass));
            return new RetObj<Object>(Result.NAME_OR_PASS_MISSING);
        }
        if(!ForumFacade.createForum(ForumName, managerName, managerPass)) {
            logger.debug(String.format("create forum failed: already exists... sid: %s, name: %s, manager: %s, pass: %s", sId, ForumName, managerName, managerPass));
            return new RetObj<>(Result.FORUM_EXISTS);
        }

        logger.info(String.format("create forum success. sid: %s, name: %s, manager: %s, pass: %s", sId, ForumName, managerName, managerPass));
        return new RetObj<>(Result.SUCCESS);
    }

    public synchronized static RetObj<Object> removeForum(UUID sId, String ForumName){
        logger.debug(String.format("removing forum... sid: %s, name: %s", sId, ForumName));
        if (!UserFacade.isLoggedInSuperAdmin(sId)) {
            // only logged in admin can create new forum
            logger.debug(String.format("removing forum failed. not logged in... sid: %s, name: %s", sId, ForumName));
            return new RetObj<>(Result.NOT_LOGGEDIN_SYSTEM);
        }
        if(!ForumFacade.removeForum(ForumName)) {
            logger.debug(String.format("removing forum failed. not exists... sid: %s, name: %s", sId, ForumName));
            return new RetObj<>(Result.FORUM_NOT_EXISTS);
        }

        logger.info(String.format("removing forum success sid: %s, name: %s", sId, ForumName));
        return new RetObj<>(Result.SUCCESS);
    }

    public synchronized static RetObj<Boolean> isInitializedSystem(){
        logger.debug(String.format("checking if system initialized"));

        if (!UserFacade.isInitializedSystem()){
            logger.debug(String.format("system not yet initialized"));
            return new RetObj<>(Result.UNINITIALIZED_SYSTEM, new Boolean(false));
        }

        logger.debug(String.format("system initialized"));
        return new RetObj<>(Result.SUCCESS, new Boolean(true));
    }

    public synchronized static void resetSystem(){
        logger.info(String.format("resetting system"));
        UserFacade.resetSystem();
        logger.info(String.format("resetting ended"));
    }

}
