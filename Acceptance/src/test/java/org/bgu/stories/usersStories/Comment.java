package org.bgu.stories.usersStories;

import org.bgu.NewThreadCreatedTestBase;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Sharon Kerzman on 24/04/2015.
 */
public class Comment extends NewThreadCreatedTestBase {
    int memberCommentId;

    @Test
    public void WriteCommentMessage_TitleAndMsgExsits_pass_testID_7_1() {
        assertTrue("can not Login member", bridge.login(FORUM_NAME, MEMBER_NAME, MEMBER_PASS));
        memberCommentId = bridge.createNewComment(SUBFORUM_NAME, newThreadId, COMMENT_TITLE, COMMENT_BODY);
        assertTrue("member can not add new comment", memberCommentId > 0);
    }

    @Test
    public void WriteCommentMessage_TitleMissing_pass_testID_7_2() {
        assertTrue("can not Login member", bridge.login(FORUM_NAME, MEMBER_NAME, MEMBER_PASS));
        memberCommentId = bridge.createNewComment(SUBFORUM_NAME, newThreadId, "", COMMENT_BODY);
        assertTrue("member can not add new comment without title", memberCommentId > 0);
    }

    @Test
    public void WriteCommentMessage_MsgMissing_pass_testID_7_3() {
        assertTrue("can not Login member", bridge.login(FORUM_NAME, MEMBER_NAME, MEMBER_PASS));
        memberCommentId = bridge.createNewComment(SUBFORUM_NAME, newThreadId, COMMENT_TITLE, "");
        assertTrue("member can not add new comment without body", memberCommentId > 0);
    }

    @Test
    public void WriteCommentMessage_bothMissing_fail_testID_7_4() {
        assertTrue("can not Login member", bridge.login(FORUM_NAME, MEMBER_NAME, MEMBER_PASS));
        memberCommentId = bridge.createNewComment(SUBFORUM_NAME, newThreadId, "", "");
        assertFalse("member can add new comment with no title and body", memberCommentId > 0);
    }

    @Test
    public void WriteCommentMessage_byGuest_fail_testID_7_5() {
        memberCommentId = bridge.createNewComment(SUBFORUM_NAME, newThreadId, COMMENT_TITLE, COMMENT_BODY);
        assertFalse("guest can add comments threads", memberCommentId <= 0);
    }

    /*
    1. owner member comment
    2. another member (member 2) comment
    3. admin comment to owner member
    4. manager comment to member 2
     */
    @Test
    public void WriteCommentMessage_ManyComments_pass_testID_7_6() {
        int otherMemberComment;
        int adminComment;
        int managerComment;

        assertTrue("can not Login member", bridge.login(FORUM_NAME, MEMBER_NAME, MEMBER_PASS));
        memberCommentId = bridge.createNewComment(SUBFORUM_NAME, newThreadId, COMMENT_TITLE, COMMENT_BODY);
        assertTrue("member can not add new comment", memberCommentId > 0);
        assertTrue("can not Logout member", bridge.logout());

        assertTrue("can not Login member", bridge.login(FORUM_NAME, MEMBER_2_NAME, MEMBER_2_PASS));
        otherMemberComment = bridge.createNewComment(SUBFORUM_NAME, newThreadId, COMMENT_TITLE, COMMENT_BODY);
        assertTrue("member can not add new comment", otherMemberComment > 0);
        assertTrue("can not Logout member", bridge.logout());

        assertTrue("can not Login member", bridge.login(FORUM_NAME, MEMBER_NAME, MEMBER_PASS));
        managerComment = bridge.createNewComment(SUBFORUM_NAME, otherMemberComment, COMMENT_TITLE, COMMENT_BODY);
        assertTrue("member can not add new comment", managerComment > 0);
        assertTrue("can not Logout member", bridge.logout());
    }

}
