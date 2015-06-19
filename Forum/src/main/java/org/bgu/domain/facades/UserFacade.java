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

    /* cache memorry for logged in super admins */
    public static HashMap<String, Member> superAdminsCache = new HashMap<>();


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

    public static boolean isLoggedInSuperAdmin(String superAdminName){
        if (!superAdminsCache.containsKey(superAdminName))
            return false;
        return true;
    }

    public static boolean validateNamePassSuperAdmin(String superAdminName, String superAdminPass){
        Member member = getSuperAdmin(superAdminName);
        if (member == null || !member.login(superAdminPass))
            return false;
        return true;
    }

    public static boolean loginSuperAdmin(String superAdminName, String superAdminPass) {
        if (superAdminsCache.containsKey(superAdminName))
            return false;
        Member superAdmin = getSuperAdmin(superAdminName);
        superAdminsCache.put(superAdminName, superAdmin);
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
        if (null == forum) {
            System.out.println("shit0");
            return false;
        }
        Member member = forum.getMemberByName(userName);
        if (null == member) {
            System.out.println("shit1");
            return false;
        }
        if (!member.login(pass)){
            System.out.println("shit2");
            return false;
        }
        return true;
    }

    public static boolean logInMember(String forumName, String userName) {
        Forum forum  = ForumFacade.getForum(forumName);
        if (null == forum)
            return false;
        if (!forum.logInUser(userName))
            return false;
        return true;
    }

    public static boolean logOut(String forumName, String userName) {
        Forum forum  = ForumFacade.getForum(forumName);
        if (null == forum)
            return false;
        if (!forum.logOut(userName))
            return false;
        return true;
    }

    public static boolean registerMember(String forumName, String userName, String userPassword){
        Forum forum = ForumFacade.getForum(forumName);
        if (null == forum)
            return false;
        if (forum.registeredMember(userName, userPassword))
            return false;
        return true;
    }

    public static boolean isLoggedInMember(String forumName, String userName){
        Forum forum = ForumFacade.getForum(forumName);
        if (null == forum)
            return false;
        if (!forum.isLoggedInMember(userName))
            return false;
        return true;
    }

    public static boolean isRegisteredMember(String forumName, String userName){
        Forum forum = ForumFacade.getForum(forumName);
        if (null == forum)
            return false;
        if (!forum.isRegisteredMember(userName))
            return false;
        return true;
    }

    public static boolean addFriend(String forumName, String userName, String otherUserName) {
        if (userName.equals(otherUserName))
            return false;
        Member user = getMember(forumName, userName);
        Member otherUser = getMember(forumName, otherUserName);
        if (null == user || null == otherUser)
            return false;
        if (user.isFriendOf(otherUser))
            return false;
        user.addFriend(otherUser);
        otherUser.addFriend(user);
        return true;
    }

    public static Member getMember(String forumName, String userName){
        Forum forum = ForumFacade.getForum(forumName);
        if (forum == null)
            return null;
        Member member = forum.getMemberByName(userName);
        return member;
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

    public static boolean isForumManager(String forumName, String forumManagerName) {
        Forum forum = ForumFacade.getForum(forumName);
        if (null == forum)
            return false;
        return forum.isForumManager(forumManagerName);
    }

    public static boolean addModerator(String forumName, String subForumName, String moderatorName){
        Forum forum = ForumFacade.getForum(forumName);
        if (null == forum)
            return false;
        SubForum subForum = forum.getSubForumByName(subForumName);
        if (null == subForum)
            return false;
        Member moderator = forum.getMemberByName(moderatorName);
        if (null == moderator)
            return false;
        if (!subForum.addModerator(moderator))
            return false;
        return true;
    }

    public static boolean removeModerator(String forumName, String subForumName, String moderatorName){
        Forum forum = ForumFacade.getForum(forumName);
        if (null == forum)
            return false;
        SubForum subForum = forum.getSubForumByName(subForumName);
        if (null == subForum)
            return false;
        Member moderator = forum.getMemberByName(moderatorName);
        if (null == moderator)
            return false;
        if (!subForum.removeModerator(moderator))
            return false;
        return true;
    }

    public static boolean removeFriend(String forumName, String userName, String friendUserName) {
        if (userName.equals(friendUserName))
            return false;
        Member member = getMember(forumName, userName);
        Member friend = getMember(forumName, friendUserName);
        if (null == member || null == friendUserName)
            return false;
        if (!member.isFriendOf(friend))
            return false;
        member.remocveFriend(friend);
        friend.addFriend(member);
        return true;
    }

                    /*****************FOR TESTING******************/
                    /**********************************************/

    public static void resetSuperAdmins() {
        // TODO - users.clear();
        systemInitialized = false;
        superAdmins.clear();
        superAdminsCache.clear();
    }
    public static void resetForumMembers(String forumName) {
        // TODO - users.clear();
        Forum forum = ForumFacade.getForum(forumName);
        forum.resetMembers();
    }


    /*****************DB OPERATIONS******************/
    //TODO - change to ORM


}
