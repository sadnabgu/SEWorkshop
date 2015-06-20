package org.bgu.service;
import org.bgu.domain.facades.UserFacade;
import org.bgu.service.Exceptions.Result;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created by hodai on 4/21/15.
 */
public class AdminServiceTest {
    public static final String ADMIN1_NAME = "admin1";
    public static final String ADMIN1_PASS = "pass1";
    public static final UUID ADMIN1_SID = null;

    public static final String MANAGER1_NAME = "manager1";
    public static final String MANAGER1_PASS = "pass1";
    public static final String MANAGER2_NAME = "manager2";
    public static final String MANAGER2_PASS = "pass2";

    @BeforeClass
    public static void initialSystem() {
        UserFacade.resetSystem();
        assertEquals(Result.SUCCESS, AdminService.initializeSystem(ADMIN1_NAME, ADMIN1_PASS)._result);
    }

    @Before
    public void logoutSystem() {
        initialSystem();
    }

    @Test
    public void loginSystem() {
        assertEquals(Result.SUCCESS, AdminService.loginSystem(ADMIN1_SID, ADMIN1_NAME, ADMIN1_PASS)._result);
    }

    @Test
    public void initializeSystem_initialSequence_pass() {
        UserFacade.resetSystem();
        assertEquals(Result.SUCCESS, AdminService.initializeSystem(ADMIN1_NAME, ADMIN1_PASS)._result);
        logoutSystem();
        assertEquals(Result.SUCCESS, AdminService.loginSystem(ADMIN1_SID, ADMIN1_NAME, ADMIN1_PASS)._result);
    }

    @Test
    public void initializeSystem_initialSequence_fail() {
        UserFacade.resetSystem();

        assertEquals(Result.UNINITIALIZED_SYSTEM, AdminService.loginSystem(ADMIN1_SID, ADMIN1_NAME, ADMIN1_PASS)._result);
        assertEquals(Result.SUCCESS, AdminService.initializeSystem(ADMIN1_NAME, ADMIN1_PASS)._result);

    }

    @Test
    public void logginSystem_initialSequence_fail() {

        logoutSystem();
        assertEquals(Result.WRONG_USER_NAME_OR_PASS, AdminService.loginSystem(ADMIN1_SID, ADMIN1_NAME, "wrongPass")._result);
        assertEquals(Result.WRONG_USER_NAME_OR_PASS, AdminService.loginSystem(ADMIN1_SID, "wrongName", ADMIN1_PASS)._result);
        assertEquals(Result.WRONG_USER_NAME_OR_PASS, AdminService.loginSystem(ADMIN1_SID, "wrongName", "wrongPass")._result);
    }

    @Test
    public void createForum_createFunction_pass() {
        loginSystem();

        assertEquals(Result.SUCCESS, AdminService.createForum(ADMIN1_SID, "form1", MANAGER1_NAME, MANAGER1_PASS)._result);
        assertEquals(Result.SUCCESS, AdminService.createForum(ADMIN1_SID, "form2", MANAGER2_NAME, MANAGER2_PASS)._result);
    }

    @Test
    public void createForum_createFunction_fail() {
        // create forum while not logedin
        logoutSystem();
        assertEquals(Result.NOT_LOGGEDIN_SYSTEM, AdminService.createForum(ADMIN1_SID, "form1", MANAGER1_NAME, MANAGER1_PASS)._result);
        loginSystem();


            assertEquals(Result.SUCCESS, AdminService.createForum(ADMIN1_SID, "form1", MANAGER1_NAME, MANAGER1_PASS)._result);
            assertEquals(Result.FORUM_EXISTS, AdminService.createForum(ADMIN1_SID, "form1", MANAGER2_NAME, MANAGER2_PASS)._result);
        // the error not affect the system (still can create forums)

            assertEquals(Result.SUCCESS, AdminService.createForum(ADMIN1_SID, "newForumName", MANAGER1_NAME, MANAGER1_PASS)._result);
    }

    @Test
    public void removeForum_validForumRemoval_successForumRemoval() {
        loginSystem();
        assertEquals(Result.SUCCESS, AdminService.createForum(ADMIN1_SID, "form1", MANAGER1_NAME, MANAGER1_PASS)._result);
        assertEquals(Result.SUCCESS, AdminService.createForum(ADMIN1_SID, "form2", MANAGER2_NAME, MANAGER2_PASS)._result);
        assertEquals(Result.SUCCESS, AdminService.removeForum(ADMIN1_SID, "form1")._result);
        assertEquals(Result.SUCCESS, AdminService.removeForum(ADMIN1_SID, "form2")._result);
    }

    @Test
    public void removeForum_removalOfUnexistedForum_failedForumRemoval() {
        loginSystem();
        assertEquals(Result.FORUM_NOT_EXISTS, AdminService.removeForum(ADMIN1_SID, "form1")._result);
        assertEquals(Result.SUCCESS, AdminService.createForum(ADMIN1_SID, "form2", MANAGER2_NAME, MANAGER2_PASS)._result);
        assertEquals(Result.SUCCESS, AdminService.removeForum(ADMIN1_SID, "form2")._result);
    }


}
