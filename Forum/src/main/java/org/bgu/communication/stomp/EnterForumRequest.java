package org.bgu.communication.stomp;

import org.bgu.communication.management.ForumContainer;
import org.bgu.communication.management.Session;
import org.bgu.communication.protocol.StompProtocol;
import org.bgu.service.ForumService;
import org.bgu.service.UserService;

import java.util.Date;
import java.util.HashMap;

public class EnterForumRequest extends StompClientFrame {
    private final String forum;

    public EnterForumRequest(String command, HashMap<String, String> headers, String content) {
        super(command);
        //this.addHeaders(headers);
        //this.setContent(content);
        this.forum = headers.get("forum");
    }

    @Override
    public StompFrame acceptProcess(StompProtocol processor) {
        try {
            UserService userService = new UserService(forum);
            ForumService forumService = new ForumService(forum, userService);
            String sessionId = Long.toString(new Date().getTime());
            ForumContainer.sessions.put(sessionId, new Session(sessionId, userService, forumService));
            System.out.println(String.format("entered forum: %s with session: %s", forum, sessionId));
            return new GeneralStompFrame(getCommand(), new HashMap<String,String>(), sessionId);
        } catch (Exception e) {
            e.printStackTrace();
            return new GeneralStompFrame(getCommand(), new HashMap<String,String>(), "error");
        }
    }
}
