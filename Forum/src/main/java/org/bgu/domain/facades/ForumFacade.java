package org.bgu.domain.facades;

import org.bgu.domain.model.Forum;
import org.bgu.domain.model.Member;
import org.bgu.domain.model.SubForum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by gur on 20/04/2015.
 */
public class ForumFacade {
    //TODO - change to ORM
    private static Collection<Forum> forums = new ArrayList<>();

    //TODO - build a cache memory between pair<forumName, userName> to User Object. Now each forum has its own "cache" memory of logged in users

    private static int forumIdGenerator = 1;

    /**** FORUMS MANAGEMENT ****/

    public static boolean createForum(String forumName, String managerName, String managerPass) {
        // check that the forum didn't exist already
        for (Iterator<Forum> iterator = forums.iterator(); iterator.hasNext(); ) {
            Forum next =  iterator.next();
            if (next.getForumName().equals(forumName)){
                return false;
            }
        }

        int forumId = generateForumId();
        Member manager = new Member(managerName, managerPass);
        Forum forum = new Forum(forumId, forumName, manager);

        forums.add(forum);
        return true;
    }

    private static int generateForumId() {
        return forumIdGenerator++;
    }

    public static int createSubForum(String forumName, String subForumName, Collection<String> moderators){
        Forum forum = getForum(forumName);
        if (null == forum)
            return -1;
        return forum.addNewSubForum(subForumName, moderators);
    }

    public static int addNewThread(String forumName, String subForumName, String userName, String threadTitle, String threadBody) {
        Forum forum = getForum(forumName);
        if (null == forum)
            return -1;
        Member creator = forum.getMemberByName(userName);
        if (null == creator)
            return -1;
        return forum.addNewThread(subForumName, creator, threadTitle, threadBody);
    }

    public static int postNewComment(String forumName, String subForumName, String userName, int msgId, String commentTitle, String commentBody) {
        Forum forum = getForum(forumName);
        if (null == forum)
            return -1;
        Member creator = forum.getMemberByName(userName);
        if (null == creator)
            return -1;
        return forum.postNewComment(subForumName, creator, msgId, commentTitle, commentBody);
    }

    public static boolean removeForum(String forumName) {
        Forum forum = getForum(forumName);
        if(forums.contains(forum)){
            forums.remove(forum);
            return true;
        }
        return false;
    }

    public static boolean removeSubForum(String forumName, String subForumName) {
        Forum forum = getForum(forumName);
        if (null == forum)
            return false;
        return (forum.removeSubForum(subForumName));
    }

    public static boolean editMessage(String forumName, String subForumName, String userName, int msgId, String edittedTitle, String edittedBody) {
        Forum forum = getForum(forumName);
        if (null == forum)
            return false;
        Member editor = forum.getMemberByName(userName);
        if (null == editor)
            return false;
        return forum.editMessage(subForumName, editor, msgId, edittedTitle, edittedBody);
    }

    public static boolean removeMesage(String forumName, String subForumName, String userName, int msgId) {
        Forum forum = getForum(forumName);
        if (null == forum)
            return false;
        Member remover = forum.getMemberByName(userName);
        if (null == remover)
            return false;
        return forum.removeMessage(subForumName, remover, msgId);
    }

    public static SubForum getSubForum(String forumName, String subForumName) {
        Forum forum = getForum(forumName);
        return forum.getSubForumByName(subForumName);
    }

    public static Forum getForum(String forumName) {
        for (Iterator<Forum> iterator = forums.iterator(); iterator.hasNext(); ) {
            Forum next =  iterator.next();
            if (next.getForumName().equals(forumName))
                return next;
        }
        return null;
    }

    public static ArrayList<String> getAllForums(){
        ArrayList<String> forumNames = new ArrayList<String>();
        for (Forum forum : forums){
            forumNames.add(forum.getForumName());
        }
        return forumNames;
    }

    public static ArrayList<String> getAllSubForums(String forumName){
        Forum forum = getForum(forumName);
        if (null == forum)
            return null;
        ArrayList<String> subForumNames = forum.getAllSubForums();
        return subForumNames;
    }

    /**** FORUMS MANAGEMENT ****/

   /**********************************************************************************************************/
    /*****************FOR TESTING*********************************************************************************/

    /**
     * clear all the forums database
     * used only for the testing
     */
    public static void resetForums() {
        forums.clear();
    }

    public static void resetForum(String forumName){
        Forum forum = getForum(forumName);
        forum.resetSubForums();
    }
}

