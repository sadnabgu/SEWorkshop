package org.bgu.communication.stomp.forum;

import org.bgu.communication.protocol.StompProtocol;
import org.bgu.communication.stomp.GeneralStompFrame;
import org.bgu.communication.stomp.StompClientFrame;
import org.bgu.communication.stomp.StompFrame;
import org.bgu.service.ForumService;

import java.util.HashMap;
import java.util.UUID;

public class AddNewThread extends StompClientFrame {
    private final String sid;
    private final String subforum;
    private final String title;

    public AddNewThread(String command, HashMap<String, String> headers, String content) {
        super(command);
        this.sid = headers.get("sid");
        this.subforum = headers.get("subforum");
        this.title = headers.get("title");
        this.addHeaders(headers);
        this.setContent(content);
    }

    @Override
    public StompFrame acceptProcess(StompProtocol processor) {
        ForumService.addNewThread(UUID.fromString(sid), subforum, title, getContent());
        return new GeneralStompFrame(getCommand(), getHeaders(), String.format(" opened new thread {%s}", title));
    }
}