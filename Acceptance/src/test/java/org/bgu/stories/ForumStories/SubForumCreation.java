package org.bgu.stories.ForumStories;

import org.bgu.MemberResgiteredToForumTestBase;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Sharon Kerzman on 24/04/2015.
 */
public class SubForumCreation extends MemberResgiteredToForumTestBase {

    @Test
    public void createNewSubForum_byAdmin_pass_testID_5_1() {
        assertTrue("could not log out admin", bridge.login(FORUM_NAME, ADMIN1_NAME,ADMIN1_PASS));
        assertTrue("could not create sub forum", bridge.createSubForum(SUBFORUM_NAME, moderates));
    }

    @Test
    public void createNewSubForum_byManager_pass_testID_5_2() {
        assertTrue("could not log in manager", bridge.login(FORUM_NAME, MANAGER_NAME, MANAGER_PASS));
        assertTrue("could not create sub forum", bridge.createSubForum(SUBFORUM_NAME, moderates));
    }

    @Test
    public void createNewSubForum_byAdminWithNoModerates_fail_testID_5_3() {
        moderates.remove(MODERATE_NAME);
        assertTrue("could not log out admin", bridge.login(FORUM_NAME, ADMIN1_NAME, ADMIN1_PASS));
        assertFalse("able to create sub forum with no moderate", bridge.createSubForum(SUBFORUM_NAME, moderates));
    }

    @Test
    public void createNewSubForum_byManagerWithNoModeates_fail_testID_5_4() {
        moderates.remove(MODERATE_NAME);
        assertTrue("could not log in manager", bridge.login(FORUM_NAME, MANAGER_NAME, MANAGER_PASS));
        assertFalse("able to create sub forum with no moderate", bridge.createSubForum(SUBFORUM_NAME, moderates));
    }

    @Test
    public void createNewSubForum_byMember_fail_testID_5_5() {
        assertTrue("could not log in manager", bridge.login(FORUM_NAME, MEMBER_NAME, MEMBER_PASS));
        assertFalse("member is able to create sub forum", bridge.createSubForum(SUBFORUM_NAME, moderates));
    }

    @Test
    public void createNewSubForum_byGuest_pass_testID_5_6() {
        assertFalse("guest is able to create sub forum", bridge.createSubForum(SUBFORUM_NAME, moderates));
    }

    @Test
    public void createNewSubForum_WithExsitingName_pass_testID_5_7() {
        assertTrue("could not log out admin", bridge.login(FORUM_NAME, ADMIN1_NAME,ADMIN1_PASS));
        assertTrue("could not create sub forum", bridge.createSubForum(SUBFORUM_NAME, moderates));
        assertFalse("able to create sub forum with existing name", bridge.createSubForum(SUBFORUM_NAME, moderates));
    }
}
