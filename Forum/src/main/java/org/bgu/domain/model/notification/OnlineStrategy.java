package org.bgu.domain.model.notification;

import java.util.Collection;

/**
 * Created by hodai on 6/22/15.
 */
public class OnlineStrategy implements NotificationStrategy {
    @Override
    public void notify(Subject subject, NotificationType type) {
        Collection<Observer> observers = subject._Observers;
        for (Observer observer : observers) {
            if (observer.isOnline()){
                observer.update(subject, type);
            }
        }
    }
}
