package org.bgu.communication.stomp.user;

import org.bgu.communication.protocol.StompProtocol;
import org.bgu.communication.stomp.GeneralStompFrame;
import org.bgu.communication.stomp.StompClientFrame;
import org.bgu.communication.stomp.StompFrame;
import org.bgu.service.Exceptions.RetObj;
import org.bgu.service.UserService;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class RemoveFriend extends StompClientFrame {
    private final String sid;
    private final String friend;

    public RemoveFriend(String command, HashMap<String, String> headers, String content) {
        super(command);
        this.sid = headers.get("sid");
        this.friend = headers.get("friend");
        addHeaders(headers);
        setContent(content);
    }

    @Override
    public StompFrame acceptProcess(StompProtocol processor) {
        RetObj<Object> retObj = UserService.unFriend(UUID.fromString(sid), friend);
        return new GeneralStompFrame(getCommand(), getHeaders(), retObj._result.toString());
    }
}

