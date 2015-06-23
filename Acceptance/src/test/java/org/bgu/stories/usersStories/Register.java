package org.bgu.stories.usersStories;


import org.bgu.ForumCreatedTestBase;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Sharon Kerzman on 24/04/2015.
 */
public class Register extends ForumCreatedTestBase{


    @Test
    public void Register_UserSignUpWithCorrectData_Pass() {
        assertTrue("can not register member", bridge.register(MEMBER_NAME, MEMBER_PASS));
    }

    @Test
    public void Register_noUserName_fail(){
        assertFalse("can register member without name", bridge.register("", MEMBER_PASS));
    }
    @Test
    public void Register_NoPassword_fail(){
        assertFalse("can register member without name", bridge.register(MEMBER_NAME, ""));
    }

    @Test
    public void Register_UserIsRegistered_fail(){
        assertTrue("can not register member", bridge.register(MEMBER_NAME, MEMBER_PASS));
        assertFalse("can register member twice", bridge.register(MEMBER_NAME, MEMBER_PASS));
    }

    @Test
    public void Register_UserIsSignedIn_fail(){
        assertTrue("can not register member", bridge.register(MEMBER_NAME, MEMBER_PASS));
        assertTrue("can not log in member", bridge.login(MANAGER_NAME,MANAGER_PASS));
        assertFalse("can register when logged in", bridge.register(MEMBER_2_NAME, MEMBER_2_PASS));
    }
}