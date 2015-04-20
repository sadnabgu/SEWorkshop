package org.bgu.domain.model;

import java.util.ArrayList;
import java.util.Collection;

public class Member extends User{
    Collection<Member> friends;

    public Member(){
        this.friends = new ArrayList<Member>();
    }

    public Collection<Member> getFriends(){
        return friends;
    }
}