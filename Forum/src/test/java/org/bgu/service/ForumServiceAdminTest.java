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
        UserFacade.reset();
        ForumFacade.createForum(FORUM_NAME, "mike", "admin");
        forum = ForumFacade.getForum(FORUM_NAME);
        mods = new ArrayList<>();
        mods.add("hodai");
        mods.add("melki");
        assertEquals(Result.SUCCESS, UserFacade.addMember(forum.getForumName(), "hodai", "hodai"));
        assertEquals(Result.SUCCESS, UserFacade.addMember(forum.getForumName(), "melki", "melki"));

        assertEquals(Result.SUCCESS, UserService.logIn(1, FORUM_NAME, "mike", "admin")._result);
        //TODO - login only one time as regular user with different session id
    }
    /* SUB-FORUM tets */

    @Test
    public void createSubForum_correctData_newSubForumCreated() {
        assertEquals(Result.SUCCESS, ForumService.addNewSubForum(1,  "protection", mods)._result);
    }

    @Test
    public void createSubForum_notLoggedIn_creationFail() {
        assertEquals(Result.SUCCESS, UserService.logOut(1)._result);
        assertEquals(Result.UNAUTHORIZED_OPERATION, ForumService.addNewSubForum(1, "protection2", mods)._result);
        assertEquals(Result.SUCCESS, UserService.logIn(1, FORUM_NAME, "mike", "admin")._result);
        assertEquals(Result.UNAUTHORIZED_OPERATION, ForumService.addNewSubForum(2, "protection2", mods)._result);
    }

    @Test
    public void createSubForum_notForumAdmin_creationFail() {
        //TODO - replace login / logout with difrent session id
        assertEquals(Result.SUCCESS, UserService.logOut(1)._result);
        assertEquals(Result.SUCCESS, UserService.logIn(2, FORUM_NAME, "hodai", "hodai")._result);
        assertEquals(Result.UNAUTHORIZED_OPERATION, ForumService.addNewSubForum(2, "protection2", mods)._result);
        assertEquals(Result.SUCCESS, UserService.logOut(2)._result);
        assertEquals(Result.SUCCESS, UserService.logIn(1, FORUM_NAME, "mike", "admin")._result);
    }

    @Test
    public void createSubForum_duplicatedSubForumName_creationFail() {
        assertEquals(Result.SUCCESS, ForumService.addNewSubForum(1, "protection2", mods)._result);
        assertEquals(Result.DUPLICATED_SUBFORUM, ForumService.addNewSubForum(1, "protection2", mods)._result);
    }

    @Test
    public void createSubForum_noModeratesGiven_creationFail() {
        assertEquals(Result.NO_MODERATORS_WERE_GIVEN,
                ForumService.addNewSubForum(1, "protection3", new ArrayList<String>())._result);
    }

    @Test
    public void createSubForum_moderateNotMember_creationFail() {
        mods2 = new ArrayList<>();
        mods2.add("hodai");
        mods2.add("tyrion");
        assertEquals(Result.SUBFORUM_MODERATOR_NOT_MEMBER,
                ForumService.addNewSubForum(1, "protection3", mods2)._result);
    }

    @Test
    public void removeSubForum_correctData_subForumRemoved() {
        assertEquals(Result.SUCCESS, ForumService.addNewSubForum(1, "protection3", mods)._result);
        assertEquals(Result.SUCCESS, ForumService.removeSubForum(1, "protection3")._result);
    }

    @Test
    public void removeSubForum_removedOrnotExisted_failed() {
        assertEquals(Result.SUCCESS, ForumService.addNewSubForum(1, "protection3", mods)._result);
        assertEquals(Result.SUCCESS, ForumService.removeSubForum(1, "protection3")._result);
        assertEquals(Result.SUBFORUM_ALREADY_REMOVED, ForumService.removeSubForum(1, "protection3")._result);
        assertEquals(Result.SUBFORUM_ALREADY_REMOVED, ForumService.removeSubForum(1, "protection4")._result);
    }

}
