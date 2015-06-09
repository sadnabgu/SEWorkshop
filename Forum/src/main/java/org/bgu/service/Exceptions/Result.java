package org.bgu.service.Exceptions;

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
    MEMBER_NOT_FOUND("the member not registered to forum "),
    MEMBER_NOT_FORUM_ADMIN("the action is allowed to forum admins only"),
    FORUM_NOT_FOUND("the forum not exist"),
    /* Sub Forum view */
    MODERATOR_NOT_MEMBER("a moderate must be a member in the forum"),
    MODERATOR_ALREADY_EXISTS("the given moderator already exists in the sub forum"),
    NO_MODERATORS_WERE_GIVEN("moderators must be delivered"),
    SUBFORUM_MODERATOR_NOT_MEMBER("the moderator delivered is not a member"),
    /* User-manage view */
    NOT_LOGGED_IN("user is not logged in"),
    FRIEND_NOT_EXIST("friend does not exist"),
    ALREADY_FRIENDS("the users are already friends"),
    NOT_FRIENDS("the users are not friends"),

    ALREADY_LOGDIN("user already loged in, please logout first"),
    WRONG_USER_PASS("Wrong username or password"),
    DUPLICATED_USERNAME("already exist user with this username");
    final public String _desc;

    Result(String description){
        _desc = description;
    }

    public String toString(){
        return _desc;
    }

}
