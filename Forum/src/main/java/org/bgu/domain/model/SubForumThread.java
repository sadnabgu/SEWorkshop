package org.bgu.domain.model;

/**
 * Created by gur on 20/04/2015.
 */
public class SubForumThread {
    Message _openingMessage;

    public SubForumThread(Message openingMessage){
        _openingMessage = openingMessage;
    }

    public Message getOpeningMessage(){
        return _openingMessage;
    }

    public void removeThread(){
        //TODO - remove the whole thread and its comments
    }
}
