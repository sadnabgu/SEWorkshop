package org.bgu.service;

import junit.framework.Assert;
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
 * members service unit test
 * Created by hodai on 4/28/15.
 */
public class ForumServiseMembersTest {
    public static final String FORUM_NAME = "sex";

    public static Forum forum;

    public static Collection<String> mods;

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

     /* new Thread tests  */



}
