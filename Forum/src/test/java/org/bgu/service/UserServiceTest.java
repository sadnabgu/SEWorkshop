package org.bgu.service;

import junit.framework.Assert;
import org.bgu.domain.model.Guest;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by hodai on 4/21/15.
 */
public class UserServiceTest {
    public static final String MEMBER1 = "member1";
    public static final String MEMBER1_PASS = "member1Pass";
    public static final String MEMBER2 = "member2";
    public static final String MEMBER2_PASS = "member2Pass";

    public static UserService userService;


    @BeforeClass
    public static void initialSystem(){
        AdminServiceTest.initialSystem();
        userService = new UserService();
        //AdminServiceTest.createForum("forum");
        Assert.assertTrue("can't create member1",
                UserService.addMember(MEMBER1, MEMBER1_PASS));
        Assert.assertTrue("can't create member2",
                UserService.addMember(MEMBER2, MEMBER2_PASS));
    }

    @Before
    public void logoutSystem(){
        userService.logOut();
    }

    @Test
    public void guestUserEntry_constructUserService_logginAsGuest(){
        userService = new UserService();
        Assert.assertTrue(userService.getUser().getClass() == Guest.class);
    }

    @Test
    public void registerNewMember_memberRegistration_newMemberRegistred(){
        // verify we are loggin now as Guest (pre condition)
        Assert.assertTrue(userService.getUser().getClass() == Guest.class);

        Assert.assertTrue("fail register first member",userService.registerMember("newMember1", "pass1"));
        //TODO - check that users really was created
        logoutSystem();
        Assert.assertTrue("fail register first member",userService.registerMember("newMember2", "pass2"));
        //TODO - check that users really was created
    }

    @Test
    public void registerNewMember_memberRegistration_fail(){
        // register while member is loggedin
        Assert.assertTrue(userService.logIn(MEMBER1, MEMBER1_PASS));
        Assert.assertFalse(userService.registerMember("newMember1", "pass1"));
        //TODO - check that users wasn't created

        // register member with already used userName
        logoutSystem();
        Assert.assertFalse(userService.registerMember(MEMBER1, MEMBER1_PASS));
        Assert.assertFalse(userService.registerMember(MEMBER1, "newPass"));
        //TODO - check that users really was created
    }

}