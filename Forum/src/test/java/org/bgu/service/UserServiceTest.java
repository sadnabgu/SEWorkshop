package org.bgu.service;

import junit.framework.Assert;
import org.bgu.domain.facades.ForumFacade;
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
    public static final String ADMIN = "admin";
    public static final String ADMIN_PASS = "pass";

    public static final int FORUM1_ID = 1;

    public static UserService userService;


    @BeforeClass
    public static void initialSystem(){
        AdminServiceTest.initialSystem();
        ForumFacade.createForum(FORUM1_ID, "sex", ADMIN, ADMIN_PASS);
        userService = new UserService(FORUM1_ID);
        //AdminServiceTest.createForum("forum");
        Assert.assertTrue("can't create member1",
                userService.addMember(MEMBER1, MEMBER1_PASS));
        Assert.assertTrue("can't create member2",
                userService.addMember(MEMBER2, MEMBER2_PASS));
    }

    @Before
    public void logoutSystem(){
        userService.logOut();
    }

    @Test
    public void guestUserEntry_constructUserService_logginAsGuest(){
        userService = new UserService(FORUM1_ID);
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
        //TODO - check that users wasn't created
    }

    @Test
    public void loginMember_login_userIsLoggedIn(){
        Assert.assertTrue(userService.logIn(MEMBER1, MEMBER1_PASS));
        //TODO - verify member is loggedin
        logoutSystem();
        Assert.assertTrue(userService.logIn(MEMBER2, MEMBER2_PASS));
        //TODO - verify member is loggedin

    }

    @Test
    public void loginMember_wrongLogin_fail(){
        // wrong member identifiers
        Assert.assertFalse(userService.logIn("notMember", "somePass"));
        Assert.assertFalse(userService.logIn("notMember", MEMBER1_PASS));
        Assert.assertFalse(userService.logIn("Guest", "somePass"));
        Assert.assertFalse(userService.logIn(MEMBER1, "notPassOfMember1"));
        //verify that no member is logged in
        Assert.assertFalse(userService.isLoggedin());

        // user is already loggedin
        Assert.assertTrue(userService.logIn(MEMBER1, MEMBER1_PASS));
        Assert.assertFalse(userService.logIn(MEMBER1, MEMBER1_PASS));
        Assert.assertFalse(userService.logIn(MEMBER2, MEMBER2_PASS));
        // verify member1 still loggedin
        Assert.assertTrue(userService.isLoggedin());
        Assert.assertTrue(userService.getUser().getUserName().equals(MEMBER1));
    }



}
