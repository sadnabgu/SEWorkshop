package org.bgu.domain.facades;

import org.bgu.domain.model.Guest;
import org.bgu.domain.model.Member;
import org.bgu.domain.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by gur on 20/04/2015.
 */
public class UserFacade {
    //TODO - need to decide ho we manage te users collections, as starting have only one collection

    /** static flag - indicate if the forum was initialized */
    private static boolean _initialized = false;
    private static Collection<Member> _superAdmins = new ArrayList<>();

    public static boolean isInitialized() {
        return _initialized;
    }

    public static Member createSuperAdmin(String adminName, String adminPass){
        if (_initialized){
           return null;
        }
        //TODO - should be a member or specific admin class?? what his email then?...
        Member admin = new Member(adminName, adminPass, null);
        if (admin != null)
            _superAdmins.add(admin);
        return admin;
    }

    public static Member loginSuperAdmin(String adminName, String adminPass) {
        if(!_initialized){
            return null;
        }
        for (Iterator<Member> iterator = _superAdmins.iterator(); iterator.hasNext(); ) {
            Member next =  iterator.next();
            if(next.getUserName() == adminName){
                if(next.login(adminPass)){
                    return next;
                } else {
                    // wrong user password or already connected
                    return null;
                }
            }
        }
        // user not found
        return null;
    }























    public static User createGuest() {
        return new Guest();
    }

    public static User loginMember(int forumId, int userId, String pass) {
        Member member = ForumFacade.getForum(forumId).getMember(userId);
        if (member.login(pass))
            return member;
        return null;
    }


    public static void memberLogOut(User member) {
        member.logOut();
    }

    public static boolean register (int forumId, int userName, String pass, String email){
        //TODO - validate that the details are valid!
        //TODO - if yes, send an appropriate e-mail to the user's mailbox
        User unvalidatedUser = new Member(userName, pass, email);
        _unValidatedUsers.add(unvalidatedUser);
        //TODO - make a time slot for the user to reply for email. After it is over, delete "on-hold" member form system
        return true;
    }

    public static User addMember(String userName) {
        for (Iterator<User> iterator = _unValidatedUsers.iterator(); iterator.hasNext(); ) {
            User next =  iterator.next();
            if(next.getUserName().equals(userName)){
                _unValidatedUsers.remove(next);
                _users.add(next);
                return next;
            }
        }
        return null;
    }


    /**
     * clear all the user database
     * used only for the testing
     */
    public static void resetUsers() {
        _users.clear();
        _superAdmins.clear();
        _initialized = false;
    }


}
