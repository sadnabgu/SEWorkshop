package org.bgu;

import java.util.Collection;

/**
 * Created by Sharon Kerzman on 24/06/2015.
 */
public class BridgeProxy implements BridgeAPI{


    @Override
    public boolean initSystem(String adminName, String adminPass) {
        return false;
    }

    @Override
    public boolean createNewForum(String forumName, String managerName, String managerPass) {
        return false;
    }


    @Override
    public boolean deleteForum(String forumName) {
        return false;
    }

    @Override
    public boolean loginAdmin(String name, String pass) {
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
    public boolean register(String forumName, String memberName, String memberPass) {
        return false;
    }

    @Override
    public boolean login(String forumName, String name, String pass) {
        return false;
    }

    @Override
    public boolean logout() {
        return false;
    }

    @Override
    public int createNewThread(String subforumName, String messageTitle, String messageBody) {
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
