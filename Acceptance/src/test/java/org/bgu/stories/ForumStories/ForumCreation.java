package org.bgu.stories.ForumStories;


import org.bgu.domain.facades.ForumFacade;
import org.bgu.domain.model.Forum;
import org.junit.Ignore;
import org.junit.Test;
import java.lang.System;

/**
 * Created by gur on 21/04/2015.
 *
 */
public class ForumCreation {
    @Test

    /*
    *Test purpose: Forum is in waiting state after giving correct data
    *
    * Steps:
    * 1. insert correct data
    * 2. verify: forum is in Waiting state
    *
    *   TODO - acceptance test cant use the facade, can only use the service layer objects!!
     */
    public void createForumWithCorrectData_SystemInitialized_NewForumInWaitingState(){
        System.out.print("testing 'createForumWithCorrectData_SystemInitialized_NewForumInWaitingState' \n");
        // Setup system to initial state :
        //ForumFacade forumFacade = new ForumFacade();

        // Simulate Super-Admin clicks Create-Forum button
        String forumName = "Tapuz";

        // Insert valid data
        Forum forumCreated = ForumFacade.createForum(forumName, "admin", "pass");

        // verify : Query system so it has a new forum in waiting state
        Forum forumReturned = ForumFacade.getForum(forumName);

        if (forumCreated!=null &&
                forumReturned!=null &&
                forumCreated == forumReturned &&
                forumName.equals(forumReturned.getName()) &&
                forumReturned.getName().equals(forumName)
            ){

            System.out.print("Passed! :) \n");
        }
        else {
            System.out.print("Failed! :( \n");
        }

        // Clear forum data
        ForumFacade.resetForums();
    }

    @Test
    @Ignore
    /*
    *Test purpose: Forum is in not exist after giving incorrect data
    *
    * Steps:
    * 1. insert incorrect data
    * 2. verify: forum is in not exists
    *
    *
     */
    public void createForumWithIncorrectData_SystemInitialized_NewForumInNotExists() {
        // TODO: Setup system to initial state

        // TODO: Simulate Super-Admin clicks Create-Forum button

        // TODO: Insert incorrect data

        // TODO: Verify: forum is in not exists
    }
}
