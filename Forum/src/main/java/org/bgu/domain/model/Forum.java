package org.bgu.domain.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by gur on 20/04/2015.
 */
public class Forum {
    Collection<SubForum> subForums;
    private int id;

    public Forum(int forumId, String forumName){
        this.subForums = new ArrayList<>();
        id = forumId;
    }

    public Collection<SubForum> getSubForums(){
        return this.subForums;
    }

    public boolean addNewSubForum(String subForumName, Member moderate) {
        //TODO - maybe implement in the sub forum level
        SubForum subForum = new SubForum(subForumName);
        if(subForum == null)
            return false;
        subForum.addModerate(moderate);

        return true;
    }

    public boolean addNewThread(String threadName) {
        //TODO - implement in the sub forum level
        return false;
    }

    public int getId() {
        return id;
    }
}