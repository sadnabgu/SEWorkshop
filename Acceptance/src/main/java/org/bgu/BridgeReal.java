package org.bgu;

import org.bgu.service.AdminService;
import org.bgu.service.ServiceObjects.Result;
import org.bgu.service.ServiceObjects.RetObj;
import org.bgu.service.UserService;

import java.io.*;
import java.util.Collection;
import java.util.Scanner;
import java.util.UUID;

/**
 * Created by Sharon Kerzman on 24/06/2015.
 */
public class BridgeReal implements BridgeAPI {
        RetObj ans;
        UUID sid;


    @Override
    public boolean initSystem(String adminName, String adminPass) {
        ans = AdminService.initializeSystem(adminName, adminPass);
        return ans._result==Result.SUCCESS;
    }

    @Override
    public boolean createNewForum(String forumName, String managerName, String managerPass) {
        ans = AdminService.createForum(sid, forumName, managerName, managerPass);
        return ans._result==Result.SUCCESS;

    }

    @Override
    public boolean deleteForum(String forumName) {
        return false;
    }

    @Override
    public boolean loginAdmin(String name, String pass) {
        RetObj<UUID> ans = AdminService.loginSystem(name, pass);
        if (ans._result == Result.SUCCESS) {
            sid = ans._value;
            return true;
        }
        return false;
    }
    @Override
    public boolean createSubForum(String subforumName, Collection moderates) {
        return false;
    }

    @Override
    public boolean deleteSubForum(String forumName, String subforumName) {
        return false;
    }

    @Override
    public boolean addModerate(String forumName, String subforumName, String moderateName) {
        return false;
    }

    @Override
    public boolean removeModerate(String forumName, String subforumName, String moderateName) {
        return false;
    }

    @Override
    public boolean register(String memberName, String memberPass) {
        return false;
    }

    @Override
    public boolean login(String name, String pass) {

        return false;
    }

    @Override
    public boolean logout() {
        if(sid == null){
            return false;
        }
        ans = UserService.logOut(sid);
        if (!ans._result.equals(Result.SUCCESS)){
            return false;
        }
       // UserService.logInGuest()
        return false;
    }

    @Override
    public int createNewThread(String messageTitle, String messageBody) {
        return 0;
    }

    @Override
    public int createNewComment(int newThreadId, String commentTitle, String commentBody) {
        return 0;
    }

    @Override
    public boolean editMessage(int messageId, String commentTitle, String commentBody) {
        return false;
    }

    @Override
    public boolean deleteMessage(int messageId) {
        return false;
    }
}
