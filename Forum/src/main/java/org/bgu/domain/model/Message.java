package org.bgu.domain.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by gur on 20/04/2015.
 */
public class Message {
    private int _msgId;
    private String _title;
    private String _body;
    private Collection<Message> _comments;
    private User _creator;

    public Message (String title, String msgBody, User creator){
        _title = title;
        _body = msgBody;
        _comments = new ArrayList<Message>();
        _creator = creator;
    }

    public int getMsgId(){
        return _msgId;
    }

    public String gettitle(){
        return _title;
    }

    public String getcBody(){
        return _body;
    }

    public boolean addComment(Message comment){
        _comments.add(comment);
        return true;
    }

















    public String getTitle() {
        return _title;
    }

    public String getBody(){
        return _body;
    }

    public Collection<Message> getComments(){
        return _comments;
    }

    public User getCreator(){
        return _creator;
    }

    public SubForumThread getThread(){
        return _subForumThread;
    }

    public void remove(Message message){
        _comments.clear();
    }



    public void setSubForumThread(SubForumThread subForumThread){
        _subForumThread = subForumThread;
    }
}
