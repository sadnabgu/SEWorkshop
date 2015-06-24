package org.bgu.stories.usersStories;

import org.bgu.NewThreadCreatedTestBase;
import org.bgu.SubForumCreatedTestBase;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by gur on 21/04/2015.
 */
public class DeleteMessage extends NewThreadCreatedTestBase {

    /*
    *Test purpose: Delete Forum succesfuly by admin
    *
    * Steps:
    * 1. delete forum
    * 2. verify: forum is in Waiting state
    *
    *
     */
    @Test
    public void DeleteMessage_byAdmin_pass_testID_9_1() {
        assertTrue("could not log in admin", bridge.login(ADMIN1_NAME, ADMIN1_PASS));
        assertTrue("admin not delete message", bridge.deleteMessage(newThreadId));
    }

    @Test
    public void DeleteMessage_byManager_pass_testID_9_2() {
        assertTrue("could not log in manager", bridge.login(MANAGER_NAME, MANAGER_PASS));
        assertTrue("manager not delete message", bridge.deleteMessage(newThreadId));
    }

    @Test
    public void DeleteMessage_byMember_pass_testID_9_3() {
        assertTrue("could not log in member", bridge.login(MEMBER_NAME, MEMBER_PASS));
        assertTrue("could not delete message", bridge.deleteMessage(newThreadId));
    }

    @Test
    public void DeleteMessage_byOtherMember_fail_testID_9_4() {
        assertTrue("could not log in member", bridge.login(MEMBER_2_NAME, MEMBER_2_PASS));
        assertFalse("other member can delete message", bridge.deleteMessage(newThreadId));
    }

    @Test
    public void DeleteMessage_byGuest_fail_testID_9_5() {
        assertFalse("guest can delete message", bridge.deleteMessage(newThreadId));
    }

    @Test
    public void DeleteMessage_byManager_DeleteSecondAndFirst_pass_testID_9_6() {
        assertTrue("can not Login member", bridge.login(MEMBER_NAME, MEMBER_PASS));
        int memberCommentId = bridge.createNewComment(newThreadId, COMMENT_TITLE, COMMENT_BODY);
        assertTrue("member can not add new comment", memberCommentId > 0);
        assertTrue("can not log out member", bridge.logout());

        assertTrue("can not Login member", bridge.login(MANAGER_NAME, MANAGER_PASS));
        assertTrue("admin not delete message", bridge.deleteMessage(memberCommentId));
        assertTrue("admin not delete message", bridge.deleteMessage(newThreadId));

    }

    @Test
         public void DeleteMessage_byManager_DeleteFirstAndSecond_fail_testID_9_7() {
        assertTrue("can not Login member", bridge.login(MEMBER_NAME, MEMBER_PASS));
        int memberCommentId = bridge.createNewComment(newThreadId, COMMENT_TITLE, COMMENT_BODY);
        assertTrue("member can not add new comment", memberCommentId > 0);
        assertTrue("can not log out member", bridge.logout());

        assertTrue("can not Login member", bridge.login(MANAGER_NAME, MANAGER_PASS));
        assertTrue("admin not delete message", bridge.deleteMessage(newThreadId));
        assertFalse("admin delete deleted message", bridge.deleteMessage(memberCommentId));
    }


}
