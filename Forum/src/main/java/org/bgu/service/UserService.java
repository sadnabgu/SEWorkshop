package org.bgu.service;

import org.bgu.domain.facades.UserFacade;
import org.bgu.domain.model.Member;
import org.bgu.domain.model.User;
import org.bgu.service.Exceptions.Result;

/**
 * interface for the user management functionality
 *
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
    public UserService(String forumName){
        _forumName = forumName;
        user = UserFacade.createGuest();
    }

    /**
     * login user, change user from guest to relevant member
     *
     * @param userName - user name
     * @param pass - user password
     * @return Result.SUCCESS upon success
     */
    public Result logIn(String userName, String pass){
        // only Guest can loggin
        if(isLogedin()){
            return Result.ALREADY_LOGDIN;
        }
        // identify user
        user = UserFacade.loginMember(_forumName, userName, pass);
        if (user == null){
            user = UserFacade.createGuest();
            return Result.WRONG_USER_PASS;
        }
        return Result.SUCCESS;
    }

    /**
     * logout the user from system (chang his type to Guest)
     *
     * TODO - wadafak am I doing here ? :|
     *
     * @return - Result.SUCCESS upon success,
     *           or Result.FAIL if user is not login.
     */
    public Result logOut(){
        // guest can't logout
        if (!isLogedin())
            return Result.FAIL;

        UserFacade.memberLogOut(user);
        user = UserFacade.createGuest();

        return Result.SUCCESS;
    }

     /**
     * client oriented registration
     * TODO - add rest of the member properties and validate it
     * TODO - verify by mail
     * @param userName - unique user name (id)
     * @param pass - the new user password
     * @return Result.SUCCESS if sucsses
     */
    public Result registerMember(String userName,
                                  String pass){
        Result result;
        // only guest can register new member
        if(isLogedin()){
            return Result.ALREADY_LOGDIN;
        }
        // TODO - validate permisions??
        result = UserFacade.addMember(_forumName, userName, pass);

        if(result != Result.SUCCESS)
            return result;

        // try to login
        return logIn(userName, pass);
    }

    public User getUser() {
        return user;
    }

    /**
     * return true if user is loged in
     * @return true if user is loged in
     */
    public boolean isLogedin() {
        return (user.getMember() != null);
    }

    public Result addFriend(String otherUserName){
        if(!isLogedin()) {
            return Result.NOT_LOGGED_IN;
        }
        Member friend = UserFacade.getUser(_forumName, otherUserName).getMember();
        if (friend == null){
            return Result.FRIEND_NOT_EXIST;
        }
        if(!(UserFacade.addFriend(user.getMember(), friend))){
            return Result.ALREADY_FRIENDS;
        }
        return Result.SUCCESS;

    }

    /**
     * @return - the user as Member object
     */
    public Member getUserAsMember() {
        return user.getMember();
    }
}
