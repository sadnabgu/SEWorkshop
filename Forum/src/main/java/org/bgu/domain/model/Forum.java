package org.bgu.domain.model;

import com.sun.xml.internal.ws.addressing.v200408.MemberSubmissionWsaServerTube;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by gur on 20/04/2015.
 */
public class Forum {
    //Fields
    private static Collection<SubForum> _subForums;
    private String _name;
    private Policy _policy;
    Collection<Member> _members;
    private boolean _onHold;
    private Collection<Message> _messages; //DB of all forum's messages

    //Constructor
    //TODO - determine all the mendetory parameters for constructing a new forum
    public Forum(String forumName, String forumAdminName, String forumAdminPass){
        _name = forumName;
        _onHold = true;                 // no policy configured
        _subForums = new ArrayList<>();
        _policy = null;
    }

    public boolean addNewSubForum(String subForumName, String moderatorName) {
        Member moderator = getMember(moderatorName);
        if ((getSubForum(subForumName) == null) && moderator!=null) {
            SubForum subForum = new SubForum(subForumName, moderator);
            _subForums.add(subForum);
            return true;
        }
        return false;
    }

    public boolean addNewThread(String subForumName, String threadName, String msgBody, int msgId, String threadOwnerName) {
        Message newMessage;
        User threadOwner;
        if (threadOwnerName.equals("guest")){
            threadOwner = new Guest();
        }
        else {
            threadOwner = getMember(threadOwnerName);
            if (threadOwner == null)
                return false;
        }
        newMessage = new Message(threadName, msgBody, threadOwner);
        SubForum subForum = getSubForum(subForumName);
        if (!subForum.addNewThread(threadName, newMessage))
            return false;
        _messages.add(newMessage);
        return true;
    }

    public Member getMember(String memberName){
        for (Iterator<Member> iterator = _members.iterator(); iterator.hasNext(); ) {
            Member next =  iterator.next();
            if (next.getUserName().equals(memberName)){
                return next;
            }
        }
        return null;
    }

    public boolean addNewComment(int msgId, String msgTitle, String msgBody, String userName) {
        Message newComment;
        Message baseMsg;
        User commentOwner;
        if (userName.equals("guest")){
            commentOwner = new Guest();
        }
        else {
            commentOwner = getMember(userName);
            if (commentOwner == null)
                return false;
        }
        baseMsg = getMsg(msgId);
        if (baseMsg == null)
            return false;
        newComment = new Message(msgTitle, msgBody, commentOwner);
        if (!baseMsg.addComment(newComment))
            return false;
        _messages.add(newComment);
        return true;
    }

    private Message getMsg(int msgId) {
        for (Iterator<Message> iterator = _messages.iterator(); iterator.hasNext(); ) {
            Message next =  iterator.next();
            if (next.getMsgId() == msgId){
                return next;
            }
        }
        return null;
    }



    public boolean removeMessage(int messageId){


        return subForum.removeMessage(message);
    }















    public Member login(String userName, String pass){
        for (Iterator<Member> iterator = _members.iterator(); iterator.hasNext(); ) {
            Member next =  iterator.next();
            if (next.getUserName().equals(userName)){
                if (next.validatePass(pass)){
                    return next;
                }
                else return null;
            }
        }
        return null;
    }

/

    public void setForumPolicy(Policy policy){
        _policy = policy;
        _onHold = false;
    }



    public SubForum getSubForum(String subForumName) {
        for (Iterator<SubForum> iterator = _subForums.iterator(); iterator.hasNext(); ) {
            SubForum next =  iterator.next();
            if (next.getName() == subForumName)
                return next;
        }
        return null;
    }



    public boolean removeSubForum(SubForum subForum) {
        _subForums.remove(subForum);
    }

    public int getId() {
        return _id;
    }

    public String getName() {
        return _name;
    }




    public void postNewComment(Message msg, Message comment, SubForumThread subForumThread, SubForum subForum){
        subForum.postNewComment(msg, comment, subForumThread);
    }


}