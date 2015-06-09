package org.bgu.service;

import org.bgu.domain.facades.ForumFacade;
import org.bgu.domain.facades.UserFacade;
import org.bgu.domain.model.Forum;
import org.bgu.domain.model.Member;
import org.bgu.domain.model.SubForum;
import org.bgu.service.Exceptions.ForumException;
import org.bgu.service.Exceptions.Result;

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
     * @param moderators
     * @return id of subForum, or exception{Result.MODERATOR_NOT_MEMBER, Result.MEMBER_NOT_FORUM_ADMIN, Result.DUPLICATED_SUBFORUM, Result.NO_MODERATORS_WERE_GIVEN, Result.SUBFORUM_MODERATOR_NOT_MEMBER;} upon failure
     */
    public boolean addNewSubForum(String subForumName, Collection<String> moderators) throws ForumException {
        //TODO - validate data according to POLICY
        Member member = userService.getUserAsMember();
        if(member == null) {
            throw new ForumException(Result.MODERATOR_NOT_MEMBER.toString());
        }
        if (!UserFacade.isForumManager(forum, member))
            throw new ForumException(Result.MEMBER_NOT_FORUM_ADMIN.toString());
        if (null != ForumFacade.getSubForum(forum, subForumName)){
            throw new ForumException(Result.DUPLICATED_SUBFORUM.toString());
        }
        if (moderators.isEmpty()){
            throw new ForumException(Result.NO_MODERATORS_WERE_GIVEN.toString());
        }
        if (ForumFacade.createSubForum(forum, subForumName, moderators) < 0){
            throw new ForumException(Result.SUBFORUM_MODERATOR_NOT_MEMBER.toString());
        }
       return true;
    }

    public boolean addNewThread(String threadName){
        //TODO - validate data according to POLICY
        boolean result = forum.addNewThread(threadName);
        if(!result)
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

    /***************************************************/
    /*****************FOR TESTING***********************/

    /**
     * un-initialize the system
     * used only for the testing
     */
    public void resetForum() {
        UserFacade.resetForumMembers(forum);
        ForumFacade.resetForum(forum);
    }
}
