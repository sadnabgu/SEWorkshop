package org.bgu;

import java.util.LinkedList;

/**
 * Created by Sharon Kerzman on 12/05/2015.
 */
public class MembersListDriver {
    private LinkedList<MemberDriver> waitingEmailMembersList;
    private LinkedList<MemberDriver> onlineMembersList;
    private LinkedList<MemberDriver> offlineMembersList;

    public MembersListDriver() {
        this.waitingEmailMembersList = new LinkedList<MemberDriver> (){
        };
    }

    public MembersListDriver(LinkedList<MemberDriver> membersList) {
        this.waitingEmailMembersList = membersList;
    }


    public void addMemberToWaitingList(MemberDriver member){
        waitingEmailMembersList.add(member);
    }

    public boolean isMemberInWaitingList(MemberDriver member){
        return waitingEmailMembersList.contains(member);
    }

    public MemberDriver getFirstMemberFromWaitingList(){
       return waitingEmailMembersList.getFirst();
    }
}


