package org.bgu.stories.ForumStories;

import org.bgu.NewThreadCreatedTestBase;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

/**
 * Created by Sharon Kerzman on 25/06/2015.
 */
public class StressTest extends NewThreadCreatedTestBase {
    static final int COUNT = 200;
    static final int TIMEOUT = 30;

    private void performStress(Runnable[] runnables, String name){
        ExecutorService ex = Executors.newFixedThreadPool(COUNT);
        for (int i = 0; i < COUNT; i++){
            ex.submit(runnables[i]);
        }
        try {
            ex.awaitTermination(TIMEOUT, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new AssertionError("stress test failed for: " + name);
        }
    }

    @Test
    public void login_200users_expectToFinishInTime(){
        Runnable[] tasks = new Runnable[COUNT];
        for (Integer i = 0; i<COUNT; i++){
            final Integer finalI = i;
            tasks[i] = new Runnable() {
                @Override
                public void run() {
                    assertTrue("can not register member", bridge.register(FORUM_NAME, finalI.toString(), finalI.toString()));
                    assertTrue("can not regiter memeber", bridge.login(FORUM_NAME, finalI.toString(), finalI.toString()));
                    assertTrue("can not logout", bridge.logout());
                }
            };
        }

        performStress(tasks, "login");
    }

    @Test
    public void login_200NewThreads_expectToFinishInTime(){
        Runnable[] tasks = new Runnable[COUNT];
        assertTrue("can not regiter memeber", bridge.login(FORUM_NAME, MEMBER_NAME, MEMBER_PASS));
        for (Integer i = 0; i<COUNT; i++){
            final Integer finalI = i;
            tasks[i] = new Runnable() {
                @Override
                public void run() {
                    assertTrue("can not create new message", bridge.createNewThread(SUBFORUM_NAME,COMMENT_TITLE,COMMENT_BODY) > 0);
                }
            };
        }

        performStress(tasks, "createNewThread");
    }

    @Test
    public void login_200NewComments_expectToFinishInTime(){
        Runnable[] tasks = new Runnable[COUNT];
        assertTrue("can not regiter memeber", bridge.login(FORUM_NAME, MEMBER_NAME, MEMBER_PASS));
        for (Integer i = 0; i<COUNT; i++){
            final Integer finalI = i;
            tasks[i] = new Runnable() {
                @Override
                public void run() {
                    assertTrue("can not create new message", bridge.createNewComment(SUBFORUM_NAME, newThreadId, COMMENT_TITLE, COMMENT_BODY) > 0);
                }
            };
        }

        performStress(tasks, "createNewCommnets");
    }
}
