package org.bgu.communication.stomp;

import org.bgu.communication.protocol.StompProtocol;
import org.bgu.service.AdminService;
import org.bgu.service.Exceptions.ForumException;

import java.util.HashMap;

/**
 * Created by gur on 09/06/2015.
 */
public class LoginRequest extends StompClientFrame {
    protected final String password;
    protected final String username;

    public LoginRequest(String command, HashMap<String, String> headers, String content) {
        super(command);
        //this.addHeaders(headers);
        //this.setContent(content);
        this.username = headers.get("username");
        this.password = headers.get("password");
    }

    @Override
    public StompFrame acceptProcess(StompProtocol processor) {
        System.out.println("Hodai hagever");
        return new GeneralStompFrame(getCommand(), new HashMap<String,String>(), "cookie");
    }
}


