package org.bgu.service;
import org.bgu.domain.facades.UserFacade;
import org.bgu.service.ServiceObjects.Result;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by hodai on 4/21/15.
 */
public class AdminServiceTest extends abstractTest{


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
    public void initializeSystem_initialSequence_pass() {
        UserFacade.resetSystem();
        assertEquals(Result.SUCCESS, AdminService.initializeSystem(ADMIN1_NAME, ADMIN1_PASS)._result);
        logoutSystem();
        loginSupperAdmin();
    }

    @Test
    public void initializeSystem_initialSequence_fail() {
        UserFacade.resetSystem();
        assertEquals(Result.UNINITIALIZED_SYSTEM, AdminService.loginSystem(ADMIN1_NAME, ADMIN1_PASS)._result);
        assertEquals(Result.SUCCESS, AdminService.initializeSystem(ADMIN1_NAME, ADMIN1_PASS)._result);

    }

    @Test
    public void logginSystem_initialSequence_fail() {

        assertEquals(Result.WRONG_USER_NAME_OR_PASS, AdminService.loginSystem( ADMIN1_NAME, "wrongPass")._result);
        assertEquals(Result.WRONG_USER_NAME_OR_PASS, AdminService.loginSystem( "wrongName", ADMIN1_PASS)._result);
        assertEquals(Result.WRONG_USER_NAME_OR_PASS, AdminService.loginSystem( "wrongName", "wrongPass")._result);
    }

    @Test
    public void createForum_createFunction_pass() {
        loginSupperAdmin();

        assertEquals(Result.SUCCESS, AdminService.createForum(superAdminSid, "form1", MANAGER1_NAME, MANAGER1_PASS)._result);
        assertEquals(Result.SUCCESS, AdminService.createForum(superAdminSid, "form2", MANAGER2_NAME, MANAGER2_PASS)._result);
        logoutSystem();
    }

    @Test
    public void createForum_createFunction_fail() {
        // create forum while not logedin
        assertEquals(Result.NOT_LOGGEDIN_SYSTEM, AdminService.createForum(superAdminSid, "form1", MANAGER1_NAME, MANAGER1_PASS)._result);
        loginSupperAdmin();

        assertEquals(Result.SUCCESS, AdminService.createForum(superAdminSid, "form1", MANAGER1_NAME, MANAGER1_PASS)._result);
        assertEquals(Result.FORUM_EXISTS, AdminService.createForum(superAdminSid, "form1", MANAGER2_NAME, MANAGER2_PASS)._result);
        // the error not affect the system (still can create forums)

        assertEquals(Result.SUCCESS, AdminService.createForum(superAdminSid, "newForumName", MANAGER1_NAME, MANAGER1_PASS)._result);
        logoutSystem();
    }

    @Test
    public void removeForum_validForumRemoval_successForumRemoval() {
        loginSupperAdmin();
        assertEquals(Result.SUCCESS, AdminService.createForum(superAdminSid, "form1", MANAGER1_NAME, MANAGER1_PASS)._result);
        assertEquals(Result.SUCCESS, AdminService.createForum(superAdminSid, "form2", MANAGER2_NAME, MANAGER2_PASS)._result);
        assertEquals(Result.SUCCESS, AdminService.removeForum(superAdminSid, "form1")._result);
        assertEquals(Result.SUCCESS, AdminService.removeForum(superAdminSid, "form2")._result);
        logoutSystem();
    }

    @Test
    public void removeForum_removalOfUnexistedForum_failedForumRemoval() {
        loginSupperAdmin();
        assertEquals(Result.FORUM_NOT_EXISTS, AdminService.removeForum(superAdminSid, "form1")._result);
        assertEquals(Result.SUCCESS, AdminService.createForum(superAdminSid, "form2", MANAGER2_NAME, MANAGER2_PASS)._result);
        assertEquals(Result.SUCCESS, AdminService.removeForum(superAdminSid, "form2")._result);
        logoutSystem();
    }


}
