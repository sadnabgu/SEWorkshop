package org.bgu.domain.model;

/**
 * Created by gur on 20/04/2015.
 */
public class ForumThread {
    Message openingMessage;
    int threadId;

    public ForumThread(int threadId, Message openingMessage){
        this.openingMessage = openingMessage;
        this.threadId = threadId;
    }

    public Message getOpeningMessage(){
        return openingMessage;
    }
}
