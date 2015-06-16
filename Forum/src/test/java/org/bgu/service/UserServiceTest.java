package org.bgu.service;

import org.bgu.domain.facades.ForumFacade;
import org.bgu.domain.facades.UserFacade;
import org.bgu.domain.model.*;
import org.bgu.service.Exceptions.Result;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;


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
    public static final String SUB_FORUM1_NAME = "ilansMother";

    public static UserService userService;
    public static Forum forum;

    @BeforeClass
    public static void initialSystem() {
        ForumFacade.createForum(FORUM1_NAME, ADMIN, ADMIN_PASS);
        forum = ForumFacade.getForum(FORUM1_NAME);
        userService = new UserService(FORUM1_NAME);
        assertEquals(Result.SUCCESS, UserFacade.addMember(FORUM1_NAME, MEMBER1, MEMBER1_PASS));
        assertEquals(Result.SUCCESS, UserFacade.addMember(FORUM1_NAME, MEMBER2, MEMBER2_PASS));
    }

    @Before
    public void logoutSystem() {
        userService.logOut();
    }

    @Test
    public void guestUserEntry_constructUserService_logginAsGuest() {
        userService = new UserService(FORUM1_NAME);
        assertTrue(userService.getUser().getClass() == Guest.class);
    }

    @Test
    public void registerNewMember_memberRegistration_newMemberRegistred() {
        // verify we are loggin now as Guest (pre condition)
        assertTrue(userService.getUser().getClass() == Guest.class);
        assertEquals(Result.SUCCESS, userService.registerMember("newMember1", "pass1")._result);
        //TODO - check that users really was created
        logoutSystem();
        assertEquals(Result.SUCCESS, userService.registerMember("newMember2", "pass2")._result);
        //TODO - check that users really was created
    }

    @Test
    public void registerNewMember_memberRegistration_fail() {
        // register while member is loggedin
        assertEquals(Result.SUCCESS, userService.logIn(MEMBER1, MEMBER1_PASS)._result);
        assertEquals(Result.ALREADY_LOGDIN, userService.registerMember("newMember1", "pass1")._result);
        //TODO - check that users wasn't created

        // register member with already used userName
        logoutSystem();
        assertEquals(Result.DUPLICATED_USERNAME, userService.registerMember(MEMBER1, MEMBER1_PASS)._result);
        assertEquals(Result.DUPLICATED_USERNAME, userService.registerMember(MEMBER1, "newPass")._result);
        //TODO - check that users wasn't created
    }

    @Test
    public void loginMember_login_userIsLoggedIn() {
        assertEquals(Result.SUCCESS, userService.logIn(MEMBER1, MEMBER1_PASS)._result);
        //TODO - verify member is loggedin
        logoutSystem();
        assertEquals(Result.SUCCESS, userService.logIn(MEMBER2, MEMBER2_PASS)._result);
        //TODO - verify member is loggedin

    }

    @Test
    public void addAndRemoveFriend_login_usersAreFriends() {
        assertEquals(Result.SUCCESS, userService.logIn(MEMBER1, MEMBER1_PASS)._result);
        assertEquals(Result.SUCCESS, userService.addFriend(MEMBER2)._result);
        // Verify member1 is friend of member2
        assertTrue("users aren't friends", userService.getUser().getMember().isFriendOf(UserFacade.getUser(FORUM1_NAME, MEMBER2).getMember()));
        // Verify member1 is friend of member2
        assertTrue("users aren't friends", UserFacade.getUser(FORUM1_NAME, MEMBER2).getMember().isFriendOf(userService.getUser().getMember()));
        assertEquals(Result.SUCCESS, userService.removeFriend(MEMBER2)._result);
        assertFalse("users are friends when they shouldn't", userService.getUser().getMember().isFriendOf(UserFacade.getUser(FORUM1_NAME, MEMBER2).getMember()));
        // Verify member1 is friend of member2
        assertFalse("users are friends when they shouldn't", UserFacade.getUser(FORUM1_NAME, MEMBER2).getMember().isFriendOf(userService.getUser().getMember()));

    }

    @Test
    public void addAndRemoveFriend_alreadyFriends_fail() {
        assertEquals(Result.SUCCESS, userService.logIn(MEMBER1, MEMBER1_PASS)._result);
        assertEquals(Result.SUCCESS, userService.addFriend(MEMBER2)._result);
        assertEquals(Result.SUCCESS, userService.logOut()._result);
        assertEquals(Result.SUCCESS, userService.logIn(MEMBER2, MEMBER2_PASS)._result);
        assertEquals(Result.ALREADY_FRIENDS, userService.addFriend(MEMBER1)._result);
        assertEquals(Result.SUCCESS, userService.logOut()._result);
        assertEquals(Result.SUCCESS, userService.logIn(MEMBER1, MEMBER1_PASS)._result);
        assertEquals(Result.ALREADY_FRIENDS, userService.addFriend(MEMBER2)._result);
        assertEquals(Result.SUCCESS, userService.removeFriend(MEMBER2)._result);
        assertFalse("users are friends when they shouldn't", userService.getUser().getMember().isFriendOf(UserFacade.getUser(FORUM1_NAME, MEMBER2).getMember()));
        // Verify member1 is friend of member2
        assertFalse("users are friends when they shouldn't", UserFacade.getUser(FORUM1_NAME, MEMBER2).getMember().isFriendOf(userService.getUser().getMember()));
    }

    @Test
    public void removeFriends_notFriends_fail() {
        assertEquals(Result.SUCCESS, userService.logIn(MEMBER1, MEMBER1_PASS)._result);
        assertEquals(Result.NOT_FRIENDS, userService.removeFriend(MEMBER2)._result);
        assertFalse("users are friends when they shouldn't", userService.getUser().getMember().isFriendOf(UserFacade.getUser(FORUM1_NAME, MEMBER2).getMember()));
        // Verify member1 is friend of member2
        assertFalse("users are friends when they shouldn't", UserFacade.getUser(FORUM1_NAME, MEMBER2).getMember().isFriendOf(userService.getUser().getMember()));
    }

    @Test
    public void addAndRemoveModerate_login_userIsModerate() {
        assertEquals(Result.SUCCESS, userService.logIn(ADMIN, ADMIN_PASS)._result);
        //Member admin = UserFacade.getUser(ADMIN,ADMIN_PASS).getMember();
        Collection<String> mediators = new ArrayList<>();
        mediators.add(ADMIN);
        ForumFacade.createSubForum(forum, SUB_FORUM1_NAME, mediators);
        // verify member1 isn't a madiator
        assertFalse("user is a moderator when he shouldn't", ForumFacade.getForum(FORUM1_NAME).getSubForum(SUB_FORUM1_NAME).isModerator(UserFacade.getUser(FORUM1_NAME, MEMBER1).getMember()));
        assertEquals(Result.SUCCESS, userService.addModerator(SUB_FORUM1_NAME, MEMBER1)._result);
        // verify member1 is a madiator
        assertTrue("user is not moderator", ForumFacade.getForum(FORUM1_NAME).getSubForum(SUB_FORUM1_NAME).isModerator(UserFacade.getUser(FORUM1_NAME, MEMBER1).getMember()));
        // verify member2 isn't a madiator
        assertFalse("user is a moderator when he shouldn't", ForumFacade.getForum(FORUM1_NAME).getSubForum(SUB_FORUM1_NAME).isModerator(UserFacade.getUser(FORUM1_NAME, MEMBER2).getMember()));
        assertEquals(Result.SUCCESS, userService.removeModerator(SUB_FORUM1_NAME, MEMBER1)._result);
        // verify member1 isn't a modiator
        assertFalse("user is a moderator when he shouldn't", ForumFacade.getForum(FORUM1_NAME).getSubForum(SUB_FORUM1_NAME).isModerator(UserFacade.getUser(FORUM1_NAME, MEMBER1).getMember()));
    }

    @Test
    public void addAndRemoveModerate_alreadyModerate_fail() {
        assertEquals(Result.SUCCESS, userService.logIn(ADMIN, ADMIN_PASS)._result);
        //Member admin = UserFacade.getUser(ADMIN,ADMIN_PASS).getMember();
        Collection<String> mediators = new ArrayList<>();
        mediators.add(ADMIN);
        ForumFacade.createSubForum(forum, SUB_FORUM1_NAME, mediators);
        // verify member1 isn't a madiator
        assertFalse("user is a moderator when he shouldn't", ForumFacade.getForum(FORUM1_NAME).getSubForum(SUB_FORUM1_NAME).isModerator(UserFacade.getUser(FORUM1_NAME, MEMBER1).getMember()));
        assertEquals(Result.SUCCESS, userService.addModerator(SUB_FORUM1_NAME, MEMBER1)._result);
        assertEquals(Result.ALREADY_MODERATE, userService.addModerator(SUB_FORUM1_NAME, MEMBER1)._result);
        // verify member1 is a madiator
        assertTrue("user is not moderator", ForumFacade.getForum(FORUM1_NAME).getSubForum(SUB_FORUM1_NAME).isModerator(UserFacade.getUser(FORUM1_NAME, MEMBER1).getMember()));
        // verify member2 isn't a madiator
        assertFalse("user is a moderator when he shouldn't", ForumFacade.getForum(FORUM1_NAME).getSubForum(SUB_FORUM1_NAME).isModerator(UserFacade.getUser(FORUM1_NAME, MEMBER2).getMember()));
        assertEquals(Result.SUCCESS, userService.removeModerator(SUB_FORUM1_NAME, MEMBER1)._result);
        // verify member1 isn't a modiator
        assertFalse("user is a moderator when he shouldn't", ForumFacade.getForum(FORUM1_NAME).getSubForum(SUB_FORUM1_NAME).isModerator(UserFacade.getUser(FORUM1_NAME, MEMBER1).getMember()));
    }

    @Test
    public void removeModerator_notAModerator_fail() {
        assertEquals(Result.SUCCESS, userService.logIn(ADMIN, ADMIN_PASS)._result);
        //Member admin = UserFacade.getUser(ADMIN,ADMIN_PASS).getMember();
        Collection<String> mediators = new ArrayList<>();
        mediators.add(ADMIN);
        ForumFacade.createSubForum(forum, SUB_FORUM1_NAME, mediators);
        assertEquals(Result.NOT_A_MODERATE, userService.removeModerator(SUB_FORUM1_NAME, MEMBER1)._result);
        assertFalse("user is a moderator when he shouldn't", ForumFacade.getForum(FORUM1_NAME).getSubForum(SUB_FORUM1_NAME).isModerator(UserFacade.getUser(FORUM1_NAME, MEMBER1).getMember()));
    }

    @Test
    public void loginMember_wrongLogin_fail() {
        // wrong member identifiers
        assertEquals(Result.WRONG_USER_PASS, userService.logIn("notMember", "somePass")._result);
        assertEquals(Result.WRONG_USER_PASS, userService.logIn("notMember", MEMBER1_PASS)._result);
        assertEquals(Result.WRONG_USER_PASS, userService.logIn("Guest", "somePass")._result);
        assertEquals(Result.WRONG_USER_PASS, userService.logIn(MEMBER1, "notPassOfMember1")._result);
        //verify that no member is logged in
        assertFalse(userService.isLogedin());

        // user is already loggedin
        assertEquals(Result.SUCCESS, userService.logIn(MEMBER1, MEMBER1_PASS)._result);
        assertEquals(Result.ALREADY_LOGDIN, userService.logIn(MEMBER1, MEMBER1_PASS)._result);
        assertEquals(Result.ALREADY_LOGDIN, userService.logIn(MEMBER2, MEMBER2_PASS)._result);


        // verify member1 still loggedin
        assertEquals(true, userService.isLogedin());
        assertTrue(userService.getUser().getUserName().equals(MEMBER1));
    }


}
