package org.bgu.service;

import org.bgu.domain.facades.ForumFacade;
import org.bgu.domain.model.Forum;

/**
 * activate all the forum services (non-users)
 *
 * !!! one object per connection !!!
 *
 * Created by hodai on 4/18/15.
 */
public class ForumService {
    //Fields
    private Forum _forum;

    public ForumService(String forumName){
        //TODO - get the relevant forum object (singleton?)
        _forum = ForumFacade.getForum(forumName);
        if (_forum == null){
            //TODO exception??
        }
    }

    public boolean addNewSubForum(String subForumName, String moderartorName){
        return ForumFacade.addNewSubForum(_forum, subForumName, moderartorName);
    }

    public boolean addNewThread(String threadName, String subForumName, int msgId, String userName){
        return ForumFacade.addNewThread(_forum., threadName, subForumName, msgId, userName);
    }

    public boolean postNewComment(int msgId, String msgTitle, String msgBody, String userName){
        return ForumFacade.addNewComment(_forum, msgId, msgTitle, msgBody, userName);
    }

    public boolean removeMessage(int msgId, String userName){
        //TODO - how to implement in an efficient way (recursion, pointers..)
        return true;
    }

    public boolean removeSubForum(String subForumName){
        
    }

}
