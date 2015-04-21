package org.bgu.service;

import junit.framework.Assert;
import org.bgu.service.AdminService;
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
        Assert.assertTrue(adminService.initializeSystem(ADMIN1_NAME, ADMIN1_PASS));
    }

    @Before
    public void logoutSystem(){
        adminService = new AdminService();
        Assert.assertFalse(adminService == null);
    }

    /** utils */
    public void loginSystem(){
        Assert.assertTrue(adminService.loginSystem(ADMIN1_NAME, ADMIN1_PASS));
    }

    @Test
    public void initializeSystem_innitialSequence_pass(){
        adminService.resetSystem();
        Assert.assertTrue(adminService.initializeSystem(ADMIN1_NAME, ADMIN1_PASS));
        logoutSystem();
        Assert.assertTrue(adminService.loginSystem(ADMIN1_NAME, ADMIN1_PASS));
    }

    @Test
    public void initializeSystem_innitialSequence_fail(){
        adminService.resetSystem();

        Assert.assertFalse(adminService.loginSystem(ADMIN1_NAME, ADMIN1_PASS));
        Assert.assertTrue(adminService.initializeSystem(ADMIN1_NAME, ADMIN1_PASS));
    }

    @Test
    public void logginSystem_innitialSequence_fail(){

        logoutSystem();
        Assert.assertFalse(adminService.loginSystem(ADMIN1_NAME, "wrongPass"));
        Assert.assertFalse(adminService.loginSystem("wrongName", ADMIN1_PASS));
        Assert.assertFalse(adminService.loginSystem("wrongName", "wrongPass"));
    }


    @Test
    public void createForum_createFunction_pass(){
        loginSystem();

        Assert.assertTrue(adminService.createForum(1, "form1"));
        Assert.assertTrue(adminService.createForum(2, "form2"));

        //TODO - test forum creation throw the ForumService
    }

    @Test
    public void createForumSameName_createFunction_pass(){
        loginSystem();

        Assert.assertTrue(adminService.createForum(3, "form"));
        Assert.assertTrue(adminService.createForum(4, "form"));

        //TODO - test forum creation throw the ForumService
    }

    @Test
    public void createForum_createFunction_fail(){
        // create forum while not logedin
        logoutSystem();
        Assert.assertFalse(adminService.createForum(5, "form1"));

        loginSystem();

        Assert.assertTrue(adminService.createForum(6, "form1"));
        Assert.assertFalse(adminService.createForum(6, "form1"));
        Assert.assertFalse(adminService.createForum(6, "newForumName"));

        //TODO - test forum wasn't changed throw the ForumService
    }


}
