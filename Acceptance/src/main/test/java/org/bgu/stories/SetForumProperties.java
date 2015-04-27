package org.bgu.stories;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by gur on 21/04/2015.
 */
public class SetForumProperties {
    @Test
    @Ignore
    /*
    *Test purpose: Forum is active after giving correct data
    *
    * Steps:
    * 1. insert correct data
    * 2. verify: forum is active
    *
    *
     */
    public void setPropertiesWithCorrectData_systemHasWaitingForum_PropertiesAreSetAndForumIsActive(){
        //TODO: setup system to have a waiting forum and super admin

        //TODO: simulate forum policy
        //TODO: simulate forum moderators

        //TODO: Policy data are correct
        //TODO: simulate activation

        //TODO: verify: query that the forum is active
    }

    @Test
    @Ignore
    /*
    *Test purpose: Forum is waiting after giving incorrect data
    *
    * Steps:
    * 1. insert incorrect data
    * 2. verify: forum is in Waiting mode
    *
    *
     */
    public void setPropertiesWithIncorrectData_systemHasWaitingForum_PropertiesAreNotSetAndForumIsWaiting(){
        //TODO: setup system to have a waiting forum and super admin

        //TODO: simulate forum policy
        //TODO: simulate forum moderators

        //TODO: Policy data are incorrect
        //TODO: simulate activation

        //TODO: verify: query that the forum is in waiting mode
    }

}
