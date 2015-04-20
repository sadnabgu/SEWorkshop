package org.bgu.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by gur on 20/04/2015.
 */
public class SubForum {
    Collection<ForumThread> threads;
    Collection<Member> moderates;

    public SubForum(){
        this.moderates = new ArrayList<>();
        this.threads = new ArrayList<>();
    }

    public Collection<ForumThread> getThreads(){
        return this.threads;
    }

    public Collection<Member> getModerates(){
        return moderates;
    }
}
