package org.bgu.domain.model;

import java.util.ArrayList;
import java.util.Collection;

public class Member extends User{
    private String _name;
    private String _pass;
    private String _email;
    private boolean _isLogedIn;
    private int _msgsTotal;
    private Collection<Member> _friends;
    private State _membershipStatus; //Regmember, SilverMember, GoldMember;
    //TODO - add field of total logged in time

    public Member(String name, String pass, String email){
        _name = name;
        _pass = pass;
        _email = email;
        _isLogedIn = false;
        _msgsTotal = 0;
        _friends = new ArrayList<Member>();
        _membershipStatus = new RegMemberStatus();      //state class of a member. default - regular member
    }

    @Override
    public boolean login(String pass) {
        /*
        if(logedIn)
            return false; // TODO - hidden assumption, no paralel connections to the same user
         */
        if(_pass.equals(pass)){
            _isLogedIn = true;
            return true;
        } else {
            return false;
        }
    }













    public int getUserId(){
        return _id;
    }

    public boolean legalDetails(String name, String pass, String email){
        //TODO - check if legal member attributes
        return true;
    }

    public void emailValidate(){
        _logedIn = true;
    }

    public Collection<Member> getFriends(){
        return _friends;
    }

    @Override
    public String getUserName() {
        return _name;
    }



    @Override
    public void logOut() {
        _logedIn = false;
    }


}