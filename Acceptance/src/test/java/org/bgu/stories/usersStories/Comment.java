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
    public void WriteCommentMessage_TitleAndMsgExsits_pass_testID_6_1() {
        assertTrue("can not Login member", bridge.login(MEMBER_NAME, MEMBER_PASS));
        memberCommentId = bridge.createNewComment(newThreadId, COMMENT_TITLE, COMMENT_BODY);
        assertTrue("member can not add new comment", memberCommentId > 0);
    }

    @Test
    public void WriteCommentMessage_TitleMissing_pass_testID_6_2() {
        assertTrue("can not Login member", bridge.login(MEMBER_NAME, MEMBER_PASS));
        memberCommentId = bridge.createNewComment(newThreadId, "", COMMENT_BODY);
        assertTrue("member can not add new comment without title", memberCommentId > 0);
    }

    @Test
    public void WriteCommentMessage_MsgMissing_pass_testID_6_3() {
        assertTrue("can not Login member", bridge.login(MEMBER_NAME, MEMBER_PASS));
        memberCommentId = bridge.createNewComment(newThreadId, COMMENT_TITLE, "");
        assertTrue("member can not add new comment without body", memberCommentId > 0);
    }

    @Test
    public void WriteCommentMessage_bothMissing_fail_testID_6_4() {
        assertTrue("can not Login member", bridge.login(MEMBER_NAME, MEMBER_PASS));
        memberCommentId = bridge.createNewComment(newThreadId, "", "");
        assertFalse("member can add new comment with no title and body", memberCommentId > 0);
    }

    @Test
    public void WriteCommentMessage_byGuest_fail_testID_6_5() {
        memberCommentId = bridge.createNewComment(newThreadId, COMMENT_TITLE, COMMENT_BODY);
        assertFalse("guest can add comments threads", memberCommentId <= 0);
    }

    /*
    1. owner member comment
    2. another member (member 2) comment
    3. admin comment to owner member
    4. manager comment to member 2
     */
    @Test
    public void WriteCommentMessage_ManyComments_pass_testID_6_1() {
        int otherMemberComment;
        int adminComment;
        int managerComment;

        assertTrue("can not Login member", bridge.login(MEMBER_NAME, MEMBER_PASS));
        memberCommentId = bridge.createNewComment(newThreadId, COMMENT_TITLE, COMMENT_BODY);
        assertTrue("member can not add new comment", memberCommentId > 0);
        assertTrue("can not Logout member", bridge.logout());

        assertTrue("can not Login member", bridge.login(MEMBER_2_NAME, MEMBER_2_PASS));
        otherMemberComment = bridge.createNewComment(newThreadId, COMMENT_TITLE, COMMENT_BODY);
        assertTrue("member can not add new comment", otherMemberComment > 0);
        assertTrue("can not Logout member", bridge.logout());

        assertTrue("can not Login member", bridge.login(ADMIN1_NAME, ADMIN1_PASS));
        adminComment = bridge.createNewComment(memberCommentId, COMMENT_TITLE, COMMENT_BODY);
        assertTrue("member can not add new comment", adminComment > 0);
        assertTrue("can not Logout member", bridge.logout());

        assertTrue("can not Login member", bridge.login(MEMBER_NAME, MEMBER_PASS));
        managerComment = bridge.createNewComment(otherMemberComment, COMMENT_TITLE, COMMENT_BODY);
        assertTrue("member can not add new comment", managerComment > 0);
        assertTrue("can not Logout member", bridge.logout());
    }

}
