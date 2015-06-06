package org.bgu.service;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
    public static void initialSystem(){
        adminService = new AdminService();
        adminService.resetSystem();
        //adminService.initializeSystem(ADMIN1_NAME, ADMIN1_PASS);
        Assert.assertEquals(Result.SUCCESS, adminService.initializeSystem(ADMIN1_NAME, ADMIN1_PASS));
    }

    @Before
    public void logoutSystem(){
        initialSystem();
        adminService = new AdminService();
    }

    /** utils */
    public void loginSystem(){
        Assert.assertEquals(Result.SUCCESS, adminService.loginSystem(ADMIN1_NAME, ADMIN1_PASS));
    }

    /** id 1.1.1 */
    @Test
    public void initializeSystem_initialSequence_pass(){
        adminService.resetSystem();
        Assert.assertEquals(Result.SUCCESS, adminService.initializeSystem(ADMIN1_NAME, ADMIN1_PASS));
        logoutSystem();
        Assert.assertEquals(Result.SUCCESS, adminService.loginSystem(ADMIN1_NAME, ADMIN1_PASS));
    }

    /** id 1.1.2 */
    @Test
    public void initializeSystem_initialSequence_fail(){
        adminService.resetSystem();

        Assert.assertEquals(Result.UNINITIALIZED_SYSTEM, adminService.loginSystem(ADMIN1_NAME, ADMIN1_PASS));
        Assert.assertEquals(Result.SUCCESS, adminService.initializeSystem(ADMIN1_NAME, ADMIN1_PASS));
    }

    @Test
    public void logginSystem_initialSequence_fail(){

        logoutSystem();
        Assert.assertEquals(Result.FAIL, adminService.loginSystem(ADMIN1_NAME, "wrongPass"));
        Assert.assertEquals(Result.FAIL, adminService.loginSystem("wrongName", ADMIN1_PASS));
        Assert.assertEquals(Result.FAIL, adminService.loginSystem("wrongName", "wrongPass"));
    }


    @Test
    public void createForum_createFunction_pass(){
        loginSystem();

        Assert.assertEquals(Result.SUCCESS, adminService.createForum("form1", MANAGER1_NAME, MANAGER1_PASS));
        Assert.assertEquals(Result.SUCCESS, adminService.createForum("form2", MANAGER2_NAME, MANAGER2_PASS));

        //TODO - test forum creation throw the ForumService
    }

    @Test
    public void createForum_createFunction_fail(){
        // create forum while not logedin
        logoutSystem();
        Assert.assertEquals(Result.NOT_LOGGEDIN_SYSTEM, adminService.createForum("form1", MANAGER1_NAME, MANAGER1_PASS));

        loginSystem();

        Assert.assertEquals(Result.SUCCESS,      adminService.createForum("form1", MANAGER1_NAME, MANAGER1_PASS));
        Assert.assertEquals(Result.FORUM_EXISTS, adminService.createForum("form1", MANAGER2_NAME, MANAGER2_PASS));

        // the error not affect the system (still can create forums)
        Assert.assertEquals(Result.SUCCESS,      adminService.createForum("newForumName", MANAGER1_NAME, MANAGER1_PASS));

        //TODO - test that forum wasn't changed through the ForumService
    }


}
