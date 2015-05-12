package org.bgu.stories.usersStories;

import junit.framework.Assert;
import org.bgu.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

/**
 * Created by Sharon Kerzman on 24/04/2015.
 */
public class EmailVerification {

    DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
    String emailPassword = "1234";
    String emailGoodVerification = "1234";
    String emailBadVerification = "1111";


    @Test
    /*
    *Test purpose: user is a member after insert correct email password
    *
    * Steps:
    * 1. insert mail password
    * 2. verify: user is Signed up and login
    *
    *
     */
    public void InsertCorrectPassword_UserIsSignedUp_UserIsActive(){
        ForumTestDriver forumTestDriver = ForumTestDriver.create();
        MemberCredentials credentials = new MemberCredentials("sharon@kerzman", "kerzman", dtf.parseDateTime("16/3/1989"), "123456", "123456", "sharon", "kerzman");
        MemberDriver member = new MemberDriver(credentials);
        SignUpTestDriver signUpTestDriver = forumTestDriver.clickSignUp(member);

        // Insert valid data

        MembersListDriver members = signUpTestDriver.clickSignUp();
        member.setRequiredEmailPassword(emailPassword);
        member.insertEmailVerificaion(emailGoodVerification);
        if (member.getState()=="online"){
            members.changeUserListFromWaitingToOnline(member);
        }

        // verify : Query system so user is now log in and exists
        Assert.assertEquals("member is not in online state", members.getFirstMemberFromOnlineList().getState(), "online");
        Assert.assertEquals("member is in waiting list", members.returnMemberIntFromWaitingList(member), -1);
        Assert.assertEquals("member is not in online list", members.returnMemberIntFromOnlineList(member), 0);
    }

    @Test
    /*
    *Test purpose: user is not member after insert incorrect email password
    *
    * Steps:
    * 1. insert incorrect mail password
    * 2. verify: user is not Signed up and login
    *
    *
     */
    public void InsertIncorrectPassword_UserIsSignedUp_UserIsNotActive(){
        ForumTestDriver forumTestDriver = ForumTestDriver.create();
        MemberCredentials credentials = new MemberCredentials("sharon@kerzman", "kerzman", dtf.parseDateTime("16/3/1989"), "123456", "123456", "sharon", "kerzman");
        MemberDriver member = new MemberDriver(credentials);
        SignUpTestDriver signUpTestDriver = forumTestDriver.clickSignUp(member);

        // Insert valid data

        MembersListDriver members = signUpTestDriver.clickSignUp();
        member.setRequiredEmailPassword(emailPassword);
        member.insertEmailVerificaion(emailBadVerification);
        if (member.getState()=="online"){
            members.changeUserListFromWaitingToOnline(member);
        }

        // verify : Query system so user is now log in and exists
        Assert.assertEquals("member is in online state", members.getFirstMemberFromWaitingList().getState(), "waiting");
        Assert.assertEquals("member is not in waiting list", members.returnMemberIntFromWaitingList(member), -0);
        Assert.assertEquals("member is in online list", members.returnMemberIntFromOnlineList(member), -1);
    }
}
