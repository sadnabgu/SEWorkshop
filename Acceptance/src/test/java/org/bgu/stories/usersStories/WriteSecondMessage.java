package org.bgu.stories.usersStories;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by Sharon Kerzman on 24/04/2015.
 */
public class WriteSecondMessage {
    @Test
    @Ignore
    /*
    *Test purpose: second message is published after sending correct data by member user
    *
    * Steps:
    * 1. insert correct data
    * 2. verify: message is published
    *
    *
     */
    public void WriteSecondMessageWithValidDataByMember_FirstMessageCreated_SecondMessageIsPublished(){
        // TODO: Setup system to initial state

        // TODO: Forum snd subFOrum is creTED

        // TODO: UserIsSignedIn

        //TODO: WriteFirstMessage

        // TODO: Simulate guest clicks write message button

        // TODO: Insert valid data

        // TODO: verify : Query system so Message is published
    }

    @Test
    @Ignore
    /*
    *Test purpose: Second message is not published after sending incorrect data by member user
    *
    * Steps:
    * 1. insert incorrect data
    * 2. verify: message is not published
    *
    *
     */
    public void WriteSecondMessageWithiInvalidDataByMember_FirstMessageCreated_MessageIsNotPublished(){
        // TODO: Setup system to initial state

        // TODO: Forum snd subFOrum is creTED

        // TODO: UserIsSignedIn

        // TODO: First message created

        // TODO: Simulate Member clicks write message button

        // TODO: Insert invalid data

        // TODO: verify : Query system so Message is not published
    }


    @Test
    @Ignore
    /*
    *Test purpose: Second message is not published by guest user
    *
    * Steps:
    * 1. verify: message is not published
    *
    *
     */
    public void ClickFirstMessageByGuest_SubForumCreated_MessageIsNotPublished(){
        // TODO: Setup system to initial state

        // TODO: Forum snd subFOrum is creTED

        // TODO: First message created

        // TODO: Simulate guest clicks write message button

        // TODO: verify : Query system so Message is not published
    }

}
