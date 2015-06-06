package org.bgu.stories.usersStories;

import org.bgu.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;

/**
 * Created by Sharon Kerzman on 12/05/2015.
 */
public class TestBase {
    DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
    MemberCredentials credentials;
    ForumTestDriver forumTestDriver;
    MemberDriver member;
    SignUpTestDriver signUpTestDriver;
    MembersListDriver members;

    @Before
    public void init(){
        credentials = new MemberCredentials("sharon@kerzman", "kerzman", dtf.parseDateTime("16/3/1989"), "123456", "123456", "sharon", "kerzman");
        // Simulate guest clicks sign up button
        forumTestDriver = ForumTestDriver.create();

        member = new MemberDriver(credentials);
        signUpTestDriver = forumTestDriver.clickSignUp(member);

        members = signUpTestDriver.clickSignUp();
    }

}
