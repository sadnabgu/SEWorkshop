package org.bgu.communication.stomp;

import org.apache.log4j.Logger;
import org.bgu.communication.reactor.ProtocolTask;
import org.bgu.domain.model.Session;
import org.bgu.domain.model.notification.NotificationType;
import org.bgu.service.ServiceObjects.ServiceMessage;

import java.awt.*;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Hashtable;
import java.util.UUID;

/**
 * Created by gur on 22/06/2015.
 */
public class Sessions {
    private static Logger logger = Logger.getLogger(Sessions.class);
    private static Sessions instance = null;
    private Hashtable<String, ProtocolTask<StompFrame>> sessions;
    StompTokenizer _tokenizer = new StompTokenizer();

    public static Sessions getInstance(){
        if (instance == null){
            synchronized (Sessions.class){
                if (instance == null){
                    instance = new Sessions();
                }
            }
        }
        return instance;
    }

    private Sessions(){
        sessions = new Hashtable<>();
    }

    public void add(String id, ProtocolTask<StompFrame> session){
        sessions.put(id, session);
    }

    public void remove(String id){
        sessions.remove(id);
    }

    public void sendNotification(String sid, String subforum, Collection<ServiceMessage> messages){
        try{
            NotificationMessage msg = new NotificationMessage(subforum, messages);
            ByteBuffer bytes = _tokenizer.getBytesForMessage(msg);
            sessions.get(sid).getConnectionHandler().addOutData(bytes);
        }
        catch (Throwable ex){
            // error sending notification
            logger.error("error seding notifications: " + ex.getMessage());
        }
    }
}
