package org.bgu;

import org.junit.Before;

import static org.junit.Assert.assertTrue;

/**
 * Created by Sharon Kerzman on 24/06/2015.
 */
public class NewThreadCreatedTestBase extends SubForumCreatedTestBase {

    public static final String COMMENT_TITLE = "OUT OF DATE MAN! OUD!!!";
    public static final String COMMENT_BODY = "do not touch it and give it to the next homeless ASAP!";
    protected int newThreadId;

    @Before
    public void init() {
        super.init();
        assertTrue("can not Login member", bridge.login(FORUM_NAME, MEMBER_NAME, MEMBER_PASS));
        newThreadId = bridge.createNewThread(THREAD_TITLE, THREAD_BODY);
        assertTrue("can not create new thread", newThreadId > 0);
        assertTrue("can not logout member", bridge.logout());
        assertTrue("can not register second member", bridge.register(FORUM_NAME, MEMBER_2_NAME, MEMBER_2_PASS));
    }
}
