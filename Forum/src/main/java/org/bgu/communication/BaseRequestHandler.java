package org.bgu.communication;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Created by gur on 06/06/2015.
 */
public abstract class BaseRequestHandler implements HttpHandler {

    private void redirect(HttpExchange exchange, String uri) throws IOException {
        exchange.getResponseHeaders().add("location", uri);
        exchange.sendResponseHeaders(302, uri.length());
        exchange.getResponseBody().write(uri.getBytes());
        exchange.getResponseBody().close();
    }

    private void error(HttpExchange exchange, String message) throws IOException {
        exchange.sendResponseHeaders(401, message.length());
        exchange.getResponseBody().write(message.getBytes());
        exchange.getResponseBody().close();
    }

    protected String getRequestString(HttpExchange exchange) throws IOException{
        StringBuilder str = new StringBuilder();
        byte[] buffer = new byte[1024];
        int length;

        length = exchange.getRequestBody().read(buffer,0 , buffer.length);
        while (length > 0){
            str.append(new String(buffer, 0, length, StandardCharsets.UTF_8));
            length = exchange.getRequestBody().read(buffer,0 , buffer.length);
        }

        return str.toString();
    }

    public void handle(HttpExchange exchange) throws IOException{
        String method = exchange.getRequestMethod().toUpperCase();
        switch (method){
            case "GET":
                redirect(exchange, "http://www.google.com"); //TODO: better redirection
                break;
            case "POST":
                handlePostRequest(exchange);
                break;
            default:
                error(exchange, "UNEXPECTED HTTP METHOD");
                break;
        }
    }

    protected abstract void handlePostRequest(HttpExchange exchange) throws IOException;
}
