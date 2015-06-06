package org.bgu.domain.model;

/**
 * Created by gur on 20/04/2015.
 */
public abstract class User {

    public abstract String getUserName();

    public abstract boolean login(String pass);

    public abstract void logOut();

    public abstract Member getMember();
}

