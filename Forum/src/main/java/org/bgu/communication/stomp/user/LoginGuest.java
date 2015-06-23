package org.bgu.communication.stomp.user;

import org.bgu.communication.protocol.StompProtocol;
import org.bgu.communication.stomp.GeneralStompFrame;
import org.bgu.communication.stomp.Sessions;
import org.bgu.communication.stomp.StompClientFrame;
import org.bgu.communication.stomp.StompFrame;
import org.bgu.service.Exceptions.RetObj;
import org.bgu.service.UserService;

import java.util.HashMap;
import java.util.UUID;

public class LoginGuest extends StompClientFrame {
    private final String forum;

    public LoginGuest(String command, HashMap<String, String> headers, String content) {
        super(command);
        this.forum = headers.get("forum");
        addHeaders(headers);
        setContent(content);
    }

    @Override
    public StompFrame acceptProcess(StompProtocol processor) {
        RetObj<UUID> sid = UserService.logInGuest(forum);
        Sessions.getInstance().add(sid._value.toString(), this.getContext());
        return new GeneralStompFrame(getCommand(), getHeaders(), sid._value.toString());
    }
}
