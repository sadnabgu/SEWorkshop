package org.bgu.stories.ForumStories;

import org.bgu.SubForumCreatedTestBase;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Sharon Kerzman on 24/06/2015.
 */
public class AddModerate extends SubForumCreatedTestBase {

    @Test
    public void addModerate_byManager_pass_testID_12_1() {
        assertTrue("can not Login manager", bridge.login(FORUM_NAME, MANAGER_NAME, MANAGER_PASS));
        assertTrue("can not add moderate", bridge.addModerate(FORUM_NAME, SUBFORUM_NAME, MODERATE_2_NAME));
    }

    @Test
    public void addModerate_byAdmin_pass_testID_12_2() {
        assertTrue("can not Login admin", bridge.login(FORUM_NAME, ADMIN1_NAME, ADMIN1_PASS));
        assertTrue("can not add moderate", bridge.addModerate(FORUM_NAME, SUBFORUM_NAME, MODERATE_2_NAME));
    }

    @Test
    public void addModerate_alreadyModerate_fail_testID_12_3() {
        assertTrue("can not Login admin", bridge.login(FORUM_NAME, MANAGER_NAME, MANAGER_PASS));
        assertFalse("can add existing moderate", bridge.addModerate(FORUM_NAME, SUBFORUM_NAME, MODERATE_NAME));
    }

    @Test
    public void addModerate_byMember_fail_testID_12_4() {
        assertTrue("can not Login admin", bridge.login(FORUM_NAME, MEMBER_NAME, MEMBER_PASS));
        assertFalse("member can add moderate", bridge.addModerate(FORUM_NAME, SUBFORUM_NAME, MODERATE_2_NAME));
    }

    @Test
    public void addModerate_byGuest_fail_testID_12_5() {
        assertFalse("guest can add moderate", bridge.addModerate(FORUM_NAME, SUBFORUM_NAME, MODERATE_2_NAME));
    }
}
