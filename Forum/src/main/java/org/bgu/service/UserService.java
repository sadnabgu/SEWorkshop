package org.bgu.service;

import org.bgu.domain.facades.UserFacade;
import org.bgu.domain.model.Guest;
import org.bgu.domain.model.Member;
import org.bgu.domain.model.User;

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
     * @return - true if login success
     */
    public boolean logIn(String userName, String pass){
        // only Guest can loggin
        if(isLoggedin()){
            return false;
        }
        // identify user
        user = UserFacade.getMember(_forumName, userName, pass);
        if (user == null){
            user = UserFacade.createGuest();
            return false;
        }
        return true;
    }

    public void logOut(){
        // TODO - wadafak am I doing here ? :|
        UserFacade.memberLogOut(user);
        user = UserFacade.createGuest();


    }

    /**
     * create new member, user not suppose to use this function directly
     */
    public boolean addMember(String userName,
                                    String pass){
        Member result;
        //TODO -  validate parameters

        // if OK create and add the user
        result = UserFacade.addMember(_forumName, userName, pass);
        if(result == null){
            return false;
        }

        return true;
    }

    /**
     * client oriented registration
     * TODO - add rest of the member properties and validate it
     * TODO - verify by mail (left for release 2??)
     * @param userName - unique user name (id)
     * @param pass - the new user password
     * @return true if success
     */
    public boolean registerMember(String userName,
                                  String pass){
        // only guest can register new member
        if(isLoggedin()){
            return false;
        }
        // TODO - validate permisions??
        if(!addMember(userName, pass)){
            return false;
        }

        return logIn(userName, pass);
    }

    public User getUser() {
        return user;
    }

    public boolean isLoggedin() {
        //TODO - find better way to check if its Guest
        return (user.getClass() != Guest.class);
    }

    public Member getUserAsMember() {
        //TODO - refactor
        if (user.getClass() == Guest.class)
            return null;

        return (Member)user;
    }
}
