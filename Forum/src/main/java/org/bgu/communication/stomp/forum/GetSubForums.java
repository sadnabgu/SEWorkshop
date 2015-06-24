package org.bgu.communication.stomp.forum;

import org.bgu.communication.protocol.StompProtocol;
import org.bgu.communication.stomp.GeneralStompFrame;
import org.bgu.communication.stomp.StompClientFrame;
import org.bgu.communication.stomp.StompFrame;
import org.bgu.service.ForumService;
import org.bgu.service.ServiceObjects.RetObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class GetSubForums extends StompClientFrame {
    private final String sid;

    public GetSubForums(String command, HashMap<String, String> headers, String content) {
        super(command);
        this.sid = headers.get("sid");
        this.addHeaders(headers);
        this.setContent(content);
    }

    @Override
    public StompFrame acceptProcess(StompProtocol processor) {
        RetObj<ArrayList<String>> retObj = ForumService.getAllSubForums(UUID.fromString(sid));
        ArrayList<String> subforums = retObj._value;

        StringBuilder builder = new StringBuilder();

        if (subforums != null) {
            for (String subforum : subforums) {
                builder.append(subforum);
                builder.append('\n');
            }
        }

        return GeneralStompFrame.create(getCommand(), getHeaders(), builder.toString().trim(), retObj);
    }
}
