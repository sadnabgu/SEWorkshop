package org.bgu.domain.model;

/**
 * Created by hodai on 6/19/15.
 */
public class Session {
    public final int _id;
    public final Member _member;
    public final Forum  _forum;

    // TODO sesion log

    public Session(int id, Member member, Forum forum){
        _id = id;
        _member = member;
        _forum = forum;
    }


    //TODO
    public String getSessionLog(){
        return null;
    }

    //TODO
    public void addLog(){
        //TODO
    }


}
