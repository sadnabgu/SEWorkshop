package org.bgu.communication.stomp;

import org.bgu.communication.protocol.StompProtocol;
import org.bgu.service.ForumService;

import java.util.HashMap;

public class GetForumsRequest extends StompClientFrame{
    public GetForumsRequest(String command) {
        super(command);
    }

    @Override
    public StompFrame acceptProcess(StompProtocol processor) {
        HashMap<String, String> forums = new HashMap<>();

        //ForumService;
        forums.put("sex", "1");
        forums.put("drugs", "2");
        forums.put("rock and roll", "3");
        return new GeneralStompFrame(getCommand(), forums, "");
    }
}
