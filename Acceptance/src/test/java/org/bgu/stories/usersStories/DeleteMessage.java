package org.bgu.stories.usersStories;

import org.bgu.NewThreadCreatedTestBase;
import org.junit.Ignore;
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
    *
    *
     */

    @Test
    @Ignore("admin action")
    public void DeleteMessage_byAdmin_pass_testID_9_1() {
        assertTrue("could not log in admin", bridge.loginAdmin(ADMIN1_NAME, ADMIN1_PASS));
        assertTrue("admin not delete message", bridge.deleteMessage(SUBFORUM_NAME, newThreadId));
    }


    @Test
    public void DeleteMessage_byMember_pass_testID_9_3() {
        assertTrue("could not log in member", bridge.login(FORUM_NAME, MEMBER_NAME, MEMBER_PASS));
        assertTrue("could not delete message", bridge.deleteMessage(SUBFORUM_NAME, newThreadId));
    }

    @Test
    public void DeleteMessage_byOtherMember_fail_testID_9_4() {
        assertTrue("could not log in member", bridge.login(FORUM_NAME, MEMBER_2_NAME, MEMBER_2_PASS));
        assertFalse("other member can delete message", bridge.deleteMessage(SUBFORUM_NAME, newThreadId));
    }

    @Test
    @Ignore("guest action")
    public void DeleteMessage_byGuest_fail_testID_9_5() {
        assertFalse("guest can delete message", bridge.deleteMessage(SUBFORUM_NAME, newThreadId));
    }

    @Test
    public void DeleteMessage_byManager_DeleteSecondAndFirst_pass_testID_9_6() {
        assertTrue("can not Login member", bridge.login(FORUM_NAME, MEMBER_NAME, MEMBER_PASS));
        int memberCommentId = bridge.createNewComment(SUBFORUM_NAME, newThreadId, COMMENT_TITLE, COMMENT_BODY);
        assertTrue("member can not add new comment", memberCommentId > 0);
        assertTrue("admin not delete message", bridge.deleteMessage(SUBFORUM_NAME, memberCommentId));
        assertTrue("admin not delete message", bridge.deleteMessage(SUBFORUM_NAME, newThreadId));

    }


    @Test
    public void DeleteMessage_byAdmin_Twice_fail_testID_9_8() {
        assertTrue("could not log in admin", bridge.login(FORUM_NAME, MEMBER_NAME, MEMBER_PASS));
        assertTrue("admin not delete message", bridge.deleteMessage(SUBFORUM_NAME, newThreadId));
        assertFalse("admin delete message twice", bridge.deleteMessage(SUBFORUM_NAME, newThreadId));

    }


}
