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

    Member adminMember; // TODO - check if can be more then one super admin

    public AdminService(){
        adminMember = null;

    }

   /**
    * first init routine  - initial administrator details
    */
   boolean initializeSystem(String adminName,
                            String adminPass){
       //TODO - validate legal data

       // make sure the system wasn't initiated before
       if (!initialized){
           return false;
       }

       //initial the system
       adminMember = UserFacade.createSuperAdmin(adminName, adminPass);
       initialized = true;
       return true;
   }
    /**
     * login to the whole damn system as the super admin
     */
    boolean loginSystem(String adminName,
                        String adminPass){
        // check authentication
        adminMember = UserFacade.loginSuperAdmin(adminName, adminPass);
        if(adminMember == null)
            return false;

        return true;
    }

    boolean createForum(int forumId, String ForumName){
        if (adminMember == null)
            return false;       // only logged in admin can create new forum

        Forum forum = ForumFacade.createForum(forumId, ForumName);
        if (forum == null)
            return false;        // fail creating forum

        return true;
    }
}
