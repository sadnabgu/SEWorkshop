package org.bgu.communication.stomp.user;

import org.bgu.communication.protocol.StompProtocol;
import org.bgu.communication.stomp.GeneralStompFrame;
import org.bgu.communication.stomp.StompClientFrame;
import org.bgu.communication.stomp.StompFrame;
import org.bgu.service.ServiceObjects.RetObj;
import org.bgu.service.UserService;

import java.util.HashMap;
import java.util.UUID;

public class Register extends StompClientFrame {
    private final String sid;
    private final String username;
    private final String password;

    public Register(String command, HashMap<String, String> headers, String content) {
        super(command);
        this.sid = headers.get("sid");
        this.username = headers.get("username");
        this.password = headers.get("password");
        addHeaders(headers);
        setContent(content);
    }

    @Override
    public StompFrame acceptProcess(StompProtocol processor) {
        RetObj<Object> retObj = UserService.registerMember(UUID.fromString(sid), username, password);
        return new GeneralStompFrame(getCommand(), getHeaders(), retObj._result.toString());
    }
}