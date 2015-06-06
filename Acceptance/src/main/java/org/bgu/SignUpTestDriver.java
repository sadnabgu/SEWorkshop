package org.bgu;

/**
 * Created by gur on 28/04/2015.
 */
public class SignUpTestDriver {
    private MemberDriver member;

    public SignUpTestDriver(MemberDriver member) {
        this.member = member;
    }

    public void setCredentials(MemberDriver member) {
        member = new MemberDriver(member.getCredentials());
    }

    public MembersListDriver clickSignUp() {
        MembersListDriver newList = new MembersListDriver();
        newList.addMemberToWaitingList(member);
        return newList;
    }
/*
    public boolean CheckEmailIsValid(){
        String email = credentials.getEmail();

        final String EMAIL_REGEX = "^[\\\\w!#$%&’*+/=?`{|}~^-]+(?:\\\\.[\\\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\\\.)+[a-zA-Z]{2,6}$";

        return email.matches(EMAIL_REGEX);
    }
*/
}
