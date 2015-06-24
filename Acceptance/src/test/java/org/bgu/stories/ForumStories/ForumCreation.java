package org.bgu.stories.ForumStories;


import org.bgu.InitializedTestBase;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by gur on 21/04/2015.
 */
public class ForumCreation extends InitializedTestBase {
    @Test
    public void createForum_pass_testID_2_1() {
        assertTrue("could not create the forum", bridge.createNewForum(FORUM_NAME));
    }

    @Test
    public void createForum_memberAction_fail_testID_2_2() {
        assertTrue("could not log out admin", bridge.logout());
        assertTrue("could not register member", bridge.register(MEMBER_NAME, MEMBER_PASS));
        assertTrue("could not log in member", bridge.login(MEMBER_NAME, MEMBER_PASS));
        assertFalse("member succeed to create Forum", bridge.createNewForum(FORUM_NAME));
    }

    @Test
    public void createForum_guestAction_fail_testID_2_3() {
        assertTrue("could not log out admin", bridge.logout());
        assertFalse("member succeed to create Forum", bridge.createNewForum(FORUM_NAME));
    }
}
