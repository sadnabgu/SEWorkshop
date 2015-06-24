package org.bgu.httpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.bgu.domain.facades.ForumFacade;
import org.bgu.service.ForumService;
import org.bgu.service.ServiceObjects.Result;
import org.bgu.service.ServiceObjects.RetObj;
import org.bgu.service.UserService;

/**
 * Created by hodai on 6/23/15.
 */
public class SimpleHttpServer {

    public static void main(String[] args) throws Exception {

        // TODO - just for testing
        ForumFacade.createForum("sex", "hodai", "123");
        RetObj<UUID> retObj = UserService.logInGuest("sex");
        UserService.logInMember(retObj._value,"hodai", "123");
        Collection<String> mod = new ArrayList<>();
        mod.add("hodai");
        ForumService.addNewSubForum(retObj._value, "protection",mod);
        ForumService.addNewSubForum(retObj._value, "Ilan's Mom",mod);
        UserService.logOut(retObj._value);



        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange httpExchange) throws IOException {

                String forumName = getSafeForumName(httpExchange);
                if(null == forumName){
                    sendError(httpExchange, "forum not found");
                    return;
                }

                Map<String, String> params = queryToMap(httpExchange.getRequestURI().getQuery());
                String sid;
                if (params.containsKey("sid")) {
                    sid = params.get("sid");
                    sendSubForums(httpExchange, sid, params, forumName);
                    return;
                } else {
                    sendWelcomeGuestPage(httpExchange, forumName);
                    return;
                }

            }
        });
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    private static void sendSubForums(HttpExchange httpExchange, String sid,  Map<String, String> params, String forumName) {
        HtmlBuilder htmlBuilder = new HtmlBuilder();
        if (params.containsKey("subforum"))
            params.remove("subforum");
        htmlBuilder.setParams(params);

        RetObj<ArrayList<String>> retObj = ForumService.getAllSubForums(UUID.fromString(sid));
        if (Result.SUCCESS != retObj._result){
            sendError(httpExchange, retObj._result.toString());
            return;
        }

        htmlBuilder.addSubForum(retObj._value);

        RetObj<String> memberName = UserService.getUserName(UUID.fromString(sid));
        if (Result.SUCCESS != memberName._result){
            sendError(httpExchange, retObj._result.toString());
            return;
        }

        sendResponse(httpExchange, 200, htmlBuilder.buildSubForumsPage(memberName._value, forumName));
    }

    private static void sendWelcomeGuestPage(HttpExchange httpExchange, String forumName) {
        // create Guest session
        RetObj<UUID> retObjSid = UserService.logInGuest(forumName);
        if (Result.SUCCESS != retObjSid._result)
            sendError(httpExchange, "unexpected error: " + retObjSid._result.toString());


        RetObj<ArrayList<String>> retObjForums = ForumService.getAllSubForums(retObjSid._value);
        if (Result.SUCCESS != retObjForums._result){
            sendError(httpExchange, retObjForums._result.toString());
        }

        Map<String, String> params = new HashMap<>();
        params.put("sid", retObjSid._value.toString());

        HtmlBuilder htmlBuilder = new HtmlBuilder();
        htmlBuilder.setParams(params);
        htmlBuilder.addSubForum(retObjForums._value);
        sendResponse(httpExchange, 200, htmlBuilder.buildSubForumsPage("Guest", forumName));

    }

    private static void sendError(HttpExchange httpExchange, String msg) {
        StringBuilder sb = new StringBuilder();
        sb.append(HtmlDesign.HTML_TOP);
        sb.append(msg);
        sb.append(HtmlDesign.HTML_BOTTOM);

        sendResponse(httpExchange, 404, sb.toString());
    }

    private static void sendResponse(HttpExchange httpExchange, int statusCode, String response) {
        try {
            httpExchange.sendResponseHeaders(statusCode, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getSafeForumName(HttpExchange httpExchange) {

        String forumName = httpExchange.getRequestURI().getPath().substring(1).split("/")[0];
        RetObj<ArrayList<String>> retObj = ForumService.getAllForums();
        if(retObj._result != Result.SUCCESS)
            return null;
        for (String f : retObj._value){
            if(forumName.equals(f))
                return forumName;
        }
        return null;
    }


    /**
     * returns the url parameters in a map
     * @param query
     * @return map
     */
    public static Map<String, String> queryToMap(final String query){
        Map<String, String> result = new HashMap<>();
        if (null == query)
            return result;

        for (String param : query.split("&")) {
            String pair[] = param.split("=");
            if (pair.length>1) {
                result.put(pair[0], pair[1]);
            }else{
                result.put(pair[0], "");
            }
        }
        return result;
    }
}