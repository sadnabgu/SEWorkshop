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

import static org.junit.Assert.*;

/**
 * members service unit test
 * Created by hodai on 4/28/15.
 */
public class ForumServiseMembersTest {
    public static final String FORUM_NAME = "sex";
    public static final String SUB_FORUM_NAME = "moms";

    public static ForumService forumService;
    public static UserService userService;
    public static int forumId;
    public static Forum forum;

    public static Collection<String> mods;
    public static Collection<String> mods2;

    @BeforeClass
    public static void initialSystem() {
        ForumFacade.resetForums();
        forumId = ForumFacade.createForum("sex", "mike", "admin");
        forum = ForumFacade.getForum("sex");
        userService = new UserService(FORUM_NAME);
        try {
            forumService = new ForumService(FORUM_NAME, userService);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mods = new ArrayList<>();
        mods.add("melki");
        Assert.assertEquals(Result.SUCCESS, UserFacade.addMember(forum.getForumName(), "hodai", "hodai"));
        Assert.assertEquals(Result.SUCCESS, UserFacade.addMember(forum.getForumName(), "melki", "melki"));
        forum.addNewSubForum(SUB_FORUM_NAME, mods);
        assertEquals(Result.SUCCESS, userService.logIn("hodai", "hodai")._result);

    }

    @Test
    public void addNewThread_correctData_newThreadAdded() {
        assertEquals(Result.SUCCESS, forumService.addNewThread(SUB_FORUM_NAME, "titel1", "opening message1")._result);
    }

}
