package org.bgu.service;
import org.bgu.service.Exceptions.Result;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by hodai on 4/21/15.
 */
public class AdminServiceTest {
    public static final String ADMIN1_NAME = "admin1";
    public static final String ADMIN1_PASS = "pass1";
    public static final String MANAGER1_NAME = "manager1";
    public static final String MANAGER1_PASS = "pass1";
    public static final String MANAGER2_NAME = "manager2";
    public static final String MANAGER2_PASS = "pass2";

    public static AdminService adminService;

    @BeforeClass
    public static void initialSystem() {
        adminService = new AdminService();
        adminService.resetSystem();
        assertEquals(Result.SUCCESS, adminService.initializeSystem(ADMIN1_NAME, ADMIN1_PASS)._result);
    }

    @Before
    public void logoutSystem() {
        initialSystem();
        adminService = new AdminService();
    }

    @Test
    public void loginSystem() {
        assertEquals(Result.SUCCESS, adminService.loginSystem(ADMIN1_NAME, ADMIN1_PASS)._result);
    }

    @Test
    public void initializeSystem_initialSequence_pass() {
        adminService.resetSystem();
        assertEquals(Result.SUCCESS, adminService.initializeSystem(ADMIN1_NAME, ADMIN1_PASS)._result);
        logoutSystem();
        assertEquals(Result.SUCCESS, adminService.loginSystem(ADMIN1_NAME, ADMIN1_PASS)._result);
    }

    @Test
    public void initializeSystem_initialSequence_fail() {
        adminService.resetSystem();

        assertEquals(Result.UNINITIALIZED_SYSTEM, adminService.loginSystem(ADMIN1_NAME, ADMIN1_PASS)._result);
        assertEquals(Result.SUCCESS, adminService.initializeSystem(ADMIN1_NAME, ADMIN1_PASS)._result);

    }

    @Test
    public void logginSystem_initialSequence_fail() {

        logoutSystem();
        assertEquals(Result.FAIL, adminService.loginSystem(ADMIN1_NAME, "wrongPass")._result);
        assertEquals(Result.FAIL, adminService.loginSystem("wrongName", ADMIN1_PASS)._result);
        assertEquals(Result.FAIL, adminService.loginSystem("wrongName", "wrongPass")._result);
    }

    @Test
    public void createForum_createFunction_pass() {
        loginSystem();

        assertEquals(Result.SUCCESS, adminService.createForum("form1", MANAGER1_NAME, MANAGER1_PASS)._result);
        assertEquals(Result.SUCCESS, adminService.createForum("form2", MANAGER2_NAME, MANAGER2_PASS)._result);
        //TODO - test forum creation throw the ForumService
    }

    @Test
    public void createForum_createFunction_fail() {
        // create forum while not logedin
        logoutSystem();
        assertEquals(Result.NOT_LOGGEDIN_SYSTEM, adminService.createForum("form1", MANAGER1_NAME, MANAGER1_PASS)._result);
        loginSystem();


            assertEquals(Result.SUCCESS, adminService.createForum("form1", MANAGER1_NAME, MANAGER1_PASS)._result);
            assertEquals(Result.FORUM_EXISTS, adminService.createForum("form1", MANAGER2_NAME, MANAGER2_PASS)._result);
        // the error not affect the system (still can create forums)

            assertEquals(Result.SUCCESS, adminService.createForum("newForumName", MANAGER1_NAME, MANAGER1_PASS)._result);


        //TODO - test that forum wasn't changed through the ForumService
    }


}
