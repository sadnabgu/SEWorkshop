package org.bgu.communication.stomp.forum;

import org.bgu.communication.protocol.StompProtocol;
import org.bgu.communication.stomp.GeneralStompFrame;
import org.bgu.communication.stomp.StompClientFrame;
import org.bgu.communication.stomp.StompFrame;
import org.bgu.service.ForumService;

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
        ArrayList<String> subforums = ForumService.getAllSubForums(UUID.fromString(sid))._value;

        StringBuilder builder = new StringBuilder();

        for(String subforum: subforums){
            builder.append(subforum);
            builder.append('\n');
        }

        return new GeneralStompFrame(getCommand(), getHeaders(), builder.toString().trim());
    }
}
