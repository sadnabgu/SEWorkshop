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
    public static final String SUB_FORUM_NAME = "protection";
    public static final int MANAGER_SID = 1;
    public static final int MEMBER_SID = 2;

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

        // login as admin TODO - replace tests to use regular member and create sub-forum using facade directly
        assertEquals(Result.SUCCESS, UserService.logIn(MEMBER_SID, FORUM_NAME, "hodai", "hodai")._result);
        assertEquals(Result.SUCCESS, UserService.logIn(MANAGER_SID, FORUM_NAME, "mike", "admin")._result);
        assertEquals(Result.SUCCESS, ForumService.addNewSubForum(MANAGER_SID, SUB_FORUM_NAME, mods)._result);


    }

  /* new Thread tests  */

    @Test
    public void addNewThread_correctData_successNewThreadAdded() {
        assertEquals(Result.SUCCESS, ForumService.addNewThread(MEMBER_SID, SUB_FORUM_NAME, "condoms", "how to use them?")._result);
    }

    @Test
    public void addNewThread_titleOrBodyMissingByOtherUsers_successNewThreadAdded() {
        assertEquals(Result.SUCCESS, ForumService.addNewThread(MANAGER_SID, SUB_FORUM_NAME, "", "Im King")._result);
        assertEquals(Result.SUCCESS, ForumService.addNewThread(MEMBER_SID, SUB_FORUM_NAME, "important title", "")._result);
    }

    @Test
    public void addNewThread_titleAndBodyMissing_newThreadFailed() {
        assertEquals(Result.NEW_THREAD_FAIL, ForumService.addNewThread(MEMBER_SID, SUB_FORUM_NAME, "", "")._result);
        assertEquals(Result.NEW_THREAD_FAIL, ForumService.addNewThread(MANAGER_SID, SUB_FORUM_NAME, "", "")._result);
    }

    @Test
    public void addNewThread_notLoggedInUser_newThreadFailed() {
        assertEquals(Result.NEW_THREAD_FAIL, ForumService.addNewThread(3, SUB_FORUM_NAME, "bla", "blaaaa")._result);
        assertEquals(Result.SUCCESS, UserService.logOut(MEMBER_SID)._result);
        assertEquals(Result.NEW_THREAD_FAIL, ForumService.addNewThread(MEMBER_SID, SUB_FORUM_NAME, "bla", "blaaaa")._result);
        assertEquals(Result.SUCCESS, UserService.logIn(MEMBER_SID, FORUM_NAME, "hodai", "hodai")._result);
    }



}
