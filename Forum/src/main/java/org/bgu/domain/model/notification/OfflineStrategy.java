package org.bgu.domain.model.notification;

import org.bgu.domain.model.Member;

import java.util.Collection;

/**
 * Created by hodai on 6/22/15.
 */
public class OfflineStrategy implements NotificationStrategy {

    @Override
    public void notify(Subject subject, NotificationType type) {
        Collection<Observer> observers = subject._Observers;
        for (Observer observer : observers) {
            observer.update(subject, type);
        }
    }
}
