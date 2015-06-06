package org.bgu.service;

import junit.framework.Assert;
import org.bgu.domain.facades.ForumFacade;
import org.bgu.domain.facades.UserFacade;
import org.bgu.domain.model.Forum;
import org.bgu.domain.model.Member;
import org.bgu.domain.model.SubForum;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

/**
 * admin unit test
 * Created by hodai on 4/28/15.
 */
public class ForumServiceTestAdmin {
    public static final String FORUM_NAME = "sex";

    public static ForumService forumServiceManager;
    public static UserService userServiceManager;
    public static Forum forum;

    public static Collection<String> mods;
    public static Collection<String> mods2;

    @BeforeClass
    public static void initialSystem(){
        forum = ForumFacade.createForum("sex", "mike", "admin");
        userServiceManager = new UserService(FORUM_NAME);
        try {
            forumServiceManager = new ForumService(FORUM_NAME, userServiceManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mods = new ArrayList<>();
        mods.add("hodai");
        mods.add("melki");
        Assert.assertEquals(Result.SUCCESS, userServiceManager.logIn("mike", "admin"));
        Assert.assertEquals(Result.SUCCESS, UserFacade.addMember(forum.getName(), "hodai", "hodai"));
        Assert.assertEquals(Result.SUCCESS, UserFacade.addMember(forum.getName(), "melki", "melki"));
    }

    @Test
    public void createSubForum_correctData_newSubForumCreated(){
        Assert.assertEquals(Result.SUCCESS, forumServiceManager.addNewSubForum("protection", mods));
    }

    @Test
    public void createSubForum_notLoggedIn_creationFaild(){
        Assert.assertEquals(Result.SUCCESS, userServiceManager.logOut());
        Assert.assertEquals(Result.MODERATOR_NOT_MEMBER, forumServiceManager.addNewSubForum("protection2", mods));
        Assert.assertEquals(Result.SUCCESS, userServiceManager.logIn("mike", "admin"));

    }

    @Test
    public void createSubForum_notForumAdmin_newSubForumCreated(){
        Assert.assertEquals(Result.SUCCESS, userServiceManager.logOut());
        Assert.assertEquals(Result.SUCCESS, userServiceManager.logIn("hodai", "hodai"));
        Assert.assertEquals(Result.MEMBER_NOT_FORUM_ADMIN, forumServiceManager.addNewSubForum("protection2", mods));
    }

    @Test
    public void createSubForum_duplicatedSubForumName_newSubForumCreated(){
        Assert.assertEquals(Result.SUCCESS, userServiceManager.logOut());
        Assert.assertEquals(Result.SUCCESS, userServiceManager.logIn("mike", "admin"));
        Assert.assertEquals(Result.DUPLICATED_SUBFORUM, forumServiceManager.addNewSubForum("protection", mods));
    }

    @Test
    public void createSubForum_noModeratesGiven_newSubForumCreated(){
        Assert.assertEquals(Result.SUCCESS, userServiceManager.logOut());
        Assert.assertEquals(Result.SUCCESS, userServiceManager.logIn("mike", "admin"));
        Assert.assertEquals(Result.NO_MODERATORS_WERE_GIVEN, forumServiceManager.addNewSubForum("protection2", new ArrayList<String>()));
    }

    @Test
    public void createSubForum_moderateNotMember_newSubForumCreated(){
        mods2 = new ArrayList<>();
        mods2.add("hodai");
        mods2.add("tyrion");
        Assert.assertEquals(Result.SUCCESS, userServiceManager.logOut());
        Assert.assertEquals(Result.SUCCESS, userServiceManager.logIn("mike", "admin"));
        Assert.assertEquals(Result.SUBFORUM_MODERATOR_NOT_MEMBER, forumServiceManager.addNewSubForum("protection2", mods2));
    }
}
