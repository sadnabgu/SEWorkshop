package org.bgu.stories.usersStories;

import org.bgu.*;
import org.bgu.domain.model.SubForum;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Sharon Kerzman on 24/04/2015.
 */
public class WriteFirstMessage extends SubForumCreatedTestBase {

    @Test
    public void WriteFirstMessage_TitleAndMsgExsits_pass_testID_6_1() {
        assertTrue("can not Login manager", bridge.login(MEMBER_NAME, MEMBER_PASS));
        int firstMessageId = bridge.createNewThread(MESSAGE_TITLE, MESSAGE_BODY);
        assertTrue("member can not add new thread", firstMessageId > 0);
    }

    @Test
    public void WriteFirstMessage_TitleMissing_pass_testID_6_2() {
        assertTrue("can not Login manager", bridge.login(MEMBER_NAME, MEMBER_PASS));
        int firstMessageId = bridge.createNewThread("", MESSAGE_BODY);
        assertTrue("member can not add new thread without title", firstMessageId > 0);
    }

    @Test
    public void WriteFirstMessage_MsgMissing_pass_testID_6_3() {
        assertTrue("can not Login manager", bridge.login(MEMBER_NAME, MEMBER_PASS));
        int firstMessageId = bridge.createNewThread(MESSAGE_TITLE, "");
        assertTrue("member can not add new thread without body", firstMessageId > 0);
    }

    @Test
    public void WriteFirstMessage_bothMissing_fail_testID_6_4() {
        assertTrue("can not Login manager", bridge.login(MEMBER_NAME, MEMBER_PASS));
        int firstMessageId = bridge.createNewThread("", "");
        assertFalse("member can add new thread without title and body", firstMessageId <= 0);
    }

    @Test
    public void WriteFirstMessage_byGuest_fail_testID_6_5() {
        int firstMessageId = bridge.createNewThread(MESSAGE_TITLE, MESSAGE_BODY);
        assertFalse("guest can add new threads", firstMessageId <= 0);
    }

}
