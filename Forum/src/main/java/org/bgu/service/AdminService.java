package org.bgu.service;

import org.bgu.domain.facades.ForumFacade;
import org.bgu.domain.facades.UserFacade;
import org.bgu.domain.model.Forum;
import org.bgu.domain.model.Member;
import org.bgu.service.Exceptions.ForumException;
import org.bgu.service.Exceptions.Result;

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
     * @throws Exception, exception {Result.REINITIALIZE_SYSTEM} upon failure
     */
   public boolean initializeSystem(String adminName, String adminPass) throws ForumException{

       // make sure the system wasn't initiated before
       if (initialized){
           throw new ForumException(Result.REINITIALIZE_SYSTEM.toString());
       }

       //initial the system
       initialized = true;
       adminMember = UserFacade.createSuperAdmin(adminName, adminPass);
       return true;
   }

    /**
     * login to the whole damn system as the super admin
     * TODO - any POLICY enforcement on chosen username-password?...)
     * @param adminName
     * @param adminPass
     * @return true upon success. exception of Result.FAIL upon failure.
     */
    public boolean loginSystem(String adminName,
                        String adminPass) throws ForumException{
        if(!initialized)
            throw new ForumException(Result.UNINITIALIZED_SYSTEM.toString());
        adminMember = UserFacade.loginSuperAdmin(adminName, adminPass);
        if(adminMember == null)
            throw new ForumException(Result.FAIL.toString());

        return true;
    }

    /**
     *
     * @param ForumName
     * @param managerName
     * @param managerPass
     * @return true upon success, Exception {Result.FORUM_EXISTS, Result.NOT_LOGGEDIN_SYSTEM} upon failure
     */
    public boolean createForum(String ForumName, String managerName, String managerPass)throws ForumException {
        if (adminMember == null)
            // only logged in admin can create new forum
            throw new ForumException(Result.NOT_LOGGEDIN_SYSTEM.toString());

        if(ForumFacade.createForum(ForumName, managerName, managerPass) < 0)
            throw new ForumException(Result.FORUM_EXISTS.toString());
        return true;
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
