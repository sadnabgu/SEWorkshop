package org.bgu.domain.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by gur on 20/04/2015.
 */
public class Forum {
    private static Collection<SubForum> subForums;
    private int id;
    private String name;
    private Collection<Member> members;
    private Collection<Member> managers;

    public Forum(int forumId, String forumName, Member manager){
        this.subForums = new ArrayList<>();
        members = new ArrayList<>();
        managers = new ArrayList<>();
        managers.add(manager);
        members.add(manager);
        id = forumId;
        name = forumName;
    }

    /*public Collection<SubForum> getSubForums(){
        return this.subForums;
    }*/

    public boolean addNewSubForum(String subForumName, Member moderate) {
        //TODO - maybe implement in the sub forum level
        SubForum subForum = new SubForum(subForumName);
        if(subForum == null)
            return false;
        subForum.addModerate(moderate);

        return true;
    }

    public SubForum getSubForum(String subForumName) {
        for (Iterator<SubForum> iterator = subForums.iterator(); iterator.hasNext(); ) {
            SubForum next =  iterator.next();
            if (next.getName() == subForumName)
                return next;
        }
        return null;
    }

    public boolean addNewThread(String threadName) {
        //TODO - implement in the sub forum level
        return false;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Collection<Member> getMembers() {
        return members;
    }

}