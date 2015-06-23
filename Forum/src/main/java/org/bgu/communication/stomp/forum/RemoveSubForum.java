package org.bgu.communication.stomp.forum;

import org.bgu.communication.protocol.StompProtocol;
import org.bgu.communication.stomp.GeneralStompFrame;
import org.bgu.communication.stomp.StompClientFrame;
import org.bgu.communication.stomp.StompFrame;
import org.bgu.service.ServiceObjects.RetObj;
import org.bgu.service.ForumService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class RemoveSubForum extends StompClientFrame {
    private final String sid;
    private final String subforum;

    public RemoveSubForum(String command, HashMap<String, String> headers, String content) {
        super(command);
        this.sid = headers.get("sid");
        this.subforum = headers.get("subforum");
        this.addHeaders(headers);
        this.setContent(content);
    }

    @Override
    public StompFrame acceptProcess(StompProtocol processor) {
        RetObj<Object> retObj = ForumService.removeSubForum(UUID.fromString(sid), subforum);
        return new GeneralStompFrame(getCommand(), getHeaders(), retObj._result.toString());
    }
}


