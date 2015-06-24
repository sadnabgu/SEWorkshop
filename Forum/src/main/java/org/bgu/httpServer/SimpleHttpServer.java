package org.bgu.httpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.bgu.domain.facades.ForumFacade;
import org.bgu.domain.model.Forum;
import org.bgu.service.ForumService;
import org.bgu.service.ServiceObjects.Result;
import org.bgu.service.ServiceObjects.RetObj;
import org.bgu.service.ServiceObjects.ServiceMessage;
import org.bgu.service.UserService;

/**
 * Created by hodai on 6/23/15.
 */
public class SimpleHttpServer {

    public static void start(String[] args) throws Exception {

        int port;

        if (args.length != 1) {
            System.err.println("Usage: <port>");
            return;
        }

        try{
            port = Integer.parseInt(args[0]);
        }
        catch(Exception ex){
            System.err.println("fail to parse - Usage: <port>");
            return;
        }

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange httpExchange) throws IOException {

                String forumName = getSafeForumName(httpExchange);
                if(null == forumName){
                    sendError(httpExchange, "forum not found");
                    return;
                }

                Map<String, String> params = queryToMap(httpExchange.getRequestURI().getQuery());
                handleRequest(httpExchange, params, forumName);

            }
        });

        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("Running web server at port: " + port);
    }


    public static void handleRequest(HttpExchange httpExchange,
                                     Map<String, String> params,
                                     String forumName){
        String sid;
        if (params.containsKey("sid")) {
            sid = params.get("sid");
            if (params.containsKey("login")){
                if(params.get("login").equals("1")) {
                    sendLoginPage(httpExchange, sid, params, forumName);
                    return;
                } else {
                    tryToLogIn(httpExchange, sid, params, forumName);
                    return;
                }
            }
            if (params.containsKey("subforum")){
                if (params.containsKey("mid")){
                    sendThreadPage(httpExchange, sid, params, forumName);
                    return;
                }
                sendThreads(httpExchange, sid, params, forumName);
                return;
            }
            sendSubForums(httpExchange, sid, params, forumName);
            return;
        } else {
            sendWelcomeGuestPage(httpExchange, forumName);
            return;
        }

    }

    private static void tryToLogIn(HttpExchange httpExchange, String sid, Map<String, String> params, String forumName) {
        if (params.containsKey("login")) {
            params.remove("login");
        }
        RetObj<Void> retObj = UserService.logInMember(UUID.fromString(sid), params.get("user"), params.get("pass"));
        if (Result.SUCCESS != retObj._result){
            sendError(httpExchange, retObj._result.toString());
        } else {
            handleRequest(httpExchange, params, forumName);
        }
    }

    private static void sendLoginPage(HttpExchange httpExchange, String sid, Map<String, String> params, String forumName) {
        HtmlBuilder htmlBuilder = new HtmlBuilder();
        if (params.containsKey("login")) {
            params.remove("login");
            params.put("login", "2");
        }
        htmlBuilder.setParams(params);


        sendResponse(httpExchange, 200, htmlBuilder.buildLoginPage(forumName));
    }

    private static void sendThreadPage(HttpExchange httpExchange, String sid, Map<String, String> params, String forumName) {
        HtmlBuilder htmlBuilder = new HtmlBuilder();
        try {
            int mid = Integer.parseInt(params.get("mid"));
            RetObj<ServiceMessage> retObj =
                    ForumService.getMessage(UUID.fromString(sid), params.get("subforum"), mid);
            if (Result.SUCCESS != retObj._result){
                sendError(httpExchange, retObj._result.toString());
                return;
            }

            if (params.containsKey("mid"))
                params.remove("mid");
            htmlBuilder.setParams(params);
            //htmlBuilder.addMessage(retObj._value);

            RetObj<String> memberName = UserService.getUserName(UUID.fromString(sid));
            if (Result.SUCCESS != memberName._result){
                sendError(httpExchange, retObj._result.toString());
                return;
            }

            sendResponse(httpExchange, 200,
                    htmlBuilder.buildTread(memberName._value, forumName, params.get("subforum"), retObj._value));
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    private static void sendThreads(HttpExchange httpExchange, String sid, Map<String, String> params, String forumName) {
        HtmlBuilder htmlBuilder = new HtmlBuilder();
        if (params.containsKey("thread"))
            params.remove("thread");
        htmlBuilder.setParams(params);

        RetObj<Collection<ServiceMessage>> retObj = ForumService.getAllThreads(UUID.fromString(sid), params.get("subforum"));
        if (Result.SUCCESS != retObj._result){
            sendError(httpExchange, retObj._result.toString());
            return;
        }

        htmlBuilder.addMessage(retObj._value);

        RetObj<String> memberName = UserService.getUserName(UUID.fromString(sid));
        if (Result.SUCCESS != memberName._result){
            sendError(httpExchange, retObj._result.toString());
            return;
        }

        sendResponse(httpExchange, 200,
                htmlBuilder.buildTreadsPage(memberName._value, forumName, params.get("subforum")));

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
                String val = pair[1].replaceFirst("/+","");
                result.put(pair[0], val);
            }else{
                result.put(pair[0], "");
            }
        }
        return result;
    }
}