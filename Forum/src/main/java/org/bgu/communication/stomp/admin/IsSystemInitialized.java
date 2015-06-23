package org.bgu.communication.stomp.admin;

import org.bgu.communication.protocol.StompProtocol;
import org.bgu.communication.stomp.GeneralStompFrame;
import org.bgu.communication.stomp.StompClientFrame;
import org.bgu.communication.stomp.StompFrame;
import org.bgu.service.AdminService;

import java.util.HashMap;

public class IsSystemInitialized extends StompClientFrame {
    public IsSystemInitialized(String command, HashMap<String, String> headers, String content) {
        super(command);
    }

    @Override
    public StompFrame acceptProcess(StompProtocol processor) {
        boolean result = AdminService.isSystemInitialized();
        return new GeneralStompFrame(getCommand(), new HashMap<String,String>(), Boolean.toString(result));
    }
}
