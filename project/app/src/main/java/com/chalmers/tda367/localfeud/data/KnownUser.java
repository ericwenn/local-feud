package com.chalmers.tda367.localfeud.data;

/**
 * Created by Alfred on 2016-04-11.
 */
public class KnownUser extends User {
    private String firstname;
    private String lastname;

    public KnownUser(int id, int age, Gender gender, String firstName, String lastName){
        super(id, age, gender);
        this.firstname = firstName;
        this.lastname = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstname = firstName;
    }

    public void setLastName(String lastName) {
        this.lastname = lastName;
    }

    public String getFirstName(){
        return this.firstname;
    }

    public String getLastName(){
        return this.lastname;
    }
}
