package org.bgu;

import org.junit.Before;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import static org.junit.Assert.assertTrue;

/**
 * Created by Sharon Kerzman on 23/06/2015.
 */
public class ForumCreatedTestBase extends InitializedTestBase{
    public static final String SUBFORUM_NAME = "MILK";
    public static final String MODERATE_NAME = "kerz";
    public static final String MODERATE_PASS = "the cowboy";
    protected Collection moderates;

    @Before
    public void init(){
        super.init();
        assertTrue("could not create forum", bridge.createNewForum(FORUM_NAME, MANAGER_NAME, MANAGER_PASS));
        assertTrue("could not log out admin", bridge.logout());
        assertTrue("could not register member", bridge.register(MEMBER_NAME, MEMBER_PASS));
        moderates = new ArrayList();
        moderates.add(MODERATE_NAME);
    }

}
