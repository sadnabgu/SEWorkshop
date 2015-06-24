package org.bgu;

import org.bgu.service.AdminService;
import org.bgu.service.ServiceObjects.Result;
import org.bgu.service.ServiceObjects.RetObj;

import java.io.*;
import java.util.Collection;
import java.util.Scanner;

/**
 * Created by Sharon Kerzman on 24/06/2015.
 */
public class BridgeReal implements BridgeAPI {
        RetObj ans;


    @Override
    public boolean initSystem(String adminName, String adminPass) {
        ans = AdminService.initializeSystem(adminName, adminPass);
        if (ans._result== Result.SUCCESS){
            return true;
        }
        return false;
    }

    @Override
    public boolean createNewForum(String forumName) {
        return false;
    }

    @Override
    public boolean deleteForum(String forumName) {
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
