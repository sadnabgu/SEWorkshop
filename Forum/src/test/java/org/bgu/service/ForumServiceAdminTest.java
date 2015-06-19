package org.bgu.service;

import org.bgu.domain.facades.ForumFacade;
import org.bgu.domain.facades.UserFacade;
import org.bgu.domain.model.Forum;
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

    public static Forum forum;

    public static Collection<String> mods;
    public static Collection<String> mods2;

    @BeforeClass
    public static void initialSystem() {
        ForumFacade.createForum(FORUM_NAME, "mike", "admin");
        forum = ForumFacade.getForum(FORUM_NAME);
        mods = new ArrayList<>();
        mods.add("hodai");
        mods.add("melki");
        assertEquals(Result.SUCCESS, UserFacade.addMember(forum.getForumName(), "hodai", "hodai"));
        assertEquals(Result.SUCCESS, UserFacade.addMember(forum.getForumName(), "melki", "melki"));
    }

    /* SUB-FORUM tets */

    @Test
    public void createSubForum_correctData_newSubForumCreated() {
        assertEquals(Result.SUCCESS, UserService.logIn(FORUM_NAME, "mike", "admin")._result);
        assertEquals(Result.SUCCESS, ForumService.addNewSubForum(FORUM_NAME,"mike",  "protection", mods)._result);
        assertEquals(Result.SUCCESS, UserService.logOut(FORUM_NAME, "mike")._result);
    }

    @Test
    public void createSubForum_notLoggedIn_creationFaild() {
        assertEquals(Result.UNAUTHORIZED_OPERATION, ForumService.addNewSubForum(FORUM_NAME, "mike", "protection2", mods)._result);
    }

    @Test
    public void createSubForum_notForumAdmin_creationFaild() {
        assertEquals(Result.SUCCESS, UserService.logIn(FORUM_NAME, "hodai", "hodai")._result);
        assertEquals(Result.UNAUTHORIZED_OPERATION, ForumService.addNewSubForum(FORUM_NAME, "hodai", "protection2", mods)._result);
        assertEquals(Result.SUCCESS, UserService.logOut(FORUM_NAME, "hodai")._result);
    }

    @Test
    public void createSubForum_duplicatedSubForumName_creationFaild() {
        assertEquals(Result.SUCCESS, UserService.logIn(FORUM_NAME, "mike", "admin")._result);
        assertEquals(Result.SUCCESS, ForumService.addNewSubForum(FORUM_NAME, "mike", "protection2", mods)._result);
        assertEquals(Result.DUPLICATED_SUBFORUM, ForumService.addNewSubForum(FORUM_NAME, "mike", "protection2", mods)._result);
        assertEquals(Result.SUCCESS, UserService.logOut(FORUM_NAME, "mike")._result);
    }

    @Test
    public void createSubForum_noModeratesGiven_creationFaild() {
        assertEquals(Result.SUCCESS, UserService.logIn(FORUM_NAME, "mike", "admin")._result);
        assertEquals(Result.NO_MODERATORS_WERE_GIVEN, ForumService.addNewSubForum(FORUM_NAME, "mike", "protection3", new ArrayList<String>())._result);
        assertEquals(Result.SUCCESS, UserService.logOut(FORUM_NAME, "mike")._result);
    }

    @Test
    public void createSubForum_moderateNotMember_creationFaild() {
        assertEquals(Result.SUCCESS, UserService.logIn(FORUM_NAME, "mike", "admin")._result);
        mods2 = new ArrayList<>();
        mods2.add("hodai");
        mods2.add("tyrion");
        assertEquals(Result.SUBFORUM_MODERATOR_NOT_MEMBER, ForumService.addNewSubForum(FORUM_NAME, "mike", "protection3", mods2)._result);
        assertEquals(Result.SUCCESS, UserService.logOut(FORUM_NAME, "mike")._result);
    }

    @Test
    public void removeSubForum_correctData_subForumRemoved() {
        assertEquals(Result.SUCCESS, UserService.logIn(FORUM_NAME, "mike", "admin")._result);
        assertEquals(Result.SUCCESS, ForumService.addNewSubForum(FORUM_NAME, "mike", "protection3", mods)._result);
        assertEquals(Result.SUCCESS, ForumService.removeSubForum(FORUM_NAME, "mike", "protection3")._result);
        assertEquals(Result.SUCCESS, UserService.logOut(FORUM_NAME, "mike")._result);
    }

    @Test
    public void removeSubForum_removedOrnotExisted_failed() {
        assertEquals(Result.SUCCESS, UserService.logIn(FORUM_NAME, "mike", "admin")._result);
        assertEquals(Result.SUCCESS, ForumService.addNewSubForum(FORUM_NAME, "mike", "protection3", mods)._result);
        assertEquals(Result.SUCCESS, ForumService.removeSubForum(FORUM_NAME, "mike", "protection3")._result);
        assertEquals(Result.SUBFORUM_ALREADY_REMOVED, ForumService.removeSubForum(FORUM_NAME, "mike", "protection3")._result);
        assertEquals(Result.SUBFORUM_ALREADY_REMOVED, ForumService.removeSubForum(FORUM_NAME, "mike", "protection4")._result);
        assertEquals(Result.SUCCESS, UserService.logOut(FORUM_NAME, "mike")._result);
    }

    /* new Thread tests  */


    @Test
    public void addNewThread_correctData_successNewThreadAdded() {
        assertEquals(Result.SUCCESS, UserService.logIn(FORUM_NAME, "mike", "admin")._result);
        assertEquals(Result.SUCCESS, ForumService.addNewSubForum(FORUM_NAME, "mike", "protection4", mods)._result);
        assertEquals(Result.SUCCESS, ForumService.addNewThread(FORUM_NAME, "protection4", "mike", "condoms", "how to use them?")._result);
        assertEquals(Result.SUCCESS, ForumService.removeSubForum(FORUM_NAME, "mike", "protection4")._result);
        assertEquals(Result.SUCCESS, UserService.logOut(FORUM_NAME, "mike")._result);
    }

    @Test
    public void addNewThread_titleOrBodyMissingByOtherUsers_successNewThreadAdded() {
        assertEquals(Result.SUCCESS, UserService.logIn(FORUM_NAME, "mike", "admin")._result);
        assertEquals(Result.SUCCESS, ForumService.addNewSubForum(FORUM_NAME, "mike", "protection4", mods)._result);
        assertEquals(Result.SUCCESS, ForumService.addNewThread(FORUM_NAME, "protection4", "mike", "", "Im King")._result);
        assertEquals(Result.SUCCESS, UserService.logIn(FORUM_NAME, "hodai", "hodai")._result);
        assertEquals(Result.SUCCESS, ForumService.addNewThread(FORUM_NAME, "protection4", "hodai", "important title", "")._result);
        assertEquals(Result.SUCCESS, ForumService.removeSubForum(FORUM_NAME, "mike", "protection4")._result);
        assertEquals(Result.SUCCESS, UserService.logOut(FORUM_NAME, "hodai")._result);
        assertEquals(Result.SUCCESS, UserService.logOut(FORUM_NAME, "mike")._result);
    }

    @Test
    public void addNewThread_titleAndBodyMissing_newThreadFailed() {
        assertEquals(Result.SUCCESS, UserService.logIn(FORUM_NAME, "mike", "admin")._result);
        assertEquals(Result.SUCCESS, ForumService.addNewSubForum(FORUM_NAME, "mike", "protection4", mods)._result);
        assertEquals(Result.NEW_THREAD_FAIL, ForumService.addNewThread(FORUM_NAME, "protection4", "mike", "", "")._result);
        assertEquals(Result.SUCCESS, ForumService.removeSubForum(FORUM_NAME, "mike", "protection4")._result);
        assertEquals(Result.SUCCESS, UserService.logOut(FORUM_NAME, "mike")._result);
    }

    @Test
    public void addNewThread_notLoggedInUser_newThreadFailed() {
        assertEquals(Result.SUCCESS, UserService.logIn(FORUM_NAME, "mike", "admin")._result);
        assertEquals(Result.SUCCESS, ForumService.addNewSubForum(FORUM_NAME, "mike", "protection4", mods)._result);
        assertEquals(Result.NEW_THREAD_FAIL, ForumService.addNewThread(FORUM_NAME, "protection4", "shakshuka", "bla", "bla bla bla blaaaa")._result);
        assertEquals(Result.SUCCESS, ForumService.removeSubForum(FORUM_NAME, "mike", "protection4")._result);
        assertEquals(Result.SUCCESS, UserService.logOut(FORUM_NAME, "mike")._result);
    }
}
