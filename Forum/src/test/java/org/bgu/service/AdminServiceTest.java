package org.bgu.service;

import junit.framework.Assert;
import org.bgu.domain.model.Forum;
import org.bgu.service.Exceptions.ForumException;
import org.bgu.service.Exceptions.Result;
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
    public static void initialSystem() {
        adminService = new AdminService();
        adminService.resetSystem();
        try {
            Assert.assertTrue(adminService.initializeSystem(ADMIN1_NAME, ADMIN1_PASS));
        } catch (ForumException e) {
            e.getMessage();
        }
    }

    @Before
    public void logoutSystem() {
        initialSystem();
        adminService = new AdminService();
    }

    @Test
    public void loginSystem() {
        try {
            Assert.assertTrue(adminService.loginSystem(ADMIN1_NAME, ADMIN1_PASS));
        } catch (ForumException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void initializeSystem_initialSequence_pass() {
        adminService.resetSystem();
        try {
            Assert.assertTrue(adminService.initializeSystem(ADMIN1_NAME, ADMIN1_PASS));
        } catch (ForumException e) {
            e.printStackTrace();
        }
        logoutSystem();
        try {
            Assert.assertTrue(adminService.loginSystem(ADMIN1_NAME, ADMIN1_PASS));
        } catch (ForumException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void initializeSystem_initialSequence_fail() {
        adminService.resetSystem();

        try {
            adminService.loginSystem(ADMIN1_NAME, ADMIN1_PASS);
        } catch (ForumException e) {
            Assert.assertEquals(Result.UNINITIALIZED_SYSTEM.toString(), e.getMessage());
        }
        try {
            Assert.assertTrue(adminService.initializeSystem(ADMIN1_NAME, ADMIN1_PASS));
        } catch (ForumException e) {
            e.getMessage();
        }

    }

    @Test
    public void logginSystem_initialSequence_fail() {

        logoutSystem();
        try {
            adminService.loginSystem(ADMIN1_NAME, "wrongPass");
        } catch (ForumException e) {
            Assert.assertEquals(Result.FAIL.toString(), e.getMessage());
        }
        try {
            adminService.loginSystem("wrongName", ADMIN1_PASS);
        } catch (ForumException e) {
            Assert.assertEquals(Result.FAIL.toString(), e.getMessage());
        }
        try {
            adminService.loginSystem("wrongName", "wrongPass");
        } catch (ForumException e) {
            Assert.assertEquals(Result.FAIL.toString(), e.getMessage());
        }
    }

    @Test
    public void createForum_createFunction_pass(){
        loginSystem();

        try {
            Assert.assertTrue(adminService.createForum("form1", MANAGER1_NAME, MANAGER1_PASS));
        }catch(ForumException e){
            e.printStackTrace();
        }
        try {
            Assert.assertTrue(adminService.createForum("form2", MANAGER2_NAME, MANAGER2_PASS));
        }catch(ForumException e){
            e.printStackTrace();
        }

        //TODO - test forum creation throw the ForumService
    }

    @Test
    public void createForum_createFunction_fail(){
        // create forum while not logedin
        logoutSystem();
        try{
            adminService.createForum("form1", MANAGER1_NAME, MANAGER1_PASS);
        }catch(ForumException e){
            Assert.assertEquals(Result.NOT_LOGGEDIN_SYSTEM.toString(),e.getMessage());
        }

        loginSystem();

        try {
            Assert.assertTrue(adminService.createForum("form1", MANAGER1_NAME, MANAGER1_PASS));
        }catch(ForumException e){
            e.printStackTrace();
        }
        try{
            Assert.assertEquals(Result.FORUM_EXISTS, adminService.createForum("form1", MANAGER2_NAME, MANAGER2_PASS));
        }catch(ForumException e){
            Assert.assertEquals(Result.FORUM_EXISTS.toString(), e.getMessage());
        }

        // the error not affect the system (still can create forums)

        try{
            adminService.createForum("newForumName", MANAGER1_NAME, MANAGER1_PASS);
        }catch(ForumException e){
            Assert.assertEquals(Result.SUCCESS.toString(), e.getMessage());
        }

        //TODO - test that forum wasn't changed through the ForumService
    }


}
