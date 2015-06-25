package org.bgu.stories.usersStories;

import org.bgu.NewThreadCreatedTestBase;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Sharon Kerzman on 24/04/2015.
 */
public class EditMessage extends NewThreadCreatedTestBase {
    public static final String EDITED_TITLE = "it's probably poor";
    public static final String EDITED_BODY = "it's fine you can drink it";
    int memberCommentId;

    @Test
    public void EditMessage_TitleAndMsgExsits_pass_testID_8_1() {
        assertTrue("can not Login member", bridge.login(FORUM_NAME, MEMBER_NAME, MEMBER_PASS));
        assertTrue("member can not edit message", bridge.editMessage(SUBFORUM_NAME, newThreadId, EDITED_TITLE, EDITED_BODY));
    }

    @Test
    public void EditMessage_TitleMissing_pass_testID_8_2() {
        assertTrue("can not Login member", bridge.login(FORUM_NAME, MEMBER_NAME, MEMBER_PASS));
        assertTrue("member can not edit message without title", bridge.editMessage(SUBFORUM_NAME, newThreadId, "", EDITED_BODY));
    }

    @Test
    public void EditMessage_MsgMissing_pass_testID_8_3() {
        assertTrue("can not Login member", bridge.login(FORUM_NAME, MEMBER_NAME, MEMBER_PASS));
        assertTrue("member can not edit message without body", bridge.editMessage(SUBFORUM_NAME, newThreadId, EDITED_TITLE, ""));
    }

    @Test
    public void EditMessage_bothMissing_fail_testID_8_4() {
        assertTrue("can not Login member", bridge.login(FORUM_NAME, MEMBER_NAME, MEMBER_PASS));
        assertFalse("member can edit message without body and title", bridge.editMessage(SUBFORUM_NAME, newThreadId, "", ""));
    }

    @Test
    public void EditMessage_byother_fail_testID_8_5() {
        assertTrue("can not Login member", bridge.login(FORUM_NAME, MEMBER_2_NAME, MEMBER_2_PASS));
        assertFalse("member can edit message without body and title", bridge.editMessage(SUBFORUM_NAME, newThreadId, "", ""));
    }
    @Test
    @Ignore("guest action")
    public void EditMessage_byGuest_fail_testID_8_6() {
        assertFalse("guest can  edit message", bridge.editMessage(SUBFORUM_NAME, newThreadId, EDITED_TITLE, EDITED_BODY));
    }

    /*
    1. owner member comment
    2. another member (member 2) comment
    3. admin comment to owner member
    4. manager comment to member 2
    5. admin edit member
    6. manager edit other
     */

    @Test
    public void EditMessage_ManyComments_pass_testID_8_7() {
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
