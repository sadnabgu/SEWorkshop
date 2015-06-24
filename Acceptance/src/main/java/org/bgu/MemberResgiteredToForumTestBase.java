package org.bgu;

import org.junit.Before;

import static org.junit.Assert.assertTrue;

/**
 * Created by Sharon Kerzman on 24/06/2015.
 */
public class MemberResgiteredToForumTestBase extends ForumCreatedTestBase {
    @Before
    public void init(){
        super.init();
        assertTrue("could not register member", bridge.register(FORUM_NAME, MEMBER_NAME, MEMBER_PASS));
    }
}
