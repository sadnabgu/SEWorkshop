package org.bgu.domain.model.notification;

import org.bgu.domain.model.Member;

import java.util.Collection;

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
    void update(Subject subject);
}

