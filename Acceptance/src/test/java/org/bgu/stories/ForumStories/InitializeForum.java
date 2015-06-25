package org.bgu.stories.ForumStories;

import org.bgu.TestBase;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Sharon Kerzman on 23/06/2015.
 */
public class InitializeForum extends TestBase {
    @Test
    @Ignore
    public void initializeSystem_pass_testID_1_1() {
        assertTrue("system is not initialized", bridge.initSystem(ADMIN1_NAME, ADMIN1_PASS));
    }

    @Test
    public void initializeSystem_noUserName_false_testID_1_2() {
        assertFalse("system is initialized when is should not be", bridge.initSystem("", ADMIN1_PASS));
    }

    @Test
    public void initializeSystem_noPassword_false_testID_1_1() {
        assertFalse("system is initialized when is should not be", bridge.initSystem(ADMIN1_NAME, ""));
    }

}
