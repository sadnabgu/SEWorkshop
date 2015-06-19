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
    public static final String MEMBER1 = "moderator1";
    public static final String MEMBER1_PASS = "moderator1Pass";
    public static final String MEMBER2 = "member2";
    public static final String MEMBER2_PASS = "member2Pass";
    public static final String ADMIN = "admin";
    public static final String ADMIN_PASS = "pass";

    public static final String FORUM1_NAME = "sex";
    public static final String SUB_FORUM1_NAME = "ilansMother";

    @BeforeClass
    public static void initialSystem() {
        ForumFacade.resetForums();
        assertTrue(ForumFacade.createForum(FORUM1_NAME, ADMIN, ADMIN_PASS));
        assertEquals(Result.SUCCESS, UserFacade.addMember(FORUM1_NAME, MEMBER1, MEMBER1_PASS));
        assertEquals(Result.SUCCESS, UserFacade.addMember(FORUM1_NAME, MEMBER2, MEMBER2_PASS));
        Collection<String> mods = new ArrayList<>();
        mods.add(MEMBER1);
        assertEquals(Result.SUCCESS, UserService.logIn(FORUM1_NAME, ADMIN, ADMIN_PASS)._result);
        assertEquals(Result.SUCCESS, ForumService.addNewSubForum(FORUM1_NAME, ADMIN, SUB_FORUM1_NAME, mods)._result);
        assertEquals(Result.SUCCESS, UserService.logOut(FORUM1_NAME, ADMIN)._result);
    }

    //TODO - think about a real "guest" like test

    @Test
    public void registerNewMember_memberRegistrationandLogin_newMemberRegistred() {
        // verify we are loggin now as Guest (pre condition)
        //assertTrue(userService.getUser().getClass() == Guest.class);
        assertEquals(Result.SUCCESS, UserService.registerMember(FORUM1_NAME, "newMember1", "pass1")._result);
        assertEquals(Result.SUCCESS, UserService.logIn(FORUM1_NAME, "newMember1", "pass1")._result);
        assertEquals(Result.SUCCESS, UserService.logOut(FORUM1_NAME, "newMember1")._result);
    }


    @Test
    public void loginMember_registerDifferentUserNamesSamePassword_newMemberRegistered() {
        assertEquals(Result.SUCCESS, UserService.registerMember(FORUM1_NAME, "realyNewUserName", MEMBER1_PASS)._result);
    }

    @Test
    public void registerNewMember_memberRegistration_fail() {
        // register while member is loggedin
        assertEquals(Result.SUCCESS, UserService.logIn(FORUM1_NAME, MEMBER1, MEMBER1_PASS)._result);
        assertEquals(Result.USERNAME_EXISTS, UserService.registerMember(FORUM1_NAME, MEMBER1, "pass1")._result);
        assertEquals(Result.SUCCESS, UserService.logOut(FORUM1_NAME, MEMBER1)._result);
        // register member with already used userName
        assertEquals(Result.USERNAME_EXISTS, UserService.registerMember(FORUM1_NAME, MEMBER1, MEMBER1_PASS)._result);
        assertEquals(Result.USERNAME_EXISTS, UserService.registerMember(FORUM1_NAME, MEMBER1, "newPass")._result);

        assertEquals(Result.WRONG_USER_NAME_OR_PASS, UserService.logIn(FORUM1_NAME, MEMBER1, "wrongpass1")._result);
        assertEquals(Result.USERNAME_EXISTS, UserService.registerMember(FORUM1_NAME, MEMBER2, MEMBER2_PASS)._result);
        assertEquals(Result.USERNAME_EXISTS, UserService.registerMember(FORUM1_NAME, MEMBER2, "other_password")._result);
    }


    @Test
    public void addAndRemoveFriend_login_usersAreFriends() {
        assertEquals(Result.SUCCESS, UserService.logIn(FORUM1_NAME, MEMBER1, MEMBER1_PASS)._result);
        assertEquals(Result.SUCCESS, UserService.addFriend(FORUM1_NAME, MEMBER1, MEMBER2)._result);
        // Verify member1 is friend of member2
        assertTrue(UserFacade.getMember(FORUM1_NAME, MEMBER1).isFriendOf(UserFacade.getMember(FORUM1_NAME, MEMBER2)));
        // Verify member1 is friend of member2
        assertTrue(UserFacade.getMember(FORUM1_NAME, MEMBER2).isFriendOf(UserFacade.getMember(FORUM1_NAME, MEMBER1)));
        assertEquals(Result.SUCCESS, UserService.logIn(FORUM1_NAME, MEMBER2, MEMBER2_PASS)._result);
        assertEquals(Result.SUCCESS, UserService.unFriend(FORUM1_NAME, MEMBER2, MEMBER1)._result);
        // Verify member1 is not friend of member2 any more ;-(
        assertFalse(UserFacade.getMember(FORUM1_NAME, MEMBER1).isFriendOf(UserFacade.getMember(FORUM1_NAME, MEMBER2)));
        // Verify member2 is not friend of member1 any more ;-(
        assertFalse(UserFacade.getMember(FORUM1_NAME, MEMBER2).isFriendOf(UserFacade.getMember(FORUM1_NAME, MEMBER1)));
        assertEquals(Result.SUCCESS, UserService.logOut(FORUM1_NAME, MEMBER1)._result);
        assertEquals(Result.SUCCESS, UserService.logOut(FORUM1_NAME, MEMBER2)._result);
    }

    @Test
    public void addAndRemoveFriend_alreadyFriends_fail() {
        assertEquals(Result.SUCCESS, UserService.logIn(FORUM1_NAME, MEMBER1, MEMBER1_PASS)._result);
        assertEquals(Result.SUCCESS, UserService.addFriend(FORUM1_NAME, MEMBER1, MEMBER2)._result);
        assertEquals(Result.SUCCESS, UserService.logOut(FORUM1_NAME, MEMBER1)._result);
        assertEquals(Result.SUCCESS, UserService.logIn(FORUM1_NAME, MEMBER2, MEMBER2_PASS)._result);
        assertEquals(Result.ALREADY_FRIENDS, UserService.addFriend(FORUM1_NAME, MEMBER2, MEMBER1)._result);
        assertEquals(Result.SUCCESS, UserService.logOut(FORUM1_NAME, MEMBER2)._result);
        assertEquals(Result.SUCCESS, UserService.logIn(FORUM1_NAME, MEMBER1, MEMBER1_PASS)._result);
        assertEquals(Result.ALREADY_FRIENDS, UserService.addFriend(FORUM1_NAME, MEMBER1, MEMBER2)._result);
        assertEquals(Result.SUCCESS, UserService.unFriend(FORUM1_NAME, MEMBER1, MEMBER2)._result);
        // Verify member1 is not friend of member2 any more ;-(
        assertFalse(UserFacade.getMember(FORUM1_NAME, MEMBER1).isFriendOf(UserFacade.getMember(FORUM1_NAME, MEMBER2)));
        // Verify member2 is not friend of member1 any more ;-(
        assertFalse(UserFacade.getMember(FORUM1_NAME, MEMBER2).isFriendOf(UserFacade.getMember(FORUM1_NAME, MEMBER1)));
        assertEquals(Result.SUCCESS, UserService.logOut(FORUM1_NAME, MEMBER1)._result);
    }

    @Test
    public void removeFriends_notFriends_fail() {
        assertEquals(Result.SUCCESS, UserService.logIn(FORUM1_NAME, MEMBER1, MEMBER1_PASS)._result);
        assertEquals(Result.NOT_FRIENDS, UserService.unFriend(FORUM1_NAME, MEMBER1, MEMBER2)._result);
        assertEquals(Result.SUCCESS, UserService.logOut(FORUM1_NAME, MEMBER1)._result);
    }

    @Test
    public void addAndRemoveFriends_friendWasntRegisteredToForum_fail() {
        assertEquals(Result.SUCCESS, UserService.logIn(FORUM1_NAME, MEMBER1, MEMBER1_PASS)._result);
        assertEquals(Result.FRIEND_NOT_EXIST, UserService.addFriend(FORUM1_NAME, MEMBER1, "GHOST")._result);
        assertEquals(Result.FRIEND_NOT_EXIST, UserService.unFriend(FORUM1_NAME, MEMBER1, "GHOST")._result);
        assertEquals(Result.SUCCESS, UserService.logOut(FORUM1_NAME, MEMBER1)._result);
    }

    @Test
    public void addAndRemoveModerate_login_userIsModerate() {
        assertEquals(Result.SUCCESS, UserService.logIn(FORUM1_NAME, ADMIN, ADMIN_PASS)._result);
        assertEquals(Result.SUCCESS, UserService.addModerator(FORUM1_NAME, SUB_FORUM1_NAME, ADMIN, MEMBER2)._result);
        assertEquals(Result.SUCCESS, UserService.removeModerator(FORUM1_NAME, SUB_FORUM1_NAME, ADMIN, MEMBER2)._result);
        assertEquals(Result.SUCCESS, UserService.logOut(FORUM1_NAME, ADMIN)._result);
    }

    @Test
    public void addAndRemoveModerate_alreadyModerate_fail() {
        assertEquals(Result.SUCCESS, UserService.logIn(FORUM1_NAME, ADMIN, ADMIN_PASS)._result);
        assertEquals(Result.ALREADY_MODERATE, UserService.addModerator(FORUM1_NAME, SUB_FORUM1_NAME, ADMIN, MEMBER1)._result);
        assertEquals(Result.SUCCESS, UserService.addModerator(FORUM1_NAME, SUB_FORUM1_NAME, ADMIN, MEMBER2)._result);
        assertEquals(Result.ALREADY_MODERATE, UserService.addModerator(FORUM1_NAME, SUB_FORUM1_NAME, ADMIN, MEMBER2)._result);
        assertEquals(Result.SUCCESS, UserService.removeModerator(FORUM1_NAME, SUB_FORUM1_NAME, ADMIN, MEMBER2)._result);
        assertEquals(Result.SUCCESS, UserService.logOut(FORUM1_NAME, ADMIN)._result);
    }

    @Test
    public void addAndRemoveModerate_notForumAdminModeratorAssignment_fail() {
        assertEquals(Result.SUCCESS, UserService.logIn(FORUM1_NAME, MEMBER1, MEMBER1_PASS)._result);
        assertEquals(Result.MEMBER_NOT_FORUM_ADMIN, UserService.addModerator(FORUM1_NAME, SUB_FORUM1_NAME, MEMBER1, MEMBER2)._result);
        assertEquals(Result.SUCCESS, UserService.logOut(FORUM1_NAME, MEMBER1)._result);
    }

    @Test
    public void addAndRemoveModerate_notLoggedInOperation_fail() {
        assertEquals(Result.NOT_LOGGED_IN, UserService.addModerator(FORUM1_NAME, SUB_FORUM1_NAME, ADMIN, MEMBER2)._result);
    }

    @Test
    public void removeModerator_notAModerator_fail() {
        assertEquals(Result.SUCCESS, UserService.logIn(FORUM1_NAME, ADMIN, ADMIN_PASS)._result);
        assertEquals(Result.NOT_MODERATOR, UserService.removeModerator(FORUM1_NAME, SUB_FORUM1_NAME, ADMIN, MEMBER2)._result);
        assertEquals(Result.SUCCESS, UserService.logOut(FORUM1_NAME, ADMIN)._result);
    }


}
