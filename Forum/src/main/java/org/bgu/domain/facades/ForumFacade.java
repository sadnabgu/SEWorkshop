package org.bgu.domain.facades;

import org.bgu.domain.model.Forum;
import org.bgu.domain.model.Member;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by gur on 20/04/2015.
 */
public class ForumFacade {
    /** collection of all the Forums in the system */
    private static Collection<Forum> forums = new ArrayList<>();

    public static Forum createForum(int forumId, String forumName, String managerName, String managerPass) {
        // check that the forum didn't exist already
        for (Iterator<Forum> iterator = forums.iterator(); iterator.hasNext(); ) {
            Forum next =  iterator.next();
            if (next.getId() == forumId){
                System.err.println("forum with id=" + forumId + " already exist");
                return null;
            }
        }

        Member manager = new Member(managerName, managerPass);
        Forum forum = new Forum(forumId, forumName, manager);

        forums.add(forum);
        return forum;
    }

    public static Forum getForum(int forumID) {
        for (Iterator<Forum> iterator = forums.iterator(); iterator.hasNext(); ) {
            Forum next =  iterator.next();
            if (next.getId() == forumID)
                return next;
        }
        return null;
    }

    /**
     * clear all the forums database
     * used only for the testing
     */
    public static void resetForums() {
        forums.clear();
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
}
