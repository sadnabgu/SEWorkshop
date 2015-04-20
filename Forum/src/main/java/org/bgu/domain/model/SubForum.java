package org.bgu.domain.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by gur on 20/04/2015.
 */
public class SubForum {
    Collection<ForumThread> threads;
    Collection<Member> moderates;   // TODO - way need this in sub-forum?

    public SubForum(){
        this.moderates = new ArrayList<Member>();
        this.threads = new ArrayList<ForumThread>();
    }

    public Collection<ForumThread> getThreads(){
        return this.threads;
    }

    public Collection<Member> getModerates(){
        return moderates;
    }
}
