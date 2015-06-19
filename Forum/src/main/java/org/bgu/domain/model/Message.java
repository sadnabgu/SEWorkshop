package org.bgu.domain.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by gur on 20/04/2015.
 */
public class Message {
    private int msgId;
    private String title;
    private String body;
    private Collection<Message> comments;
    private User creator;

    public Message (int msgId, User creator, String title,String body){
        this.msgId = msgId;
        this.creator = creator;
        this.title = title;
        this.body = body;
        this.comments = new ArrayList<>();
    }

    public int getMsgId() {
        return msgId;
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

    public Message searchForMsgId(int msgId) {
        for (Message c : comments){
            if (c.getMsgId() == msgId)
                return c;
        }
        return null;
    }

    public void addNewComment(Message newMsg) {
        comments.add(newMsg);
    }

    public void removeAllMsgs() {
        for (Message c : comments) {
            c.removeAllMsgs();
            comments.remove(c);
        }
    }

    public User getCreator() {
        return creator;
    }

    public void removeMessage(Message relevantMsg) {
        comments.remove(relevantMsg);
    }

    public void edit(String edittedTitle, String edittedBody) {
        this.title = edittedTitle;
        this.body = edittedBody;
    }
}
