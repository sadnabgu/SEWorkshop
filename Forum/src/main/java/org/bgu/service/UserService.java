package org.bgu.service;

import org.bgu.domain.facades.ForumFacade;
import org.bgu.domain.facades.UserFacade;
import org.bgu.domain.model.Member;
import org.bgu.domain.model.User;
import org.bgu.service.Exceptions.Result;
import org.bgu.service.Exceptions.RetObj;

/**
 * interface for the user management functionality
 * <p/>
 * Created by hodai on 4/18/15.
 */
public class UserService {
    /**
     *
     * @param forumName
     * @param userName
     * @param pass
     * @return
     */
    public static RetObj<Object> logIn(int sId, String forumName, String userName, String pass) {
        // only Guest can loggin
        if (!(UserFacade.validatePassword(forumName, userName, pass)))
            return new RetObj<>(Result.WRONG_USER_NAME_OR_PASS);
        if (!(UserFacade.logInMember(sId, forumName, userName)))
            return new RetObj<>(Result.ALREADY_LOGDIN);
        // identify user
        return new RetObj<>(Result.SUCCESS);
    }

    /**
     *
     * @param sId
     * @return
     */
    public static RetObj<Object> logOut(int sId) {
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
     public static RetObj<Object> registerMember(String ForumName, String userName, String pass) {
         // only guest can register new member
         if (!UserFacade.registerMember(ForumName, userName, pass))
             return new RetObj<>(Result.USERNAME_EXISTS);
         return new RetObj<>(Result.SUCCESS);
     }

    /**
     *
     * @param forumName
     * @param userName
     * @param otherUserName
     * @return
     */
    public static RetObj<Object> addFriend(String forumName, String userName, String otherUserName) {
        if (!UserFacade.isLoggedInMember(forumName, userName)){
            return new RetObj<>(Result.NOT_LOGGED_IN);
        }
        if (!UserFacade.isRegisteredMember(forumName, otherUserName))
            return new RetObj<>(Result.FRIEND_NOT_EXIST);

        if(!(UserFacade.addFriend(forumName, userName, otherUserName))){
            return new RetObj<>(Result.ALREADY_FRIENDS);
        }
        return new RetObj<>(Result.SUCCESS);
    }

    /**
     *
     * @param forumName
     * @param subForumName
     * @param forumManagerName
     * @param moderatorName
     * @return
     */
    public static RetObj<Object> addModerator(String forumName, String subForumName, String forumManagerName, String moderatorName) {
        if (!UserFacade.isLoggedInMember(forumName, forumManagerName))
            return new RetObj<>(Result.NOT_LOGGED_IN);
        if (!UserFacade.isForumManager(forumName, forumManagerName))
            return new RetObj<>(Result.MEMBER_NOT_FORUM_ADMIN);
        if (!UserFacade.addModerator(forumName, subForumName, moderatorName))
            return new RetObj<>(Result.ALREADY_MODERATE);
        return new RetObj<>(Result.SUCCESS);
    }

    /**
     *
     * @param forumName
     * @param userName
     * @param otherUserName
     * @return
     */
    public static RetObj<Object> unFriend(String forumName, String userName, String otherUserName) {
        if (!UserFacade.isLoggedInMember(forumName, userName)){
            return new RetObj<>(Result.NOT_LOGGED_IN);
        }
        if (!UserFacade.isRegisteredMember(forumName, otherUserName))
            return new RetObj<>(Result.FRIEND_NOT_EXIST);

        if(!(UserFacade.removeFriend(forumName, userName, otherUserName))){
            return new RetObj<>(Result.NOT_FRIENDS);
        }
        return new RetObj<>(Result.SUCCESS);
    }

    /**
     *
     * @param forumName
     * @param subForumName
     * @param forumManagerName
     * @param moderatorName
     * @return
     */
    public static RetObj<Object> removeModerator(String forumName, String subForumName, String forumManagerName, String moderatorName) {
        if (!UserFacade.isLoggedInMember(forumName, forumManagerName))
            return new RetObj<>(Result.NOT_LOGGED_IN);
        if (!UserFacade.isForumManager(forumName, forumManagerName))
            return new RetObj<>(Result.MEMBER_NOT_FORUM_ADMIN);
        if (!UserFacade.removeModerator(forumName, subForumName, moderatorName))
            return new RetObj<>(Result.NOT_MODERATOR);
        return new RetObj<>(Result.SUCCESS);
    }

}
