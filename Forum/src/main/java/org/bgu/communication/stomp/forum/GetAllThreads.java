package org.bgu.communication.stomp.forum;

import org.bgu.communication.protocol.StompProtocol;
import org.bgu.communication.stomp.GeneralStompFrame;
import org.bgu.communication.stomp.StompClientFrame;
import org.bgu.communication.stomp.StompFrame;
import org.bgu.service.ForumService;
import org.bgu.service.ServiceObjects.RetObj;
import org.bgu.service.ServiceObjects.ServiceMessage;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by gur on 24/06/2015.
 */
public class GetAllThreads extends StompClientFrame{
    String sid;
    String subforum;

    public GetAllThreads(String command, HashMap<String, String> headers, String content) {
        super(command);
        this.sid = headers.get("sid");
        this.subforum = headers.get("subforum");
        this.addHeaders(headers);
        this.setContent(content);
    }

    @Override
    public StompFrame acceptProcess(StompProtocol processor) {
        RetObj<Collection<ServiceMessage>> retObj = ForumService.getAllThreads(UUID.fromString(sid), subforum);

        Collection<ServiceMessage> messages = retObj._value;

        ObjectMapper mapper = new ObjectMapper();
        String jsonMessages;
        try {
            jsonMessages = mapper.writeValueAsString(messages);
        } catch (IOException e) {
            jsonMessages = "error parsing json";
        }

        return GeneralStompFrame.create(getCommand(), getHeaders(), jsonMessages, retObj);
    }

}
