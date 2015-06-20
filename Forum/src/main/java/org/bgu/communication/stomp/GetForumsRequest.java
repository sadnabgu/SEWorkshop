package org.bgu.communication.stomp;

import org.bgu.communication.protocol.StompProtocol;
import org.bgu.service.ForumService;

import java.util.Collection;
import java.util.HashMap;

public class GetForumsRequest extends StompClientFrame{
    public GetForumsRequest(String command) {
        super(command);
    }

    @Override
    public StompFrame acceptProcess(StompProtocol processor) {
        Collection<String> forums =ForumService.getAllForums();

        StringBuilder forumString = new StringBuilder();
        for (String current: forums){
            forumString.append(current + "\n");
        }

        return new GeneralStompFrame(getCommand(), null, forumString.toString().trim());
    }
}
