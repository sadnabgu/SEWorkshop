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
        this.waitingEmailMembersList = new LinkedList<MemberDriver> ();
        this.onlineMembersList = new LinkedList<MemberDriver> ();
        this.offlineMembersList = new LinkedList<MemberDriver> ();
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

    public MemberDriver getFirstMemberFromOnlineList(){
        return onlineMembersList.getFirst();
    }

    public void changeUserListFromWaitingToOnline(MemberDriver member) {
        waitingEmailMembersList.remove(member);
        onlineMembersList.add(member);
    }

    public int returnMemberIntFromWaitingList(MemberDriver member){
        return waitingEmailMembersList.indexOf(member);
    }

    public int returnMemberIntFromOnlineList(MemberDriver member){
        return onlineMembersList.indexOf(member);
    }
}


