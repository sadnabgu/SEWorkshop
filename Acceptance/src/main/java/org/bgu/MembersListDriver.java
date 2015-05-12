package org.bgu;
import org.bgu.domain.model.Member;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Sharon Kerzman on 12/05/2015.
 */
public class MembersListDriver {
    private LinkedList<MemberDriver> membersList;

    public MembersListDriver() {
        this.membersList = new LinkedList<MemberDriver> (){
        };
    }

    public MembersListDriver(LinkedList<MemberDriver> membersList) {
        this.membersList = membersList;
    }


    public void addMember(MemberDriver member){
        membersList.add(member);
    }

    public boolean isMember(MemberDriver member){
        return membersList.contains(member);
    }

    public MemberDriver getFirstMember(){
       return membersList.getFirst();
    }
}


