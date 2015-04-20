package org.bgu.domain.model;

/**
 * Created by gur on 20/04/2015.
 */
public class Complaint {
    Member from;
    Member on;
    SubForum subForum;
    String text;

    public Complaint(Member from, Member on, SubForum subForum, String text){
        this.from = from;
        this.on = on;
        this.subForum = subForum;
        this.text = text;
    }

    public Member getFrom(){
        return from;
    }

    public Member getOn(){
        return on;
    }

    public String getText(){
        return text;
    }

    public SubForum getSubForum(){
        return subForum;
    }
}
