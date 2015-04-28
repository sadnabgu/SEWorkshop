package org.bgu.domain.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * Created by hodai on 4/28/15.
 */
public class UserCollection {
    public int forumId;
    public Collection<User> users;
    public Collection<Member> admins;

    public UserCollection(int forumId){
        this.forumId = forumId;
        users = new ArrayList<>();
        admins = new ArrayList<>();
    }
}
