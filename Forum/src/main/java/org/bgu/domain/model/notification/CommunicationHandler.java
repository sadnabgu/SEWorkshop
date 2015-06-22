package org.bgu.domain.model.notification;

/**
 * this clas is kind of proxy between the notification package and the communication package
 *
 * Created by hodai on 6/22/15.
 */
public class CommunicationHandler {
    /**
     * this method translate the notification message to comminication message type,
     * and use the communication layer to send (push) the notification message
     *
     * @param msg - the Notification message that contain the data
     * @return - true if success
     */
    public boolean voidsendNotification(NotificationMsg msg){
        // TODO
        return false;
    }
}
