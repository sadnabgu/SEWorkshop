package org.bgu.service.ServiceObjects;

import org.bgu.domain.model.Message;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by hodai on 6/23/15.
 */
public class ServiceMessage {
    public final int _id;
    public final String _title;
    public final String _body;
    public final String _creator;
    public final Collection<ServiceMessage> _replies;

    public ServiceMessage(int id, String title, String body, String creator){
        if (creator == null){
            creator = "guest";
        }
        _creator = creator;
        _id = id;
        _title = title;
        _body = body;
        _replies = new ArrayList<>();
    }

    public ServiceMessage(Message message){
        _id = message.getMsgId();
        _title = message.getTitle();
        _body = message.getBody();
        String cname;
        try {
            cname = message.getCreator().getUserName();
        }
        catch (Throwable ex){
            cname = "guest";
        }
        _creator = cname;
        _replies = new ArrayList<>();
        for (Message m : message.getComments()){
            _replies.add(new ServiceMessage(m));
        }
    }
}
