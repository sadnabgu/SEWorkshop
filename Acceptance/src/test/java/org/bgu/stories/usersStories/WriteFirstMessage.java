package org.bgu.stories.usersStories;

import org.bgu.*;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by Sharon Kerzman on 24/04/2015.
 */
public class WriteFirstMessage extends TestBase {

    @Test
    @Ignore
    /*
    *Test purpose: first message is not published after sending incorrect data by member user
    *
    * Steps:
    * 1. insert incorrect data
    * 2. verify: message is not published
    *
    *
     */
    public void WriteFirstMessageWithiInvalidDataByMember_UserIsSignedUp_MessageIsNotPublished(){
        // TODO: Setup system to initial state

        // TODO: Forum snd subFOrum is creTED

        // TODO: UserIsSignedIn

        // TODO: Simulate Member clicks write message button

        // TODO: Insert invalid data

        // TODO: verify : Query system so Message is not published
    }
    @Test
    /*
    *Test purpose: first message is published after sending correct data by member user
    *
    * Steps:
    * 1. insert correct data
    * 2. verify: message is published
    *
    *
     */
    public void WriteFirstMessageWithValidDataByMember_UserIsSignedUp_MessageIsPublished(){


    }


    @Test
    @Ignore
    /*
    *Test purpose: first message is not published after sending incorrect data by member user
    *
    * Steps:
    * 1. insert incorrect data
    * 2. verify: message is not published
    *
    *
     */
    public void ClickFirstMessageByGuest_SubForumCreated_MessageIsNotPublished(){
        // TODO: Setup system to initial state

        // TODO: Forum snd subFOrum is creTED

        // TODO: Simulate guest clicks write message button

        // TODO: verify : Query system so Message is not published
    }
}
