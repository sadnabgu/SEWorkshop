package org.bgu.service;

import org.bgu.domain.facades.UserFacade;
import org.bgu.domain.model.Forum;
import org.bgu.domain.model.Guest;
import org.bgu.domain.model.Member;
import org.bgu.domain.model.User;

/**
 * interface for the user management functionality
 *
 * Created by hodai on 4/18/15.
 */
public class UserService {

    private final int _forumId;
    private User _user;
    /**
     * construct user service per client (connection)
     * initial state the client identify as Guest
     */
    public UserService(int forumId){
        _forumId = forumId;
        _user = UserFacade.createGuest();
    }

    public  boolean logIn(int userId, String pass){
        // only Guest can login
        if(isLoggedin()){
            return false;
        }
        _user = UserFacade.loginMember(_forumId, userId, pass);
        if (_user == null){
            _user = UserFacade.createGuest();
            return false;
        }
        return true;
    }

    public void logOut(){
        UserFacade.memberLogOut(_user);
        _user = UserFacade.createGuest();
    }

    /**
     *
     * @param userName
     * @param pass
     * @param email
     * @return true if a user entered legal data, and an email was sent to him in order to finish registration process.
     * false otherwise
     */
    public boolean registerMember(int userId, String pass, String email){
        return UserFacade.register(_forumId, userId, pass, email);
    }

    public boolean emailValidation(int userId){
        _user = UserFacade.addMember(userId);
        if (_user == null)
            return false;
        return true;
    }

    public User getUser() {
        return _user;
    }

    public boolean isLoggedin() {
        //TODO - find better way to check if its Guest
        return (_user.getClass() != Guest.class);
    }

}
