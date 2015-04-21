package org.bgu.domain.facades;

import org.bgu.domain.model.Forum;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by gur on 20/04/2015.
 */
public class ForumFacade {
    /** collection of all the Forums in the system */
    private static Collection<Forum> forums = new ArrayList<>();

    public static Forum createForum(int forumId, String forumName) {
        // check that the forum didn't exist already
        for (Iterator<Forum> iterator = forums.iterator(); iterator.hasNext(); ) {
            Forum next =  iterator.next();
            if (next.getId() == forumId){
                System.err.println("forum with id=" + forumId + " already exist");
                return null;
            }
        }

        Forum forum = new Forum(forumId, forumName);

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
}
