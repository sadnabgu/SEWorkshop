package org.bgu.domain.model.notification;

import java.util.Collection;

/**
 * Created by hodai on 6/22/15.
 */
public abstract class Subject {

    protected Collection<Observer> _Observers;
    protected NotificationStrategy _strategy;

    public void registerObserver(Observer observer){
        _Observers.add(observer);
    }

    public boolean unRegisterObserver(Observer observer){
        // TODO - maybe remove observers by their data equality
        if (_Observers.contains(observer)){
            return false;
        } else {
            _Observers.remove(observer);
            return true;
        }
    }

    public void notify(NotificationType type){
        _strategy.notify(this, type);
    }

}
