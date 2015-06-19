package org.bgu.service;

import junit.framework.Assert;
import org.bgu.domain.facades.ForumFacade;
import org.bgu.domain.facades.UserFacade;
import org.bgu.domain.model.Forum;
import org.bgu.service.Exceptions.Result;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;/**/
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * members service unit test
 * Created by hodai on 4/28/15.
 */
public class ForumServiseMembersTest {
    public static final String FORUM_NAME = "sex";

    public static Forum forum;

    public static Collection<String> mods;

    @BeforeClass
    public static void initialSystem() {
        AdminService.resetSystem();
        ForumFacade.createForum(FORUM_NAME, "mike", "admin");
        forum = ForumFacade.getForum(FORUM_NAME);
        mods = new ArrayList<>();
        mods.add("hodai");
        mods.add("melki");
        assertEquals(Result.SUCCESS, UserFacade.addMember(forum.getForumName(), "hodai", "hodai"));
        assertEquals(Result.SUCCESS, UserFacade.addMember(forum.getForumName(), "melki", "melki"));

        // login as admin TODO - replace tests to use regular member
        assertEquals(Result.SUCCESS, UserService.logIn(1, FORUM_NAME, "mike", "admin")._result);
    }

  /* new Thread tests  */

    @Test
    public void addNewThread_correctData_successNewThreadAdded() {
        assertEquals(Result.SUCCESS, ForumService.addNewSubForum(1, "protection4", mods)._result);
        assertEquals(Result.SUCCESS, ForumService.addNewThread(FORUM_NAME, "protection4", "mike", "condoms", "how to use them?")._result);
        assertEquals(Result.SUCCESS, ForumService.removeSubForum(1, "protection4")._result);
    }

    @Test
    public void addNewThread_titleOrBodyMissingByOtherUsers_successNewThreadAdded() {
        assertEquals(Result.SUCCESS, ForumService.addNewSubForum(1, "protection4", mods)._result);
        assertEquals(Result.SUCCESS, ForumService.addNewThread(FORUM_NAME, "protection4", "mike", "", "Im King")._result);
        assertEquals(Result.SUCCESS, UserService.logIn(2, FORUM_NAME, "hodai", "hodai")._result);
        assertEquals(Result.SUCCESS, ForumService.addNewThread(FORUM_NAME, "protection4", "hodai", "important title", "")._result);
        assertEquals(Result.SUCCESS, ForumService.removeSubForum(1, "protection4")._result);
        assertEquals(Result.SUCCESS, UserService.logOut(2)._result);
    }

    @Test
    public void addNewThread_titleAndBodyMissing_newThreadFailed() {
        assertEquals(Result.SUCCESS, ForumService.addNewSubForum(1, "protection4", mods)._result);
        assertEquals(Result.NEW_THREAD_FAIL, ForumService.addNewThread(FORUM_NAME, "protection4", "mike", "", "")._result);
        assertEquals(Result.SUCCESS, ForumService.removeSubForum(1, "protection4")._result);
    }

    @Test
    public void addNewThread_notLoggedInUser_newThreadFailed() {
        assertEquals(Result.SUCCESS, ForumService.addNewSubForum(1, "protection4", mods)._result);
        assertEquals(Result.NEW_THREAD_FAIL,
                ForumService.addNewThread(FORUM_NAME, "protection4", "shakshuka", "bla", "bla bla bla blaaaa")._result);
        assertEquals(Result.SUCCESS, ForumService.removeSubForum(1, "protection4")._result);
    }



}
