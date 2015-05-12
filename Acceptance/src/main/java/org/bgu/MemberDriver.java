package org.bgu;

/**
 * Created by Sharon Kerzman on 12/05/2015.
 */
public class MemberDriver {
    private MemberCredentials credentials;
    private String name = "";
    private String state;
    private String requiredEmailPassword;

    public MemberDriver(MemberCredentials credentials) {
        if (credentials.getPassword() == credentials.getRepassword()) {
            this.credentials = new MemberCredentials(credentials);
            this.name = credentials.getFirstname();
            setState("waiting");
        }
    }

    public MemberCredentials getCredentials() {
        return credentials;
    }

    public void setCredentials(MemberCredentials credentials) {
        this.credentials = credentials;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    public String getRequiredEmailPassword() {
        return requiredEmailPassword;
    }

    public void setRequiredEmailPassword(String requiredEmailPassword) {
        this.requiredEmailPassword = requiredEmailPassword;
    }

    public void insertEmailVerificaion(String emailVerification) {
        if (emailVerification == requiredEmailPassword){
            state = "online";
        }
    }
}
