package org.bgu.stories.usersStories;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by Sharon Kerzman on 24/04/2015.
 */
public class MentorComplaint {
    @Test
    @Ignore
    /*
    *Test purpose: complaint sent after insert correct data by user
    *
    * Steps:
    * 1. member login
    * 2. insert correct data
    * 3. verify: Complaint sent
    *
    *
     */
    public void ComplaintSentWithCorrectData_UserIsLogin_ComplaintSent(){
        // TODO: Setup system to initial state

        // TODO: member login

        // TODO: Simulate member clicks Complaint mentor button

        // TODO: Insert valid data

        // TODO: verify : Query system so complaint was sent
    }

    @Test
    @Ignore
    /*
    *Test purpose: complaint wasn't sent after insert incorrect data by member
    *
    * Steps:
    * 1. member login
    * 2. insert incorrect data
    * 3. verify: Complaint was not sent
    *
    *
     */
    public void ComplaintWasNotSentWithInCorrectDataByMember_UserIsLogin_ComplaintWasNotSent(){
        // TODO: Setup system to initial state

        // TODO: user login

        // TODO: Simulate Member clicks Complaint mentor button

        // TODO: Insert invalid data

        // TODO: verify : Query system so complaint was not sent
    }

    @Test
    @Ignore
    /*
    *Test purpose: complaint wasn't sent with guest
    *
    * Steps:
    * 1. click complaint mentor by guest
    * 2. verify: Complaint was not sent
    *
    *
     */
    public void ComplaintWasNotSentWithInCorrectDataByMember_UserIsLogin_ComplaintWasNotSent(){
        // TODO: Setup system to initial state

        // TODO: Simulate guest clicks Complaint mentor button

        // TODO: verify : Query system so complaint was not sent
    }
}
