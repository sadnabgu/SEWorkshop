package org.bgu.domain.model.notification;

import org.bgu.communication.stomp.Sessions;
import org.bgu.domain.facades.UserFacade;
import org.bgu.domain.model.Member;
import org.bgu.domain.model.Session;

import java.util.Collection;


/**
 * Created by hodai on 6/22/15.
 */
public class OnlineStrategy implements NotificationStrategy {

    Sessions communicationSessions = Sessions.getInstance();



    @Override
    public void update(Subject subject) {
        NotificationType type = subject.getNotificationType();
        Collection<Member> members = subject.getContext();

        for (Session s : UserFacade.getAllSessions()) {
            for (Member m : members) {
                if (s._member == m) {
                    //for unit tests
                    communicationSessions.setNotificationPush(type, s._id);
                }
            }
        }
    }
}
