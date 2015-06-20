package org.bgu.domain.facades;

import org.bgu.domain.model.*;
import org.bgu.service.Exceptions.Result;

import java.util.*;

/**
 * Created by gur on 20/04/2015.
 */
public class UserFacade {

    private static boolean systemInitialized = false;

    //TODO - change to ORM
    private static Collection<Member> superAdmins = new ArrayList<>();

    /** hold all the sessions information of activates members */
    private static HashMap<Integer, Session> sessions = new HashMap<>();

    /***********SUPER ADMIN HANDLE**************** */

    /* notice - just add new superadmin to database, no auto-login */
    public static boolean addSuperAdmin(String superAdminName, String superAdminPass) {
        Member newSuperAdmin;
        //TODO - change to ORM
        if (!systemInitialized){
            //the system yet to be initialized
            systemInitialized = true;
            newSuperAdmin = new Member(superAdminName, superAdminPass);
            superAdmins.add(newSuperAdmin);
            //TODO - decide if after first initialization the super admin (the first one anyway) will be automaticly "logged-in"
            //superAdminsCache.put(superAdminName ,newSuperAdmin);
            return true;
        }
        else if (null == getSuperAdmin(superAdminName))
            return false;
        Member admin = new Member(superAdminName, superAdminPass);
        superAdmins.add(admin);
        return true;
    }

    public static boolean isInitializedSystem(){
        if (!systemInitialized)
            return false;
        return true;
    }

    public static boolean isLoggedInSuperAdmin(int sId){
        if (!sessions.containsKey(sId))
            return false;
        Session session = sessions.get(sId);
        if(!superAdmins.contains(session._member))
            return false;
        return true;
    }

    public static boolean validateNamePassSuperAdmin(String superAdminName, String superAdminPass){
        Member member = getSuperAdmin(superAdminName);
        if (member == null || !member.login(superAdminPass))
            return false;
        return true;
    }

    public static boolean loginSuperAdmin(int sId, String superAdminName) {
        if(sessions.containsKey(sId))
            return false;

        Member superAdmin = getSuperAdmin(superAdminName);
        if (null == superAdmin) {
            // not suppose to happen
            return false;
        }
        sessions.put(sId, new Session(sId, superAdmin, null));
        return true;
    }

    private static Member getSuperAdmin(String superAdminName) {
        for (Member m : superAdmins){
            if (m.getUserName().equals(superAdminName))
                return m;
        }
        return null;
    }

    /***********SUPER ADMIN HANDLE**************** */

    public static User createGuest() {
        //TODO - ??
        return new Guest();
    }

    /***********FORUM USERS HANDLE**************** */

    public static boolean validatePassword(String forumName, String userName, String pass){
        Forum forum = ForumFacade.getForum(forumName);
        if (null == forum)
            return false;
        Member member = forum.getMemberByName(userName);
        if (null == member)
            return false;
        if (!member.login(pass))
            return false;
        return true;
    }

    public static boolean logInMember(int sId, String forumName, String userName) {
        if (sessions.containsKey(sId))
            return false;
        Forum forum  = ForumFacade.getForum(forumName);
        if (null == forum)
            return false;
        Member member = forum.logInUser(userName);
        if(null == member)
            return false;

        // login success - add the session information
        sessions.put(sId, new Session(sId, member, forum));
        return true;
    }

    public static boolean logOut(int sId) {
        if(!sessions.containsKey(sId))
            return false;
        Session session = sessions.get(sId);
        if (!session._forum.logOut(session._member.getUserName()))
            return false;

        sessions.remove(sId);
        return true;
    }

    public static boolean registerMember(String forumName, String userName, String userPassword){
        Forum forum = ForumFacade.getForum(forumName);
        if (null == forum)
            return false;
        if (!forum.registeredMember(userName, userPassword))
            return false;
        return true;
    }

    public static boolean isLoggedInMember(int sId){
        if(!sessions.containsKey(sId))
            return false;
        Session session = sessions.get(sId);
        // TODO - replace signature to get Member Object rather than name
        if (!session._forum.isLoggedInMember(session._member.getUserName()))
            return false;
        return true;
    }

    public static boolean isRegisteredMember(int sId, String userName){
        Session session = sessions.get(sId);
        if (!session._forum.isRegisteredMember(userName))
            return false;
        return true;
    }

    public static boolean addFriend(int sId, String otherUserName) {
        Session session = sessions.get(sId);

        if (session._member.getUserName().equals(otherUserName))
            return false;

        // TODO - replace the signature to get Forum Object rather than name
        Member otherUser = getMember(session._forum.getForumName(), otherUserName);
        if (null == otherUser)
            return false;
        if (session._member.isFriendOf(otherUser))
            return false;
        session._member.addFriend(otherUser);
        otherUser.addFriend(session._member);
        return true;
    }

    public static Member getMember(String forumName, String userName){
        Forum forum = ForumFacade.getForum(forumName);
        if (forum == null)
            return null;
        return forum.getMemberByName(userName);
    }

    public static Result addMember(String forumName, String userName, String pass) {
        // TODO refactor
        Forum forum = ForumFacade.getForum(forumName);
        if (forum == null)
            return Result.FORUM_NOT_FOUND;
        Collection<Member> members = forum.getMembers();

        // check that the userName not exist
        for (Iterator<Member> iterator = members.iterator(); iterator.hasNext(); ) {
            User next =  iterator.next();
            if(next.getUserName().equals(userName)){
                return Result.DUPLICATED_USERNAME;
            }
        }

        //TODO - password / user policy
        Member member = new Member(userName, pass); //TODO - should be a member or specific admin class??

        members.add(member);
        return Result.SUCCESS;
    }

    public static boolean isForumManager(int sId) {
        Session session = sessions.get(sId);
        if (null == session)
            return false;
        // TODO - refactor signature of isForumManager??
        return session._forum.isForumManager(session._member.getUserName());
    }

    public static boolean addModerator(int sId, String subForumName, String moderatorName){
        Session session = sessions.get(sId);
        if (null == session)
            return false;
        SubForum subForum = session._forum.getSubForumByName(subForumName);
        if (null == subForum)
            return false;
        Member moderator = session._forum.getMemberByName(moderatorName);
        if (null == moderator)
            return false;
        if (!subForum.addModerator(moderator))
            return false;
        return true;
    }

    public static boolean removeModerator(int sId, String subForumName, String moderatorName){
        Session session = sessions.get(sId);
        if (null == session)
            return false;
        SubForum subForum = session._forum.getSubForumByName(subForumName);
        if (null == subForum)
            return false;
        Member moderator = session._forum.getMemberByName(moderatorName);
        if (null == moderator)
            return false;
        if (!subForum.removeModerator(moderator))
            return false;
        return true;
    }

    public static boolean removeFriend(int sId, String friendUserName) {
        Session session = sessions.get(sId);
        if (null == session)
            return false;

        Member friend = getMember(session._forum.getForumName(), friendUserName);
        if (null == friend)
            return false;
        if (!session._member.isFriendOf(friend))
            return false;
        session._member.removeFriend(friend);
        friend.removeFriend(session._member);
        return true;
    }

    protected static Session getSession(int sId) {
        return sessions.get(sId);
    }

                    /*****************FOR TESTING******************/
                    /**********************************************/

    public static void resetSuperAdmins() {
        // TODO - users.clear();
        systemInitialized = false;
        superAdmins.clear();
        //TODO sessions.clear();
    }
    public static void resetForumMembers(String forumName) {
        // TODO - users.clear();
        Forum forum = ForumFacade.getForum(forumName);
        forum.resetMembers();
    }

    public static void reset() {
        sessions.clear();
    }
}
