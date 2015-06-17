package org.bgu.service;

import org.bgu.domain.facades.ForumFacade;
import org.bgu.domain.facades.UserFacade;
import org.bgu.domain.model.Member;
import org.bgu.service.Exceptions.Result;
import org.bgu.service.Exceptions.RetObj;

/**
 * one object per admin connection/session
 *
 * Created by hodai on 4/20/15.
 */
public class AdminService {

    /** static flag - indicate if the forum was initialized */
    static private boolean initialized = false;

    Member adminMember;
    // TODO - check if can be more then one super admin

    public AdminService(){
        adminMember = null;

    }

    /**
     * first init routine  - initial administrator details
     * TODO - check the nature of system-init ("initialized" state per super-admin first initialization? ; any POLICY enforcement on chosen username-password?...)
     * @param adminName - initial
     * @param adminPass - initial
     * @return true upon success. exception upon failure.
     */
   public RetObj<Object> initializeSystem(String adminName, String adminPass){

       // make sure the system wasn't initiated before
       if (initialized){
           return new RetObj<>(Result.REINITIALIZE_SYSTEM);
       }

       //initial the system
       initialized = true;
       adminMember = UserFacade.createSuperAdmin(adminName, adminPass);
       return new RetObj<>(Result.SUCCESS);
   }

    /**
     * login to the whole damn system as the super admin
     * TODO - any POLICY enforcement on chosen username-password?...)
     * @param adminName
     * @param adminPass
     * @return true upon success. exception of Result.FAIL upon failure.
     */
    public RetObj<Object> loginSystem(String adminName,String adminPass){
        if(!initialized)
            return new RetObj<>(Result.UNINITIALIZED_SYSTEM);
        adminMember = UserFacade.loginSuperAdmin(adminName, adminPass);
        if(adminMember == null)
            return new RetObj<>(Result.FAIL);

        return new RetObj<>(Result.SUCCESS);
    }

    /**
     *
     * @param ForumName
     * @param managerName
     * @param managerPass
     * @return true upon success, Exception {Result.FORUM_EXISTS, Result.NOT_LOGGEDIN_SYSTEM} upon failure
     */
    public RetObj<Object> createForum(String ForumName, String managerName, String managerPass){
        if (adminMember == null) {
            // only logged in admin can create new forum
            return new RetObj<>(Result.NOT_LOGGEDIN_SYSTEM);
        }
        if(ForumFacade.createForum(ForumName, managerName, managerPass) < 0)
            return new RetObj<>(Result.FORUM_EXISTS);
        return new RetObj<>(Result.SUCCESS);
    }

    /**
     * un-initialize the system
     * used only for the testing
     */
    public void resetSystem() {
        initialized = false;
        adminMember = null;
        UserFacade.resetSuperAdmins();
        ForumFacade.resetForums();
    }
}
