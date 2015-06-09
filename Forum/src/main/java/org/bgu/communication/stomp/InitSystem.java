package org.bgu.communication.stomp;

import org.bgu.communication.management.ForumContainer;
import org.bgu.communication.management.Session;
import org.bgu.communication.protocol.StompProtocol;
import org.bgu.service.AdminService;
import org.bgu.service.Exceptions.ForumException;

import java.util.Date;
import java.util.HashMap;

public class InitSystem extends LoginRequest{
    public InitSystem(String command, HashMap<String, String> headers, String content) {
        super(command, headers, content);
    }

    @Override
    public StompFrame acceptProcess(StompProtocol processor) {
        try {
        AdminService adminService = new AdminService();
            adminService.initializeSystem(username, password);
            //
        } catch (ForumException e) {
            e.printStackTrace();
        }

        return new GeneralStompFrame(getCommand(), new HashMap<String,String>(), "true");
    }
}

