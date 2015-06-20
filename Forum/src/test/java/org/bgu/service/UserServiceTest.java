package org.bgu.service;

import org.bgu.domain.facades.ForumFacade;
import org.bgu.domain.facades.UserFacade;
import org.bgu.service.Exceptions.Result;
import org.bgu.service.Exceptions.RetObj;
import org.junit.BeforeClass;
import org.junit.Test;


import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import static org.junit.Assert.*;


/**
 * Created by hodai on 4/21/15.
 */
public class UserServiceTest extends abstractTest{

    public static final String MEMBER1 = "moderator1";
    public static final String MEMBER1_PASS = "moderator1Pass";
    public static final String MEMBER2 = "member2";
    public static final String MEMBER2_PASS = "member2Pass";

    public static final String SUB_FORUM1_NAME = "ilansMother";
    private static UUID member1Sid;
    private static UUID member2Sid;
    private static UUID guestSid;

    /** utills */
    private static void loginMember1(){
        member1Sid = login(FORUM_NAME, MEMBER1, MEMBER1_PASS);
    }
    private static void loginMember2(){
        member2Sid = login(FORUM_NAME, MEMBER2, MEMBER2_PASS);
    }

    private static void loginGuest(){
        RetObj<UUID> retObj = UserService.logInGuest(FORUM_NAME);
        assertEquals(Result.SUCCESS, retObj._result);
        guestSid = retObj._value;
    }


    @BeforeClass
    public static void initialSystem() {
        UserFacade.resetSystem();
        assertTrue(ForumFacade.createForum(FORUM_NAME, MANAGER1_NAME, MANAGER1_PASS));
        assertEquals(Result.SUCCESS, UserFacade.addMember(FORUM_NAME, MEMBER1, MEMBER1_PASS));
        assertEquals(Result.SUCCESS, UserFacade.addMember(FORUM_NAME, MEMBER2, MEMBER2_PASS));
        Collection<String> mods = new ArrayList<>();
        mods.add(MEMBER1);

        // add initial sub-forum
        ForumFacade.getForum(FORUM_NAME).addNewSubForum(SUB_FORUM1_NAME, mods);
        loginMember1();
        loginMember2();
    }

    //TODO - think about a real "guest" like test

    @Test
    public void registerNewMember_memberRegistrationandLogin_newMemberRegistred() {
        // verify we are loggin now as Guest (pre condition)
        loginGuest();
        assertEquals(Result.SUCCESS, UserService.registerMember(guestSid, "newMember1", "pass1")._result);
        UUID sId = login(FORUM_NAME, "newMember1", "pass1");
        assertEquals(Result.SUCCESS, UserService.logOut(sId)._result);
    }

    @Test
    public void loginMember_registerDifferentUserNamesSamePassword_newMemberRegistered() {
        assertEquals(Result.SUCCESS, UserService.registerMember(guestSid, "realyNewUserName", MEMBER1_PASS)._result);
    }

    @Test
    public void registerNewMember_memberRegistration_fail() {
        assertEquals(Result.USERNAME_EXISTS, UserService.registerMember(guestSid, MEMBER1, "pass1")._result);
        assertEquals(Result.SUCCESS, UserService.logOut(member1Sid)._result);
        // register member with already used userName
        assertEquals(Result.USERNAME_EXISTS, UserService.registerMember(guestSid, MEMBER1, MEMBER1_PASS)._result);
        assertEquals(Result.USERNAME_EXISTS, UserService.registerMember(guestSid, MEMBER1, "newPass")._result);

        RetObj<UUID> retObj = UserService.logInGuest(FORUM_NAME);
        assertEquals(Result.SUCCESS, retObj._result);
        assertEquals(Result.WRONG_USER_NAME_OR_PASS, UserService.logInMember(retObj._value, MEMBER1, "wrongpass1")._result);
        assertEquals(Result.USERNAME_EXISTS, UserService.registerMember(guestSid, MEMBER2, MEMBER2_PASS)._result);
        assertEquals(Result.USERNAME_EXISTS, UserService.registerMember(guestSid, MEMBER2, "other_password")._result);
        loginMember1();
    }

    @Test
    public void addAndRemoveFriend_login_usersAreFriends() {
        assertEquals(Result.SUCCESS, UserService.addFriend(member1Sid, MEMBER2)._result);
        // Verify member1 is friend of member2
        assertTrue(UserFacade.getMember(FORUM_NAME, MEMBER1).isFriendOf(UserFacade.getMember(FORUM_NAME, MEMBER2)));
        // Verify member1 is friend of member2
        assertTrue(UserFacade.getMember(FORUM_NAME, MEMBER2).isFriendOf(UserFacade.getMember(FORUM_NAME, MEMBER1)));

        assertEquals(Result.SUCCESS, UserService.unFriend(member2Sid, MEMBER1)._result);
        // Verify member1 is not friend of member2 any more ;-(
        assertFalse(UserFacade.getMember(FORUM_NAME, MEMBER1).isFriendOf(UserFacade.getMember(FORUM_NAME, MEMBER2)));
        // Verify member2 is not friend of member1 any more ;-(
        assertFalse(UserFacade.getMember(FORUM_NAME, MEMBER2).isFriendOf(UserFacade.getMember(FORUM_NAME, MEMBER1)));
    }

    @Test
    public void addAndRemoveFriend_alreadyFriends_fail() {
        // TODO - login only once per user with different sId
        assertEquals(Result.SUCCESS, UserService.addFriend(member1Sid, MEMBER2)._result);
        assertEquals(Result.ALREADY_FRIENDS, UserService.addFriend(member2Sid, MEMBER1)._result);
        assertEquals(Result.ALREADY_FRIENDS, UserService.addFriend(member1Sid, MEMBER2)._result);
        assertEquals(Result.SUCCESS, UserService.unFriend(member1Sid, MEMBER2)._result);
        // Verify member1 is not friend of member2 any more ;-(
        assertFalse(UserFacade.getMember(FORUM_NAME, MEMBER1).isFriendOf(UserFacade.getMember(FORUM_NAME, MEMBER2)));
        // Verify member2 is not friend of member1 any more ;-(
        assertFalse(UserFacade.getMember(FORUM_NAME, MEMBER2).isFriendOf(UserFacade.getMember(FORUM_NAME, MEMBER1)));
    }

    @Test
    public void removeFriends_notFriends_fail() {
        assertEquals(Result.NOT_FRIENDS, UserService.unFriend(member1Sid, MEMBER2)._result);
    }

    @Test
    public void addAndRemoveFriends_friendWasntRegisteredToForum_fail() {
        assertEquals(Result.FRIEND_NOT_EXIST, UserService.addFriend(member1Sid, "GHOST")._result);
        assertEquals(Result.FRIEND_NOT_EXIST, UserService.unFriend(member1Sid, "GHOST")._result);
    }

    @Test
    public void addAndRemoveModerate_login_userIsModerate() {
        // TODO - move to Admin test???
        loginAdmin();
        assertEquals(Result.SUCCESS, UserService.addModerator(managerSid, SUB_FORUM1_NAME, MEMBER2)._result);
        assertEquals(Result.SUCCESS, UserService.removeModerator(managerSid, SUB_FORUM1_NAME, MEMBER2)._result);
        assertEquals(Result.SUCCESS, UserService.logOut(managerSid)._result);
    }

    @Test
    public void addAndRemoveModerate_alreadyModerate_fail() {

        loginAdmin();

        assertEquals(Result.ALREADY_MODERATE, UserService.addModerator(managerSid, SUB_FORUM1_NAME, MEMBER1)._result);
        assertEquals(Result.SUCCESS, UserService.addModerator(managerSid, SUB_FORUM1_NAME, MEMBER2)._result);
        assertEquals(Result.ALREADY_MODERATE, UserService.addModerator(managerSid, SUB_FORUM1_NAME, MEMBER2)._result);
        assertEquals(Result.SUCCESS, UserService.removeModerator(managerSid, SUB_FORUM1_NAME, MEMBER2)._result);

        assertEquals(Result.SUCCESS, UserService.logOut(managerSid)._result);
    }

    @Test
    public void addAndRemoveModerate_notForumAdminModeratorAssignment_fail() {
        assertEquals(Result.MEMBER_NOT_FORUM_ADMIN, UserService.addModerator(member1Sid, SUB_FORUM1_NAME, MEMBER2)._result);
    }

    @Test
    public void addAndRemoveModerate_notLoggedInOperation_fail() {
        assertEquals(Result.SUCCESS, UserService.logOut(member1Sid)._result);
        assertEquals(Result.NOT_LOGGED_IN, UserService.addModerator(member1Sid, SUB_FORUM1_NAME, MEMBER2)._result);
        loginMember1();
    }

    @Test
    public void removeModerator_notAModerator_fail() {
        loginAdmin();
        assertEquals(Result.NOT_MODERATOR, UserService.removeModerator(managerSid, SUB_FORUM1_NAME, MEMBER2)._result);
        assertEquals(Result.SUCCESS, UserService.logOut(managerSid)._result);

    }


}
