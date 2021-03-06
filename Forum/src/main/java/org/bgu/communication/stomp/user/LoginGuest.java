package org.bgu.communication.stomp.user;

import org.bgu.communication.protocol.StompProtocol;
import org.bgu.communication.stomp.GeneralStompFrame;
import org.bgu.communication.stomp.Sessions;
import org.bgu.communication.stomp.StompClientFrame;
import org.bgu.communication.stomp.StompFrame;
import org.bgu.service.ServiceObjects.RetObj;
import org.bgu.service.UserService;

import java.util.HashMap;
import java.util.UUID;

public class LoginGuest extends StompClientFrame {
    private final String forum;

    public LoginGuest(String command, HashMap<String, String> headers, String content) {
        super(command);
        this.forum = headers.get("forum");
        //TODO - why this?? -> addHeaders(headers);
        setContent(content);
    }

    @Override
    public StompFrame acceptProcess(StompProtocol processor) {
        RetObj<UUID> retObj = UserService.logInGuest(forum);
        GeneralStompFrame gsf = GeneralStompFrame.create(getCommand(), getHeaders(), retObj._result.toString(), retObj);
        if (retObj._value != null) {
            Sessions.getInstance().add(retObj._value.toString(), this.getContext());
            gsf.addHeader("sid", retObj._value.toString());
        }
        return gsf;
    }
}
