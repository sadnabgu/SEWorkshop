package org.bgu;

import java.io.IOException;
import java.util.Collection;

/**
 * Created by Sharon Kerzman on 24/06/2015.
 */
public class BridgeReal implements BridgeAPI {
    protected Runtime rt;
    protected String[] args;
    protected Process proc;

    public BridgeReal(){
        rt = Runtime.getRuntime();
        args = new String[2]; // creates arguments
    }


    @Override
    public boolean initSystem(String adminName, String adminPass) throws IOException, InterruptedException {
        args[0] = "LoginGuest"; // first is the method to invoke
        args[1] = "sport"; // second and more are real args.. this time is forum name to log in
        proc = rt.exec("path to CommandLineClient.exe", args); // create a process and executes
        proc.waitFor(); // waits till process finishes
        proc.getOutputStream(); // gets the output stream of the process to perform actions

        return true;
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
