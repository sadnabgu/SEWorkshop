package org.bgu;

import org.joda.time.DateTime;

/**
 * Created by gur on 28/04/2015.
 */
public class SignUpTestDriver {
    private MemberCredentials credentials;

    public void setCredentials(MemberCredentials credentials) {
        this.credentials = credentials;
    }

    public MemberCredentials clickSignUp() {
        return credentials;
    }

    public boolean CheckEmailIsValid(){
        String email = credentials.getEmail();

        final String EMAIL_REGEX = "^[\\\\w!#$%&’*+/=?`{|}~^-]+(?:\\\\.[\\\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\\\.)+[a-zA-Z]{2,6}$";

        return email.matches(EMAIL_REGEX);
    }

}
