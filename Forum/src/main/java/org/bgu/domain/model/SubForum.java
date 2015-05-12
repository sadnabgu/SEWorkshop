package org.bgu.domain.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by gur on 20/04/2015.
 */
public class SubForum {
    Collection<SubForumThread> _threads;
    Collection<Member> _moderates;
    String _name;

    public SubForum(String subForumName, Member moderator){
        _name = subForumName;
        _moderates = new ArrayList<Member>();
        addModerate(moderator);
        _threads = new ArrayList<SubForumThread>();
    }

    public boolean addNewThread(String threadName, Message opennignMessage){
        SubForumThread subForumThread = new SubForumThread(opennignMessage);
        _threads.add(subForumThread);
        return true;
    }

    public boolean addModerate(Member moderator) {
        return _moderates.add(moderator);
    }














    public Collection<SubForumThread> getThreads(){
        return _threads;
    }

    public Collection<Member> getModerates(){
        return _moderates;
    }



    public String getName() {
        return _name;
    }



    public boolean removeMessage(User member, Message message){
        if (message.getCreator() == member){
            SubForumThread msgThread = message.getThread();
            if (message == msgThread.getOpeningMessage())
                _threads.remove(msgThread);
            else
                msgThread.removeMessage(message);
            return true;
        }
        return false;
    }

    public boolean postNewComment(Message msg, Message comment, SubForumThread subForumThread){
        return subForumThread.postNewComment(comment, subForumThread)
    }
}
