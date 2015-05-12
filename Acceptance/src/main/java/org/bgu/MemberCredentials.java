package org.bgu;

import org.joda.time.DateTime;

/**
 * Created by gur on 28/04/2015.
 */
public class MemberCredentials {
    // TODO: add getters & setters
    private String email = "";
    private String username = "";
    private DateTime dob = DateTime.now();
    private String password = "";
    private String repassword = "";
    private String firstname = "";
    private String lastname = "";

    public MemberCredentials(){}

    public MemberCredentials(String email, String username, DateTime dob, String password, String repassword, String firstname, String lastname){
        this.email = email;
        this.username = username;
        this.dob = dob;
        this.password = password;
        this.repassword = repassword;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public MemberCredentials(MemberCredentials other){
        if (other.getPassword() == other.getRepassword()) {
            this.email = other.getEmail();
            this.username = other.getUsername();
            this.dob = other.getDob();
            this.password = other.getPassword();
            this.repassword = other.getRepassword();
            this.firstname = other.getFirstname();
            this.lastname = other.getLastname();
        }
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null || !(obj instanceof MemberCredentials)){
            return false;
        }

        MemberCredentials other = (MemberCredentials)obj;
        return this.getEmail().equals(other.getEmail()) && this.getUsername().equals(other.getUsername()) && this.getDob().equals(other.getDob()) &&
                this.getPassword().equals(other.getPassword()) && this.getRepassword().equals(other.getRepassword()) &&
                this.getFirstname().equals(other.getFirstname()) && this.getLastname().equals(other.getLastname());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public DateTime getDob() {
        return dob;
    }

    public void setDob(DateTime dob) {
        this.dob = dob;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}