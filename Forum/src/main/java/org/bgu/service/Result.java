package org.bgu.service;

/**
 * Created by michael on 05/06/15.
 * This enum describes every result generated from a requested service.
 */
public enum Result {
    SUCCESS("SUCESS"),
    FAIL("GENERAL FAILURE"),
    REINITIALIZE_SYSTEM("system already initialized!"),
    UNINITIALIZED_SYSTEM("system was uninitialized!"),
    NOT_LOGGEDIN_SYSTEM("not logged in to the system!"),
    FORUM_EXISTS("the given forum already exists!"),
    MODERATOR_NOT_MEMBER("a moderate must be a member in the forum!"),
    DUPLICATED_SUBFORUM("a sub forum with same name wxists!"),
    ALREADY_LOGDIN("user already loged in, please logout first"),
    WRONG_USER_PASS("Wrong username or password"),
    FORUM_NOT_FOUND("the forum not exist"),
    DUPLICATED_USERNAME("already exist user with this username");

    final public String _desc;

    Result(String description){
        _desc = description;
    }

    public String toString(){
        return _desc;
    }

}
