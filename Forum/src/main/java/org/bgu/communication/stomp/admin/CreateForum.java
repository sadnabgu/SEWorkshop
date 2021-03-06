package org.bgu.communication.stomp.admin;

import org.bgu.communication.protocol.StompProtocol;
import org.bgu.communication.stomp.GeneralStompFrame;
import org.bgu.communication.stomp.StompClientFrame;
import org.bgu.communication.stomp.StompFrame;
import org.bgu.service.AdminService;
import org.bgu.service.ServiceObjects.RetObj;

import java.util.HashMap;
import java.util.UUID;

public class CreateForum extends StompClientFrame {
    private final String adminSessionId;
    private final String forum;
    private final String manager;
    private final String password;

    public CreateForum(String command, HashMap<String, String> headers, String content) {
        super(command);
        this.adminSessionId = headers.get("adminSessionId");
        this.forum = headers.get("forum");
        this.manager = headers.get("manager");
        this.password = headers.get("password");
        //TODO - why??? -> this.addHeaders(headers);
    }

    @Override
    public StompFrame acceptProcess(StompProtocol processor) {
        RetObj<Object> retObj = AdminService.createForum(UUID.fromString(adminSessionId), forum, manager, password);
        return GeneralStompFrame.create(getCommand(), getHeaders(), retObj._result.toString(), retObj);
    }
}

