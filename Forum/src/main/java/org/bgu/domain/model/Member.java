package org.bgu.domain.model;

import java.util.ArrayList;
import java.util.Collection;

public class Member extends User{
    private String name;
    private String pass;
    private boolean logedIn; //TODO - it's really nessesary???

    Collection<Member> friends;

    public Member(String name, String pass){
        this.friends = new ArrayList<>();
        this.name = name;
        this.pass = pass;
    }

    public Collection<Member> getFriends(){
        return friends;
    }

    @Override
    public String getUserName() {
        return name;
    }

    @Override
    public boolean login(String pass) {
        /*
        if(logedIn)
            return false; // TODO - hidden assumption, no paralel connections to the same user
            */
        if(this.pass.equals(pass)){
            logedIn = true;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void logOut() {
        logedIn = false;
    }

    @Override
    public Member getMember() {
        return this;
    }

    public boolean isFriendOf(Member friend) {
        return friends.contains(friend);
    }

    public void addFriend(Member friend) {
        friends.add(friend);
    }

    public void removeFriend(Member friend) {
        friends.remove(friend);
    }
}