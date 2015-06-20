package org.bgu.service;

import org.bgu.domain.facades.ForumFacade;
import org.bgu.domain.facades.UserFacade;
import org.bgu.domain.model.Forum;
import org.bgu.service.Exceptions.Result;
import org.bgu.service.Exceptions.RetObj;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;/**/
import java.util.Collection;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * members service unit test
 * Created by hodai on 4/28/15.
 */
public class ForumServiceMembersTest {
    private static final String FORUM_NAME = "sex";
    private static final String SUB_FORUM_NAME = "protection";
    private static UUID managerSid;
    private static UUID memberSid;
    private static Forum forum;
    private static Collection<String> mods;

    @BeforeClass
    public static void initialSystem() {
        UserFacade.resetSystem();
        ForumFacade.createForum(FORUM_NAME, "mike", "admin");
        forum = ForumFacade.getForum(FORUM_NAME);
        mods = new ArrayList<>();
        mods.add("hodai");
        mods.add("melki");
        assertEquals(Result.SUCCESS, UserFacade.addMember(forum.getForumName(), "hodai", "hodai"));
        assertEquals(Result.SUCCESS, UserFacade.addMember(forum.getForumName(), "melki", "melki"));

        // login as admin TODO - replace tests to use regular member and create sub-forum using facade directly
        RetObj<UUID> retObj = UserService.logIn(FORUM_NAME, "hodai", "hodai");
        assertEquals(Result.SUCCESS, retObj._result);
        memberSid = retObj._value;
        retObj = UserService.logIn(FORUM_NAME, "mike", "admin");
        assertEquals(Result.SUCCESS, retObj._result);
        managerSid = retObj._value;
        assertEquals(Result.SUCCESS, ForumService.addNewSubForum(managerSid, SUB_FORUM_NAME, mods)._result);


    }

  /* new Thread tests  */

    @Test
    public void addNewThread_correctData_successNewThreadAdded() {
        assertEquals(Result.SUCCESS, ForumService.addNewThread(memberSid, SUB_FORUM_NAME, "condoms", "how to use them?")._result);
    }

    @Test
    public void addNewThread_titleOrBodyMissingByOtherUsers_successNewThreadAdded() {
        assertEquals(Result.SUCCESS, ForumService.addNewThread(managerSid, SUB_FORUM_NAME, "", "Im King")._result);
        assertEquals(Result.SUCCESS, ForumService.addNewThread(memberSid, SUB_FORUM_NAME, "important title", "")._result);
    }

    @Test
    public void addNewThread_titleAndBodyMissing_newThreadFailed() {
        assertEquals(Result.NEW_THREAD_FAIL, ForumService.addNewThread(memberSid, SUB_FORUM_NAME, "", "")._result);
        assertEquals(Result.NEW_THREAD_FAIL, ForumService.addNewThread(managerSid, SUB_FORUM_NAME, "", "")._result);
    }

    @Test
    public void addNewThread_notLoggedInUser_newThreadFailed() {
        assertEquals(Result.SUCCESS, UserService.logOut(memberSid)._result);
        assertEquals(Result.NEW_THREAD_FAIL, ForumService.addNewThread(memberSid, SUB_FORUM_NAME, "bla", "blaaaa")._result);
        assertEquals(Result.SUCCESS, UserService.logOut(managerSid)._result);
        assertEquals(Result.NEW_THREAD_FAIL, ForumService.addNewThread(managerSid, SUB_FORUM_NAME, "bla", "blaaaa")._result);

        RetObj<UUID> retObj = UserService.logIn(FORUM_NAME, "hodai", "hodai");
        assertEquals(Result.SUCCESS, retObj._result);
        memberSid = retObj._value;

        retObj = UserService.logIn(FORUM_NAME, "mike", "admin");
        assertEquals(Result.SUCCESS, retObj._result);
        managerSid = retObj._value;

    }

}
