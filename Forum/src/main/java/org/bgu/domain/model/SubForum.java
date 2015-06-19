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
        if (!(moderates.contains(moderate)) || null == moderate)
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
            return newThread.getMsgId();
        }
    }

    public int postNewComment(Member creator, int msgId, String commentTitle, String commentBody) {
        int newMsgId = msgIdGenerator();
        if (commentTitle.isEmpty() && commentBody.isEmpty()){
            return -1;
        }
        else{
            Message newMsg = new Message(newMsgId, creator, commentTitle, commentBody);
            boolean flag = false;
            for (Message t : threads){
                Message relevantMsg;
                relevantMsg = t.searchForMsgId(msgId);
                if (null != relevantMsg) {
                    relevantMsg.addNewComment(newMsg);
                    flag = true;
                    break;
                }
            }
            if (!flag)
                return -1;
            return newMsg.getMsgId();
        }
    }

    public boolean removeMessage(Member remover, int msgId) {
        for (Message t : threads){
            if (t.getMsgId() == msgId) {
                if (t.getCreator().getUserName().equals(remover.getUserName())) {
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
        else{
            for (Message t : threads){
                if (t.getMsgId() == msgId) {
                    if (t.getCreator().getUserName().equals(editor.getUserName())) {
                        t.edit(edittedTitle, edittedBody);
                        return true;
                    }
                    return false;
                }
                Message relevantMsg;
                relevantMsg = t.searchForMsgId(msgId);
                if (null != relevantMsg) {
                    if (relevantMsg.getCreator().getUserName().equals(editor.getUserName())) {
                        t.edit(edittedTitle, edittedBody);
                        return true;
                    }
                    return false;
                }
            }
            return false;
        }
    }
}
