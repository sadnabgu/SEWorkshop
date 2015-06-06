package org.bgu.stories.usersStories;

import junit.framework.Assert;
import org.bgu.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Sharon Kerzman on 24/04/2015.
 */
public class SignUp {
    DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
    // Parsing the date
    MemberCredentials credentials;
    MemberCredentials falseCredentials;

    @Before
    public void init(){
         credentials = new MemberCredentials("sharon@kerzman", "kerzman", dtf.parseDateTime("16/3/1989"), "123456", "123456", "sharon", "kerzman");
        falseCredentials = new MemberCredentials("sharon@kerzman", "kerzman", dtf.parseDateTime("16/3/1989"), "123456", "111111", "sharon", "kerzman");
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

        // Simulate guest clicks sign up button
        ForumTestDriver forumTestDriver = ForumTestDriver.create();

        MemberDriver member = new MemberDriver(credentials);
        SignUpTestDriver signUpTestDriver = forumTestDriver.clickSignUp(member);

        // Insert valid data

        MembersListDriver members = signUpTestDriver.clickSignUp();

        // verify : Query system so user is now log in and exists
        Assert.assertTrue("member is not exist in data base", members.isMemberInWaitingList(member));
        Assert.assertEquals("member is not in waiting state", members.getFirstMemberFromWaitingList().getState(), "waiting");
        MemberCredentials testedMemberCredentials = member.getCredentials();
        Assert.assertEquals("given credentials are different from server", credentials, testedMemberCredentials);
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

        // Simulate guest clicks sign up button
        ForumTestDriver forumTestDriver = ForumTestDriver.create();

        MemberDriver member = new MemberDriver(falseCredentials);
        SignUpTestDriver signUpTestDriver = forumTestDriver.clickSignUp(member);

        // Insert valid data

        MembersListDriver members = signUpTestDriver.clickSignUp();

        // verify : Query system so user is now log in and exists
        Assert.assertNull("member user name is exist when it shouldn't", members.getFirstMemberFromWaitingList().getCredentials());

    }
}
