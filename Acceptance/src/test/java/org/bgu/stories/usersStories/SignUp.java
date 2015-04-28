package org.bgu.stories.usersStories;

import junit.framework.Assert;
import org.bgu.ForumTestDriver;
import org.bgu.MemberCredentials;
import org.bgu.SignUpTestDriver;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by Sharon Kerzman on 24/04/2015.
 */
public class SignUp {
    DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
    // Parsing the date
    MemberCredentials credentials;

    @Before
    public void init(){
         credentials = new MemberCredentials("sharon@kerzman", "kerzman", dtf.parseDateTime("16/3/1989"), "123456", "123456", "sharon", "kerzman");
    }

    @Test
    //@Ignore
    /*
    *Test purpose: user is a member after signing up with correct data
    *
    * Steps:
    * 1. insert correct data
    * 2. verify: user is Signed up and login
    *
    *
     */
    public void SignUp_UserSignUpWithCorrectData_UserFoundInSystem(){
        // TODO: Setup system to initial state

        // Simulate guest clicks sign up button
        ForumTestDriver forumTestDriver = ForumTestDriver.create();
        SignUpTestDriver signUpTestDriver = forumTestDriver.clickSignUp();

        // Insert valid data
        signUpTestDriver.setCredentials(credentials);
        MemberCredentials result = signUpTestDriver.clickSignUp();

        // verify : Query system so user is now log in and exists
        Assert.assertEquals("given credentials are different from server", credentials, result);
    }



    @Test
    /*
    *Test purpose: user is a guest after signed up with incorrect data
    *
    * Steps:
    * 1. insert incorrect data
    * 2. verify: user is a guest
    *
    *
     */
    public void UserLoginWithIncorrectData_UserIsSignedUp_UserIsAGuest(){

    }
}
