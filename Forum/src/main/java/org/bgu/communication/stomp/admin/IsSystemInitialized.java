package org.bgu.communication.stomp.admin;

import org.bgu.communication.protocol.StompProtocol;
import org.bgu.communication.stomp.GeneralStompFrame;
import org.bgu.communication.stomp.StompClientFrame;
import org.bgu.communication.stomp.StompFrame;
import org.bgu.service.AdminService;
import org.bgu.service.ServiceObjects.RetObj;

import java.util.HashMap;

public class IsSystemInitialized extends StompClientFrame {
    public IsSystemInitialized(String command, HashMap<String, String> headers, String content) {
        super(command);
    }

    @Override
    public StompFrame acceptProcess(StompProtocol processor) {
        RetObj<Boolean> result = AdminService.isInitializedSystem();
        return new GeneralStompFrame(getCommand(), new HashMap<String,String>(), Boolean.toString(result._value));
    }
}
