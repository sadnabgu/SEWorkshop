package org.bgu.service;

import org.bgu.communication.server.Server;
import org.bgu.service.Exceptions.ForumException;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by gur on 09/06/2015.
 */
public class ServerTest {@Ignore

    @Test
    public void test() throws InterruptedException, ForumException {
        String[] args = new String[3];
        //"Usage: <port> <pool_size> <reactor/tpc>"
        args[0] = "12345";
        args[1] = "20";
        args[2] = "reactor";

        Server.start(args);
    }
}