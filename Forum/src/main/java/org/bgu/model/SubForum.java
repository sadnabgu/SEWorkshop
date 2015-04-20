package org.bgu.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by gur on 20/04/2015.
 */
public class SubForum {
    Collection<ForumThread> threads;

    public SubForum(){
        this.threads = new ArrayList<>();
    }

    public Collection<ForumThread> getThreads(){
        return this.threads;
    }
}
