package org.bgu.communication;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.concurrent.Executors;

/**
 * Created by gur on 06/06/2015.
 */
public class ComServer {
    private final int port;
    private final Collection<HttpContext> contexts;
    private final Hashtable<String, HttpHandler> requestHandlers;
    private final HttpServer server;

    public ComServer (int port) throws IOException {
        this.port = port;
        this.contexts = new ArrayList<>();
        this.requestHandlers = new Hashtable<>();
        server = HttpServer.create(new InetSocketAddress(port), 0);
    }

    public void listen() throws IOException {
        server.setExecutor(Executors.newSingleThreadExecutor());
        server.start();
    }

    public void stop(){
        server.stop(0);
    }

    public void addRequestHandler(String route, BaseRequestHandler handler){
        contexts.add(server.createContext(route, handler));
    }
}
