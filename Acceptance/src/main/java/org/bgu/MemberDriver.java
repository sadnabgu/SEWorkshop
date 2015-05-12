package org.bgu;

/**
 * Created by Sharon Kerzman on 12/05/2015.
 */
public class MemberDriver {
    private MemberCredentials credentials;
    private String name = "";

    public MemberDriver(MemberCredentials credentials) {
        if (credentials.getPassword() == credentials.getRepassword()) {
            this.credentials = new MemberCredentials(credentials);
            this.name = credentials.getFirstname();
        }
    }

    public MemberCredentials getCredentials() {
        return credentials;
    }

    public void setCredentials(MemberCredentials credentials) {
        this.credentials = credentials;
    }

}
