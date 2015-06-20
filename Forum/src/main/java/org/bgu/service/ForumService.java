package org.bgu.service;

import org.bgu.domain.facades.ForumFacade;
import org.bgu.domain.facades.UserFacade;
import org.bgu.service.Exceptions.Result;
import org.bgu.service.Exceptions.RetObj;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * activate all the forum services (non-users)
 * <p>
 * Created by hodai on 4/18/15.
 */
public class ForumService {

    /**
     *
     * @param sId
     * @param subForumName
     * @param moderators
     * @return
     */
    public static RetObj<Object> addNewSubForum(UUID sId, String subForumName, Collection<String> moderators){
        //TODO - validate data according to POLICY
        if (!(UserFacade.isForumManager(sId)))
            return new RetObj<>(Result.UNAUTHORIZED_OPERATION);
        if (null != ForumFacade.getSubForum(sId, subForumName)) {
            return new RetObj<>(Result.DUPLICATED_SUBFORUM);
        }
        if (moderators.isEmpty()) {
            return new RetObj<>(Result.NO_MODERATORS_WERE_GIVEN);
        }
        if (ForumFacade.createSubForum(sId, subForumName, moderators) < 0) {
            return new RetObj<>(Result.SUBFORUM_MODERATOR_NOT_MEMBER);
        }
        return new RetObj<>(Result.SUCCESS);
    }

    /**
     * adds a new thread for some subForum
     *
     * @param sId
     * @param subForumName
     * @param threadTitle
     * @param threadBody
     * @return - msgId of the newly thread upon success. exception{} upon fail
     */
    public static RetObj<Integer> addNewThread(UUID sId, String subForumName, String threadTitle, String threadBody){
        //TODO - validate data according to POLICY
        int newMsgId = ForumFacade.addNewThread(sId, subForumName, threadTitle, threadBody);
        if (newMsgId < 0)
            return new RetObj<>(Result.NEW_THREAD_FAIL);
        return new RetObj<>(Result.SUCCESS,newMsgId);
    }

    /**
     *
     * @param sId
     * @param subForumName
     * @param MsgId
     * @param commentTitle
     * @param commentBody
     * @return
     */
    public static RetObj<Integer> postNewComment(UUID sId, String subForumName, int MsgId, String commentTitle, String commentBody){
        //TODO - validate data according to POLICY
        int newMsgId = ForumFacade.postNewComment(sId, subForumName, MsgId, commentTitle, commentBody);
        if (newMsgId < 0)
            return new RetObj<>(Result.NEW_COMMENT_FAIL);
        return new RetObj<>(Result.SUCCESS,newMsgId);
    }

    /**
     *
     * @param sId
     * @param subForumName
     * @param MsgId
     * @return
     */
    public static RetObj<Object> removeMessage(UUID sId, String subForumName, int MsgId){
        if (!ForumFacade.removeMessage(sId, subForumName, MsgId))
            return new RetObj<>(Result.REMOVE_COMMENT_FAILED);
        return new RetObj<>(Result.SUCCESS);
    }

    /**
     *
     * @param sId
     * @param subForumName
     * @param MsgId
     * @param commentTitle
     * @param commentBody
     * @return
     */
    public static RetObj<Object> editMessage(UUID sId, String subForumName, int MsgId, String commentTitle, String commentBody){
        if (!ForumFacade.editMessage(sId, subForumName, MsgId, commentTitle, commentBody))
            return new RetObj<>(Result.EDIT_COMMENT_FAILED);
        return new RetObj<>(Result.SUCCESS);
    }

    /**
     *
     * @param sId
     * @param subForumName
     * @return
     */
    public static RetObj<Object> removeSubForum(UUID sId, String subForumName){
        //TODO - validate data according to POLICY
        if (!UserFacade.isForumManager(sId))
            return new RetObj<>(Result.UNAUTHORIZED_OPERATION);
        if (!ForumFacade.removeSubForum(sId, subForumName)) {
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

    public static ArrayList<String> getAllForums() {
        return ForumFacade.getAllForums();
    }

    public static ArrayList<String> getAllSubForums(String forumName) {
        return ForumFacade.getAllSubForums(forumName);
    }
}
