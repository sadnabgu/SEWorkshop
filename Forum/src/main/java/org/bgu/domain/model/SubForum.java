package org.bgu.domain.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by gur on 20/04/2015.
 */
public class SubForum {
    Collection<ForumThread> threads;
    Collection<Member> moderates;   // TODO - way need this in sub-forum?
    String name;

    public SubForum(String subForumName){
        this.name = subForumName;
        this.moderates = new ArrayList<Member>();
        this.threads = new ArrayList<ForumThread>();
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

    public String getName() {
        return name;
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
