package org.bgu;

import org.junit.Before;

import static org.junit.Assert.assertTrue;

/**
 * Created by Sharon Kerzman on 24/06/2015.
 */
public class SubForumCreatedTestBase extends MemberResgiteredToForumTestBase {

    public static final String MESSAGE_TITLE = "why is this so sour?";
    public static final String MESSAGE_BODY = "is it out of date or just poor milk?";

    @Before
    public void init() {
        super.init();
        assertTrue("can not Login manager", bridge.login(MANAGER_NAME, MANAGER_PASS));
        assertTrue("could not register member", bridge.createSubForum(SUBFORUM_NAME, moderates));
        assertTrue("can not logout member", bridge.login(MANAGER_NAME, MANAGER_PASS));
    }
}
