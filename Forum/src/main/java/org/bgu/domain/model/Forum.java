package org.bgu.domain.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by gur on 20/04/2015.
 */
public class Forum {
    private int id;
    private static Collection<SubForum> subForums;
    private String name;
    /** collection of all the members and moderates and managers of this forum */
    private Collection<Member> members;
    /** Collection of all the managers of this forum (sub-set of members) */
    private Collection<Member> managers;
    private int forumId;

    public Forum(int forumId, String forumName, Member manager){
        id = forumId;
        name = forumName;
        subForums = new ArrayList<>();
        members = new ArrayList<>();
        managers = new ArrayList<>();
        managers.add(manager);
        members.add(manager);
    }

    public SubForum addNewSubForum(String subForumName, Collection<String> moderators) {
        for (String s : moderators) {
            Member m = getMemberByName(s);
            if (null == m)
                return null;
        }
        SubForum subForum = new SubForum(subForumName);
        for (String s : moderators) {
            Member m = getMemberByName(s);
            subForum.addModerate(m);
        }
        subForums.add(subForum);
        return subForum;
    }

    public Member getMemberByName(String memberName){
        for (Member m : members){
            if (m.getUserName().equals(memberName))
                return m;
        }
        return null;
    }

    public SubForum getSubForum(String subForumName) {
        for (Iterator<SubForum> iterator = subForums.iterator(); iterator.hasNext(); ) {
            SubForum next =  iterator.next();
            if (next.getName().equals(subForumName))
                return next;
        }
        return null;
    }

    public boolean addNewThread(String threadName) {
        //TODO - implement in the sub forum level
        return false;
    }

    public String getForumName() {
        return name;
    }

    public boolean isForumManager(Member member) {
        if (managers.contains(member))
            return true;
        return false;
    }

    public int getForumId() {
        return forumId;
    }

    public Collection<Member> getMembers() {
        return members;
    }

    /**********************************************************************************************************/
    /*****************FOR TESTING*********************************************************************************/

    public void resetMembers(){
        members.clear();
        managers.clear();
    }

    public void resetSubForums(){
        subForums.clear();
    }


}