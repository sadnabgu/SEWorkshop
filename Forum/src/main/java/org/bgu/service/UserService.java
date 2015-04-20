package org.bgu.service;

import org.bgu.domain.facades.UserFacade;
import org.bgu.domain.model.Member;
import org.bgu.domain.model.User;

/**
 * interface for the user management functionality
 *
 * Created by hodai on 4/18/15.
 */
public class UserService {
    private User user;


    public UserService(){
        user = UserFacade.createGuest();
    }

    boolean logIn(String userName, String pass){
        // TODO - identify user
        user = UserFacade.getMember(userName, pass);
        if (user == null){
            return false;
        }
        return true;
    }

    void logOut(){
        // TODO - wadafak am I doing here ? :|
        UserFacade.memberLogOut(user);
    }

    /* create new member while user ask for signUp */
    boolean addMember(String userName,
                    String pass){
        Member result;
        //TODO -  validate parameters

        // if OK create and add the user
        result = UserFacade.addMember(userName, pass);
        if(result == null)
            return false;

        return true;
    }
}
