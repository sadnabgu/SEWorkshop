package org.bgu.communication.stomp;

import java.util.HashMap;

/**
 * Created by gur on 09/06/2015.
 */
public abstract class LoginRequest extends StompClientFrame {
    protected final String password;
    protected final String username;
    protected final String sid;

    public LoginRequest(String command, HashMap<String, String> headers, String content) {
        super(command);
        this.username = headers.get("username");
        this.password = headers.get("password");
        this.sid = headers.get("sid");
    }
}


