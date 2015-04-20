package org.bgu.model;

import java.util.ArrayList;
import java.util.Collection;

public class Member extends User{
    Collection<Member> friends;

    public Member(){
        this.friends = new ArrayList<>();
    }

    public Collection<Member> getFriends(){
        return friends;
    }
}