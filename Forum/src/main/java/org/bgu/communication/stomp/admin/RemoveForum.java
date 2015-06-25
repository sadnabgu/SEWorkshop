package org.bgu.communication.stomp.admin;

import org.bgu.communication.protocol.StompProtocol;
import org.bgu.communication.stomp.GeneralStompFrame;
import org.bgu.communication.stomp.StompClientFrame;
import org.bgu.communication.stomp.StompFrame;
import org.bgu.service.AdminService;
import org.bgu.service.ServiceObjects.RetObj;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by gur on 20/06/2015.
 */
public class RemoveForum extends StompClientFrame {
    private final String adminSessionId;
    private final String forum;

    public RemoveForum(String command, HashMap<String, String> headers, String content) {
        super(command);
        this.adminSessionId = headers.get("adminSessionId");
        this.forum = headers.get("forum");
        this.addHeaders(headers);
    }

    @Override
    public StompFrame acceptProcess(StompProtocol processor) {
        RetObj<Object> retObj = AdminService.removeForum(UUID.fromString(adminSessionId), forum);
        return GeneralStompFrame.create(getCommand(), getHeaders(), retObj._result.toString(), retObj);
    }
}
