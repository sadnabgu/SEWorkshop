package org.bgu.communication.stomp.user;

import org.bgu.communication.protocol.StompProtocol;
import org.bgu.communication.stomp.GeneralStompFrame;
import org.bgu.communication.stomp.StompClientFrame;
import org.bgu.communication.stomp.StompFrame;
import org.bgu.service.ServiceObjects.RetObj;
import org.bgu.service.UserService;

import java.util.HashMap;
import java.util.UUID;

public class AddModerator extends StompClientFrame {
    private final String sid;
    private final String subforum;
    private final String moderator;

    public AddModerator(String command, HashMap<String, String> headers, String content) {
        super(command);
        this.sid = headers.get("sid");
        this.subforum = headers.get("subforum");
        this.moderator = headers.get("moderator");
        addHeaders(headers);
        setContent(content);
    }

    @Override
    public StompFrame acceptProcess(StompProtocol processor) {
        RetObj<Object> retObj = UserService.addModerator(UUID.fromString(sid), subforum, moderator);
        return GeneralStompFrame.create(getCommand(), getHeaders(), retObj._result.toString(), retObj);
    }
}

