package org.bgu.service;

import org.bgu.domain.model.Forum;
import org.bgu.service.Exceptions.Result;
import org.bgu.service.Exceptions.RetObj;

import java.util.Collection;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

/**
 * Created by hodai on 6/20/15.
 */
public abstract class abstractTest {
    public static final String ADMIN1_NAME = "admin1";
    public static final String ADMIN1_PASS = "pass1";

    public static final String MANAGER1_NAME = "manager1";
    public static final String MANAGER1_PASS = "pass1";
    public static final String MANAGER2_NAME = "manager2";
    public static final String MANAGER2_PASS = "pass2";
    public static final String MEMBER_NAME = "member";
    public static final String MEMEBER_PASS = "memberpass";
    public static final String GOLD_NAME = "kerzman";
    public static final String GOLD_PASS = "kerzman";

    public static final String FORUM_NAME = "sex";
    public static UUID memberSid;
    public static UUID managerSid;
    public static UUID moderateSid;
    public static UUID superAdminSid;
    public static UUID goldMemberSid;


    /** utills */
    protected static UUID login(String forum, String userName, String Password){
        RetObj<UUID> retObj = UserService.logInGuest(forum);
        assertEquals(Result.SUCCESS, retObj._result);
        RetObj<Void> retObj2 = UserService.logInMember(retObj._value, userName, Password);
        assertEquals(Result.SUCCESS, retObj2._result);
        return retObj._value;
    }

    protected static UUID loginSystem(String name, String password){
        RetObj<UUID> retObj = AdminService.loginSystem(name, password);
        assertEquals(Result.SUCCESS, retObj._result);
        return retObj._value;
    }


    protected static void loginAdmin(){
        managerSid = login(FORUM_NAME, MANAGER1_NAME, MANAGER1_PASS);
    }
    protected static void loginGoldMember(){
        goldMemberSid = login(FORUM_NAME, GOLD_NAME, GOLD_PASS);
    }
    protected static void loginMember(){
        memberSid = login(FORUM_NAME, "melki", "melki");
    }
    protected static void loginModerate(){
        moderateSid = login(FORUM_NAME, "hodai", "hodai");
    }
    protected static void loginSupperAdmin(){
        superAdminSid = loginSystem(ADMIN1_NAME, ADMIN1_PASS);
    }
}
