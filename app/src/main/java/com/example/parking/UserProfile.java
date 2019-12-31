package com.example.parking;

public class UserProfile {

    public String firstName;
    public String lastName;

    public UserProfile(){

    }

    public UserProfile(String firstName,String lastName){
        this.firstName = firstName;
        this.lastName = lastName;

    }

    public String getFirstName(){
        return  firstName;
    }

    public String setLastName(String lastName){
        this.lastName = lastName;
        return lastName;
    }


    public String getLastName(){
        return lastName;
    }

    public String setFirstName(String firstName){
        this.firstName = firstName;
        return firstName;
    }
}
