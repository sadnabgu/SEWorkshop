package org.bgu.communication.management;

import org.bgu.service.ForumService;
import org.bgu.service.UserService;

import java.util.Date;

/**
 * Created by gur on 09/06/2015.
 */
public class Session {
    Date creationTime;
    UserService userService;
    ForumService forumService;
    String sessionId;

    public Session(String sessionId, UserService userService, ForumService forumService){
        this.creationTime = new Date();
        this.forumService = forumService;
        this.userService = userService;
        this.sessionId = sessionId;
    }
}
