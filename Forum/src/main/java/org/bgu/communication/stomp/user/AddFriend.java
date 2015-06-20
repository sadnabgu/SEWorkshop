package org.bgu.communication.stomp.user;

import org.bgu.communication.protocol.StompProtocol;
import org.bgu.communication.stomp.GeneralStompFrame;
import org.bgu.communication.stomp.StompClientFrame;
import org.bgu.communication.stomp.StompFrame;
import org.bgu.service.UserService;

import java.util.HashMap;
import java.util.UUID;

public class AddFriend extends StompClientFrame {
    private final String sid;
    private final String friend;

    public AddFriend(String command, HashMap<String, String> headers, String content) {
        super(command);
        this.sid = headers.get("sid");
        this.friend = headers.get("friend");
        addHeaders(headers);
        setContent(content);
    }

    @Override
    public StompFrame acceptProcess(StompProtocol processor) {
        UserService.addFriend(UUID.fromString(sid), friend);
        return new GeneralStompFrame(getCommand(), getHeaders(), String.format("%s is now friend of %s", friend, sid));
    }
}

