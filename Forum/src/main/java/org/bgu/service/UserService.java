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
    private User user;

    /**
     * construct user service per client (connection)
     * initial state the client identify as Guest
     */
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
        user = UserFacade.createGuest();


    }

    /* create new member while user ask for signUp */
    public static boolean addMember(String userName,
                                    String pass){
        Member result;
        //TODO -  validate parameters

        // if OK create and add the user
        result = UserFacade.addMember(userName, pass);
        if(result == null)
            return false;

        return true;
    }

    /**
     * client oriented registration
     * TODO - add rest of the member properties and validate it
     * @param userName - unique user name (id)
     * @param pass - the new user password
     * @return true if success
     */
    public boolean registerMember(String userName,
                                  String pass){
        // only guest can register new member
        if(user.getClass() != Guest.class){  //TODO - find better way to check if its Guest
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
}
