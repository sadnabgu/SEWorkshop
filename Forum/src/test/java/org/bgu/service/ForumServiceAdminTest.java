package org.bgu.service;

import org.bgu.domain.facades.ForumFacade;
import org.bgu.domain.facades.UserFacade;
import org.bgu.domain.model.Forum;
import org.bgu.domain.model.notification.OnlineStrategy;
import org.bgu.service.Exceptions.Result;
import org.bgu.service.Exceptions.RetObj;
import org.bgu.service.ServiceObjects.Result;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * admin unit test
 * Created by hodai on 4/28/15.
 */
public class ForumServiceAdminTest extends abstractTest{
    private static Forum forum;

    private static Collection<String> mods;
    private static Collection<String> mods2;


    @BeforeClass
    public static void initialSystem() {
        UserFacade.resetSystem();
        ForumFacade.createForum(FORUM_NAME, MANAGER1_NAME, MANAGER1_PASS);
        forum = ForumFacade.getForum(FORUM_NAME);
        mods = new ArrayList<>();
        mods.add("hodai");
        assertEquals(Result.SUCCESS, UserFacade.addMember(FORUM_NAME, "hodai", "hodai"));

        loginAdmin();
        loginModerate();
        //TODO - login only one time as regular user with different session id
    }
    /* SUB-FORUM tets */



    @Test
    public void createSubForum_correctData_newSubForumCreated() {
        assertEquals(Result.SUCCESS, ForumService.addNewSubForum(managerSid,  "protection", mods)._result);
    }

    @Test
    public void createSubForum_notLoggedIn_creationFail() {
        assertEquals(Result.UNAUTHORIZED_OPERATION, ForumService.addNewSubForum(UUID.randomUUID(), "protection2", mods)._result);
        assertEquals(Result.SUCCESS, UserService.logOut(managerSid)._result);
        assertEquals(Result.UNAUTHORIZED_OPERATION, ForumService.addNewSubForum(managerSid, "protection2", mods)._result);
        loginAdmin();
        assertEquals(Result.UNAUTHORIZED_OPERATION, ForumService.addNewSubForum(moderateSid, "protection2", mods)._result);
        assertEquals(Result.SUCCESS, ForumService.addNewSubForum(managerSid, "protection2", mods)._result);
    }

    @Test
    public void createSubForum_notForumAdmin_creationFail() {
        //TODO - replace login / logout with difrent session id
        assertEquals(Result.UNAUTHORIZED_OPERATION, ForumService.addNewSubForum(moderateSid, "protection2", mods)._result);
        assertEquals(Result.SUCCESS, UserService.logOut(managerSid)._result);
        assertEquals(Result.UNAUTHORIZED_OPERATION, ForumService.addNewSubForum(managerSid, "protection2", mods)._result);
        loginAdmin();
    }

    @Test
    public void createSubForum_duplicatedSubForumName_creationFail() {
        assertEquals(Result.SUCCESS, ForumService.addNewSubForum(managerSid, "protection3", mods)._result);
        assertEquals(Result.DUPLICATED_SUBFORUM, ForumService.addNewSubForum(managerSid, "protection3", mods)._result);
        assertEquals(Result.SUCCESS, ForumService.removeSubForum(managerSid, "protection3")._result);
    }

    @Test
    public void createSubForum_noModeratesGiven_creationFail() {
        assertEquals(Result.NO_MODERATORS_WERE_GIVEN,
                ForumService.addNewSubForum(managerSid, "protection3", new ArrayList<String>())._result);
    }

    @Test
    public void createSubForum_moderateNotMember_creationFail() {
        mods2 = new ArrayList<>();
        mods2.add("hodai");
        mods2.add("tyrion");
        assertEquals(Result.SUBFORUM_MODERATOR_NOT_MEMBER,
                ForumService.addNewSubForum(managerSid, "protection3", mods2)._result);
    }

    @Test
    public void removeSubForum_correctData_subForumRemoved() {
        assertEquals(Result.SUCCESS, ForumService.addNewSubForum(managerSid, "protection3", mods)._result);
        assertEquals(Result.SUCCESS, ForumService.removeSubForum(managerSid, "protection3")._result);
    }

    @Test
    public void removeSubForum_removedOrnotExisted_failed() {
        assertEquals(Result.SUCCESS, ForumService.addNewSubForum(managerSid, "protection3", mods)._result);
        assertEquals(Result.SUCCESS, ForumService.removeSubForum(managerSid, "protection3")._result);
        assertEquals(Result.SUBFORUM_ALREADY_REMOVED, ForumService.removeSubForum(managerSid, "protection3")._result);
        assertEquals(Result.SUBFORUM_ALREADY_REMOVED, ForumService.removeSubForum(managerSid, "protection4")._result);
    }

}
