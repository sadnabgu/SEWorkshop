package org.bgu.communication.stomp.forum;

import org.bgu.communication.protocol.StompProtocol;
import org.bgu.communication.stomp.GeneralStompFrame;
import org.bgu.communication.stomp.StompClientFrame;
import org.bgu.communication.stomp.StompFrame;
import org.bgu.service.ForumService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by gur on 20/06/2015.
 */
public class CreateSubForum extends StompClientFrame {
    private final String sid;
    private final String subforum;

    public CreateSubForum(String command, HashMap<String, String> headers, String content) {
        super(command);
        this.sid = headers.get("sid");
        this.subforum = headers.get("subforum");
        this.addHeaders(headers);
        this.setContent(content);
    }

    @Override
    public StompFrame acceptProcess(StompProtocol processor) {
        String [] moderators = getContent().trim().split("\n");
        Collection<String> moderatorsCollection = new ArrayList<>();
        for (String current: moderators){
            moderatorsCollection.add(current);
        }
        ForumService.addNewSubForum(UUID.fromString(sid), subforum, moderatorsCollection);
        return new GeneralStompFrame(getCommand(), getHeaders(), String.format(" sub forum {%s} created", subforum));
    }
}

