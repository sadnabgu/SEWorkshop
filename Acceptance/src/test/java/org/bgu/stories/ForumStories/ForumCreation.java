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
        assertTrue("could not create the forum", bridge.createNewForum(FORUM_NAME, MANAGER_NAME, MANAGER_PASS));
    }

    @Test
    public void createForum_memberAction_fail_testID_2_2() {
        assertTrue("could not create the forum", bridge.createNewForum(FORUM_NAME, MANAGER_NAME, MANAGER_PASS));
        assertFalse("could create forum with exsisting name", bridge.createNewForum(FORUM_NAME, MANAGER_NAME, MANAGER_PASS));
    }
}
