package org.bgu.domain.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by gur on 20/04/2015.
 */
public class Forum {
    private int _forumId;
    private static Collection<SubForum> _subForums;
    private String _name;

    //TODO - change to ORM
    /** collection of all the members and moderates and managers of this forum */
    private Collection<Member> _members;
    /** Collection of all the managers of this forum (sub-set of _members) */
    private Collection<Member> _managers;

    /* cache memorry for logged in members */
    private  HashMap<String, Member> _loggedInMembers = new HashMap<>();

    private int _subForumIdGenerate;


    public Forum(int forumId, String forumName, Member manager){
        _forumId = forumId;
        _name = forumName;
        _subForums = new ArrayList<>();
        _members = new ArrayList<>();
        _managers = new ArrayList<>();
        _managers.add(manager);
        _members.add(manager);
        _subForumIdGenerate = 1;
    }

    private int generateSubForumId(){
        return _subForumIdGenerate++;
    }

    /*****FORUM STRUCTURE MANAGEMENT****  */

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
            subForum.addModerator(m);
        }
        _subForums.add(subForum);
        return subForum.getSubForumId();
    }

    public int addNewThread(String subForumName, Member creator, String threadTitle, String threadBody) {
        SubForum subForum = getSubForumByName(subForumName);

        if (subForum == null)
            return -1;  // sub forum not found
        int newMsgId = subForum.addNewThread(creator, threadTitle, threadBody);
        return newMsgId;
    }

    public boolean removeSubForum(String subForumName) {
        SubForum subForum = getSubForumByName(subForumName);
        if (null == subForum) return false;
        _subForums.remove(subForum);
        return true;
    }

    /*****FORUM STRUCTURE MANAGEMENT****  */



    /*****FORUM USERS MANAGEMENT****/
    public boolean isForumManager(String forumManagerName) {
        Member member = _loggedInMembers.get(forumManagerName);
        if (_managers.contains(member))
            return true;
        return false;
    }

    //disallow to login twice for already loggedin member
    public boolean logInUser(String memberName){
        if (_loggedInMembers.containsKey(memberName))
            return false;
        Member member = getMemberByName(memberName);
        _loggedInMembers.put(memberName, member);
        return true;
    }

    public boolean logOut(String memberName){
        if (!_loggedInMembers.containsKey(memberName))
            return false;
        _loggedInMembers.remove(memberName);
        return true;
    }

    public boolean registeredMember(String userName, String password){
        Member member  = getMemberByName(userName);
        if (!(null == member))
            return false;
        member = new Member(userName, password);
        _members.add(member);
        return true;
    }

    public boolean isLoggedInMember(String userName){
        if (_loggedInMembers.containsKey(userName))
            return true;
        return false;
    }

    public boolean isRegisteredMember(String memberName){
       Member member = getMemberByName(memberName);
       if (_members.contains(member))
           return true;
        return false;
    }

    /*****FORUM USERS MANAGEMENT****/



    /**********************************************************************************************************/
    /*****************FOR TESTING*********************************************************************************/

    public void resetMembers(){
        _members.clear();
        _managers.clear();
    }

    public void resetSubForums(){
        _subForums.clear();
    }



    /*****************DB OPERATIONS******************/
    //TODO - change to ORM

    public Member getMemberByName(String memberName){
        for (Member m : _members){
            if (m.getUserName().equals(memberName))
                return m;
        }
        return null;
    }

    public SubForum getSubForumByName(String subForumName) {
        for (Iterator<SubForum> iterator = _subForums.iterator(); iterator.hasNext(); ) {
            SubForum next =  iterator.next();
            if (next.getSubForumName().equals(subForumName))
                return next;
        }
        return null;
    }

    public int getForumId() {
        return _forumId;
    }

    public String getForumName() {
        return _name;
    }

    public Collection<Member> getMembers() {
        return _members;
    }

    public ArrayList<String> getAllSubForums() {
        ArrayList<String> subForumNames = new ArrayList<String>();
        for (SubForum subForum : _subForums){
            subForumNames.add(subForum.getSubForumName());
        }
        return subForumNames;
    }
}