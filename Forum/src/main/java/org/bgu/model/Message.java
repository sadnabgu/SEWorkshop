package org.bgu.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by gur on 20/04/2015.
 */
public class Message {
    String title;
    String body;
    Collection<Message> comments;

    public Message (String title){
        this.title = title;
        this.comments = new ArrayList<>();
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
