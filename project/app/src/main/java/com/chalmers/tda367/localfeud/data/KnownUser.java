package com.chalmers.tda367.localfeud.data;

/**
 * Created by Alfred on 2016-04-11.
 */
public class KnownUser extends User {
    private String firstName;
    private String lastName;

    public KnownUser(int id, int age, Sex sex, String firstName, String lastName){
        super(id, age, sex);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public String getLastName(){
        return this.lastName;
    }
}
