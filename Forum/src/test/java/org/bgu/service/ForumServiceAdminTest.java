package org.bgu.service;

import org.bgu.domain.facades.ForumFacade;
import org.bgu.domain.facades.UserFacade;
import org.bgu.domain.model.Forum;
import org.bgu.service.Exceptions.ForumException;
import org.bgu.service.Exceptions.Result;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * admin unit test
 * Created by hodai on 4/28/15.
 */
public class ForumServiceAdminTest {
    public static final String FORUM_NAME = "sex";

    public static ForumService forumServiceManager;
    public static UserService userServiceManager;
    public static int forumId;
    public static Forum forum;

    public static Collection<String> mods;
    public static Collection<String> mods2;

    @BeforeClass
    public static void initialSystem() {
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
        assertEquals(Result.SUCCESS, UserFacade.addMember(forum.getForumName(), "hodai", "hodai"));
        assertEquals(Result.SUCCESS, UserFacade.addMember(forum.getForumName(), "melki", "melki"));
    }

    /* SUB-FORUM tets */

    @Test
    public void createSubForum_correctData_newSubForumCreated() {
        assertEquals(Result.SUCCESS, userServiceManager.logIn("mike", "admin")._result);
        assertEquals(Result.SUCCESS, forumServiceManager.addNewSubForum("protection", mods)._result);
        assertEquals(Result.SUCCESS, userServiceManager.logOut()._result);
    }

    @Test
    public void createSubForum_notLoggedIn_creationFaild() {
        assertEquals(Result.MODERATOR_NOT_MEMBER, forumServiceManager.addNewSubForum("protection2", mods)._result);
    }

    @Test
    public void createSubForum_notForumAdmin_creationFaild() {
        assertEquals(Result.SUCCESS, userServiceManager.logIn("hodai", "hodai")._result);
        assertEquals(Result.MEMBER_NOT_FORUM_ADMIN, forumServiceManager.addNewSubForum("protection2", mods)._result);
        assertEquals(Result.SUCCESS, userServiceManager.logOut()._result);
    }

    @Test
    public void createSubForum_duplicatedSubForumName_creationFaild() {
        assertEquals(Result.SUCCESS, userServiceManager.logIn("mike", "admin")._result);
        assertEquals(Result.SUCCESS, forumServiceManager.addNewSubForum("protection2", mods)._result);
        assertEquals(Result.DUPLICATED_SUBFORUM, forumServiceManager.addNewSubForum("protection2", mods)._result);
        assertEquals(Result.SUCCESS, userServiceManager.logOut()._result);
    }

    @Test
    public void createSubForum_noModeratesGiven_creationFaild() {
        assertEquals(Result.SUCCESS, userServiceManager.logIn("mike", "admin")._result);
        assertEquals(Result.NO_MODERATORS_WERE_GIVEN, forumServiceManager.addNewSubForum("protection3", new ArrayList<String>())._result);
        assertEquals(Result.SUCCESS, userServiceManager.logOut()._result);
    }

    @Test
    public void createSubForum_moderateNotMember_creationFaild() {
        assertEquals(Result.SUCCESS, userServiceManager.logIn("mike", "admin")._result);
        mods2 = new ArrayList<>();
        mods2.add("hodai");
        mods2.add("tyrion");
        assertEquals(Result.SUCCESS, userServiceManager.logOut()._result);
        assertEquals(Result.SUCCESS, userServiceManager.logIn("mike", "admin")._result);
        assertEquals(Result.SUBFORUM_MODERATOR_NOT_MEMBER, forumServiceManager.addNewSubForum("protection3", mods2)._result);
        assertEquals(Result.SUCCESS, userServiceManager.logOut()._result);
    }

    @Test
    public void removeSubForum_correctData_subForumRemoved() {
        assertEquals(Result.SUCCESS, userServiceManager.logIn("mike", "admin")._result);
        assertEquals(Result.SUCCESS, forumServiceManager.addNewSubForum("protection3", mods)._result);
        assertEquals(Result.SUCCESS, forumServiceManager.removeSubForum("protection3")._result);
        assertEquals(Result.SUCCESS, userServiceManager.logOut()._result);
    }

    @Test
    public void removeSubForum_removedOrnotExisted_failed() {
        assertEquals(Result.SUCCESS, userServiceManager.logIn("mike", "admin")._result);
        assertEquals(Result.SUCCESS, forumServiceManager.addNewSubForum("protection3", mods)._result);
        assertEquals(Result.SUCCESS, forumServiceManager.removeSubForum("protection3")._result);
        assertEquals(Result.SUBFORUM_ALREADY_REMOVED, forumServiceManager.removeSubForum("protection3")._result);
        assertEquals(Result.SUBFORUM_ALREADY_REMOVED, forumServiceManager.removeSubForum("protection4")._result);
        assertEquals(Result.SUCCESS, userServiceManager.logOut()._result);
    }


    /* new Thread tests  */
}
