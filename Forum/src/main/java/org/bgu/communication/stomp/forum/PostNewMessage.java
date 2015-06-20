package org.bgu.communication.stomp.forum;

import org.bgu.communication.protocol.StompProtocol;
import org.bgu.communication.stomp.GeneralStompFrame;
import org.bgu.communication.stomp.StompClientFrame;
import org.bgu.communication.stomp.StompFrame;
import org.bgu.service.ForumService;

import java.util.HashMap;
import java.util.UUID;

public class PostNewMessage extends StompClientFrame {
    private final String sid;
    private final String subforum;
    private final String parentId;
    private final String title;

    public PostNewMessage(String command, HashMap<String, String> headers, String content) {
        super(command);
        this.sid = headers.get("sid");
        this.subforum = headers.get("subforum");
        this.parentId = headers.get("parent");
        this.title = headers.get("title");
        this.addHeaders(headers);
        this.setContent(content);
    }

    @Override
    public StompFrame acceptProcess(StompProtocol processor) {
        ForumService.postNewComment(UUID.fromString(sid), subforum, Integer.parseInt(parentId), title, getContent());
        return new GeneralStompFrame(getCommand(), getHeaders(), String.format(" commented on {%s}", parentId));
    }
}

