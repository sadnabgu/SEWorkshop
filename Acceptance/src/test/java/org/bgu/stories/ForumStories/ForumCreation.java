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
    @Ignore
    /*
    *Test purpose: Forum is in waiting state after giving correct data
    *
    * Steps:
    * 1. insert correct data
    * 2. verify: forum is in Waiting state
    *
    *
     */
    public void createForumWithCorrectData_SystemInitialized_NewForumInWaitingState(){
        System.out.print("testing 'createForumWithCorrectData_SystemInitialized_NewForumInWaitingState' \n");
        // Setup system to initial state :
        //ForumFacade forumFacade = new ForumFacade();

        // Simulate Super-Admin clicks Create-Forum button
        int forumID = 1234;
        String forumName = "Tapuz";

        // Insert valid data
        Forum forumCreated = ForumFacade.createForum(forumID, forumName);

        // verify : Query system so it has a new forum in waiting state
        Forum forumReturned = ForumFacade.getForum(forumID);

        if (forumCreated!=null &&
                forumReturned!=null &&
                forumCreated == forumReturned &&
                forumName.equals(forumReturned.getName()) &&
                forumReturned.getId() == forumID
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
