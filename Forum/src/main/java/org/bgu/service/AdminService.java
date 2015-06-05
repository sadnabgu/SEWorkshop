package org.bgu.service;

import org.bgu.domain.facades.ForumFacade;
import org.bgu.domain.facades.UserFacade;
import org.bgu.domain.model.Forum;
import org.bgu.domain.model.Member;

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
    *
    * TODO - validate legal data, POLICY
    */
   public Result initializeSystem(String adminName,
                            String adminPass){

       // make sure the system wasn't initiated before
       if (initialized){
           return Result.REINITIALIZE_SYSTEM;
       }

       //initial the system
       initialized = true;
       adminMember = UserFacade.createSuperAdmin(adminName, adminPass);
       return Result.SUCCESS;
   }
    /**
     * login to the whole damn system as the super admin
     *
     * TODO - validate legal data, POLICY
     */
    public Result loginSystem(String adminName,
                        String adminPass){
        if(!initialized){
            return Result.UNINITIALIZED_SYSTEM;
        }
        adminMember = UserFacade.loginSuperAdmin(adminName, adminPass);
        if(adminMember == null)
            return Result.FAIL;

        return Result.SUCCESS;
    }

    public Result createForum(String ForumName){
        if (adminMember == null){
            return Result.NOT_LOGGEDIN_SYSTEM;       // only logged in admin can create new forum
        }

        Forum forum = ForumFacade.createForum(ForumName, "admin", "pass");
        if (forum == null)
            return Result.FORUM_EXISTS;        // fail creating forum

        return Result.SUCCESS;
    }

    /**
     * un-initialize the system
     * used only for the testing
     */
    public void resetSystem() {
        initialized = false;
        adminMember = null;
        UserFacade.resetUsers();
        ForumFacade.resetForums();
    }
}
