package com.example.parking;

public class Users {
    public String firstname;
    public String email;
    public String lastname;
    public String hasticket;

    public Users(String firstname,String email, String lastname, String hasticket) {
        this.firstname = firstname;
        this.email=email;
        this.lastname = lastname;
        this.hasticket=hasticket;
    }

    public Users() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHasticket() {
        return hasticket;
    }

    public void setHasticket(String hasticket) {
        this.hasticket = hasticket;
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
