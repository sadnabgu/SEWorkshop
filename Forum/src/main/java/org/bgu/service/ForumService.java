package org.bgu.service;

import org.bgu.domain.facades.ForumFacade;
import org.bgu.domain.facades.UserFacade;
import org.bgu.domain.model.Message;
import org.bgu.service.ServiceObjects.Result;
import org.bgu.service.ServiceObjects.RetObj;
import org.bgu.service.ServiceObjects.ServiceMessage;

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
     * @return - array of all the forums names as strings
     */
    public static RetObj<ArrayList<String>> getAllForums() {
        ArrayList<String> forums = ForumFacade.getAllForums();
        if (null == forums){
            return new RetObj<>(Result.FAIL);
        } else {
            return new RetObj<>(Result.SUCCESS, forums);
        }
    }

    /**
     * @param sId - session Id
     * @return - array of all the sub forums in the session forum
     *           forum must be defined for the given 'sId'.
     */
    public static RetObj<ArrayList<String>> getAllSubForums(UUID sId) {
        ArrayList<String> subForums = ForumFacade.getAllSubForums(sId);
        if (null == subForums){
            return new RetObj<>(Result.FAIL);
        } else {
            return new RetObj<>(Result.SUCCESS, subForums);
        }
    }

    /**
     * return all the sub-forum ('subForumName') threads as collection of ServiceMessages
     * @param sId - session id
     * @param subForumName - the name of the sub-forum
     * @return all the sub-forum ('subForumName') threads as collection of ServiceMessages
     */
    public static RetObj<Collection<ServiceMessage>> getAllThreads(UUID sId, String subForumName) {
        if (!ForumFacade.getAllSubForums(sId).contains(subForumName)){
            return new RetObj<>(Result.SUBFORUM_NOT_FOUND);
        }
        Collection<Message> threads = ForumFacade.getAllThreads(sId, subForumName);
        if (null == threads){
            return new RetObj<>(Result.FAIL);
        } else {
            Collection<ServiceMessage> serviceMessages = new ArrayList<>();
            for(Message message : threads){
                serviceMessages.add(new ServiceMessage(message));
            }
            return new RetObj<>(Result.SUCCESS, serviceMessages);
        }
    }

    /**
     * return all the messages (as ServiceMessages) that are comment of the message (messageId)
     * message id can be a thread opening message or a comment to another message.
     *
     * @param sId - session id
     * @param subForum - the name of the sub-forum
     * @param messageId - the id of the source message
     * @return collection of 'ServiceMessages' contain all the comments
     */
    public static RetObj<Collection<ServiceMessage>> getAllComments(UUID sId, String subForum, int messageId){
        Collection<Message> messages = ForumFacade.getAllComments(sId, subForum, messageId);
        Collection<ServiceMessage> serviceMessages = new ArrayList<>();
        if (null != messages){
            for (Message m : messages){
                serviceMessages.add(new ServiceMessage(m));
            }
            return new RetObj<>(Result.SUCCESS, serviceMessages);
        } else {
            return new RetObj<>(Result.MESSAGE_NOT_FOUND);
        }
    }

    /**
     * return the content of an message as 'ServiceMessage'
     * @param sId - session id
     * @param subForum - the sub-forum
     * @param messageId - the wanted message id
     * @return - return the content of the message 'messageId' as 'ServiceMessage'
     */
    public static RetObj<ServiceMessage> getMessage(UUID sId, String subForum, int messageId){
        Message message = ForumFacade.getMessage(sId, subForum, messageId);
        if (null == message){
            return new RetObj<>(Result.FAIL);
        } else {
            return new RetObj<>(Result.SUCCESS, new ServiceMessage(message));
        }
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
}
