package org.bgu.communication.stomp.user;

import org.bgu.communication.protocol.StompProtocol;
import org.bgu.communication.stomp.GeneralStompFrame;
import org.bgu.communication.stomp.StompClientFrame;
import org.bgu.communication.stomp.StompFrame;
import org.bgu.service.ServiceObjects.RetObj;
import org.bgu.service.UserService;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by gur on 20/06/2015.
 */
public class LoginMember extends StompClientFrame {
    private final String sid;
    private final String username;
    private final String password;

    public LoginMember(String command, HashMap<String, String> headers, String content) {
        super(command);
        this.sid = headers.get("sid");
        this.username = headers.get("username");
        this.password = headers.get("password");
        //TODO - why this?? -> addHeaders(headers);
        setContent(content);
    }

    @Override
    public StompFrame acceptProcess(StompProtocol processor) {
        RetObj<Void> retObj = UserService.logInMember(UUID.fromString(sid), username, password);
        return GeneralStompFrame.create(getCommand(), getHeaders(), retObj._result.toString(), retObj);
    }
}

