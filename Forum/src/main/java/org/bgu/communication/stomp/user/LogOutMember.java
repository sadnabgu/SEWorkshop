package org.bgu.communication.stomp.user;

import org.bgu.communication.protocol.StompProtocol;
import org.bgu.communication.stomp.GeneralStompFrame;
import org.bgu.communication.stomp.StompClientFrame;
import org.bgu.communication.stomp.StompFrame;
import org.bgu.service.ServiceObjects.RetObj;
import org.bgu.service.UserService;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by gur on 20/06/2015.
 */
public class LogOutMember extends StompClientFrame {
    private final String sid;

    public LogOutMember(String command, HashMap<String, String> headers, String content) {
        super(command);
        this.sid = headers.get("sid");
        setContent(content);
    }

    @Override
    public StompFrame acceptProcess(StompProtocol processor) {
        RetObj<Object> retObj = UserService.logOut(UUID.fromString(sid));
        return GeneralStompFrame.create(getCommand(), getHeaders(), retObj._result.toString(), retObj);
    }
}

