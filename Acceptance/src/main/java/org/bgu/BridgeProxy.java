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
    public boolean createNewThread() {
        return true;
    }

    @Override
    public boolean createNewComment() {
        return true;
    }

    @Override
    public boolean editMessage() {
        return true;
    }

    @Override
    public boolean deleteMessage() {
        return true;
    }

    @Override
    public boolean createNewForum(String forumName, String managerName, String managerPass) {
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
    public boolean deleteSubForum() {
        return true;
    }

    @Override
    public boolean addModerate() {
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