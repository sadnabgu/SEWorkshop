package org.bgu.stories.ForumStories;

import org.bgu.SubForumCreatedTestBase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Sharon Kerzman on 24/06/2015.
 */
public class RemoveModerate extends SubForumCreatedTestBase {

    @Before
    public void init(){
        super.init();
        assertTrue("can not Login manager", bridge.login(MANAGER_NAME, MANAGER_PASS));
        assertTrue("can not add moderate", bridge.addModerate(FORUM_NAME, SUBFORUM_NAME, MODERATE_2_NAME));
        assertTrue("can not Login manager", bridge.logout());

    }
    @Test
    public void removeModerate_byManager_pass_testID_13_1() {
        assertTrue("can not Login manager", bridge.login(MANAGER_NAME, MANAGER_PASS));
        assertTrue("manager can not remove moderate", bridge.removeModerate(FORUM_NAME, SUBFORUM_NAME, MODERATE_2_NAME));
    }

    @Test
    public void removeModerate_byAdmin_pass_testID_13_2() {
        assertTrue("can not Login admin", bridge.login(ADMIN1_NAME, ADMIN1_PASS));
        assertTrue("admin can not remove moderate", bridge.removeModerate(FORUM_NAME, SUBFORUM_NAME, MODERATE_2_NAME));
    }

    @Test
    public void removeModerate_byMember_fail_testID_13_3() {
        assertTrue("can not Login admin", bridge.login(MEMBER_NAME, MEMBER_PASS));
        assertFalse("member can remove moderate", bridge.removeModerate(FORUM_NAME, SUBFORUM_NAME, MODERATE_2_NAME));
    }

    @Test
    public void removeModerate_byGuest_fail_testID_13_4() {
        assertFalse("guest can remove moderate", bridge.removeModerate(FORUM_NAME, SUBFORUM_NAME, MODERATE_2_NAME));
    }

    @Test
    public void removeModerate_notModerate_fail_testID_13_5() {
        assertTrue("can not Login admin", bridge.login(MANAGER_NAME, MANAGER_PASS));
        assertFalse("not moderate was removed", bridge.removeModerate(FORUM_NAME, SUBFORUM_NAME, MEMBER_NAME));
    }

    @Test
    public void removeModerate_deleteAll_fail_testID_13_6() {
        assertTrue("can not Login admin", bridge.login(MANAGER_NAME, MANAGER_PASS));
        assertTrue("manager can not remove moderate", bridge.removeModerate(FORUM_NAME, SUBFORUM_NAME, MODERATE_2_NAME));
        assertFalse("can add remove all moderates", bridge.removeModerate(FORUM_NAME, SUBFORUM_NAME, MODERATE_NAME));

    }
}
