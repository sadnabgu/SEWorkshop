package org.bgu.stories.ForumStories;

import org.bgu.NewThreadCreatedTestBase;
import org.bgu.TestBase;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Sharon Kerzman on 25/06/2015.
 */
public class VolumeTest extends NewThreadCreatedTestBase {
    static final int COUNT = 200;
    static final int TIMEOUT = 30000;

    private void performVolume(Runnable runnable, String name) {
        Thread thread = new Thread(runnable);
        try {
            for (int i = 0; i < COUNT; i++) {
                thread.start();
                thread.join(TIMEOUT);
            }
        } catch (InterruptedException e) {
            throw new AssertionError("stress test failed for: " + name);
        }
    }

    @Test
    @Ignore
    public void login_200users_expectToFinishInTime() {
        final Integer[] index = new Integer[1];
        index[0] = 0;
        Runnable task = new Runnable() {
            @Override
            public void run() {
                index[0]++;
                assertTrue("can not register member", bridge.register(FORUM_NAME, index[0].toString(), index[0].toString()));
                assertTrue("can not regiter memeber", bridge.login(FORUM_NAME, index[0].toString(), index[0].toString()));
                assertTrue("can not logout", bridge.logout());
            }
        };

        performVolume(task, "log-in");
    }
}
