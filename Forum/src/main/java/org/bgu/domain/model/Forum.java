package org.bgu.domain.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by gur on 20/04/2015.
 */
public class Forum {
    private int _forumId;
    private static Collection<SubForum> _subForums;
    private String _name;
    /** collection of all the members and moderates and managers of this forum */
    private Collection<Member> _members;
    /** Collection of all the managers of this forum (sub-set of members) */
    private Collection<Member> _managers;
    private int _subForumId;


    public Forum(int forumId, String forumName, Member manager){
        _forumId = forumId;
        _name = forumName;
        _subForums = new ArrayList<>();
        _members = new ArrayList<>();
        _managers = new ArrayList<>();
        _managers.add(manager);
        _members.add(manager);
        _subForumId=0;
    }

    private int generateSubForumId(){
        return _subForumId++;
    }

    public int addNewSubForum(String subForumName, Collection<String> moderators) {
        for (String s : moderators) {
            Member m = getMemberByName(s);
            if (null == m)
                return -1;
        }
        int subForumId = generateSubForumId();
        SubForum subForum = new SubForum(subForumName, subForumId);
        for (String s : moderators) {
            Member m = getMemberByName(s);
            subForum.addModerate(m);
        }
        _subForums.add(subForum);
        return subForum.getSubForumId();
    }

    public Member getMemberByName(String memberName){
        for (Member m : _members){
            if (m.getUserName().equals(memberName))
                return m;
        }
        return null;
    }

    public SubForum getSubForum(String subForumName) {
        for (Iterator<SubForum> iterator = _subForums.iterator(); iterator.hasNext(); ) {
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
        return _name;
    }

    public boolean isForumManager(Member member) {
        if (_managers.contains(member))
            return true;
        return false;
    }

    public int getForumId() {
        return _forumId;
    }

    public Collection<Member> getMembers() {
        return _members;
    }

    public boolean isManager(Member manager){
        return _managers.contains(manager);
    }

    /**********************************************************************************************************/
    /*****************FOR TESTING*********************************************************************************/

    public void resetMembers(){
        _members.clear();
        _managers.clear();
    }

    public void resetSubForums(){
        _subForums.clear();
    }

}