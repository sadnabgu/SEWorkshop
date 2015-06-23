package org.bgu.service.ServiceObjects;

import org.bgu.domain.model.Message;

/**
 * Created by hodai on 6/23/15.
 */
public class ServiceMessage {
    public final int _id;
    public final String _title;
    public final String _body;

    public ServiceMessage(int id, String title, String body){
        _id = id;
        _title = title;
        _body = body;
    }

    public ServiceMessage(Message message){
        _id = message.getMsgId();
        _title = message.getTitle();
        _body = message.getBody();
    }
}
