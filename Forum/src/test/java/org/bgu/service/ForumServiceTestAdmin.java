package org.bgu.service;

import junit.framework.Assert;
import org.bgu.domain.facades.ForumFacade;
import org.bgu.domain.model.Forum;
import org.junit.Before;
import org.junit.Test;

/**
 * admin unit test
 * Created by hodai on 4/28/15.
 */
public class ForumServiceTestAdmin {
    public static final int FORUM_ID = 1;

    ForumService forumService;

    @Before
    public void logoutSystem(){
        forumService = new ForumService(FORUM_ID);
        ForumFacade.createForum(FORUM_ID, "forum 1");
    }

    @Test
    public void createSubForum_correctData_newSubForumCreated(){
        //TODO
    }

    @Test
    public void createSubForum_WrongData_creationFaild(){
        //TODO
    }

    @Test
    public void createSubForum_notForumAdmin_newSubForumCreated(){
        //TODO
    }

}
