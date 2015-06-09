package org.bgu.service;

import junit.framework.Assert;
import org.bgu.domain.facades.ForumFacade;
import org.bgu.domain.facades.UserFacade;
import org.bgu.domain.model.Forum;
import org.bgu.domain.model.Guest;
import org.bgu.domain.model.Member;
import org.bgu.service.Exceptions.ForumException;
import org.bgu.service.Exceptions.Result;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import sun.misc.ASCIICaseInsensitiveComparator;

import java.util.ArrayList;
import java.util.Collection;

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
    public static void initialSystem(){
        ForumFacade.createForum(FORUM1_NAME, ADMIN, ADMIN_PASS);
        forum = ForumFacade.getForum(FORUM1_NAME);
        userService = new UserService(FORUM1_NAME);
        Assert.assertEquals(Result.SUCCESS, UserFacade.addMember(FORUM1_NAME, MEMBER1, MEMBER1_PASS));
        Assert.assertEquals(Result.SUCCESS, UserFacade.addMember(FORUM1_NAME, MEMBER2, MEMBER2_PASS));
    }

    @Before
    public void logoutSystem(){
        try {
            userService.logOut();
        } catch (ForumException e) {
            e.printStackTrace();
        }
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

        try {
            Assert.assertTrue(userService.registerMember("newMember1", "pass1"));
        } catch (ForumException e) {
            e.printStackTrace();
        }
        //TODO - check that users really was created
        logoutSystem();
        try {
            Assert.assertTrue(userService.registerMember("newMember2", "pass2"));
        } catch (ForumException e) {
            e.printStackTrace();
        }
        //TODO - check that users really was created
    }

    @Test
    public void registerNewMember_memberRegistration_fail(){
        // register while member is loggedin
        try {
            Assert.assertTrue(userService.logIn(MEMBER1, MEMBER1_PASS));
        } catch (ForumException e) {
            e.printStackTrace();
        }
        try {
            userService.registerMember("newMember1", "pass1");
        } catch (ForumException e) {
            Assert.assertEquals(Result.ALREADY_LOGDIN.toString(), e.getMessage());
        }
        //TODO - check that users wasn't created

        // register member with already used userName
        logoutSystem();
        try {
            userService.registerMember(MEMBER1, MEMBER1_PASS);
        } catch (ForumException e) {
            Assert.assertEquals(Result.DUPLICATED_USERNAME.toString(), e.getMessage());
        }
        try {
            userService.registerMember(MEMBER1, "newPass");
        } catch (ForumException e) {
            Assert.assertEquals(Result.DUPLICATED_USERNAME.toString(), e.getMessage());
        }
        //TODO - check that users wasn't created
    }

    @Test
    public void loginMember_login_userIsLoggedIn(){
        try {
            Assert.assertTrue(userService.logIn(MEMBER1, MEMBER1_PASS));
        } catch (ForumException e) {
            e.printStackTrace();
        }
        //TODO - verify member is loggedin
        logoutSystem();
        try {
            userService.logIn(MEMBER2, MEMBER2_PASS);
        } catch (ForumException e) {
            Assert.assertEquals(Result.SUCCESS.toString(), e.getMessage());
        }
        //TODO - verify member is loggedin

}

    @Test
    public void addAndRemoveFriend_login_usersAreFriends(){
        try {
            Assert.assertTrue(userService.logIn(MEMBER1, MEMBER1_PASS));
        } catch (ForumException e) {
            e.printStackTrace();
        }
        try {
            Assert.assertTrue(userService.addFriend(MEMBER2));
        } catch (ForumException e) {
            e.printStackTrace();
        }
        // Verify member1 is friend of member2
        Assert.assertTrue("users aren't friends", userService.getUser().getMember().isFriendOf(UserFacade.getUser(FORUM1_NAME, MEMBER2).getMember()));
        // Verify member1 is friend of member2
        Assert.assertTrue("users aren't friends", UserFacade.getUser(FORUM1_NAME, MEMBER2).getMember().isFriendOf(userService.getUser().getMember()));
        try {
            Assert.assertTrue(userService.removeFriend(MEMBER2));
        } catch (ForumException e) {
            e.printStackTrace();
        }
        Assert.assertFalse("users are friends when they shouldn't", userService.getUser().getMember().isFriendOf(UserFacade.getUser(FORUM1_NAME, MEMBER2).getMember()));
        // Verify member1 is friend of member2
        Assert.assertFalse("users are friends when they shouldn't", UserFacade.getUser(FORUM1_NAME, MEMBER2).getMember().isFriendOf(userService.getUser().getMember()));

    }

    @Test
    public void addAndRemoveFriend_alreadyFriends_fail(){
        try {
            Assert.assertTrue(userService.logIn(MEMBER1, MEMBER1_PASS));
        } catch (ForumException e) {
            e.printStackTrace();
        }
        try {
            Assert.assertTrue(userService.addFriend(MEMBER2));
        } catch (ForumException e) {
            e.printStackTrace();
        }
        try {
            userService.logOut();
        } catch (ForumException e) {
            e.printStackTrace();
        }
        try {
            Assert.assertTrue(userService.logIn(MEMBER2, MEMBER2_PASS));
        } catch (ForumException e) {
            e.printStackTrace();
        }
        try {
            userService.addFriend(MEMBER1);
        } catch (ForumException e) {
            Assert.assertEquals(Result.ALREADY_FRIENDS.toString(), e.getMessage());
        }
        try {
            userService.logOut();
        } catch (ForumException e) {
            e.printStackTrace();
        }
        try {
            Assert.assertTrue(userService.logIn(MEMBER1, MEMBER1_PASS));
        } catch (ForumException e) {
            e.printStackTrace();
        }
        try {
            userService.addFriend(MEMBER2);
        } catch (ForumException e) {
            Assert.assertEquals(Result.ALREADY_FRIENDS.toString(), e.getMessage());
        }
        try {
            Assert.assertTrue(userService.removeFriend(MEMBER2));
        } catch (ForumException e) {
            e.printStackTrace();
        }
        Assert.assertFalse("users are friends when they shouldn't", userService.getUser().getMember().isFriendOf(UserFacade.getUser(FORUM1_NAME, MEMBER2).getMember()));
        // Verify member1 is friend of member2
        Assert.assertFalse("users are friends when they shouldn't", UserFacade.getUser(FORUM1_NAME, MEMBER2).getMember().isFriendOf(userService.getUser().getMember()));
    }

    @Test
    public void removeFriends_notFriends_fail(){
        try {
            Assert.assertTrue(userService.logIn(MEMBER1, MEMBER1_PASS));
        } catch (ForumException e) {
            e.printStackTrace();
        }
        try {
            userService.removeFriend(MEMBER2);
        } catch (ForumException e) {
            Assert.assertEquals(Result.NOT_FRIENDS.toString(), e.getMessage());
        }
        Assert.assertFalse("users are friends when they shouldn't", userService.getUser().getMember().isFriendOf(UserFacade.getUser(FORUM1_NAME, MEMBER2).getMember()));
        // Verify member1 is friend of member2
        Assert.assertFalse("users are friends when they shouldn't", UserFacade.getUser(FORUM1_NAME, MEMBER2).getMember().isFriendOf(userService.getUser().getMember()));
    }

    @Test
    public void addAndRemoveModerate_login_userIsModerate(){
        try {
            Assert.assertTrue(userService.logIn(ADMIN, ADMIN_PASS));
        } catch (ForumException e) {
            e.printStackTrace();
        }
        //Member admin = UserFacade.getUser(ADMIN,ADMIN_PASS).getMember();
        Collection<String> mediators = new ArrayList<>();
        mediators.add(ADMIN);
        ForumFacade.createSubForum(forum, SUB_FORUM1_NAME, mediators);
        // verify member1 isn't a madiator
        Assert.assertFalse("user is a moderator when he shouldn't", ForumFacade.getForum(FORUM1_NAME).getSubForum(SUB_FORUM1_NAME).isModerator(UserFacade.getUser(FORUM1_NAME, MEMBER1).getMember()));
        try {
            Assert.assertTrue(userService.addModerator(SUB_FORUM1_NAME, MEMBER1));
        } catch (ForumException e) {
            e.printStackTrace();
        }
        // verify member1 is a madiator
        Assert.assertTrue("user is not moderator", ForumFacade.getForum(FORUM1_NAME).getSubForum(SUB_FORUM1_NAME).isModerator(UserFacade.getUser(FORUM1_NAME, MEMBER1).getMember()));
        // verify member2 isn't a madiator
        Assert.assertFalse("user is a moderator when he shouldn't", ForumFacade.getForum(FORUM1_NAME).getSubForum(SUB_FORUM1_NAME).isModerator(UserFacade.getUser(FORUM1_NAME, MEMBER2).getMember()));
        try {
            Assert.assertTrue(userService.removeModerator(SUB_FORUM1_NAME, MEMBER1));
        } catch (ForumException e) {
            e.printStackTrace();
        }
        // verify member1 isn't a modiator
        Assert.assertFalse("user is a moderator when he shouldn't", ForumFacade.getForum(FORUM1_NAME).getSubForum(SUB_FORUM1_NAME).isModerator(UserFacade.getUser(FORUM1_NAME, MEMBER1).getMember()));
    }

    @Test
    public void addAndRemoveModerate_alreadyModerate_fail(){
        try {
            Assert.assertTrue(userService.logIn(ADMIN, ADMIN_PASS));
        } catch (ForumException e) {
            e.printStackTrace();
        }
        //Member admin = UserFacade.getUser(ADMIN,ADMIN_PASS).getMember();
        Collection<String> mediators = new ArrayList<>();
        mediators.add(ADMIN);
        ForumFacade.createSubForum(forum, SUB_FORUM1_NAME, mediators);
        // verify member1 isn't a madiator
        Assert.assertFalse("user is a moderator when he shouldn't", ForumFacade.getForum(FORUM1_NAME).getSubForum(SUB_FORUM1_NAME).isModerator(UserFacade.getUser(FORUM1_NAME, MEMBER1).getMember()));
        try {
            Assert.assertTrue(userService.addModerator(SUB_FORUM1_NAME, MEMBER1));
        } catch (ForumException e) {
            e.printStackTrace();
        }
        try {
            userService.addModerator(SUB_FORUM1_NAME, MEMBER1);
        } catch (ForumException e) {
            Assert.assertEquals(Result.ALREADY_MODERATE.toString(), e.getMessage());
        }
        // verify member1 is a madiator
        Assert.assertTrue("user is not moderator", ForumFacade.getForum(FORUM1_NAME).getSubForum(SUB_FORUM1_NAME).isModerator(UserFacade.getUser(FORUM1_NAME, MEMBER1).getMember()));
        // verify member2 isn't a madiator
        Assert.assertFalse("user is a moderator when he shouldn't", ForumFacade.getForum(FORUM1_NAME).getSubForum(SUB_FORUM1_NAME).isModerator(UserFacade.getUser(FORUM1_NAME, MEMBER2).getMember()));
        try {
            Assert.assertTrue(userService.removeModerator(SUB_FORUM1_NAME, MEMBER1));
        } catch (ForumException e) {
            e.printStackTrace();
        }
        // verify member1 isn't a modiator
        Assert.assertFalse("user is a moderator when he shouldn't", ForumFacade.getForum(FORUM1_NAME).getSubForum(SUB_FORUM1_NAME).isModerator(UserFacade.getUser(FORUM1_NAME, MEMBER1).getMember()));
        }

    @Test
    public void removeModerator_notAModerator_fail(){
        try {
            Assert.assertTrue(userService.logIn(ADMIN, ADMIN_PASS));
        } catch (ForumException e) {
            e.printStackTrace();
        }
        //Member admin = UserFacade.getUser(ADMIN,ADMIN_PASS).getMember();
        Collection<String> mediators = new ArrayList<>();
        mediators.add(ADMIN);
        ForumFacade.createSubForum(forum, SUB_FORUM1_NAME, mediators);
        try {
            userService.removeModerator(SUB_FORUM1_NAME, MEMBER1);
        } catch (ForumException e) {
            Assert.assertEquals(Result.NOT_A_MODERATE.toString(), e.getMessage());
        }
        Assert.assertFalse("user is a moderator when he shouldn't", ForumFacade.getForum(FORUM1_NAME).getSubForum(SUB_FORUM1_NAME).isModerator(UserFacade.getUser(FORUM1_NAME, MEMBER1).getMember()));
    }

    @Test
    public void loginMember_wrongLogin_fail(){
        // wrong member identifiers
        try {
            userService.logIn("notMember", "somePass");
        } catch (ForumException e) {
            Assert.assertEquals(Result.WRONG_USER_PASS.toString(), e.getMessage());
        }
        try {
            userService.logIn("notMember", MEMBER1_PASS);
        } catch (ForumException e) {
            Assert.assertEquals(Result.WRONG_USER_PASS.toString(), e.getMessage());
        }
        try {
            userService.logIn("Guest", "somePass");
        } catch (ForumException e) {
            Assert.assertEquals(Result.WRONG_USER_PASS.toString(), e.getMessage());
        }
        try {
            userService.logIn(MEMBER1, "notPassOfMember1");
        } catch (ForumException e) {
            Assert.assertEquals(Result.WRONG_USER_PASS.toString(), e.getMessage());
        }
        //verify that no member is logged in
        Assert.assertFalse(userService.isLogedin());

        // user is already loggedin
        try {
            Assert.assertTrue(userService.logIn(MEMBER1, MEMBER1_PASS));
        } catch (ForumException e) {
            e.printStackTrace();
        }
        try {
            userService.logIn(MEMBER1, MEMBER1_PASS);
        } catch (ForumException e) {
            Assert.assertEquals(Result.ALREADY_LOGDIN.toString(), e.getMessage());
        }

        try {
            Assert.assertEquals(Result.ALREADY_LOGDIN, userService.logIn(MEMBER2, MEMBER2_PASS));
        } catch (ForumException e) {
            Assert.assertEquals(Result.ALREADY_LOGDIN.toString(), e.getMessage());
        }

        // verify member1 still loggedin
        Assert.assertEquals(true, userService.isLogedin());
        Assert.assertTrue(userService.getUser().getUserName().equals(MEMBER1));
    }



}
