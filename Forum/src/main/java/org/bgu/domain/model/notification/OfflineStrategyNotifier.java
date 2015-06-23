package org.bgu.domain.model.notification;

import org.bgu.communication.stomp.Sessions;
import org.bgu.domain.facades.UserFacade;
import org.bgu.domain.model.Member;
import org.bgu.domain.model.Session;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Michael on 6/23/2015.
 */
public class OfflineStrategyNotifier implements NotificationStrategy {

    Sessions communicationSessions;

    @Override
    public void update(Subject subject) {
        NotificationType type = subject.getNotificationType();
        Collection<Member> members = subject.getContext();
        HashMap<Member, Collection<NotificationType>> waitingList = new HashMap<>();

        //notify all logged in members as usual for all types (except log-in)
        for (Session s : UserFacade.getAllSessions()) {
            for (Member m : members) {
                if (s._member == m) {
                    communicationSessions.setNotificationPush(type, s._id);
                    members.remove(m);
                }
            }
        }

        //put all logged-off members to a waiting list
        for (Member m : members) {
            Collection<NotificationType> waitingNotifications;
            if (!waitingList.containsKey(m)) {
                waitingNotifications = new ArrayList<NotificationType>();
                waitingNotifications.add(type);
                waitingList.put(m, waitingNotifications);
            } else {
                waitingNotifications = waitingList.get(m);
                waitingNotifications.add(type);
            }
        }

        //notify the member who just logged in for all his notifications while he was logged out, and all others
        for (Session s : UserFacade.getAllSessions()) {
            for (Member m : waitingList.keySet()) {
                if (s._member == m) {
                    for (NotificationType t : waitingList.get(m)) {
                        communicationSessions.setNotificationPush(type, s._id);
                    }
                    waitingList.remove(m);
                }
            }
        }
    }
}
