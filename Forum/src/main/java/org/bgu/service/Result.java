package org.bgu.service;

/**
 * Created by michael on 05/06/15.
 * This enum describes every result generated from a requested service.
 */
public enum Result {
    SUCCESS("SUCESS"),
    FAIL("GENERAL FAILURE"),
    /* System view */
    REINITIALIZE_SYSTEM("system already initialized"),
    UNINITIALIZED_SYSTEM("system was uninitialized"),
    NOT_LOGGEDIN_SYSTEM("not logged in to the system"),
    /* Forum view */
    FORUM_EXISTS("the given forum already exists"),
    DUPLICATED_SUBFORUM("a sub forum with same name exists"),
    MEMBER_NOT_FORUM_ADMIN("the action is allowed to forum admins only"),
    /* Sub Forum view */
    MODERATOR_NOT_MEMBER("a moderate must be a member in the forum"),
    MODERATOR_ALREADY_EXISTS("the given moderator already exists in the sub forum"),
    NO_MODERATORS_WERE_GIVEN("moderators must be delivered"),
    SUBFORUM_MODERATOR_NOT_MEMBER("the moderator delivered is not a member");

    final public String _desc;

    Result(String description){
        _desc = description;
    }

    public String toString(){
        return _desc;
    }

}
