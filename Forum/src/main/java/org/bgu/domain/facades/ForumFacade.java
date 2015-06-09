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
    private static int forumIdGenerator = 1;

    public static int createForum(String forumName, String managerName, String managerPass) {
        // check that the forum didn't exist already
        for (Iterator<Forum> iterator = forums.iterator(); iterator.hasNext(); ) {
            Forum next =  iterator.next();
            if (next.getForumName().equals(forumName)){
                return -1;
            }
        }

        int forumId = generateForumId();
        Member manager = new Member(managerName, managerPass);
        Forum forum = new Forum(forumId, forumName, manager);

        forums.add(forum);
        return forum.getForumId();
    }

    private static int generateForumId() {
        return forumIdGenerator++;
    }

    public static int createSubForum(Forum forum, String subForumName, Collection<String> moderators){
        return forum.addNewSubForum(subForumName, moderators);
    }

    public static SubForum getSubForum(Forum forum, String subForumName) {
        return forum.getSubForum(subForumName);
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

    public static ArrayList<String> getAllSubForums(Forum forum){
        ArrayList<String> subForumNames = forum.getAllSubForums();
        return subForumNames;
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

    public static int removeSubForum(Forum forum, String subForumName) {
        return 0;
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

    public static boolean isManager(String forumName, Member member) {
        if(member==null){
            return false;
        }
        return getForum(forumName).isManager(member);
    }


    public static boolean addModerate(String forumName, String subForumName, Member moderate) {
        Forum forum = getForum(forumName);
        return forum.getSubForum(subForumName).addModerate(moderate);
    }

    public static boolean removeModerate(String forumName, String subForumName, Member moderate) {
        Forum forum = getForum(forumName);
        return forum.getSubForum(subForumName).removeModerate(moderate);
    }


}

