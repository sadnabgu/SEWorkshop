package org.bgu.service;

import org.bgu.communication.server.Server;
import org.bgu.domain.facades.ForumFacade;
import org.bgu.httpServer.SimpleHttpServer;
import org.bgu.service.ServiceObjects.ForumException;
import org.bgu.service.ServiceObjects.RetObj;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * Created by gur on 09/06/2015.
 */
public class ServerTest {

    @Ignore
    @BeforeClass
    public static void prepareBasicForum(){
        // TODO - just for testing
        ForumFacade.resetForums();
        ForumFacade.createForum("sex", "hodai", "123");
        RetObj<UUID> retObj = UserService.logInGuest("sex");
        UserService.logInMember(retObj._value,"hodai", "123");
        Collection<String> mod = new ArrayList<>();
        mod.add("hodai");
        ForumService.addNewSubForum(retObj._value, "protection",mod);
        ForumService.addNewSubForum(retObj._value, "Ilan's Mom",mod);
        RetObj<Integer> retMsg = ForumService.addNewThread(retObj._value, "protection", "thread1", "body 1");
        ForumService.addNewThread(retObj._value, "protection", "thread2", "body 2");
        ForumService.addNewThread(retObj._value, "Ilan's Mom", "thread3", "body 3");
        ForumService.postNewComment(retObj._value,"protection", retMsg._value, "comment1 title", "comment1 bode");
        retMsg = ForumService.postNewComment(retObj._value,"protection", retMsg._value, "comment2 title", "comment2 bode");
        ForumService.postNewComment(retObj._value,"protection", retMsg._value, "comment3 title", "comment3 bode");

        UserService.logOut(retObj._value);
    }

    @Test
    public void test() throws InterruptedException, ForumException {
        String[] args = new String[3];
        //"Usage: <port> <pool_size> <reactor/tpc>"
        args[0] = "12345";
        args[1] = "20";
        args[2] = "reactor";

        Server.start(args);
    }

    @Ignore
    @Test
    public void runWebServer() throws Exception {
        String[] args = new String[1];
        //"Usage: <port>"
        args[0] = "8080";

        SimpleHttpServer.start(args);
        while (true){
            Thread.sleep(1000);
        }
    }
}