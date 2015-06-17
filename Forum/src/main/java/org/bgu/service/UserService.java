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
    private final String _forumName;
    private User user;

    /**
     * construct user service per client (connection)
     * initial state the client identify as Guest
     *
     * @param forumName - the forum we want to login to
     */
    public UserService(String forumName) {
        _forumName = forumName;
        user = UserFacade.createGuest();
    }

    /**
     * login user, change user from guest to relevant member
     *
     * @param userName - user name
     * @param pass     - user password
     * @return Result.SUCCESS upon success
     */
    public RetObj<Object> logIn(String userName, String pass) {
        // only Guest can loggin
        if (isLogedin()) {
            return new RetObj<>(Result.ALREADY_LOGDIN);
        }
        // identify user
        user = UserFacade.loginMember(_forumName, userName, pass);
        if (user == null) {
            user = UserFacade.createGuest();
            return new RetObj<>(Result.WRONG_USER_PASS);
        }
        return new RetObj<>(Result.SUCCESS);
    }

    /**
     * logout the user from system (chang his type to Guest)
     * <p/>
     * TODO - wadafak am I doing here ? :|
     *
     * @return - Result.SUCCESS upon success,
     *           or Result.FAIL if user is not login.
     */
    public RetObj<Object> logOut() {
        // guest can't logout
        if (!isLogedin())
            return new RetObj<>(Result.FAIL);

        UserFacade.memberLogOut(user);
        user = UserFacade.createGuest();

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
     public RetObj<Object> registerMember(String userName, String pass) {
         Result result;
         // only guest can register new member
         if (isLogedin()) {
             return new RetObj<>(Result.ALREADY_LOGDIN);
         }
         // TODO - validate permisions??
         result = UserFacade.addMember(_forumName, userName, pass);

         if (result != Result.SUCCESS)
             return new RetObj<>(result);

         // try to login
         return logIn(userName, pass);
     }

    // TODO - make it private or protected
    public User getUser() {
        return user;
    }

    /**
     * return true if user is loged in
     *
     * @return true if user is loged in
     */
    public boolean isLogedin() {
        return (user.getMember() != null);
    }

    public RetObj<Object> addFriend(String otherUserName) {
        if(!isLogedin()) {
            return new RetObj<>(Result.NOT_LOGGED_IN);
        }
        Member friend = UserFacade.getUser(_forumName, otherUserName).getMember();
        if (friend == null){
            return new RetObj<>(Result.FRIEND_NOT_EXIST);
        }
        if(!(UserFacade.addFriend(user.getMember(), friend))){
            return new RetObj<>(Result.ALREADY_FRIENDS);
        }
        return new RetObj<>(Result.SUCCESS);
    }

    public RetObj<Object> removeFriend(String otherUserName) {
        if (!isLogedin()) {
            return new RetObj<>(Result.NOT_LOGGED_IN);
        }
        Member friend = UserFacade.getUser(_forumName, otherUserName).getMember();
        if (friend == null) {
            return new RetObj<>(Result.FRIEND_NOT_EXIST);
        }
        if (!(UserFacade.removeFriend(user.getMember(), friend))) {
            return new RetObj<>(Result.NOT_FRIENDS);
        }
        return new RetObj<>(Result.SUCCESS);
    }

    public RetObj<Object> addModerator(String subForumName, String otherUserName) {
        if (!isLogedin()) {
            return new RetObj<>(Result.NOT_LOGGED_IN);
        }
        if (!ForumFacade.isManager(_forumName, user.getMember())) {
            return new RetObj<>(Result.MEMBER_NOT_FORUM_ADMIN);
        }
        if (ForumFacade.getForum(_forumName).getSubForum(subForumName) == null) {
            return new RetObj<>(Result.FORUM_NOT_FOUND);
        }
        Member moderate = UserFacade.getUser(_forumName, otherUserName).getMember();
        if (moderate == null) {
            return new RetObj<>(Result.MEMBER_NOT_FOUND);
        }
        if (!(ForumFacade.addModerate(_forumName, subForumName, moderate))) {
            return new RetObj<>(Result.ALREADY_MODERATE);
        }
        return new RetObj<>(Result.SUCCESS);
    }

    public RetObj<Object> removeModerator(String subForumName, String otherUserName) {
        if (!isLogedin()) {
            return new RetObj<>(Result.NOT_LOGGED_IN);
        }
        if (!ForumFacade.isManager(_forumName, user.getMember())) {
            return new RetObj<>(Result.MEMBER_NOT_FORUM_ADMIN);
        }
        if (ForumFacade.getForum(_forumName).getSubForum(subForumName) == null) {
            return new RetObj<>(Result.FORUM_NOT_FOUND);
        }
        Member moderate = UserFacade.getUser(_forumName, otherUserName).getMember();
        if (moderate == null) {
            return new RetObj<>(Result.MEMBER_NOT_FOUND);
        }
        if (!(ForumFacade.removeModerate(_forumName, subForumName, moderate))) {
            return new RetObj<>(Result.NOT_A_MODERATE);
        }
        return new RetObj<>(Result.SUCCESS);
    }

    /**
     * @return - the user as Member object
     * TODO - internal - make it private or protected
     */
    public Member getUserAsMember() {
        return user.getMember();
    }
}
