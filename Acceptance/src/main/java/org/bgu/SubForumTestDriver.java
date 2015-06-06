package org.bgu;

/**
 * Created by Sharon Kerzman on 12/05/2015.
 */
public class SubForumTestDriver {
        public static SubForumTestDriver create() {
            return new SubForumTestDriver();
        }

        public SignUpTestDriver clickSignUp(MemberDriver member) {
            return new SignUpTestDriver(member);
        }
}
