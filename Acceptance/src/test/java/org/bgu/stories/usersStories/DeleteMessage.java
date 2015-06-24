package org.bgu.stories.usersStories;

import org.bgu.MemberResgiteredToForumTestBase;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by Sharon Kerzman on 24/04/2015.
 */
public class DeleteMessage extends MemberResgiteredToForumTestBase {
    @Test
    public void deleteMessage_ByOwnerMember_pass() {

    }

    @Test
    public void deleteMessage_ByAdmin_pass() {

    }

    @Test
    public void deleteMessage_ByManager_pass() {

    }

    @Test
    public void deleteMessage_ByAnotherManager_fail(){

    }

    @Test
    public void deleteMessage_ByGuest_fail(){

    }

    @Test
    public void deleteFirstAndSecondMessages_ByOwnerUser_pass(){

    }

    @Test
    public void deleteSecondAndFirstMessage_ByOwnerUser_fail(){

    }

    @Test
    @Ignore
    /*
    *Test purpose: can not Delete message by not owner member
    *
    * Steps:
    * 1. delete message by not owner member
    * 2. verify: message is not deleted
    *
    *
     */
    public void CanNotDeleteMessageByNotOwnerMember_UserIsSignedUp_MessageIsNotDeleted() {
        // TODO: Setup system to initial state

        // TODO: Forum snd subFOrum is creTED

        // TODO: UserIsSignedIn

        // TODO: write message

        // TODO: loging with another member

        // TODO: Simulate  clicks delete message button

        // TODO: verify : Query system so Message is not deleted
    }
}
