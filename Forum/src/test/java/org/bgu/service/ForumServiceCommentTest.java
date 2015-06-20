package org.bgu.service;

import org.bgu.domain.facades.ForumFacade;
import org.bgu.domain.facades.UserFacade;
import org.bgu.domain.model.Forum;
import org.bgu.service.Exceptions.Result;
import org.bgu.service.Exceptions.RetObj;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

/**
 * Created by hodai on 6/20/15.
 */
public class ForumServiceCommentTest extends abstractTest{
    private static final String SUB_FORUM_NAME = "protection";
    private static final String TREAD_TITLE1 = "thread title 1";
    private static final String THREAD_MSG1 = "thread msg 1";
    private static final String TREAD_TITLE2 = "thread title 2";
    private static final String THREAD_MSG2 = "thread msg 2";
    private static Forum forum;
    private static Collection<String> mods;

    @BeforeClass
    public static void initialSystem() {
        UserFacade.resetSystem();
        ForumFacade.createForum(FORUM_NAME, MANAGER1_NAME, MANAGER1_PASS);
        forum = ForumFacade.getForum(FORUM_NAME);
        mods = new ArrayList<>();
        mods.add("hodai");
        assertEquals(Result.SUCCESS, UserFacade.addMember(forum.getForumName(), "hodai", "hodai"));
        assertEquals(Result.SUCCESS, UserFacade.addMember(forum.getForumName(), "melki", "melki"));
        assertEquals(Result.SUCCESS, UserFacade.addMember(forum.getForumName(), "kerzman", "kerzman"));

        // login as admin TODO - replace tests to use regular member and create sub-forum using facade directly
        loginModerate();
        loginAdmin();
        loginGoldMember();
        loginMember();

        assertEquals(Result.SUCCESS, ForumService.addNewSubForum(managerSid, SUB_FORUM_NAME, mods)._result);
        assertEquals(Result.SUCCESS, ForumService.addNewThread(managerSid, SUB_FORUM_NAME, TREAD_TITLE1, THREAD_MSG1)._result);
        assertEquals(Result.SUCCESS, ForumService.addNewThread(managerSid, SUB_FORUM_NAME, TREAD_TITLE2, THREAD_MSG2)._result);


    }


        /* comments tests */

    @Test
    public void addNewThread_correctData_successNewThreadAdded() {
        assertEquals(Result.SUCCESS, ForumService.addNewThread(goldMemberSid, SUB_FORUM_NAME, "condoms", "how to use them?")._result);
    }

    @Test
    public void deleteThread_ByCreator_successThreadRemoved(){
        RetObj<Integer> returnedMessageObj = ForumService.addNewThread(goldMemberSid, SUB_FORUM_NAME, "condoms", "how to use them?");
        assertEquals(Result.SUCCESS, returnedMessageObj._result);
        assertEquals(Result.SUCCESS, ForumService.removeMessage(goldMemberSid, SUB_FORUM_NAME, returnedMessageObj._value)._result);
    }

    @Test
    public void deleteThread_ByOther_fail(){
        RetObj<Integer> returnedMessageObj = ForumService.addNewThread(goldMemberSid, SUB_FORUM_NAME, "condoms", "how to use them?");
        assertEquals(Result.SUCCESS, returnedMessageObj._result);
        assertEquals(Result.REMOVE_COMMENT_FAILED, ForumService.removeMessage(memberSid, SUB_FORUM_NAME, returnedMessageObj._value)._result);
    }

    @Test
    public void deleteThread_ByGoldMember_successThreadRemoved(){
        for(int i = 0; i<8; i++){
            addNewThread_correctData_successNewThreadAdded();
        }
        RetObj<Integer> returnedMessageObj = ForumService.addNewThread(memberSid, SUB_FORUM_NAME, "condoms - results", "i know how to use them!");
        assertEquals(Result.SUCCESS, returnedMessageObj._result);
        assertEquals(Result.SUCCESS, ForumService.removeMessage(goldMemberSid,SUB_FORUM_NAME,returnedMessageObj._value)._result);
    }

    @Test
    public void editThread_ByCreator_successThreadEdited(){
        RetObj<Integer> returnedMessageObj = ForumService.addNewThread(goldMemberSid, SUB_FORUM_NAME, "condoms", "how to use them?");
        assertEquals(Result.SUCCESS, returnedMessageObj._result);
        assertEquals(Result.SUCCESS, ForumService.editMessage(goldMemberSid, SUB_FORUM_NAME, returnedMessageObj._value, "condoms", "how to wear them?")._result);
    }

    @Test
    public void editThread_ByOther_fail(){
        RetObj<Integer> returnedMessageObj = ForumService.addNewThread(goldMemberSid, SUB_FORUM_NAME, "condoms", "how to use them?");
        assertEquals(Result.SUCCESS, returnedMessageObj._result);
        assertEquals(Result.EDIT_COMMENT_FAILED, ForumService.editMessage(memberSid, SUB_FORUM_NAME, returnedMessageObj._value, "condoms - results", "i don't know how to use them!")._result);
    }

    @Test
    public void editThread_ByGoldMember_successThreadEdited(){
        for(int i = 0; i<8; i++){
            addNewThread_correctData_successNewThreadAdded();
        }
        RetObj<Integer> returnedMessageObj = ForumService.addNewThread(memberSid, SUB_FORUM_NAME, "condoms - results", "i know how to use them!");
        assertEquals(Result.SUCCESS, returnedMessageObj._result);
        assertEquals(Result.SUCCESS, ForumService.editMessage(goldMemberSid, SUB_FORUM_NAME, returnedMessageObj._value, "condoms - results", "i don't know how to use them!")._result);
    }

    @Test
         public void editThread_ByGoldMemberWithNoData_fail(){
        for(int i = 0; i<8; i++){
            addNewThread_correctData_successNewThreadAdded();
        }
        RetObj<Integer> returnedMessageObj = ForumService.addNewThread(memberSid, SUB_FORUM_NAME, "condoms - results", "i know how to use them!");
        assertEquals(Result.SUCCESS, returnedMessageObj._result);
        assertEquals(Result.EDIT_COMMENT_FAILED, ForumService.editMessage(goldMemberSid, SUB_FORUM_NAME, returnedMessageObj._value, "", "")._result);
    }

    @Test
    public void editThread_ByCreatorWithNoData_fail(){
        RetObj<Integer> returnedMessageObj = ForumService.addNewThread(goldMemberSid, SUB_FORUM_NAME, "condoms", "how to use them?");
        assertEquals(Result.SUCCESS, returnedMessageObj._result);
        assertEquals(Result.EDIT_COMMENT_FAILED, ForumService.editMessage(goldMemberSid, SUB_FORUM_NAME, returnedMessageObj._value, "", "")._result);
    }

    @Test
    public void editThread_ByGoldMemberWithOnlyTitleOrBody_fail(){
        for(int i = 0; i<8; i++){
            addNewThread_correctData_successNewThreadAdded();
        }
        RetObj<Integer> returnedMessageObj = ForumService.addNewThread(memberSid, SUB_FORUM_NAME, "condoms - results", "i know how to use them!");
        assertEquals(Result.SUCCESS, returnedMessageObj._result);
        assertEquals(Result.SUCCESS, ForumService.editMessage(goldMemberSid, SUB_FORUM_NAME, returnedMessageObj._value, "condoms - results", "")._result);
        assertEquals(Result.SUCCESS, ForumService.editMessage(goldMemberSid, SUB_FORUM_NAME, returnedMessageObj._value, "", "i know how to use them!")._result);
    }

    @Test
    public void editThread_ByCreatorWithOnlyTitleOrBody_fail(){
        RetObj<Integer> returnedMessageObj = ForumService.addNewThread(goldMemberSid, SUB_FORUM_NAME, "condoms", "how to use them?");
        assertEquals(Result.SUCCESS, returnedMessageObj._result);
        assertEquals(Result.SUCCESS, ForumService.editMessage(goldMemberSid, SUB_FORUM_NAME, returnedMessageObj._value, "condoms - results", "")._result);
        assertEquals(Result.SUCCESS, ForumService.editMessage(goldMemberSid, SUB_FORUM_NAME, returnedMessageObj._value, "", "i know how to use them!")._result);
    }
}
