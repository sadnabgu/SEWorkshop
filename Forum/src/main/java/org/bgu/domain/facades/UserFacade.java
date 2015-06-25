package org.bgu.domain.facades;

import org.apache.log4j.Logger;
import org.bgu.domain.model.*;
import org.bgu.service.ServiceObjects.Result;

import java.util.*;

/**
 * Created by gur on 20/04/2015.
 */
public class UserFacade {

    private static Logger logger = Logger.getLogger(UserFacade.class);
    private static boolean systemInitialized = false;

    //TODO - change to ORM
    private static Collection<Member> superAdmins = new ArrayList<>();

    /** hold all the sessions information of activates members */
    private static HashMap<UUID, Session> sessions = new HashMap<>();

    /***********SUPER ADMIN HANDLE**************** */

        /* notice - just add new superadmin to database, no auto-login */
    public static boolean initSystem(String superAdminName, String superAdminPass) {
        logger.trace("enter initSystem");
        //TODO - change to ORM
        if (!systemInitialized){
            //the system yet to be initialized
            systemInitialized = true;
            logger.trace("system was initialized");
            return addSuperAdmin(superAdminName, superAdminPass);
        }
        else {
            logger.warn("ini system fail because system already initialized");
            return false;
        }
    }

    /* notice - just add new superadmin to database, no auto-login */
    public static boolean addSuperAdmin(String superAdminName, String superAdminPass) {
        logger.trace(String.format("enter addSuperAdmin - name: %s, pass: %s", superAdminName, superAdminPass));
        //TODO - change to ORM
        if (null != getSuperAdmin(superAdminName)) {
            logger.warn("super admin " + superAdminName + "already exist");
            return false;
        }
        if (!systemInitialized) {
            logger.debug("system not initialized");
            return false;
        }
        if (superAdminName.equals("") || superAdminPass.equals("")){
            logger.debug("admin name or pass are empty");
            return false;
        }
        Member admin = new Member(superAdminName, superAdminPass);
        superAdmins.add(admin);
        logger.trace("system initialized");
        return true;

    }

    public static boolean isInitializedSystem(){
        logger.trace("enter isInitializedSystem");
        if (!systemInitialized)
            return false;
        return true;
    }

    public static boolean isLoggedInSuperAdmin(UUID sId){
        logger.trace("enter isLoggedInSuperAdmin");
        if (!sessions.containsKey(sId)) {
            logger.debug("admin session not found sid: " + sId.toString());
            return false;
        }
        Session session = sessions.get(sId);
        if(!superAdmins.contains(session._member)) {
            logger.debug("Member not super admin");
            return false;
        }
        return true;
    }

    public static boolean validateNamePassSuperAdmin(String superAdminName, String superAdminPass){
        logger.trace(String.format("enter validateNamePassSuperAdmin - name: %s, pass: s",
                superAdminName, superAdminPass));
        Member member = getSuperAdmin(superAdminName);
        if (member == null || !member.login(superAdminPass)) {
            logger.info("fail to validate super admin");
            return false;
        }
        return true;
    }

    private static Member getSuperAdmin(String superAdminName) {
        logger.trace("enter getSuperAdmin name: " + superAdminName);
        for (Member m : superAdmins){
            if (m.getUserName().equals(superAdminName))
                return m;
        }
        logger.warn("not found super admin: " + superAdminName);
        return null;
    }

    public static Collection<Session> getAllSessions(){
        logger.trace("enter getAllSessions");
        Collection<Session> opennedSessions = new ArrayList<>();
        for (Session s : sessions.values()){
            opennedSessions.add(s);
        }
        return opennedSessions;
    }

    public static boolean validatePassword(UUID sId, String userName, String pass){
        logger.trace("enter validatePassword");
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
        logger.trace("enter addSuperAdminSession");
        Member admin = getSuperAdmin(adminName);
        if(admin == null) {
            logger.warn("admin not found, admin name: " + adminName);
            return null;
        }
        UUID sId = UUID.randomUUID();
        // login success - add the session information
        sessions.put(sId, new Session(sId, admin, null));
        logger.trace("add super admin session sid: " + sId.toString());
        return sId;
    }

    public static UUID addGuestSession(String forumName) {
        logger.trace("enter addGuestSession( " + forumName + ")");
        Forum forum  = ForumFacade.getForum(forumName);
        if (null == forum) {
            logger.debug("forum (" +forumName + ") not found");
            return null;
        }

        UUID sId = UUID.randomUUID();
        // login success - add the session information
        sessions.put(sId, new Session(sId, null, forum));
        logger.trace("create new guest session, sid: " + sId.toString());
        return sId;
    }

    public static Result logInMember(UUID sId, String userName) {
        logger.trace("enter logInMember");
        if(!sessions.containsKey(sId)) {
            logger.warn("session: " + sId.toString() + " not found");
            return Result.SESSION_NOT_FOUND;
        }
        Session session = sessions.get(sId);

        if (null == session._forum) {
            logger.warn("session of forum is null");
            return Result.FORUM_NOT_EXISTS;
        }
        if(null != session._member)
            return Result.ALREADY_LOGDIN;
        Member member = session._forum.logInUser(userName);

        if (null == member){
            return Result.ALREADY_LOGDIN;
        }

        // login success - update the session information
        session.terminateSession();
        sessions.remove(sId);
        sessions.put(sId, new Session(sId, member, session._forum));
        logger.trace("login member success");
        return Result.SUCCESS;
    }

    public static boolean logOut(UUID sId) {
        logger.trace("enter logOut, sid: " + sId.toString());
        if(!sessions.containsKey(sId)) {
            logger.warn("sid not found");
            return false;
        }
        Session session = sessions.get(sId);
        if(session._member == null){
            return false;
        }
        if (!session._forum.logOut(session._member.getUserName()))
            return false;

        Forum forum = session._forum;
        sessions.remove(sId);
        sessions.put(sId, new Session(sId,null, forum));
        logger.trace("logout success");
        return true;
    }

    public static boolean registerMember(UUID sId, String userName, String userPassword){
        logger.trace("enter registerMember");
        if(!sessions.containsKey(sId))
            return false;
        Session session = sessions.get(sId);
        if(session._forum == null)
            return false;
        return session._forum.registeredMember(userName, userPassword);
    }

    public static boolean isLoggedInMember(UUID sId){
        logger.trace("enter registerMember");
        if(!sessions.containsKey(sId))
            return false;
        Session session = sessions.get(sId);
        if(null == session._member)
            return false;

        // TODO - replace signature to get Member Object rather than name
        if (!session._forum.isLoggedInMember(session._member.getUserName()))
            return false;
        return true;
    }

    public static boolean isRegisteredMember(UUID sId, String userName){
        logger.trace("enter isRegisteredMember sid: " + sId.toString() + ", name: " + userName);
        Session session = sessions.get(sId);
        if (!session._forum.isRegisteredMember(userName))
            return false;
        return true;
    }

    public static boolean addFriend(UUID sId, String otherUserName) {
        logger.trace("enter addFriend");
        Session session = sessions.get(sId);

        if (session._member.getUserName().equals(otherUserName))
            return false;

        // TODO - replace the signature to get Forum Object rather than name
        Member otherUser = getMember(session._forum.getForumName(), otherUserName);
        if (null == otherUser) {
            logger.warn("otherUser == null");
            return false;
        }
        if (session._member.isFriendOf(otherUser))
            return false;
        session._member.addFriend(otherUser);
        otherUser.addFriend(session._member);
        return true;
    }

    public static Member getMember(String forumName, String userName){
        logger.trace("enter getMember");
        Forum forum = ForumFacade.getForum(forumName);
        if (forum == null)
            return null;
        return forum.getMemberByName(userName);
    }

    public static Result addMember(String forumName, String userName, String pass) {
        logger.trace("enter addMember");
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
        logger.info(String.format("add member, forum: %s, name: %s, pass:***", forumName, userName));
        return Result.SUCCESS;
    }

    public static boolean isForumManager(UUID sId) {
        logger.trace("enter isForumManager, sid: " + sId.toString());
        Session session = sessions.get(sId);
        if (null == session)
            return false;
        if (null == session._member)
            return false;
        // TODO - refactor signature of isForumManager??
        return session._forum.isForumManager(session._member.getUserName());
    }

    public static boolean addModerator(UUID sId, String subForumName, String moderatorName){
        logger.trace("enter addModerator");
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
        logger.info("added moderator: " + moderatorName + ", to sub-forum: " + subForumName);
        return true;
    }

    public static boolean removeModerator(UUID sId, String subForumName, String moderatorName){
        logger.trace("enter removeModerator - sub-forum: " + subForumName + ", moderator; " + moderatorName);
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
        logger.info("moderator: " + moderatorName + "was removed from sub-forum: " + subForumName);
        return true;
    }

    public static boolean removeFriend(UUID sId, String friendUserName) {
        logger.trace("enter removeFriend sid: " + sId.toString() + ", friend: " + friendUserName);
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
        logger.info("friend: " + friendUserName + " of: " + session._member.getUserName() + " was removed");
        return true;
    }

    protected static Session getSession(UUID sId) {
        return sessions.get(sId);
    }

    public static String getSessionUserName(UUID sId) {
        logger.trace("enter getSessionUserName");
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
