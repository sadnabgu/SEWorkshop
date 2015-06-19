package org.bgu.service;

import org.bgu.domain.facades.ForumFacade;
import org.bgu.domain.facades.UserFacade;
import org.bgu.service.Exceptions.Result;
import org.bgu.service.Exceptions.RetObj;

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
    /**
     *
     * @param forumName
     * @param forumManagerName
     * @param subForumName
     * @param moderators
     * @return id of subForum, or exception{Result.MODERATOR_NOT_MEMBER, Result.MEMBER_NOT_FORUM_ADMIN, Result.DUPLICATED_SUBFORUM, Result.NO_MODERATORS_WERE_GIVEN, Result.SUBFORUM_MODERATOR_NOT_MEMBER;} upon failure
     */
    public static RetObj<Object> addNewSubForum(String forumName, String forumManagerName, String subForumName, Collection<String> moderators){
        //TODO - validate data according to POLICY
        if (!(UserFacade.isForumManager(forumName, forumManagerName)))
            return new RetObj<>(Result.UNAUTHORIZED_OPERATION);
        if (null != ForumFacade.getSubForum(forumName, subForumName)) {
            return new RetObj<>(Result.DUPLICATED_SUBFORUM);
        }
        if (moderators.isEmpty()) {
            return new RetObj<>(Result.NO_MODERATORS_WERE_GIVEN);
        }
        if (ForumFacade.createSubForum(forumName, subForumName, moderators) < 0) {
            return new RetObj<>(Result.SUBFORUM_MODERATOR_NOT_MEMBER);
        }
        return new RetObj<>(Result.SUCCESS);
    }

    /**
     * adds a nre thread for some subForum
     *
     * @param threadTitle
     * @param threadBody
     * @return msgId of the newly thread upon success. exception{} upon fail
     */
    public static RetObj<Integer> addNewThread(String forumName, String subForumName, String userName, String threadTitle, String threadBody){
        //TODO - validate data according to POLICY
        int newMsgId = ForumFacade.addNewThread(forumName, subForumName, userName, threadTitle, threadBody);
        if (newMsgId < 0)
            return new RetObj<>(Result.NEW_THREAD_FAIL);
        return new RetObj<>(Result.SUCCESS,newMsgId);
    }

    /**
     *
     * @param forumName
     * @param forumManagerName
     * @param subForumName
     * @return
     */
    public static RetObj<Object> removeSubForum(String forumName, String forumManagerName, String subForumName){
        //TODO - validate data according to POLICY
        if (!UserFacade.isForumManager(forumName, forumManagerName))
            return new RetObj<>(Result.UNAUTHORIZED_OPERATION);
        if (!ForumFacade.removeSubForum(forumName, subForumName)) {
            return new RetObj<>(Result.SUBFORUM_ALREADY_REMOVED);
        }
        return new RetObj<>(Result.SUCCESS);
    }



    /**
     * change the properties of this specific forum
     * <p>
     * TODO - need to implement
     *
     * @return - true if changes success
     */
    public static boolean setProperties() {
        //TODO - implement
        return false;
    }

    /***************************************************/
    /*****************FOR TESTING***********************/

    /**
     * un-initialize the system
     * used only for the testing
     */
    public static void resetForum(String forumName) {
        UserFacade.resetForumMembers(forumName);
        ForumFacade.resetForum(forumName);
    }

    public static ArrayList<String> getAllForums() {
        return ForumFacade.getAllForums();
    }

    public static ArrayList<String> getAllSubForums(String forumName) {
        return ForumFacade.getAllSubForums(forumName);
    }
}
