package org.bgu.service;

import org.bgu.domain.facades.ForumFacade;
import org.bgu.domain.facades.UserFacade;
import org.bgu.service.Exceptions.Result;
import org.junit.BeforeClass;
import org.junit.Test;


import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;


/**
 * Created by hodai on 4/21/15.
 */
public class UserServiceTest {
    public static final String MODERATOR1 = "moderator1";
    public static final String MODERATOR1_PASS = "moderator1Pass";
    public static final String MEMBER2 = "member2";
    public static final String MEMBER2_PASS = "member2Pass";
    public static final String ADMIN = "admin";
    public static final String ADMIN_PASS = "pass";

    public static final String FORUM1_NAME = "sex";
    public static final String SUB_FORUM1_NAME = "ilansMother";

    @BeforeClass
    public static void initialSystem() {
        ForumFacade.resetForums();
        UserFacade.reset();
        assertTrue(ForumFacade.createForum(FORUM1_NAME, ADMIN, ADMIN_PASS));
        assertEquals(Result.SUCCESS, UserFacade.addMember(FORUM1_NAME, MODERATOR1, MODERATOR1_PASS));
        assertEquals(Result.SUCCESS, UserFacade.addMember(FORUM1_NAME, MEMBER2, MEMBER2_PASS));
        Collection<String> mods = new ArrayList<>();
        mods.add(MODERATOR1);

        // add initial sub-forum
        ForumFacade.getForum(FORUM1_NAME).addNewSubForum(SUB_FORUM1_NAME, mods);
    }

    //TODO - think about a real "guest" like test

    @Test
    public void registerNewMember_memberRegistrationandLogin_newMemberRegistred() {
        // verify we are loggin now as Guest (pre condition)
        //assertTrue(userService.getUser().getClass() == Guest.class);
        assertEquals(Result.SUCCESS, UserService.registerMember(FORUM1_NAME, "newMember1", "pass1")._result);
        assertEquals(Result.SUCCESS, UserService.logIn(2, FORUM1_NAME, "newMember1", "pass1")._result);
        assertEquals(Result.SUCCESS, UserService.logOut(2)._result);
    }

    @Test
    public void loginMember_registerDifferentUserNamesSamePassword_newMemberRegistered() {
        assertEquals(Result.SUCCESS, UserService.registerMember(FORUM1_NAME, "realyNewUserName", MODERATOR1_PASS)._result);
    }

    @Test
    public void registerNewMember_memberRegistration_fail() {
        // register while member is loggedin
        assertEquals(Result.SUCCESS, UserService.logIn(2, FORUM1_NAME, MODERATOR1, MODERATOR1_PASS)._result);
        assertEquals(Result.USERNAME_EXISTS, UserService.registerMember(FORUM1_NAME, MODERATOR1, "pass1")._result);
        assertEquals(Result.SUCCESS, UserService.logOut(2)._result);
        // register member with already used userName
        assertEquals(Result.USERNAME_EXISTS, UserService.registerMember(FORUM1_NAME, MODERATOR1, MODERATOR1_PASS)._result);
        assertEquals(Result.USERNAME_EXISTS, UserService.registerMember(FORUM1_NAME, MODERATOR1, "newPass")._result);

        assertEquals(Result.WRONG_USER_NAME_OR_PASS, UserService.logIn(2, FORUM1_NAME, MODERATOR1, "wrongpass1")._result);
        assertEquals(Result.USERNAME_EXISTS, UserService.registerMember(FORUM1_NAME, MEMBER2, MEMBER2_PASS)._result);
        assertEquals(Result.USERNAME_EXISTS, UserService.registerMember(FORUM1_NAME, MEMBER2, "other_password")._result);
    }

    @Test
    public void addAndRemoveFriend_login_usersAreFriends() {
        assertEquals(Result.SUCCESS, UserService.logIn(2, FORUM1_NAME, MODERATOR1, MODERATOR1_PASS)._result);
        assertEquals(Result.SUCCESS, UserService.addFriend(2, MEMBER2)._result);
        // Verify member1 is friend of member2
        assertTrue(UserFacade.getMember(FORUM1_NAME, MODERATOR1).isFriendOf(UserFacade.getMember(FORUM1_NAME, MEMBER2)));
        // Verify member1 is friend of member2
        assertTrue(UserFacade.getMember(FORUM1_NAME, MEMBER2).isFriendOf(UserFacade.getMember(FORUM1_NAME, MODERATOR1)));
        assertEquals(Result.SUCCESS, UserService.logIn(3, FORUM1_NAME, MEMBER2, MEMBER2_PASS)._result);
        assertEquals(Result.SUCCESS, UserService.unFriend(3, MODERATOR1)._result);
        // Verify member1 is not friend of member2 any more ;-(
        assertFalse(UserFacade.getMember(FORUM1_NAME, MODERATOR1).isFriendOf(UserFacade.getMember(FORUM1_NAME, MEMBER2)));
        // Verify member2 is not friend of member1 any more ;-(
        assertFalse(UserFacade.getMember(FORUM1_NAME, MEMBER2).isFriendOf(UserFacade.getMember(FORUM1_NAME, MODERATOR1)));
        assertEquals(Result.SUCCESS, UserService.logOut(2)._result);
        assertEquals(Result.SUCCESS, UserService.logOut(3)._result);
    }

    @Test
    public void addAndRemoveFriend_alreadyFriends_fail() {
        // TODO - login only once per user with different sId
        assertEquals(Result.SUCCESS, UserService.logIn(2, FORUM1_NAME, MODERATOR1, MODERATOR1_PASS)._result);
        assertEquals(Result.SUCCESS, UserService.addFriend(2, MEMBER2)._result);
        assertEquals(Result.SUCCESS, UserService.logOut(2)._result);
        assertEquals(Result.SUCCESS, UserService.logIn(3, FORUM1_NAME, MEMBER2, MEMBER2_PASS)._result);
        assertEquals(Result.ALREADY_FRIENDS, UserService.addFriend(3, MODERATOR1)._result);
        assertEquals(Result.SUCCESS, UserService.logOut(3)._result);
        assertEquals(Result.SUCCESS, UserService.logIn(2, FORUM1_NAME, MODERATOR1, MODERATOR1_PASS)._result);
        assertEquals(Result.ALREADY_FRIENDS, UserService.addFriend(2, MEMBER2)._result);
        assertEquals(Result.SUCCESS, UserService.unFriend(2, MEMBER2)._result);
        // Verify member1 is not friend of member2 any more ;-(
        assertFalse(UserFacade.getMember(FORUM1_NAME, MODERATOR1).isFriendOf(UserFacade.getMember(FORUM1_NAME, MEMBER2)));
        // Verify member2 is not friend of member1 any more ;-(
        assertFalse(UserFacade.getMember(FORUM1_NAME, MEMBER2).isFriendOf(UserFacade.getMember(FORUM1_NAME, MODERATOR1)));
        assertEquals(Result.SUCCESS, UserService.logOut(2)._result);
    }

    @Test
    public void removeFriends_notFriends_fail() {
        assertEquals(Result.SUCCESS, UserService.logIn(2, FORUM1_NAME, MODERATOR1, MODERATOR1_PASS)._result);
        assertEquals(Result.NOT_FRIENDS, UserService.unFriend(2, MEMBER2)._result);
        assertEquals(Result.SUCCESS, UserService.logOut(2)._result);
    }

    @Test
    public void addAndRemoveFriends_friendWasntRegisteredToForum_fail() {
        assertEquals(Result.SUCCESS, UserService.logIn(2, FORUM1_NAME, MODERATOR1, MODERATOR1_PASS)._result);
        assertEquals(Result.FRIEND_NOT_EXIST, UserService.addFriend(2, "GHOST")._result);
        assertEquals(Result.FRIEND_NOT_EXIST, UserService.unFriend(2, "GHOST")._result);
        assertEquals(Result.SUCCESS, UserService.logOut(2)._result);
    }

    @Test
    public void addAndRemoveModerate_login_userIsModerate() {
        // TODO - move to Admin test???
        assertEquals(Result.SUCCESS, UserService.logIn(2, FORUM1_NAME, ADMIN, ADMIN_PASS)._result);
        assertEquals(Result.SUCCESS, UserService.addModerator(2, SUB_FORUM1_NAME, MEMBER2)._result);
        assertEquals(Result.SUCCESS, UserService.removeModerator(2, SUB_FORUM1_NAME, MEMBER2)._result);
        assertEquals(Result.SUCCESS, UserService.logOut(2)._result);
    }

    @Test
    public void addAndRemoveModerate_alreadyModerate_fail() {
        assertEquals(Result.SUCCESS, UserService.logIn(2, FORUM1_NAME, ADMIN, ADMIN_PASS)._result);
        assertEquals(Result.ALREADY_MODERATE, UserService.addModerator(2, SUB_FORUM1_NAME, MODERATOR1)._result);
        assertEquals(Result.SUCCESS, UserService.addModerator(2, SUB_FORUM1_NAME, MEMBER2)._result);
        assertEquals(Result.ALREADY_MODERATE, UserService.addModerator(2, SUB_FORUM1_NAME, MEMBER2)._result);
        assertEquals(Result.SUCCESS, UserService.removeModerator(2, SUB_FORUM1_NAME, MEMBER2)._result);
        assertEquals(Result.SUCCESS, UserService.logOut(2)._result);
    }

    @Test
    public void addAndRemoveModerate_notForumAdminModeratorAssignment_fail() {
        assertEquals(Result.SUCCESS, UserService.logIn(2, FORUM1_NAME, MODERATOR1, MODERATOR1_PASS)._result);
        assertEquals(Result.MEMBER_NOT_FORUM_ADMIN, UserService.addModerator(2, SUB_FORUM1_NAME, MEMBER2)._result);
        assertEquals(Result.SUCCESS, UserService.logOut(2)._result);
    }

    @Test
    public void addAndRemoveModerate_notLoggedInOperation_fail() {
        assertEquals(Result.NOT_LOGGED_IN, UserService.addModerator(2, SUB_FORUM1_NAME, MEMBER2)._result);
    }

    @Test
    public void removeModerator_notAModerator_fail() {
        assertEquals(Result.SUCCESS, UserService.logIn(2, FORUM1_NAME, ADMIN, ADMIN_PASS)._result);
        assertEquals(Result.NOT_MODERATOR, UserService.removeModerator(2, SUB_FORUM1_NAME, MEMBER2)._result);
        assertEquals(Result.SUCCESS, UserService.logOut(2)._result);
    }


}
