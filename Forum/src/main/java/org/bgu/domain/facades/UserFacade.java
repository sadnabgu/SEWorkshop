package org.bgu.domain.facades;

import org.bgu.domain.model.*;
import org.bgu.service.ServiceObjects.Result;

import java.util.*;

/**
 * Created by gur on 20/04/2015.
 */
public class UserFacade {

    private static boolean systemInitialized = false;

    //TODO - change to ORM
    private static Collection<Member> superAdmins = new ArrayList<>();

    /** hold all the sessions information of activates members */
    private static HashMap<UUID, Session> sessions = new HashMap<>();

    /***********SUPER ADMIN HANDLE**************** */

        /* notice - just add new superadmin to database, no auto-login */
    public static boolean initSystem(String superAdminName, String superAdminPass) {
        //TODO - change to ORM
        if (!systemInitialized){
            //the system yet to be initialized
            systemInitialized = true;
            return addSuperAdmin(superAdminName, superAdminPass);
        }
        else {
            return false;
        }
    }

    /* notice - just add new superadmin to database, no auto-login */
    public static boolean addSuperAdmin(String superAdminName, String superAdminPass) {
        //TODO - change to ORM
        if (null != getSuperAdmin(superAdminName))
            return false;
        if (!systemInitialized)
            return false;
        if (superAdminName.equals("") || superAdminPass.equals("")){
            return false;
        }
        Member admin = new Member(superAdminName, superAdminPass);
        superAdmins.add(admin);
        return true;

    }

    public static boolean isInitializedSystem(){
        if (!systemInitialized)
            return false;
        return true;
    }

    public static boolean isLoggedInSuperAdmin(UUID sId){
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

    private static Member getSuperAdmin(String superAdminName) {
        for (Member m : superAdmins){
            if (m.getUserName().equals(superAdminName))
                return m;
        }
        return null;
    }

    public static Collection<Session> getAllSessions(){
        Collection<Session> opennedSessions = new ArrayList<>();
        for (Session s : sessions.values()){
            opennedSessions.add(s);
        }
        return opennedSessions;
    }

    public static boolean validatePassword(UUID sId, String userName, String pass){
        if(!sessions.containsKey(sId))
            return false;
        Session session = sessions.get(sId);

        if (null == session._forum)
            return false;

        Member member = session._forum.getMemberByName(userName);
        if (null == member)
            return false;

        return member.login(pass);
    }

    public static UUID addSuperAdminSession(String adminName) {
        Member admin = getSuperAdmin(adminName);
        if(admin == null)
            return null;
        UUID sId = UUID.randomUUID();
        // login success - add the session information
        sessions.put(sId, new Session(sId, admin, null));
        return sId;
    }

    public static UUID addGuestSession(String forumName) {
        Forum forum  = ForumFacade.getForum(forumName);
        if (null == forum)
            return null;

        UUID sId = UUID.randomUUID();
        // login success - add the session information
        sessions.put(sId, new Session(sId, null, forum));
        return sId;
    }

    public static Result logInMember(UUID sId, String userName) {
        if(!sessions.containsKey(sId))
            return Result.SESSION_NOT_FOUND;
        Session session = sessions.get(sId);

        if (null == session._forum)
            return Result.FORUM_NOT_EXISTS;
        if(null != session._member)
            return Result.ALREADY_LOGDIN;
        Member member = session._forum.logInUser(userName);

        // login success - update the session information
        session.terminateSession();
        sessions.remove(sId);
        sessions.put(sId, new Session(sId, member, session._forum));
        return Result.SUCCESS;
    }

    public static boolean logOut(UUID sId) {
        if(!sessions.containsKey(sId))
            return false;
        Session session = sessions.get(sId);
        if(session._member == null){
            return false;
        }
        if (!session._forum.logOut(session._member.getUserName()))
            return false;
        sessions.remove(sId);
        sessions.put(sId, new Session(sId,null, session._forum));
        return true;
    }

    public static boolean registerMember(UUID sId, String userName, String userPassword){
        if(!sessions.containsKey(sId))
            return false;
        Session session = sessions.get(sId);
        if(session._forum == null)
            return false;
        return session._forum.registeredMember(userName, userPassword);
    }

    public static boolean isLoggedInMember(UUID sId){
        if(!sessions.containsKey(sId))
            return false;
        Session session = sessions.get(sId);
        // TODO - replace signature to get Member Object rather than name
        if (!session._forum.isLoggedInMember(session._member.getUserName()))
            return false;
        return true;
    }

    public static boolean isRegisteredMember(UUID sId, String userName){
        Session session = sessions.get(sId);
        if (!session._forum.isRegisteredMember(userName))
            return false;
        return true;
    }

    public static boolean addFriend(UUID sId, String otherUserName) {
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
        // TODO refactor - join with register member
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

    public static boolean isForumManager(UUID sId) {
        Session session = sessions.get(sId);
        if (null == session)
            return false;
        // TODO - refactor signature of isForumManager??
        return session._forum.isForumManager(session._member.getUserName());
    }

    public static boolean addModerator(UUID sId, String subForumName, String moderatorName){
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

    public static boolean removeModerator(UUID sId, String subForumName, String moderatorName){
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

    public static boolean removeFriend(UUID sId, String friendUserName) {
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

    protected static Session getSession(UUID sId) {
        return sessions.get(sId);
    }

    public static String getSessionUserName(UUID sId) {
        Session session = sessions.get(sId);
        if (null == session)
            return null;
        Member member = session._member;
        if (null == member) {
            return new Guest().getUserName();
        } else {
            return member.getUserName();
        }
    }
                    /*****************FOR TESTING******************/
                    /**********************************************/

    public static void resetSuperAdmins() {
        // TODO - users.clear();
        systemInitialized = false;
        superAdmins.clear();
        //TODO sessions.clear();
    }

    /**
     * un-initialize the system
     * used only for the testing
     */
    public static void resetSystem() {
        UserFacade.resetSuperAdmins();
        ForumFacade.resetForums();
        sessions.clear();
    }

}
