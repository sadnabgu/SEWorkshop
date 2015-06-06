package org.bgu.service;

import junit.framework.Assert;
import org.bgu.domain.facades.ForumFacade;
import org.bgu.domain.model.Forum;
import org.bgu.domain.model.Member;
import org.bgu.domain.model.SubForum;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * admin unit test
 * Created by hodai on 4/28/15.
 */
public class ForumServiceTestAdmin {
    public static final String FORUM_NAME = "sex";

    public static ForumService forumServiceManager;
    public static UserService userServiceManager;
    public static Forum forum;
    public static SubForum sForum;



    @BeforeClass
    public static void initialSystem(){
        forum = ForumFacade.createForum("sex", "mike", "admin");
        userServiceManager = new UserService(FORUM_NAME);
        try {
            forumServiceManager = new ForumService(FORUM_NAME, userServiceManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createSubForum_correctData_newSubForumCreated(){
        //Assert.assertTrue();
        //Assert.assertEquals(Result.SUCCESS, forumServiceManager.addNewSubForum("protection"));
        //Assert.assertTrue(forum.equals(ForumFacade.getForum("sex")));
        //Assert.assertTrue(forum.equals(ForumFacade.getForum("sex")));
    }

    @Test
    public void createSubForum_WrongData_creationFaild(){
        //Assert.assertEquals(Result.DUPLICATED_SUBFORUM, forumServiceManager.addNewSubForum("duplicated"));
    }

    @Test
    public void createSubForum_notForumAdmin_newSubForumCreated(){
        //TODO
    }

}
