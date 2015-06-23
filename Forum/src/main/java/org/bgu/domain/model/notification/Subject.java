package org.bgu.domain.model.notification;

import org.bgu.domain.model.Member;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by hodai on 6/22/15.
 */
public abstract class Subject {

    protected Collection<NotificationStrategy> _Observers = new ArrayList<>();

    public void attach(NotificationStrategy notifier){
        if (!_Observers.contains(notifier)) {
            _Observers.add(notifier);
        }
    }

    public void detach(NotificationStrategy notifier){
        if (_Observers.contains(notifier) )_Observers.remove(notifier);
    }

    public abstract void notifyObserver();

    public abstract Collection<Member> getContext();

    public abstract NotificationType getNotificationType();

}
