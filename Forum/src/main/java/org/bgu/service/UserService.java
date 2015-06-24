package org.bgu.service;

import org.bgu.domain.facades.UserFacade;
import org.bgu.service.ServiceObjects.Result;
import org.bgu.service.ServiceObjects.RetObj;

import java.util.UUID;

/**
 * interface for the user management functionality
 * <p/>
 * Created by hodai on 4/18/15.
 */
public class UserService {

    public static RetObj<Boolean> isInitializedSystem(){
        boolean ans = UserFacade.isInitializedSystem();
        return new RetObj<>(Result.SUCCESS, ans);
    }

    public static RetObj<UUID> logInGuest(String forumName) {
        UUID sId = UserFacade.addGuestSession(forumName);

        if (null == sId)
            return new RetObj<>(Result.FORUM_NOT_EXISTS);
        // identify user
        return new RetObj<>(Result.SUCCESS, sId);
    }

    /**
     *
     * @param sId
     * @param userName
     * @param pass
     * @return
     */
    public static RetObj<Void> logInMember(UUID sId, String userName, String pass) {

        // only Guest can loggin
        if (!(UserFacade.validatePassword(sId, userName, pass)))
            return new RetObj<>(Result.WRONG_USER_NAME_OR_PASS);

        if (!(UserFacade.logInMember(sId, userName)))
            return new RetObj<>(Result.ALREADY_LOGDIN);
        // identify user
        return new RetObj<>(Result.SUCCESS);
    }

    /**
     *
     * @param sId
     * @return
     */
    public static RetObj<Object> logOut(UUID sId) {
        if (!UserFacade.logOut(sId))
            return new RetObj<>(Result.NOT_LOGGED_IN);
        return new RetObj<>(Result.SUCCESS);
    }

     /**
     * client oriented registration
     * TODO - add rest of the member properties and validate it
     * TODO - verify by mail
     *
     * @param userName - unique user name (id)
     * @param pass - the new user password
     * @return Result.SUCCESS if sucsses
     */
     public static RetObj<Object> registerMember(UUID sId, String userName, String pass) {
         // only guest can register new member
         if (!UserFacade.registerMember(sId, userName, pass))
             return new RetObj<>(Result.USERNAME_EXISTS);
         return new RetObj<>(Result.SUCCESS);
     }

    /**
     *
     * @param sId
     * @param otherUserName
     * @return
     */
    public static RetObj<Object> addFriend(UUID sId, String otherUserName) {
        if (!UserFacade.isLoggedInMember(sId)){
            return new RetObj<>(Result.NOT_LOGGED_IN);
        }
        if (!UserFacade.isRegisteredMember(sId, otherUserName))
            return new RetObj<>(Result.FRIEND_NOT_EXIST);

        if(!(UserFacade.addFriend(sId, otherUserName))){
            return new RetObj<>(Result.ALREADY_FRIENDS);
        }
        return new RetObj<>(Result.SUCCESS);
    }

    /**
     *
     * @param sId
     * @param subForumName
     * @param moderatorName
     * @return
     */
    public static RetObj<Object> addModerator(UUID sId, String subForumName, String moderatorName) {
        if (!UserFacade.isLoggedInMember(sId))
            return new RetObj<>(Result.NOT_LOGGED_IN);
        if (!UserFacade.isForumManager(sId))
            return new RetObj<>(Result.MEMBER_NOT_FORUM_ADMIN);
        if (!UserFacade.addModerator(sId, subForumName, moderatorName))
            return new RetObj<>(Result.ALREADY_MODERATE);
        return new RetObj<>(Result.SUCCESS);
    }

    /**
     *
     * @param sId
     * @param otherUserName
     * @return
     */
    public static RetObj<Object> unFriend(UUID sId, String otherUserName) {
        if (!UserFacade.isLoggedInMember(sId)){
            return new RetObj<>(Result.NOT_LOGGED_IN);
        }
        if (!UserFacade.isRegisteredMember(sId, otherUserName))
            return new RetObj<>(Result.FRIEND_NOT_EXIST);

        if(!(UserFacade.removeFriend(sId, otherUserName))){
            return new RetObj<>(Result.NOT_FRIENDS);
        }
        return new RetObj<>(Result.SUCCESS);
    }

    /**
     *
     * @param sId
     * @param subForumName
     * @param moderatorName
     * @return
     */
    public static RetObj<Object> removeModerator(UUID sId, String subForumName, String moderatorName) {
        if (!UserFacade.isLoggedInMember(sId))
            return new RetObj<>(Result.NOT_LOGGED_IN);
        if (!UserFacade.isForumManager(sId))
            return new RetObj<>(Result.MEMBER_NOT_FORUM_ADMIN);
        if (!UserFacade.removeModerator(sId, subForumName, moderatorName))
            return new RetObj<>(Result.NOT_MODERATOR);
        return new RetObj<>(Result.SUCCESS);
    }

    /**
     * retorn the name of the member registred for the given sid
     * @param sId - session id
     * @return - the name of the member or Guest if not login
     */
    public static RetObj<String> getUserName(UUID sId) {
        String name = UserFacade.getSessionUserName(sId);
        if(null == name)
            return new RetObj<>(Result.FAIL);
        return new RetObj<>(Result.SUCCESS, name);
    }
}
