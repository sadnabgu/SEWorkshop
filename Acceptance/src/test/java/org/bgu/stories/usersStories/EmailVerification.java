package org.bgu.stories.usersStories;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by Sharon Kerzman on 24/04/2015.
 */
public class EmailVerification {
    @Test
    @Ignore
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
        // TODO: Setup system to initial state

        // TODO: Simulate guest clicks sign up button

        // TODO: Insert valid data

        // TODO: insert email password

        // TODO: verify : Query system so user is now log in and exists
    }

    @Test
    @Ignore
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
        // TODO: Setup system to initial state

        // TODO: Simulate guest clicks sign up button

        // TODO: Insert valid data

        // TODO: insert incorrect email password

        // TODO: verify : Query system so user is not log in and exists
    }
}
