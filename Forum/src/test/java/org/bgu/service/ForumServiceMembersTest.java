package org.bgu.service;

import org.bgu.domain.facades.ForumFacade;
import org.bgu.domain.facades.UserFacade;
import org.bgu.domain.model.Forum;
import org.bgu.service.ServiceObjects.Result;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;/**/
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * members service unit test
 * Created by hodai on 4/28/15.
 */
public class ForumServiceMembersTest extends abstractTest{
    private static final String SUB_FORUM_NAME = "protection";
    private static Forum forum;
    private static Collection<String> mods;

    @BeforeClass
    public static void initialSystem() {
        UserFacade.resetSystem();
        ForumFacade.createForum(FORUM_NAME,MANAGER1_NAME, MANAGER1_PASS);
        forum = ForumFacade.getForum(FORUM_NAME);
        mods = new ArrayList<>();
        mods.add("hodai");
        mods.add("melki");
        assertEquals(Result.SUCCESS, UserFacade.addMember(forum.getForumName(), "hodai", "hodai"));
        assertEquals(Result.SUCCESS, UserFacade.addMember(forum.getForumName(), "melki", "melki"));

        // login as admin TODO - replace tests to use regular member and create sub-forum using facade directly
        loginModerate();
        loginAdmin();
        assertEquals(Result.SUCCESS, ForumService.addNewSubForum(managerSid, SUB_FORUM_NAME, mods)._result);


    }

  /* new Thread tests  */

    @Test
    public void addNewThread_correctData_successNewThreadAdded() {
        assertEquals(Result.SUCCESS, ForumService.addNewThread(moderateSid, SUB_FORUM_NAME, "condoms", "how to use them?")._result);
    }

    @Test
    public void addNewThread_titleOrBodyMissingByOtherUsers_successNewThreadAdded() {
        assertEquals(Result.SUCCESS, ForumService.addNewThread(managerSid, SUB_FORUM_NAME, "", "Im King")._result);
        assertEquals(Result.SUCCESS, ForumService.addNewThread(moderateSid, SUB_FORUM_NAME, "important title", "")._result);
    }

    @Test
    public void addNewThread_titleAndBodyMissing_newThreadFailed() {
        assertEquals(Result.NEW_THREAD_FAIL, ForumService.addNewThread(moderateSid, SUB_FORUM_NAME, "", "")._result);
        assertEquals(Result.NEW_THREAD_FAIL, ForumService.addNewThread(managerSid, SUB_FORUM_NAME, "", "")._result);
    }

    @Test
    public void addNewThread_notLoggedInUser_newThreadFailed() {
        assertEquals(Result.SUCCESS, UserService.logOut(moderateSid)._result);
        assertEquals(Result.NEW_THREAD_FAIL, ForumService.addNewThread(moderateSid, SUB_FORUM_NAME, "bla", "blaaaa")._result);
        assertEquals(Result.SUCCESS, UserService.logOut(managerSid)._result);
        assertEquals(Result.NEW_THREAD_FAIL, ForumService.addNewThread(managerSid, SUB_FORUM_NAME, "bla", "blaaaa")._result);

        loginModerate();
        loginAdmin();

    }

}
