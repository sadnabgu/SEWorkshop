package org.bgu.model;

/**
 * Created by gur on 20/04/2015.
 */
public class ForumThread {
    Message openingMessage;

    public ForumThread(Message openingMessage){
        this.openingMessage = openingMessage;
    }

    public Message getOpeningMessage(){
        return openingMessage;
    }
}
