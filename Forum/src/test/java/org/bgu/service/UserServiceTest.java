package org.bgu.service;

import junit.framework.Assert;
import org.bgu.domain.facades.ForumFacade;
import org.bgu.domain.facades.UserFacade;
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

    public static final String FORUM1_NAME = "sex";

    public static UserService userService;


    @BeforeClass
    public static void initialSystem(){
        ForumFacade.createForum(FORUM1_NAME, ADMIN, ADMIN_PASS);
        userService = new UserService(FORUM1_NAME);
        Assert.assertEquals(Result.SUCCESS, UserFacade.addMember(FORUM1_NAME, MEMBER1, MEMBER1_PASS));
        Assert.assertEquals(Result.SUCCESS, UserFacade.addMember(FORUM1_NAME, MEMBER2, MEMBER2_PASS));
    }

    @Before
    public void logoutSystem(){
        userService.logOut();
    }

    @Test
    public void guestUserEntry_constructUserService_logginAsGuest(){
        userService = new UserService(FORUM1_NAME);
        Assert.assertTrue(userService.getUser().getClass() == Guest.class);
    }

    @Test
    public void registerNewMember_memberRegistration_newMemberRegistred(){
        // verify we are loggin now as Guest (pre condition)
        Assert.assertTrue(userService.getUser().getClass() == Guest.class);

        Assert.assertEquals(Result.SUCCESS, userService.registerMember("newMember1", "pass1"));
        //TODO - check that users really was created
        logoutSystem();
        Assert.assertEquals(Result.SUCCESS, userService.registerMember("newMember2", "pass2"));
        //TODO - check that users really was created
    }

    @Test
    public void registerNewMember_memberRegistration_fail(){
        // register while member is loggedin
        Assert.assertEquals(Result.SUCCESS, userService.logIn(MEMBER1, MEMBER1_PASS));
        Assert.assertEquals(Result.ALREADY_LOGDIN, userService.registerMember("newMember1", "pass1"));
        //TODO - check that users wasn't created

        // register member with already used userName
        logoutSystem();
        Assert.assertEquals(Result.DUPLICATED_USERNAME, userService.registerMember(MEMBER1, MEMBER1_PASS));
        Assert.assertEquals(Result.DUPLICATED_USERNAME, userService.registerMember(MEMBER1, "newPass"));
        //TODO - check that users wasn't created
    }

    @Test
    public void loginMember_login_userIsLoggedIn(){
        Assert.assertEquals(Result.SUCCESS, userService.logIn(MEMBER1, MEMBER1_PASS));
        //TODO - verify member is loggedin
        logoutSystem();
        Assert.assertEquals(Result.SUCCESS, userService.logIn(MEMBER2, MEMBER2_PASS));
    //TODO - verify member is loggedin

}

    @Test
    public void addFriend_login_usersAreFriends(){
        Assert.assertEquals(Result.SUCCESS, userService.logIn(MEMBER1, MEMBER1_PASS));
        Assert.assertEquals(Result.SUCCESS, userService.addFriend(MEMBER2));
        // Verify member1 is friend of member2
        Assert.assertTrue("users aren't friends", userService.getUser().getMember().isFriendOf(UserFacade.getUser(FORUM1_NAME, MEMBER2).getMember()));
        // Verify member1 is friend of member2
        Assert.assertTrue("users aren't friends", UserFacade.getUser(FORUM1_NAME,MEMBER2).getMember().isFriendOf(userService.getUser().getMember()));
    }

    @Test
    public void addFriend_alreadyFriends_fail(){
        Assert.assertEquals(Result.SUCCESS, userService.logIn(MEMBER1, MEMBER1_PASS));
        Assert.assertEquals(Result.SUCCESS, userService.addFriend(MEMBER2));
        userService.logOut();
        Assert.assertEquals(Result.SUCCESS, userService.logIn(MEMBER2, MEMBER2_PASS));
        Assert.assertEquals(Result.ALREADY_FRIENDS, userService.addFriend(MEMBER1));
        userService.logOut();
        Assert.assertEquals(Result.SUCCESS, userService.logIn(MEMBER1, MEMBER1_PASS));
        Assert.assertEquals(Result.ALREADY_FRIENDS, userService.addFriend(MEMBER2));
    }

    @Test
    public void loginMember_wrongLogin_fail(){
        // wrong member identifiers
        Assert.assertEquals(Result.WRONG_USER_PASS, userService.logIn("notMember", "somePass"));
        Assert.assertEquals(Result.WRONG_USER_PASS, userService.logIn("notMember", MEMBER1_PASS));
        Assert.assertEquals(Result.WRONG_USER_PASS, userService.logIn("Guest", "somePass"));
        Assert.assertEquals(Result.WRONG_USER_PASS, userService.logIn(MEMBER1, "notPassOfMember1"));
        //verify that no member is logged in
        Assert.assertFalse(userService.isLogedin());

        // user is already loggedin
        Assert.assertEquals(Result.SUCCESS, userService.logIn(MEMBER1, MEMBER1_PASS));
        Assert.assertEquals(Result.ALREADY_LOGDIN, userService.logIn(MEMBER1, MEMBER1_PASS));
        Assert.assertEquals(Result.ALREADY_LOGDIN, userService.logIn(MEMBER2, MEMBER2_PASS));
        // verify member1 still loggedin
        Assert.assertEquals(true, userService.isLogedin());
        Assert.assertTrue(userService.getUser().getUserName().equals(MEMBER1));
    }



}
