package org.bgu.domain.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by gur on 20/04/2015.
 */
public class Forum {
    Collection<SubForum> subForums;

    public Forum(){
        this.subForums = new ArrayList<SubForum>();
    }

    public Collection<SubForum> getSubForums(){
        return this.subForums;
    }
}
