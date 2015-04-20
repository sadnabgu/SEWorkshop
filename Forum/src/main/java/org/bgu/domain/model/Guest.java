package org.bgu.domain.model;

public class Guest extends User{

    @Override
    public String getUserName() {
        return "Guest user";
    }

    @Override
    public boolean login(String pass) {
        return false;
    }

    @Override
    public void logOut() {
        // do nothing
    }
}
