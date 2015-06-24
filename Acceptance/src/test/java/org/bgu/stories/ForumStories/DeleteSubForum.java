package org.bgu.stories.ForumStories;

import org.bgu.SubForumCreatedTestBase;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by gur on 21/04/2015.
 */
public class DeleteSubForum extends SubForumCreatedTestBase {

    /*
    *Test purpose: Delete Forum succesfuly by admin
    *
    * Steps:
    * 1. delete forum
    * 2. verify: forum is in Waiting state
    *
    *
     */
    @Test
    public void DeleteSubForum_byAdmin_pass_testID_11_1() {
        assertTrue("could not log in admin", bridge.login(FORUM_NAME, ADMIN1_NAME, ADMIN1_PASS));
        assertTrue("could not delete sub forum", bridge.deleteSubForum(FORUM_NAME, SUBFORUM_NAME));
    }

    @Test
    public void DeleteSubForum_byManager_pass_testID_11_2() {
        assertTrue("could not log in manager", bridge.login(FORUM_NAME, MANAGER_NAME, MANAGER_PASS));
        assertTrue("could not delete sub forum", bridge.deleteSubForum(FORUM_NAME, SUBFORUM_NAME));
    }

    @Test
    public void DeleteSubForum_byMember_fail_testID_11_3() {
        assertTrue("could not log in member", bridge.login(FORUM_NAME, MEMBER_NAME, MEMBER_PASS));
        assertFalse("member can delete sub forum", bridge.deleteSubForum(FORUM_NAME, SUBFORUM_NAME));
    }

    @Test
    public void DeleteSubForum_byGuest_fail_testID_11_4() {
        assertFalse("member can delete sub forum", bridge.deleteSubForum(FORUM_NAME, SUBFORUM_NAME));
    }

    @Test
    public void DeleteForum_byManager_DeleteTwice_pass_testID_11_5() {
        assertTrue("can not log in manager", bridge.login(FORUM_NAME, MANAGER_NAME, MANAGER_PASS));
        assertTrue("could not delete sub forum", bridge.deleteSubForum(FORUM_NAME, SUBFORUM_NAME));
        assertFalse("can delete sub forum twice", bridge.deleteSubForum(FORUM_NAME, SUBFORUM_NAME));
    }

}
