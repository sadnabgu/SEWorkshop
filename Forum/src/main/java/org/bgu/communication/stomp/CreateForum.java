package org.bgu.communication.stomp;

import org.bgu.communication.management.ForumContainer;
import org.bgu.communication.protocol.StompProtocol;
import org.bgu.service.Exceptions.ForumException;

import java.util.HashMap;

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
        this.addHeaders(headers);
    }

    @Override
    public StompFrame acceptProcess(StompProtocol processor) {
        try {
            if (ForumContainer.adminSessionId.equals(adminSessionId)){
                ForumContainer.adminService.createForum(forum, manager, password);
                return new GeneralStompFrame(getCommand(), getHeaders(), forum);
            }
            return null;

        } catch (ForumException e) {
            e.printStackTrace();
            return null;
        }
    }
}
