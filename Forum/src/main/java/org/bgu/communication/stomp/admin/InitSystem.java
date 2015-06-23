package org.bgu.communication.stomp.admin;

import org.bgu.communication.protocol.StompProtocol;
import org.bgu.communication.stomp.GeneralStompFrame;
import org.bgu.communication.stomp.LoginRequest;
import org.bgu.communication.stomp.StompFrame;
import org.bgu.service.AdminService;
import org.bgu.service.ServiceObjects.RetObj;

import java.util.HashMap;

public class InitSystem extends LoginRequest {
    public InitSystem(String command, HashMap<String, String> headers, String content) {
        super(command, headers, content);
    }

    @Override
    public StompFrame acceptProcess(StompProtocol processor) {
        RetObj<Object> retObj = AdminService.initializeSystem(username, password);

        return new GeneralStompFrame(getCommand(), new HashMap<String,String>(), retObj._result.toString());
    }
}

