package org.bgu.service;

import org.bgu.domain.facades.ForumFacade;
import org.bgu.domain.facades.UserFacade;
import org.bgu.domain.model.Forum;
import org.bgu.domain.model.Member;
import org.bgu.domain.model.SubForum;
import org.bgu.service.Exceptions.ForumException;
import org.bgu.service.Exceptions.Result;

import java.util.ArrayList;
import java.util.Collection;

/**
 * activate all the forum services (non-users)
 * <p>
 * !!! one object per connection !!!
 * <p>
 * Created by hodai on 4/18/15.
 */
public class ForumService {
    //Fields
    private Forum forum;
    private UserService userService;

    //TODO - replace exception with factory method
    public ForumService(String forumName, UserService us) throws Exception {
        forum = ForumFacade.getForum(forumName);
        if (forum == null) {
            throw new Exception("forum not found");
        }
        userService = us;
    }

    /**
     * adds new Sub forum with the initial moderator as the connected forum's manager
     *
     * @param subForumName
     * @param moderators
     * @return id of subForum, or exception{Result.MODERATOR_NOT_MEMBER, Result.MEMBER_NOT_FORUM_ADMIN, Result.DUPLICATED_SUBFORUM, Result.NO_MODERATORS_WERE_GIVEN, Result.SUBFORUM_MODERATOR_NOT_MEMBER;} upon failure
     */
    public boolean addNewSubForum(String subForumName, Collection<String> moderators) throws ForumException {
        //TODO - validate data according to POLICY
        Member member = userService.getUserAsMember();
        if (member == null) {
            throw new ForumException(Result.MODERATOR_NOT_MEMBER.toString());
        }
        if (!UserFacade.isForumManager(forum, member))
            throw new ForumException(Result.MEMBER_NOT_FORUM_ADMIN.toString());
        if (null != ForumFacade.getSubForum(forum, subForumName)) {
            throw new ForumException(Result.DUPLICATED_SUBFORUM.toString());
        }
        if (moderators.isEmpty()) {
            throw new ForumException(Result.NO_MODERATORS_WERE_GIVEN.toString());
        }
        if (ForumFacade.createSubForum(forum, subForumName, moderators) < 0) {
            throw new ForumException(Result.SUBFORUM_MODERATOR_NOT_MEMBER.toString());
        }
        return true;
    }

    /**
     * adds a nre thread for some subForum
     * @param threadTitle
     * @param threadBody
     * @return msgId of the newly thread upon success. exception{} upon fail
     */
   /* public int addNewThread(String subForumName, String threadTitle, String threadBody) {
        //TODO - validate data according to POLICY
        int newMsgId = forum.addNewThread(subForumName, threadTitle, threadTitle);
        if (newMsgId < 0)
            return new ForumException(Result.MSG);
        return true;
    }*/

    public boolean removeSubForum(String subForumName) throws ForumException {
        //TODO - validate data according to POLICY
        Member member = userService.getUserAsMember();
        if (member == null) {
            throw new ForumException(Result.MODERATOR_NOT_MEMBER.toString());
        }
        if (!UserFacade.isForumManager(forum, member))
            throw new ForumException(Result.MEMBER_NOT_FORUM_ADMIN.toString());
        if (!ForumFacade.removeSubForum(forum, subForumName)) {
            throw new ForumException(Result.SUBFORUM_ALREADY_REMOVED.toString());
        }
        return true;
    }

    public ArrayList<String> getAllForums() {
        return ForumFacade.getAllForums();
    }

    public ArrayList<String> getAllSubForums() {
        return ForumFacade.getAllSubForums(forum);
    }

    /**
     * change the properties of this specific forum
     * <p>
     * TODO - need to implement
     *
     * @return - true if changes success
     */
    public boolean setProperties() {
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
