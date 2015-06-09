package org.bgu.domain.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by gur on 20/04/2015.
 */
public class SubForum {
    private Collection<ForumThread> threads;
    private Collection<Member> moderates;   // TODO - way need this in sub-forum?
    private String name;
    private int subForumId;
    private String subForumName;

    public SubForum(String subForumName, int subForumId){
        this.name = subForumName;
        this.moderates = new ArrayList<Member>();
        this.threads = new ArrayList<ForumThread>();
        this.subForumId = subForumId;
    }

    public Collection<ForumThread> getThreads(){
        return this.threads;
    }

    public Collection<Member> getModerates(){
        return moderates;
    }

    public boolean addModerate(Member moderate) {
        if (moderates.contains(moderate) || null == moderate)
            return false;
        moderates.add(moderate);
        return true;
    }

    public String getSubForumName() {
        return name;
    }

    public int getSubForumId() {
        return subForumId;
    }

    public boolean isModerator(Member moderate){
        return moderates.contains(moderate);
    }

    public boolean removeModerate(Member moderate) {
        if (!(moderates.contains(moderate)) || null == moderate)
            return false;
        moderates.remove(moderate);
        return true;
    }
}
