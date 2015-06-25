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
   public static RetObj<Object> initializeSystem(String adminName, String adminPass){
       logger.debug(String.format("Going to initialize system admin: %s, pass: %s", adminName, adminPass));

       if (!UserFacade.initSystem(adminName, adminPass)) {
           logger.error(String.format("Cannot to initialize system admin: %s, pass: %s", adminName, adminPass));
           return new RetObj<>(Result.REINITIALIZE_SYSTEM);
       }

       logger.info(String.format("Cannot to initialize system admin: %s, pass: %s", adminName, adminPass));
       return new RetObj<>(Result.SUCCESS);

   }

    /**
     * login to the whole damn system as the super admin
     * @param adminName - registered(initialized) admin name
     * @param adminPass - registered(initialized) admin password
     * @return true upon success. exception of Result.FAIL upon failure.
     */
    public static RetObj<UUID> loginSystem(String adminName, String adminPass){
        if (!UserFacade.isInitializedSystem())
            return new RetObj<>(Result.UNINITIALIZED_SYSTEM);
        if (!UserFacade.validateNamePassSuperAdmin(adminName, adminPass))
            return new RetObj<>(Result.WRONG_USER_NAME_OR_PASS);
        UUID sId = UserFacade.addSuperAdminSession(adminName);
        if (sId == null)
            return new RetObj<>(Result.WRONG_USER_NAME_OR_PASS);
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
    public static RetObj<Object> createForum(UUID sId, String ForumName, String managerName, String managerPass){
        if (!UserFacade.isLoggedInSuperAdmin(sId)) {
            // only logged in admin can create new forum
            return new RetObj<>(Result.NOT_LOGGEDIN_SYSTEM);
        }
        if (null == managerName || null == managerPass)
            return new RetObj<Object>(Result.NAME_OR_PASS_MISSING);
        if(!ForumFacade.createForum(ForumName, managerName, managerPass))
            return new RetObj<>(Result.FORUM_EXISTS);
        return new RetObj<>(Result.SUCCESS);
    }

    public static RetObj<Object> removeForum(UUID sId, String ForumName){
        if (!UserFacade.isLoggedInSuperAdmin(sId)) {
            // only logged in admin can create new forum
            return new RetObj<>(Result.NOT_LOGGEDIN_SYSTEM);
        }
        if(!ForumFacade.removeForum(ForumName))
            return new RetObj<>(Result.FORUM_NOT_EXISTS);
        return new RetObj<>(Result.SUCCESS);
    }

    public static RetObj<Boolean> isInitializedSystem(){
        if (!UserFacade.isInitializedSystem()){
            return new RetObj<>(Result.UNINITIALIZED_SYSTEM, new Boolean(false));
        }
        else return new RetObj<>(Result.SUCCESS, new Boolean(true));
    }

    public static void resetSystem(){
        UserFacade.resetSystem();
    }

}
