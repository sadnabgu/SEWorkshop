package org.bgu;

/**
 * Created by gur on 28/04/2015.
 */
public class ForumTestDriver {
    public static ForumTestDriver create() {
       return new ForumTestDriver();
    }

    public SubForumTestDriver createNewSubForum(){
        return new SubForumTestDriver();
    }

    public SignUpTestDriver clickSignUp(MemberDriver member) {
        return new SignUpTestDriver(member);
    }
}
