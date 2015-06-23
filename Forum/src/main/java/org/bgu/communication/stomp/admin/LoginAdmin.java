package org.bgu.communication.stomp.admin;

import org.bgu.communication.protocol.StompProtocol;
import org.bgu.communication.stomp.GeneralStompFrame;
import org.bgu.communication.stomp.LoginRequest;
import org.bgu.communication.stomp.StompClientFrame;
import org.bgu.communication.stomp.StompFrame;
import org.bgu.service.AdminService;
import org.bgu.service.Exceptions.ForumException;
import org.bgu.service.Exceptions.Result;
import org.bgu.service.Exceptions.RetObj;

import java.util.HashMap;
import java.util.UUID;

public class LoginAdmin extends StompClientFrame {
    private final String username;
    private final String password;

    public LoginAdmin(String command, HashMap<String, String> headers, String content) {
        super(command);

        username = headers.get("username");
        password = headers.get("password");
    }

    @Override
    public StompFrame acceptProcess(StompProtocol processor) {
        RetObj<UUID> retObj = AdminService.loginSystem(username, password);
        GeneralStompFrame gsf = new GeneralStompFrame(getCommand(), getHeaders(), retObj._result.toString());
        if(retObj._result == Result.SUCCESS)
            gsf.addHeader("sid",retObj._value.toString());
        return gsf;
    }
}

