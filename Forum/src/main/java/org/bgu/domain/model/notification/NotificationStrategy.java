package org.bgu.domain.model.notification;

/**
 * this interface implement strategy pattern,
 * basic implementers (strategies):
 *      - offline
 *      - online
 *      - custom
 *
 * Created by hodai on 6/22/15.
 */
public interface NotificationStrategy {
    void notify(Subject subject, NotificationType type);
}

