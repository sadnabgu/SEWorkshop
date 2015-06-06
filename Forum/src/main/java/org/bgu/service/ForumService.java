package org.bgu.service;

import org.bgu.domain.facades.ForumFacade;
import org.bgu.domain.facades.UserFacade;
import org.bgu.domain.model.Forum;
import org.bgu.domain.model.Member;
import org.bgu.domain.model.SubForum;

import java.util.Collection;

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

    /**
     * adds new Sub forum with the initial moderator as the connected forum's manager
     * @param subForumName
     * @return Result for the operation
     */
    public Result addNewSubForum(String subForumName, Collection<String> moderators){
        //TODO - validate data according to POLICY
        Member member = userService.getUserAsMember();
        if(member == null)
            return Result.MODERATOR_NOT_MEMBER;
        if (!UserFacade.isForumManager(forum, member))
            return Result.MEMBER_NOT_FORUM_ADMIN;
        if (null != ForumFacade.getSubForum(forum, subForumName)){
            return Result.DUPLICATED_SUBFORUM;
        }
        if (moderators.isEmpty()){
            return Result.NO_MODERATORS_WERE_GIVEN;
        }
        if (ForumFacade.createSubForum(forum, subForumName, moderators) == null){
            return Result.SUBFORUM_MODERATOR_NOT_MEMBER;
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
