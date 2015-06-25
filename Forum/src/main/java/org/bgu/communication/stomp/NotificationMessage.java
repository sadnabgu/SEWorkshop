package org.bgu.communication.stomp;

import org.bgu.service.ServiceObjects.ServiceMessage;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by gur on 25/06/2015.
 */
public class NotificationMessage extends StompFrame {
    protected NotificationMessage(String subforum, Collection<ServiceMessage> messages) {
        super("notification");
        addHeader("type","edit");
        addHeader("subforum", subforum);

        if (messages == null){
            messages = new ArrayList<>();
        }

        ObjectMapper mapper = new ObjectMapper();
        String jsonMessages = "";
        try {
            jsonMessages = mapper.writeValueAsString(messages);
        } catch (IOException e) {
            jsonMessages = "error parsing json";
        }
        setContent(jsonMessages);
    }
}
