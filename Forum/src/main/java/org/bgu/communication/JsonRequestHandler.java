package org.bgu.communication;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * Created by gur on 06/06/2015.
 */
public abstract class JsonRequestHandler extends BaseRequestHandler {
    protected void handlePostRequest(HttpExchange exchange) throws IOException {
        JSONParser parser = new JSONParser();
        Gson gson = new Gson();
        try {
            Object requestBody = parser.parse(getRequestString(exchange));
            Object response = processRequest(requestBody);
            String responseString = gson.toJson(response);
            ByteBuffer responseData = StandardCharsets.UTF_8.encode(responseString);
            exchange.sendResponseHeaders(200, responseData.array().length);
            exchange.getResponseBody().write(responseData.array());
            exchange.getResponseBody().close();
        } catch (ParseException ex) {
            throw new IOException(ex);
        }
    }

    protected abstract Object processRequest(Object request);
}
