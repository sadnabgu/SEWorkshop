package org.bgu.domain.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by gur on 20/04/2015.
 */
public class SubForum {
    //TODO - maybe make a collection of type "Messages" instead of "Forum-Threads" and eliminate "Forum-Thread" class
    private int subForumId;
    private Collection<ForumThread> threads;
    private Collection<Member> moderates;
    private String subForumName;
    private int threadIdGenerate;

    public SubForum(String subForumName, int subForumId){
        this.subForumName = subForumName;
        this.subForumId = subForumId;
        this.moderates = new ArrayList<Member>();
        this.threads = new ArrayList<ForumThread>();
        this.threadIdGenerate = 1;
    }

    public Collection<ForumThread> getThreads(){
        return this.threads;
    }

    public Collection<Member> getModerates(){
        return moderates;
    }

    public boolean addModerate(Member moderate) {
        if (moderates.contains(moderate) || null == moderate)
            return false;
        moderates.add(moderate);
        return true;
    }

    public String getSubForumName() {
        return subForumName;
    }

    public int getSubForumId() {
        return subForumId;
    }

    public boolean isModerator(Member moderate){
        return moderates.contains(moderate);
    }

    public boolean removeModerate(Member moderate) {
        if (!(moderates.contains(moderate)) || null == moderate)
            return false;
        moderates.remove(moderate);
        return true;
    }

    public int addNewThread(User creator, String threadTitle, String threadBody) {
        if (threadBody.isEmpty() && threadTitle.isEmpty()){
            return -1;
        }
        else{
            int newThreadID = generaThreadId();
            Message newMessage = new Message(creator, threadTitle, threadBody);
            ForumThread newThread = new ForumThread(newThreadID, newMessage);
            threads.add(newThread);
            return newThreadID;
        }
    }

    private int generaThreadId() {
        return threadIdGenerate++;
    }
}
