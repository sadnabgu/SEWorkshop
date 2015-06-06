package org.bgu;

/**
 * Created by Sharon Kerzman on 12/05/2015.
 */
public class MessageDriver {

    SubForumTestDriver subForum;
    String messageData;

    public MessageDriver(SubForumTestDriver subForum, String message) {
        this.messageData = message;
        this.subForum = subForum;


    }
}
