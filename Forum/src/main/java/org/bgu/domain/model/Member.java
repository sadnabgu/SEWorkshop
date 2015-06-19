package org.bgu.domain.model;

import java.util.ArrayList;
import java.util.Collection;

public class Member extends User{
    private String name;
    public String pass;

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
        if(this.pass.equals(pass))
            return true;
       return false;
    }

    public boolean isFriendOf(Member friend) {
        return friends.contains(friend);
    }

    public void addFriend(Member friend) {
        friends.add(friend);
    }

    public void remocveFriend(Member friend) {
        friends.remove(friend);
    }
}