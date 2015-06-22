package org.bgu.domain.model.notification;


/**
 * Created by hodai on 6/22/15.
 */
public abstract class Observer {
    private CommunicationHandler _com;

    public void update(Subject sub, NotificationType type){
        NotificationMsg msg = new NotificationMsg();
        msg.type = type;
        // TODO ...
        // TODO - send the message
    }

    public abstract boolean isOnline();
}
