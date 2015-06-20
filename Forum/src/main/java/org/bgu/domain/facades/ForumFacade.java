package org.bgu.domain.facades;

import org.bgu.domain.model.Forum;
import org.bgu.domain.model.Member;
import org.bgu.domain.model.Session;
import org.bgu.domain.model.SubForum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;

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

    public static int createSubForum(UUID sId, String subForumName, Collection<String> moderators){
        Session session = UserFacade.getSession(sId);
        if (null == session)
            return -1;
        return session._forum.addNewSubForum(subForumName, moderators);
    }

    public static int addNewThread(UUID sId, String subForumName, String threadTitle, String threadBody) {
        Session session = UserFacade.getSession(sId);
        if (null == session)
            return -1;
        return session._forum.addNewThread(subForumName, session._member, threadTitle, threadBody);
    }

    public static int postNewComment(UUID sId, String subForumName, int msgId, String commentTitle, String commentBody) {
        Session session = UserFacade.getSession(sId);
        if (null == session)
            return -1;
        return session._forum.postNewComment(subForumName, session._member, msgId, commentTitle, commentBody);
    }

    public static boolean removeForum(String forumName) {
        Forum forum = getForum(forumName);
        if(forums.contains(forum)){
            forums.remove(forum);
            return true;
        }
        return false;
    }

    public static boolean removeSubForum(UUID sId, String subForumName) {
        Session session = UserFacade.getSession(sId);
        if (null == session)
            return false;
        return (session._forum.removeSubForum(subForumName));
    }

    public static boolean editMessage(UUID sId, String subForumName, int msgId, String edittedTitle, String edittedBody) {
        Session session = UserFacade.getSession(sId);
        if (null == session)
            return false;
        return session._forum.editMessage(subForumName, session._member, msgId, edittedTitle, edittedBody);
    }

    public static boolean removeMessage(UUID sId, String subForumName, int msgId) {
        Session session = UserFacade.getSession(sId);
        if (null == session)
            return false;
        return session._forum.removeMessage(subForumName, session._member, msgId);
    }

    public static SubForum getSubForum(UUID sId, String subForumName) {
        Session session = UserFacade.getSession(sId);
        if (null == session)
            return null;
        return session._forum.getSubForumByName(subForumName);
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
        ArrayList<String> forumNames = new ArrayList<>();
        for (Forum forum : forums){
            forumNames.add(forum.getForumName());
        }
        return forumNames;
    }

    public static ArrayList<String> getAllSubForums(String forumName){
        Forum forum = getForum(forumName);
        if (null == forum)
            return null;
        return forum.getAllSubForums();
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

