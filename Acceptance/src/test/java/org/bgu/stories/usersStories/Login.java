package org.bgu.stories.usersStories;

import org.bgu.MemberResgiteredToForumTestBase;
import org.bgu.service.UserService;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by gur on 21/04/2015.
 */
public class Login extends MemberResgiteredToForumTestBase {
    @Test
    public void Login_CorrectData_Pass_testID_4_1() {
        assertTrue("can not login member", bridge.login(MEMBER_NAME, MEMBER_PASS));
    }

    @Test
    public void Login_DifferentUserName_fail_testID_4_2(){
        assertFalse("can login member with different name", bridge.login(MEMBER_2_NAME , MEMBER_PASS));
    }
    @Test
    public void Login_DifferentPassword_fail_testID_4_3(){
        assertFalse("can login member with different password", bridge.register(MEMBER_NAME, MEMBER_2_PASS));
    }

    @Test
    public void Login_AlreadyLogedIn_fail_testID_4_4(){
        assertTrue("can not login member", bridge.login(MEMBER_NAME, MEMBER_PASS));
        assertFalse("can login member when already logged in", bridge.login(MEMBER_NAME, MEMBER_PASS));
    }

}
