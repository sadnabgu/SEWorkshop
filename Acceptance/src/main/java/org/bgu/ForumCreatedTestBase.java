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
    public static final String MEMBER_2_NAME= "tzipi";
    public static final String MEMBER_2_PASS = "shmipi";

    protected Collection moderates;

    @Before
    public void init(){
        super.init();
        assertTrue("could not create forum", bridge.createNewForum(FORUM_NAME, MANAGER_NAME, MANAGER_PASS));
        assertTrue("could not log out admin", bridge.logout());
        moderates = new ArrayList();
        moderates.add(MODERATE_NAME);
    }

}
