package org.bgu.communication.stomp.forum;

import org.bgu.communication.protocol.StompProtocol;
import org.bgu.communication.stomp.GeneralStompFrame;
import org.bgu.communication.stomp.StompClientFrame;
import org.bgu.communication.stomp.StompFrame;
import org.bgu.service.ForumService;

import java.util.HashMap;
import java.util.UUID;

public class RemoveMessage extends StompClientFrame {
    private final String sid;
    private final String subforum;
    private final String msgid;

    public RemoveMessage(String command, HashMap<String, String> headers, String content) {
        super(command);
        this.sid = headers.get("sid");
        this.subforum = headers.get("subforum");
        this.msgid = headers.get("msgid");
        this.addHeaders(headers);
        this.setContent(content);
    }

    @Override
    public StompFrame acceptProcess(StompProtocol processor) {
        ForumService.removeMessage(UUID.fromString(sid), subforum, Integer.parseInt(msgid));
        return new GeneralStompFrame(getCommand(), getHeaders(), String.format(" message {%s}", msgid));
    }
}
