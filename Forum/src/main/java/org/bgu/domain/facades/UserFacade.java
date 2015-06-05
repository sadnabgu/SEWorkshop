package org.bgu.domain.facades;

import org.bgu.domain.model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by gur on 20/04/2015.
 */
public class UserFacade {
    //TODO - need to decide ho we manage te users collections, as starting have only one collection

    private static Collection<Member> superAdmins = new ArrayList<>();

    public static User createGuest() {
        //TODO - ??
        return new Guest();
    }

    public static User getMember(String forumName, String userName, String pass) {
        Forum forum = ForumFacade.getForum(forumName);
        if (forum == null)
            return null;
        Collection<Member> members = forum.getMembers();

        for (Iterator<Member> memberIterator = members.iterator(); memberIterator.hasNext(); ) {
            User next =  memberIterator.next();
            if(next.getUserName().equals(userName)){
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
            if(next.getUserName().equals(adminName)){
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

    public static Member addMember(String forumName, String userName, String pass) {
        // TODO refactor
        Forum forum = ForumFacade.getForum(forumName);
        if (forum == null)
            return null;
        Collection<Member> members = forum.getMembers();

        // check that the userName not exist
        for (Iterator<Member> iterator = members.iterator(); iterator.hasNext(); ) {
            User next =  iterator.next();
            if(next.getUserName().equals(userName)){
                return null;
            }
        }

        //TODO - password / user policy
        Member member = new Member(userName, pass); //TODO - should be a member or specific admin class??

        members.add(member);
        return member;
    }

    /**
     * clear all the user database
     * used only for the testing
     */
    public static void resetUsers() {
        // TODO - users.clear();
        superAdmins.clear();
    }


}
