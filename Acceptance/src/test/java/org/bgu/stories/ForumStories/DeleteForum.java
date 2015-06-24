package org.bgu.stories.ForumStories;

import org.bgu.MemberResgiteredToForumTestBase;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by gur on 21/04/2015.
 *
 */
public class DeleteForum extends MemberResgiteredToForumTestBase {

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
    public void DeleteForum_byAdmin_pass_testID_10_1(){
        assertTrue("could not log out admin", bridge.login(FORUM_NAME, ADMIN1_NAME,ADMIN1_PASS));
        assertTrue("could not delete forum", bridge.deleteForum(FORUM_NAME));
    }
    @Test
    public void DeleteForum_byManager_fail_testID_10_2(){
        assertTrue("could not log out admin", bridge.login(FORUM_NAME, MANAGER_NAME,MANAGER_PASS));
        assertFalse("manager can delete forum", bridge.deleteForum(FORUM_NAME));
    }
    @Test
    public void DeleteForum_byMemebr_fail_testID_10_3(){
        assertTrue("could not log out admin", bridge.login(FORUM_NAME, MEMBER_NAME,MEMBER_PASS));
        assertFalse("member can delete forum", bridge.deleteForum(FORUM_NAME));
    }
    @Test
    public void DeleteForum_byGuest_fail_testID_10_4(){
        assertFalse("guest can delete forum", bridge.deleteForum(FORUM_NAME));
    }
    @Test
    public void DeleteForum_byAdmin_DeleteTwice_pass_testID_10_5(){
        assertTrue("can not log out admin", bridge.login(FORUM_NAME, ADMIN1_NAME,ADMIN1_PASS));
        assertTrue("can not delete forum", bridge.deleteForum(FORUM_NAME));
        assertFalse("can delete forum twice", bridge.deleteForum(FORUM_NAME));
    }

}
