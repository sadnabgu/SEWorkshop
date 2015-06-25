package org.bgu;

import org.apache.log4j.Logger;
import org.bgu.communication.server.Server;
import org.bgu.httpServer.SimpleHttpServer;
import org.bgu.service.ServiceObjects.ForumException;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by hodai on 6/24/15.
 */
public class Main {

    public static void main(String[] args) {
        Logger logger = Logger.getLogger(Main.class + "sid");
        logger.info("starting...");
        // usage: <desktop port> <web port>
        if(args.length != 2){
            // set default ports
            args = new String[2];
            args[0] = "12345";
            args[1] = "80";
            /*[[
            System.err.println("Usage: <desktop port> <web port>");
            Sy-stem.exit(1);
            */
        }

        // web server
        String[] webArgs = new String[1];
        //"Usage: <port>"
        webArgs[0] = args[1];
        try {
            SimpleHttpServer.start(webArgs);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // desktopServer
        String[] desktopArgs = new String[3];
        //"Usage: <port> <pool_size> <reactor/tpc>"
        desktopArgs[0] = args[0];
        desktopArgs[1] = "20";
        desktopArgs[2] = "reactor";

        Server.start(desktopArgs);

    }
}
