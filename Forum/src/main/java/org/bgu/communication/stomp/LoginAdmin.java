package org.bgu.communication.stomp;

import org.bgu.communication.management.ForumContainer;
import org.bgu.communication.protocol.StompProtocol;
import org.bgu.service.AdminService;
import org.bgu.service.Exceptions.ForumException;

import java.util.Date;
import java.util.HashMap;

public class LoginAdmin extends LoginRequest{
    public LoginAdmin(String command, HashMap<String, String> headers, String content) {
        super(command, headers, content);
    }

    @Override
    public StompFrame acceptProcess(StompProtocol processor) {
        try {
            AdminService adminService = new AdminService();
            adminService.loginSystem(username, password);
            ForumContainer.adminSessionId = Long.toString(new Date().getTime());
            ForumContainer.adminService = adminService;
            return new GeneralStompFrame(getCommand(), getHeaders(), ForumContainer.adminSessionId);
        } catch (ForumException e) {
            e.printStackTrace();
            return null;
        }
    }
}

