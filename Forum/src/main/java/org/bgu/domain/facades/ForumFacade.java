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
    /** collection of all the Forums in the system */
    private static Collection<Forum> forums = new ArrayList<>();

    public static Forum createForum(String forumName, String managerName, String managerPass) {
        // check that the forum didn't exist already
        for (Iterator<Forum> iterator = forums.iterator(); iterator.hasNext(); ) {
            Forum next =  iterator.next();
            if (next.getName().equals(forumName)){
                return null;
            }
        }

        Member manager = new Member(managerName, managerPass);
        Forum forum = new Forum(forumName, manager);

        forums.add(forum);
        return forum;
    }

    public static SubForum createSubForum(Forum forum, String subForumName, Collection<String> moderators){
        return forum.addNewSubForum(subForumName, moderators);
    }

    public static SubForum getSubForum(Forum forum, String subForumName) {
        return forum.getSubForum(subForumName);
    }

    public static Forum getForum(String forumName) {
        for (Iterator<Forum> iterator = forums.iterator(); iterator.hasNext(); ) {
            Forum next =  iterator.next();
            if (next.getName().equals(forumName))
                return next;
        }
        return null;
    }

    public static boolean addModeratorToSubForum(Forum forum, String subForumName, Member moderator){
        SubForum sForum = forum.getSubForum(subForumName);
        return sForum.addModerate(moderator);
    }


    /**
     * remove the forum
     * used only for the testing
     * @param forum - the forum object
     * @return - false if forum not found
     */
    public static boolean removeForum(Forum forum) {
        if(forums.contains(forum)){
            forums.remove(forum);
            return true;
        }
        return false;
    }

   /**********************************************************************************************************/
    /*****************FOR TESTING*********************************************************************************/

    /**
     * clear all the forums database
     * used only for the testing
     */
    public static void resetForums() {
        forums.clear();
    }

    public static void resetForum(Forum forum){
        forum.resetSubForums();
    }
}

