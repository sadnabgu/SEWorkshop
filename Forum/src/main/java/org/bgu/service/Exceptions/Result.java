package org.bgu.service.Exceptions;

/**
 * Created by michael on 05/06/15.
 * This enum describes every result generated from a requested service.
 */
public enum Result {
    SUCCESS("SUCESS"),
    FAIL("GENERAL FAILURE"),
    /* System view */
    REINITIALIZE_SYSTEM("system already was initialized by this super admin"),
    UNINITIALIZED_SYSTEM("please initialize system with this admin"),
    NOT_LOGGEDIN_SYSTEM("not logged in to the system"),
    /* Forum view */
    FORUM_EXISTS("the given forum already exists"),
    DUPLICATED_SUBFORUM("a sub forum with same name exists"),
    MEMBER_NOT_FORUM_ADMIN("the action is allowed to forum admins only"),
    FORUM_NOT_FOUND("the forum not exist"),
    /* Sub Forum view */
    UNAUTHORIZED_OPERATION("the operation is authorized only to forum managers"),
    NO_MODERATORS_WERE_GIVEN("moderators must be delivered"),
    SUBFORUM_MODERATOR_NOT_MEMBER("the moderator delivered is not a member"),
    /* User-manage view */
    NOT_LOGGED_IN("user is not logged in"),
    FRIEND_NOT_EXIST("friend does not exist"),
    ALREADY_FRIENDS("the users are already friends"),
    NOT_FRIENDS("the users are not friends"),
    ALREADY_MODERATE("the user is already a moderate"),
    NOT_MODERATOR("not a moderator"),

    ALREADY_LOGDIN("user already loged in, please logout first"),
    USERNAME_EXISTS("user name or password exists"),
    WRONG_USER_NAME_OR_PASS("Wrong username or password"),
    DUPLICATED_USERNAME("already exist user with this username"),
    SUBFORUM_ALREADY_REMOVED("the subforum was already removed from forum"),
    NEW_THREAD_FAIL("the new thread was failed. please fill title or body");
    final public String _desc;

    Result(String description){
        _desc = description;
    }

    public String toString(){
        return _desc;
    }
}
