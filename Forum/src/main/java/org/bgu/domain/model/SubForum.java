package org.bgu.domain.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by gur on 20/04/2015.
 */
public class SubForum {
    //TODO - maybe make a collection of type "Messages" instead of "Forum-Threads" and eliminate "Forum-Thread" class
    private int subForumId;
    private Collection<Message> threads;
    private Collection<Member> moderates;
    private String subForumName;
    private int threadIdGenerate;

    public SubForum(String subForumName, int subForumId){
        this.subForumName = subForumName;
        this.subForumId = subForumId;
        this.moderates = new ArrayList<>();
        this.threads = new ArrayList<>();
        this.threadIdGenerate = 1;
    }

    public Collection<Message> getThreads(){
        return this.threads;
    }

    public Collection<Member> getModerates(){
        return moderates;
    }

    private int msgIdGenerator() {return threadIdGenerate++;}

    public boolean addModerator(Member moderator) {
        if (null == moderator || moderates.contains(moderator))
            return false;
        moderates.add(moderator);
        return true;
    }

    public String getSubForumName() {
        return subForumName;
    }

    public int getSubForumId() {
        return subForumId;
    }

    public boolean removeModerator(Member moderate) {
        if (!(moderates.contains(moderate)) || null == moderate || moderates.size()==1)
            return false;
        moderates.remove(moderate);
        return true;
    }

    public int addNewThread(Member creator, String threadTitle, String threadBody) {
        int newThreadID = msgIdGenerator();
        if (threadBody.isEmpty() && threadTitle.isEmpty()){
            return -1;
        }
        else{
            Message newThread = new Message(newThreadID, creator, threadTitle, threadBody);
            threads.add(newThread);
            if (creator != null)
                creator.updateNumberOfMessage();
            return newThread.getMsgId();
        }
    }

    /**
     * add new comment for the given message
     * @param creator - the creator of this comment
     * @param msgId - message id
     * @param commentTitle - title
     * @param commentBody - comment body
     * @return - the mew comment id,
     *           or -1 of ilegal inputs,
     *           or -2 if message 'nsgId' not found
     */
    public int postNewComment(Member creator, int msgId, String commentTitle, String commentBody) {
        int newMsgId = msgIdGenerator();
        if (commentTitle.isEmpty() && commentBody.isEmpty()){
            return -1;
        } else {
            Message newMsg = new Message(newMsgId, creator, commentTitle, commentBody);
            Message relevantMsg = getMessage(msgId);
            if (null != relevantMsg) {
                relevantMsg.addNewComment(newMsg);
                if(creator != null)
                    creator.updateNumberOfMessage();
                return newMsg.getMsgId();
            } else {
                return -2;
            }
        }
    }

    public boolean removeMessage(Member remover, int msgId) {
        for (Message t : threads){
            if (t.getMsgId() == msgId) {
                if ((t.getCreator().getUserName().equals(remover.getUserName())) || remover.getType()==MemberTypes.GOLD ) {
                    t.removeAllMsgs();
                    threads.remove(t);
                    return true;
                }
                return false;
            }
            Message relevantMsg;
            relevantMsg = t.searchForMsgId(msgId);
            if (null != relevantMsg) {
                if (relevantMsg.getCreator().getUserName().equals(remover.getUserName())) {
                    relevantMsg.removeAllMsgs();
                    t.removeMessage(relevantMsg);
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public boolean editMessage(Member editor, int msgId, String edittedTitle, String edittedBody) {
        if (edittedBody.isEmpty() && edittedTitle.isEmpty()){
            return false;
        }
        else {
            Message msg = getMessage(msgId);
            if (null != msg) {
                if ((msg.getCreator().getUserName().equals(editor.getUserName())) || editor.getType() == MemberTypes.GOLD) {
                    msg.edit(edittedTitle, edittedBody);
                    return true;
                }
                return false;
            } else {
                return false;
            }
        }
    }

    public Collection<Message> getComments(int msgId) {
        Message msg = getMessage(msgId);
        if (null != msg){
            return msg.getComments();
        }
        return null;
    }

    public Message getMessage(int msgId) {
        for (Message t : threads){
            if (t.getMsgId() == msgId) {
                return t;
            }
            Message relevantMsg;
            relevantMsg = t.searchForMsgId(msgId);
            if (null != relevantMsg) {
                return relevantMsg;
            }
        }
        return null;
    }
}
