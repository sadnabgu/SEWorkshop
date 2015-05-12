package org.bgu.domain.facades;

import org.bgu.domain.model.Forum;
import org.bgu.domain.model.Message;
import org.bgu.domain.model.SubForum;
import org.bgu.domain.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by gur on 20/04/2015.
 */

public class ForumFacade {
    /** collection of all the Forums in the system */
    private static Collection<Forum> _forums = new ArrayList<>();

    public static boolean createForum(String forumName, String forumAdminName, String forumAdminPass) {
        // check that the forum didn't exist already
        if (getForum(forumName) != null){
            return false;
        }
        //TODO - check valid forum data
        Forum forum = new Forum(forumName, forumAdminName, forumAdminPass);

        _forums.add(forum);
        return true;
    }

    public static Forum getForum(String forumName) {
        for (Iterator<Forum> iterator = _forums.iterator(); iterator.hasNext(); ) {
            Forum next =  iterator.next();
            return next;
        }
        return null;
    }

    public static boolean addNewSubForum(Forum forum, String subForumName, String moderartorName) {
        return forum.addNewSubForum(subForumName, moderartorName);
    }

    public static boolean addNewThread(Forum forum, String threadName, String msgBody, String subForumName, int msgId, String threadOwnerName) {
        return forum.addNewThread(threadName, msgBody, subForumName, msgId, threadOwnerName);
    }

    public static boolean addNewComment(Forum forum, int msgId, String msgTitle, String msgBody, String userName) {
        return forum.addNewComment(msgId, msgTitle, msgBody, userName);
    }

    public static boolean removeMsg(Forum forum, int msgId, String userName) {
        //TODO - forum's policy msg remove permistions...
        return forum.removeMessage(msgId);
    }
















    /**
     * clear all the forums database
     * used only for the testing
     */
    public static void resetForums() {
        forums.clear();
    }



}
