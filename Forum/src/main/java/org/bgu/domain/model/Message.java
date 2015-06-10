package org.bgu.domain.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by gur on 20/04/2015.
 */
public class Message {
    String title;
    String body;
    Collection<Message> comments;
    User creator;

    public Message (User creator, String title,String body){
        this.creator = creator;
        this.title = title;
        this.body = body;
        this.comments = new ArrayList<Message>();
    }

    public String getTitle() {
        return title;
    }

    public String getBody(){
        return body;
    }

    public Collection<Message> getComments(){
        return comments;
    }

}
