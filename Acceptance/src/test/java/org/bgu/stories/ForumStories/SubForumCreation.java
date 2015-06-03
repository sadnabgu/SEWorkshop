package org.bgu.stories.ForumStories;

import org.bgu.domain.facades.ForumFacade;
import org.bgu.domain.model.Forum;
import org.bgu.domain.model.Member;
import org.bgu.domain.model.SubForum;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by Sharon Kerzman on 24/04/2015.
 */
public class SubForumCreation {
    @Test
    /*
    *Test purpose: Sub Forum is in created after giving correct data
    *
    * Steps:
    * 1. insert correct data
    * 2. verify: sub forum is created
    *
    * TODO - work with Junit !!!
    *
     */
    public void createSubForumWithCorrectData_ForumCreation_NewSubForumIsCreated(){
        System.out.print("testing 'createForumWithCorrectData_SystemInitialized_NewForumInWaitingState' \n");
        // Setup system to initial state
        // ForumFacade forumFacade = new ForumFacade();

        // Forum creation
        int forumID = 1234;
        String forumName = "Tapuz";
        Forum forumCreated = ForumFacade.createForum(forumID, forumName, "admin", "pass");

        // Member creation
        String memberName = "Milky";
        String memberPass = "ParrotOnTheShoulder";
        Member member = new Member(memberName, memberPass);

        // Simulate Super-Admin clicks Create Sub Forum button
        String subName = "Cars";

        // Insert valid data
        boolean flag = forumCreated.addNewSubForum(subName, member);

        // verify : Query system so it has a new Sub forum is created
        SubForum subForumReturned = forumCreated.getSubForum(subName);
        if (flag &&
                subForumReturned != null &&
                subName.equals(subForumReturned.getName())
            ){
            System.out.print("Passed! :) \n");
        }else {
            System.out.print("Failed! :( \n");
        }

        // Clear forum data
        ForumFacade.resetForums();


    }

        @Test
        @Ignore
    /*
    *Test purpose: Sub Forum is not exists after giving incorrect data
    *
    * Steps:
    * 1. insert correct data
    * 2. verify: sub forum is not exists
    *
     */
        public void createSubForumWithInCorrectData_ForumCreation_NewSubForumIsNotCreated(){
            // TODO: Setup system to initial state

            // TODO: Forum creation

            // TODO: Simulate Super-Admin clicks Create Sub Forum button

            // TODO: Insert invalid data

            // TODO: verify : new Sub Forum is not exists
        }
}
