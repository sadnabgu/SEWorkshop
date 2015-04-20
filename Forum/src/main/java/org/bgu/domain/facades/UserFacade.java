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

    private static Collection<User> users = new ArrayList<>();
    private static Collection<Member> superAdmins = new ArrayList<>();

    public static User createGuest() {
        //TODO - ??
        return new Guest();
    }

    public static User getMember(String userName, String pass) {
        for (Iterator<User> iterator = users.iterator(); iterator.hasNext(); ) {
            User next =  iterator.next();
            if(next.getUserName() == userName){
                if(next.login(pass)){
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

    public static void memberLogOut(User user) {
        user.logOut();
    }

    public static Member createSuperAdmin(String adminName, String adminPass) {
        Member admin = new Member(adminName, adminPass); //TODO - should be a member or specific admin class??
        if (admin == null)
            return null;
        superAdmins.add(admin);
        return admin;
    }

    public static Member loginSuperAdmin(String adminName, String adminPass) {
        for (Iterator<Member> iterator = superAdmins.iterator(); iterator.hasNext(); ) {
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

    public static Member addMember(String userName, String pass) {
        Member member = new Member(userName, pass); //TODO - should be a member or specific admin class??
        if (member == null)
            return null;
        users.add(member);
        return member;
    }
}
