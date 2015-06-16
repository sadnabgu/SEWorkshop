package org.bgu.service;

import org.bgu.domain.facades.ForumFacade;
import org.bgu.domain.facades.UserFacade;
import org.bgu.domain.model.Forum;
import org.bgu.domain.model.Guest;
import org.bgu.service.Exceptions.ForumException;
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
        assertTrue(userService.registerMember("newMember1", "pass1").compareResult(Result.SUCCESS));
        //TODO - check that users really was created
        logoutSystem();
        assertTrue(userService.registerMember("newMember2", "pass2").compareResult(Result.SUCCESS));
        //TODO - check that users really was created
    }

    @Test
    public void registerNewMember_memberRegistration_fail() {
        // register while member is loggedin
        assertTrue(userService.logIn(MEMBER1, MEMBER1_PASS).compareResult(Result.SUCCESS));
        assertTrue(userService.registerMember("newMember1", "pass1").compareResult(Result.ALREADY_LOGDIN));
        //TODO - check that users wasn't created

        // register member with already used userName
        logoutSystem();
        assertTrue(userService.registerMember(MEMBER1, MEMBER1_PASS).compareResult(Result.DUPLICATED_USERNAME));
        assertTrue(userService.registerMember(MEMBER1, "newPass").compareResult(Result.DUPLICATED_USERNAME));
        //TODO - check that users wasn't created
    }

    @Test
    public void loginMember_login_userIsLoggedIn() {
        assertTrue(userService.logIn(MEMBER1, MEMBER1_PASS).compareResult(Result.SUCCESS));
        //TODO - verify member is loggedin
        logoutSystem();
        assertTrue(userService.logIn(MEMBER2, MEMBER2_PASS).compareResult(Result.SUCCESS));
        //TODO - verify member is loggedin

    }

    @Test
    public void addAndRemoveFriend_login_usersAreFriends() {
        assertTrue(userService.logIn(MEMBER1, MEMBER1_PASS).compareResult(Result.SUCCESS));
        assertTrue(userService.addFriend(MEMBER2).compareResult(Result.SUCCESS));
        // Verify member1 is friend of member2
        assertTrue("users aren't friends", userService.getUser().getMember().isFriendOf(UserFacade.getUser(FORUM1_NAME, MEMBER2).getMember()));
        // Verify member1 is friend of member2
        assertTrue("users aren't friends", UserFacade.getUser(FORUM1_NAME, MEMBER2).getMember().isFriendOf(userService.getUser().getMember()));
        try {
            assertTrue(userService.removeFriend(MEMBER2));
        } catch (ForumException e) {
            e.printStackTrace();
        }
        assertFalse("users are friends when they shouldn't", userService.getUser().getMember().isFriendOf(UserFacade.getUser(FORUM1_NAME, MEMBER2).getMember()));
        // Verify member1 is friend of member2
        assertFalse("users are friends when they shouldn't", UserFacade.getUser(FORUM1_NAME, MEMBER2).getMember().isFriendOf(userService.getUser().getMember()));

    }

    @Test
    public void addAndRemoveFriend_alreadyFriends_fail() {
        assertTrue(userService.logIn(MEMBER1, MEMBER1_PASS).compareResult(Result.SUCCESS));
        assertTrue(userService.addFriend(MEMBER2).compareResult(Result.SUCCESS));
        userService.logOut().compareResult(Result.SUCCESS);
        assertTrue(userService.logIn(MEMBER2, MEMBER2_PASS).compareResult(Result.SUCCESS));
        assertTrue(userService.addFriend(MEMBER1).compareResult(Result.ALREADY_FRIENDS));
        assertTrue(userService.logOut().compareResult(Result.SUCCESS));
        assertTrue(userService.logIn(MEMBER1, MEMBER1_PASS).compareResult(Result.SUCCESS));
        assertTrue(userService.addFriend(MEMBER2).compareResult(Result.ALREADY_FRIENDS));
        try {
            assertTrue(userService.removeFriend(MEMBER2));
        } catch (ForumException e) {
            e.printStackTrace();
        }
        assertFalse("users are friends when they shouldn't", userService.getUser().getMember().isFriendOf(UserFacade.getUser(FORUM1_NAME, MEMBER2).getMember()));
        // Verify member1 is friend of member2
        assertFalse("users are friends when they shouldn't", UserFacade.getUser(FORUM1_NAME, MEMBER2).getMember().isFriendOf(userService.getUser().getMember()));
    }

    @Test
    public void removeFriends_notFriends_fail() {
        assertTrue(userService.logIn(MEMBER1, MEMBER1_PASS).compareResult(Result.SUCCESS));
        try {
            userService.removeFriend(MEMBER2);
        } catch (ForumException e) {
            assertEquals(Result.NOT_FRIENDS.toString(), e.getMessage());
        }
        assertFalse("users are friends when they shouldn't", userService.getUser().getMember().isFriendOf(UserFacade.getUser(FORUM1_NAME, MEMBER2).getMember()));
        // Verify member1 is friend of member2
        assertFalse("users are friends when they shouldn't", UserFacade.getUser(FORUM1_NAME, MEMBER2).getMember().isFriendOf(userService.getUser().getMember()));
    }

    @Test
    public void addAndRemoveModerate_login_userIsModerate() {
        assertTrue(userService.logIn(ADMIN, ADMIN_PASS).compareResult(Result.SUCCESS));
        //Member admin = UserFacade.getUser(ADMIN,ADMIN_PASS).getMember();
        Collection<String> mediators = new ArrayList<>();
        mediators.add(ADMIN);
        ForumFacade.createSubForum(forum, SUB_FORUM1_NAME, mediators);
        // verify member1 isn't a madiator
        assertFalse("user is a moderator when he shouldn't", ForumFacade.getForum(FORUM1_NAME).getSubForum(SUB_FORUM1_NAME).isModerator(UserFacade.getUser(FORUM1_NAME, MEMBER1).getMember()));
        try {
            assertTrue(userService.addModerator(SUB_FORUM1_NAME, MEMBER1));
        } catch (ForumException e) {
            e.printStackTrace();
        }
        // verify member1 is a madiator
        assertTrue("user is not moderator", ForumFacade.getForum(FORUM1_NAME).getSubForum(SUB_FORUM1_NAME).isModerator(UserFacade.getUser(FORUM1_NAME, MEMBER1).getMember()));
        // verify member2 isn't a madiator
        assertFalse("user is a moderator when he shouldn't", ForumFacade.getForum(FORUM1_NAME).getSubForum(SUB_FORUM1_NAME).isModerator(UserFacade.getUser(FORUM1_NAME, MEMBER2).getMember()));
        try {
            assertTrue(userService.removeModerator(SUB_FORUM1_NAME, MEMBER1));
        } catch (ForumException e) {
            e.printStackTrace();
        }
        // verify member1 isn't a modiator
        assertFalse("user is a moderator when he shouldn't", ForumFacade.getForum(FORUM1_NAME).getSubForum(SUB_FORUM1_NAME).isModerator(UserFacade.getUser(FORUM1_NAME, MEMBER1).getMember()));
    }

    @Test
    public void addAndRemoveModerate_alreadyModerate_fail() {
        assertTrue(userService.logIn(ADMIN, ADMIN_PASS).compareResult(Result.SUCCESS));
        //Member admin = UserFacade.getUser(ADMIN,ADMIN_PASS).getMember();
        Collection<String> mediators = new ArrayList<>();
        mediators.add(ADMIN);
        ForumFacade.createSubForum(forum, SUB_FORUM1_NAME, mediators);
        // verify member1 isn't a madiator
        assertFalse("user is a moderator when he shouldn't", ForumFacade.getForum(FORUM1_NAME).getSubForum(SUB_FORUM1_NAME).isModerator(UserFacade.getUser(FORUM1_NAME, MEMBER1).getMember()));
        try {
            assertTrue(userService.addModerator(SUB_FORUM1_NAME, MEMBER1));
        } catch (ForumException e) {
            e.printStackTrace();
        }
        try {
            userService.addModerator(SUB_FORUM1_NAME, MEMBER1);
        } catch (ForumException e) {
            assertEquals(Result.ALREADY_MODERATE.toString(), e.getMessage());
        }
        // verify member1 is a madiator
        assertTrue("user is not moderator", ForumFacade.getForum(FORUM1_NAME).getSubForum(SUB_FORUM1_NAME).isModerator(UserFacade.getUser(FORUM1_NAME, MEMBER1).getMember()));
        // verify member2 isn't a madiator
        assertFalse("user is a moderator when he shouldn't", ForumFacade.getForum(FORUM1_NAME).getSubForum(SUB_FORUM1_NAME).isModerator(UserFacade.getUser(FORUM1_NAME, MEMBER2).getMember()));
        try {
            assertTrue(userService.removeModerator(SUB_FORUM1_NAME, MEMBER1));
        } catch (ForumException e) {
            e.printStackTrace();
        }
        // verify member1 isn't a modiator
        assertFalse("user is a moderator when he shouldn't", ForumFacade.getForum(FORUM1_NAME).getSubForum(SUB_FORUM1_NAME).isModerator(UserFacade.getUser(FORUM1_NAME, MEMBER1).getMember()));
    }

    @Test
    public void removeModerator_notAModerator_fail() {
        assertTrue(userService.logIn(ADMIN, ADMIN_PASS).compareResult(Result.SUCCESS));
        //Member admin = UserFacade.getUser(ADMIN,ADMIN_PASS).getMember();
        Collection<String> mediators = new ArrayList<>();
        mediators.add(ADMIN);
        ForumFacade.createSubForum(forum, SUB_FORUM1_NAME, mediators);
        try {
            userService.removeModerator(SUB_FORUM1_NAME, MEMBER1);
        } catch (ForumException e) {
            assertEquals(Result.NOT_A_MODERATE.toString(), e.getMessage());
        }
        assertFalse("user is a moderator when he shouldn't", ForumFacade.getForum(FORUM1_NAME).getSubForum(SUB_FORUM1_NAME).isModerator(UserFacade.getUser(FORUM1_NAME, MEMBER1).getMember()));
    }

    @Test
    public void loginMember_wrongLogin_fail() {
        // wrong member identifiers
        assertTrue(userService.logIn("notMember", "somePass").compareResult(Result.WRONG_USER_PASS));
        assertTrue(userService.logIn("notMember", MEMBER1_PASS).compareResult(Result.WRONG_USER_PASS));
        assertTrue(userService.logIn("Guest", "somePass").compareResult(Result.WRONG_USER_PASS));
        assertTrue(userService.logIn(MEMBER1, "notPassOfMember1").compareResult(Result.WRONG_USER_PASS));
        //verify that no member is logged in
        assertFalse(userService.isLogedin());

        // user is already loggedin
        assertTrue(userService.logIn(MEMBER1, MEMBER1_PASS).compareResult(Result.SUCCESS));
        assertTrue(userService.logIn(MEMBER1, MEMBER1_PASS).compareResult(Result.ALREADY_LOGDIN));
        assertTrue(userService.logIn(MEMBER2, MEMBER2_PASS).compareResult(Result.ALREADY_LOGDIN));


        // verify member1 still loggedin
        assertEquals(true, userService.isLogedin());
        assertTrue(userService.getUser().getUserName().equals(MEMBER1));
    }


}
