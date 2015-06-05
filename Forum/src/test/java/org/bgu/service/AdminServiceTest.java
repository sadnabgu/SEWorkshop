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
        Assert.assertFalse(adminService == null);
    }

    /** utils */
    public void loginSystem(){
        Assert.assertEquals(Result.SUCCESS, adminService.loginSystem(ADMIN1_NAME, ADMIN1_PASS));
    }

    /** id 1.1.1 */
    @Test
    public void initializeSystem_innitialSequence_pass(){
        adminService.resetSystem();
        Assert.assertEquals(Result.SUCCESS, adminService.initializeSystem(ADMIN1_NAME, ADMIN1_PASS));
        logoutSystem();
        Assert.assertEquals(Result.SUCCESS, adminService.loginSystem(ADMIN1_NAME, ADMIN1_PASS));
    }

    /** id 1.1.2 */
    @Test
    public void initializeSystem_innitialSequence_fail(){
        adminService.resetSystem();

        Assert.assertEquals(Result.UNINITIALIZED_SYSTEM, adminService.loginSystem(ADMIN1_NAME, ADMIN1_PASS));
        Assert.assertEquals(Result.SUCCESS, adminService.initializeSystem(ADMIN1_NAME, ADMIN1_PASS));
    }

    @Test
    public void logginSystem_innitialSequence_fail(){

        logoutSystem();
        Assert.assertEquals(Result.FAIL, adminService.loginSystem(ADMIN1_NAME, "wrongPass"));
        Assert.assertEquals(Result.FAIL, adminService.loginSystem("wrongName", ADMIN1_PASS));
        Assert.assertEquals(Result.FAIL, adminService.loginSystem("wrongName", "wrongPass"));
    }


    @Test
    public void createForum_createFunction_pass(){
        loginSystem();

        Assert.assertEquals(Result.SUCCESS, adminService.createForum("form1"));
        Assert.assertEquals(Result.SUCCESS, adminService.createForum("form2"));

        //TODO - test forum creation throw the ForumService
    }

    @Test
    public void createForum_createFunction_fail(){
        // create forum while not logedin
        logoutSystem();
        Assert.assertEquals(Result.NOT_LOGGEDIN_SYSTEM, adminService.createForum("form1"));

        loginSystem();

        Assert.assertEquals(Result.SUCCESS, adminService.createForum("form1"));
        Assert.assertEquals(Result.FORUM_EXISTS, adminService.createForum("form1"));
        Assert.assertEquals(Result.SUCCESS, adminService.createForum("newForumName"));

        //TODO - test forum wasn't changed through the ForumService
    }

    @Test
    public void testError(){
        Result err1 = Result.FAIL;
        System.out.println(err1.toString());
    }


}
