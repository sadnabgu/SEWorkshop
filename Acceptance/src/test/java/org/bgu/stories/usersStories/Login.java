package org.bgu.stories.usersStories;

import org.bgu.service.UserService;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by gur on 21/04/2015.
 */
public class Login {
    @Test
    /*
    *Test purpose: user is a member after login with correct data
    *
    * Steps:
    * 1. insert correct data
    * 2. verify: user is login with the same data name
    *
    *
     */
    public void UserLoginWithCorrectData_UserIsSignedUp_UserIsActive(){
        UserService user = new UserService("bad forum"); // TODO - what the hell
        user.registerMember("bar", "refaeli");
        user.logOut();
        assertFalse("user is logged in", user.isLogedin());
        user.logIn("bar", "refaeli");
        assertTrue("user is not logged in", user.isLogedin());


    }

    @Test
    /*
    *Test purpose: user is a guest after login with incorrect data
    *
    * Steps:
    * 1. insert incorrect data
    * 2. verify: user is a guest
    *
    *
     */
    public void UserLoginWithIncorrectData_UserIsSignedUp_UserIsAGuest(){
        UserService user = new UserService("bad forum"); // TODO - what the hell
        user.registerMember("bar", "refaeli");
        user.logOut();
        assertFalse("user is logged in", user.isLogedin());
        user.logIn("bar", "sella");
        assertFalse("user is not logged in", user.isLogedin());
    }

}
