package org.bgu.service;

import junit.framework.Assert;
import org.bgu.domain.facades.ForumFacade;
import org.bgu.domain.model.Forum;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * admin unit test
 * Created by hodai on 4/28/15.
 */
public class ForumServiceTestAdmin {
    public static final int FORUM_ID = 1;

    ForumService forumService;
    UserService userService;
    Forum forum;

    @BeforeClass
    public void systemInit(){
        forum = ForumFacade.createForum(FORUM_ID, "forum 1", "admin", "pass");

        userService = new UserService(FORUM_ID);
        forumService = new ForumService(FORUM_ID, userService);

        // login as forum admin
        Assert.assertTrue( "fail to login", userService.logIn("admin", "pass"));
    }

    @AfterClass
    public void systemRestore(){
        ForumFacade.removeForum(forum);
    }


    @Test
    public void createSubForum_correctData_newSubForumCreated(){
        //TODO
        //forumService.addNewSubForum("sub1", )
    }

    @Test
    public void createSubForum_WrongData_creationFaild(){
        //TODO
    }

    @Test
    public void createSubForum_notForumAdmin_newSubForumCreated(){
        //TODO
    }

}
