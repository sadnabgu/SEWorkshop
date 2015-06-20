package org.bgu.service;

import org.bgu.domain.facades.ForumFacade;
import org.bgu.domain.facades.UserFacade;
import org.bgu.domain.model.Forum;
import org.bgu.service.Exceptions.Result;
import org.bgu.service.Exceptions.RetObj;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

/**
 * Created by hodai on 6/20/15.
 */
public class ForumServiceCommentTest {
    private static final String FORUM_NAME = "sex";
    private static final String SUB_FORUM_NAME = "protection";
    private static UUID managerSid;
    private static UUID memberSid;
    private static final String TREAD_TITLE1 = "thread title 1";
    private static final String THREAD_MSG1 = "thread msg 1";
    private static final String TREAD_TITLE2 = "thread title 2";
    private static final String THREAD_MSG2 = "thread msg 2";
    private static Forum forum;
    private static Collection<String> mods;

    @BeforeClass
    public static void initialSystem() {
        UserFacade.resetSystem();
        ForumFacade.createForum(FORUM_NAME, "mike", "admin");
        forum = ForumFacade.getForum(FORUM_NAME);
        mods = new ArrayList<>();
        mods.add("hodai");
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
        assertEquals(Result.SUCCESS, ForumService.addNewThread(managerSid, SUB_FORUM_NAME, TREAD_TITLE1, THREAD_MSG1)._result);
        assertEquals(Result.SUCCESS, ForumService.addNewThread(managerSid, SUB_FORUM_NAME, TREAD_TITLE2, THREAD_MSG2)._result);


    }


        /* comments tests */

    @Test
    public void addNewComment_correctData_successNewThreadAdded() {
        assertEquals(Result.SUCCESS, ForumService.addNewThread(memberSid, SUB_FORUM_NAME, "condoms", "how to use them?")._result);
    }
}
