package org.bgu;

import java.util.Collection;

/**
 * Created by Sharon Kerzman on 23/06/2015.
 */
public class BridgeProxy implements BridgeAPI{

    @Override
    public boolean initSystem(String adminName, String adminPass) {
        return true;
    }

    @Override
    public boolean login(String name, String pass) {
        return true;
    }

    @Override
    public boolean logout() {
        return true;
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
        return true;
    }

    @Override
    public boolean deleteMessage(int messageId) {
        return true;
    }

    @Override
    public boolean createNewForum(String forumName) {
        return true;
    }

    @Override
    public boolean deleteForum(String forumName) {
        return true;
    }

    @Override
    public boolean createSubForum(String subforumName, Collection moderates) {
        return true;
    }

    @Override
    public boolean deleteSubForum(String forumName, String subforumName) {
        return true;
    }

    @Override
    public boolean addModerate(String forumName, String subforumName, String moderateName) {
        return true;
    }

    @Override
    public boolean deleteModerate() {
        return true;
    }

    @Override
    public boolean register(String memberName, String memberPass) {
        return true;
    }
}
