package org.bgu.domain.model;

import org.bgu.domain.model.notification.NotificationStrategy;
import org.bgu.domain.model.notification.NotificationType;
import org.bgu.domain.model.notification.Subject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by gur on 20/04/2015.
 */
public class Forum extends Subject{
    private int _forumId;
    private static Collection<SubForum> _subForums;
    private String _name;
    //if the forum was not configured yet with a policy
    private boolean _isConfigured;
    private ForumSecurityPolicy _forumSecurityPolicy;
    private ForumPermitionPolicy _forumPermitionPolicy;
    private ForumPostingPolicy _forumPostingPolicy;
    //TODO - change to ORM
    /** collection of all the members and moderates and managers of this forum */
    private Collection<Member> _members;
    /** Collection of all the managers of this forum (sub-set of _members) */
    private Collection<Member> _managers;
    /* cache memorry for logged in members */
    private  HashMap<String, Member> _loggedInMembers = new HashMap<>();
    private int _subForumIdGenerate;

    private NotificationType _notification;


    public Forum(int forumId, String forumName, Member manager){
        _forumId = forumId;
        _name = forumName;
        _subForums = new ArrayList<>();
        _members = new ArrayList<>();
        _managers = new ArrayList<>();
        _managers.add(manager);
        _members.add(manager);
        _subForumIdGenerate = 1;
        _isConfigured = false;
    }

    private int generateSubForumId(){
        return _subForumIdGenerate++;
    }

    /*****FORUM STRUCTURE MANAGEMENT****  */

    public boolean isConfigured(){
        return _isConfigured;
    }

    /*public setSecurityPolicy(int passWordMinLength, boolean isQuestionEnforce, int daysForPasswordValidity){
        _forumSecurityPolicy = new ForumSecurityPolicy(passWordMinLength, isQuestionEnforce, daysForPasswordValidity);
    }

    public setPermitionPolicy(ArrayList<Permition> permitions){
        _forumPermitionPolicy = new ForumPermitionPolicy(permitions);
    }

    public setPostingPolicy(ArrayList<String> forbiddenWords){
        _forumPostingPolicy = new ForumPostingPolicy(forbiddenWords);
    }*/

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
        //TODO - add notifications
        SubForum subForum = getSubForumByName(subForumName);

        if (subForum == null)
            return -1;  // sub forum not found
        int newMsgId = subForum.addNewThread(creator, threadTitle, threadBody);
        _notification = NotificationType.NEW_MESSAGE;
        notifyObserver();
        return newMsgId;
    }

    public int postNewComment(String subForumName, Member creator, int msgId, String commentTitle, String commentBody) {
        //TODO - add notification
        SubForum subForum = getSubForumByName(subForumName);
        if (subForum == null)
            return -1;  // sub forum not found
        int newMsgId = subForum.postNewComment(creator, msgId, commentTitle, commentBody);
        _notification = NotificationType.NEW_MESSAGE;
        notifyObserver();
        return newMsgId;
    }

    public boolean editMessage(String subForumName, Member editor, int msgId, String edittedTitle, String edittedBody) {
        SubForum subForum = getSubForumByName(subForumName);

        if (subForum == null)
            return false;  // sub forum not found
        _notification = NotificationType.EDITED_MESSAGE;
        notifyObserver();
       return subForum.editMessage(editor, msgId, edittedTitle, edittedBody);
    }

    public boolean removeSubForum(String subForumName) {
        SubForum subForum = getSubForumByName(subForumName);
        if (null == subForum) return false;
        _subForums.remove(subForum);
        return true;
    }

    public boolean removeMessage(String subForumName, Member remover, int msgId) {
        SubForum subForum = getSubForumByName(subForumName);
        if (subForum == null)
            return false;  // sub forum not found
        _notification = NotificationType.REMOVED_MESSAGE;
        notifyObserver();
        return subForum.removeMessage(remover, msgId);
    }

    public ArrayList<String> getAllSubForums() {
        ArrayList<String> subForumNames = new ArrayList<String>();
        for (SubForum subForum : _subForums){
            subForumNames.add(subForum.getSubForumName());
        }
        return subForumNames;
    }

    public SubForum getSubForumByName(String subForumName) {
        for (Iterator<SubForum> iterator = _subForums.iterator(); iterator.hasNext(); ) {
            SubForum next =  iterator.next();
            if (next.getSubForumName().equals(subForumName))
                return next;
        }
        return null;
    }

    public String getForumName() {
        return _name;
    }

    //TODO - maybe remove it?
    public int getForumId() {
        return _forumId;
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
    public Member logInUser(String memberName){
        if (_loggedInMembers.containsKey(memberName))
            return null;
        Member member = getMemberByName(memberName);
        _loggedInMembers.put(memberName, member);
        _notification = NotificationType.LOG_IN_FORUM;
        notifyObserver();
        return member;
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
        if (userName.equals("") || password.equals("")){
            return false;
        }
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

    public Member getMemberByName(String memberName){
        for (Member m : _members){
            if (m.getUserName().equals(memberName))
                return m;
        }
        return null;
    }

    public Collection<Member> getMembers() {
        return _members;
    }

    @Override
    public void notifyObserver() {
        for (NotificationStrategy ns : _Observers){
            ns.update(this);
        }
    }

    @Override
    public Collection<Member> getContext() {
        return getMembers();
    }

    @Override
    public NotificationType getNotificationType() {

        return _notification;
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


}