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

    // TODO - check if can be more then one super admin
    Member _superAdminMember;

   public AdminService(){
       _superAdminMember = null;
   }

    public boolean isInitialized(){
        return (UserFacade.isInitialized());
    }

    /**
     * first init routine  - initial administrator details
     */
    public boolean initializeSystem(String adminName, String adminPass) throws Exception {
        //TODO - validate legal data
        _superAdminMember = UserFacade.createSuperAdmin(adminName, adminPass);
        if(_superAdminMember == null)
            return false;
        return true;
    }

    /**
     * login to the whole damn system as the super admin
     */
    public boolean loginSystem(String adminName, String adminPass){
        // check authentication
        _superAdminMember = UserFacade.loginSuperAdmin(adminName, adminPass);
        if(_superAdminMember == null)
            return false;
        return true;
    }

    //TODO - logout form system

    /**
     *
     * @param forumName
     * @param forumAdminName
     * @return true if a new Forum was added to the system, "on-hold", before any policy was configured
     */
    public boolean createForum(String forumName, String forumAdminName, String forumAdminPass) throws Exception {
        if (_superAdminMember == null){
            throw new Exception("Log in first!");
        }
        if (!ForumFacade.createForum(forumName, forumAdminName, forumAdminPass))
            throw new Exception("Forum creation process failed!");
        return true;
    }

    //TODO - erase!!
    /**
     * un-initialize the system
     * used only for the testing
     */
    public void resetSystem() {
        _superAdminMember = null;
        UserFacade.resetUsers();
        ForumFacade.resetForums();
    }
}
