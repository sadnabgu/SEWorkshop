package org.bgu.service;

import junit.framework.Assert;
import org.bgu.domain.facades.ForumFacade;
import org.bgu.domain.facades.UserFacade;
import org.bgu.domain.model.Forum;
import org.bgu.service.Exceptions.ForumException;
import org.bgu.service.Exceptions.Result;
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
    public static int forumId;
    public static Forum forum;

    public static Collection<String> mods;
    public static Collection<String> mods2;

    @BeforeClass
    public static void initialSystem(){
        forumId = ForumFacade.createForum("sex", "mike", "admin");
        forum = ForumFacade.getForum("sex");
        userServiceManager = new UserService(FORUM_NAME);
        try {
            forumServiceManager = new ForumService(FORUM_NAME, userServiceManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mods = new ArrayList<>();
        mods.add("hodai");
        mods.add("melki");
        Assert.assertEquals(Result.SUCCESS, UserFacade.addMember(forum.getForumName(), "hodai", "hodai"));
        Assert.assertEquals(Result.SUCCESS, UserFacade.addMember(forum.getForumName(), "melki", "melki"));
    }

    /* SUB-FORUM tets */

    @Test
    public void createSubForum_correctData_newSubForumCreated(){
        Assert.assertEquals(Result.SUCCESS, userServiceManager.logIn("mike", "admin"));
        try {
            Assert.assertTrue(forumServiceManager.addNewSubForum("protection", mods));
        } catch (ForumException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(Result.SUCCESS, userServiceManager.logOut());
    }

    @Test
    public void createSubForum_notLoggedIn_creationFaild(){
        try{
            forumServiceManager.addNewSubForum("protection2", mods);
        }catch (ForumException e){
            System.out.println("***" + e.getMessage() + "***");
            Assert.assertEquals(Result.MODERATOR_NOT_MEMBER.toString(), e.getMessage());
        }
    }

    @Test
    public void createSubForum_notForumAdmin_creationFaild(){
        Assert.assertEquals(Result.SUCCESS, userServiceManager.logIn("hodai", "hodai"));
        try{
            forumServiceManager.addNewSubForum("protection2", mods);
        }catch (ForumException e){
            Assert.assertEquals(Result.MEMBER_NOT_FORUM_ADMIN.toString(), e.getMessage());
            System.out.println("***" + e.getMessage() + "***");
        }
        Assert.assertEquals(Result.SUCCESS, userServiceManager.logOut());
    }

    @Test
    public void createSubForum_duplicatedSubForumName_creationFaild(){
        Assert.assertEquals(Result.SUCCESS, userServiceManager.logIn("mike", "admin"));
        try {
            Assert.assertTrue(forumServiceManager.addNewSubForum("protection2", mods));
        } catch (ForumException e) {
            e.printStackTrace();
        }
        try{
            forumServiceManager.addNewSubForum("protection2", mods);
        }catch (ForumException e){
            System.out.println("***" + e.getMessage() + "***");
            Assert.assertEquals(Result.DUPLICATED_SUBFORUM.toString(), e.getMessage());
        }
        Assert.assertEquals(Result.SUCCESS, userServiceManager.logOut());
    }

    @Test
    public void createSubForum_noModeratesGiven_creationFaild(){
        Assert.assertEquals(Result.SUCCESS, userServiceManager.logIn("mike", "admin"));
        try {
            forumServiceManager.addNewSubForum("protection3", new ArrayList<String>());
        }catch (ForumException e){
            System.out.println("***" + e.getMessage() + "***");
            Assert.assertEquals(Result.NO_MODERATORS_WERE_GIVEN.toString(), e.getMessage());
        }
        Assert.assertEquals(Result.SUCCESS, userServiceManager.logOut());
    }

    @Test
    public void createSubForum_moderateNotMember_newSubForumCreated(){
        Assert.assertEquals(Result.SUCCESS, userServiceManager.logIn("mike", "admin"));
        mods2 = new ArrayList<>();
        mods2.add("hodai");
        mods2.add("tyrion");
        Assert.assertEquals(Result.SUCCESS, userServiceManager.logOut());
        Assert.assertEquals(Result.SUCCESS, userServiceManager.logIn("mike", "admin"));
        try {
            forumServiceManager.addNewSubForum("protection3", mods2);
        }catch(ForumException e){
            Assert.assertEquals(Result.SUBFORUM_MODERATOR_NOT_MEMBER.toString(), e.getMessage());
        }
        Assert.assertEquals(Result.SUCCESS, userServiceManager.logOut());
    }


}
