package org.bgu.service;

import org.bgu.domain.facades.ForumFacade;
import org.bgu.domain.model.Forum;
import org.bgu.domain.model.Member;

/**
 * activate all the forum services (non-users)
 *
 * !!! one object per connection !!!
 *
 * Created by hodai on 4/18/15.
 */
public class ForumService {
    //Fields
    private Forum forum;
    private UserService userService;

    //TODO - replace exception with factory method
    public ForumService(String forumName, UserService us) throws Exception {
        forum = ForumFacade.getForum(forumName);
        if (forum == null){
            throw new Exception("forum not found");
        }
        userService = us;
    }

    public Result addNewSubForum(String subForumName){
        //TODO - validate data according to POLICY
        Member moderator = userService.getUserAsMember(); // find the moderator object;
        if(moderator == null)
            return Result.MODERATOR_NOT_MEMBER;
        if (!ForumFacade.createSubForum(forum, subForumName,moderator)){
            return Result.DUPLICATED_SUBFORUM;
        }
       return Result.SUCCESS;
    }

    public boolean addNewThread(String threadName){
        //TODO - validate
        boolean result = forum.addNewThread(threadName);
        if(result == false)
            return false;
        return true;
    }

    /**
     * change the properties of this specific forum
     *
     * TODO - need to implement
     *
     * @return - true if changes success
     */
    public boolean setProperties(){
        //TODO - implement
        return false;
    }
}
