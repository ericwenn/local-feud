package com.chalmers.tda367.localfeud.data;

/**
 *  A User with revealed identity.
 */
public class KnownUser extends User {
    public KnownUser(int id, int age, Gender gender, String realFirstName, String realLastName){
        super(id, age, gender, realFirstName, realLastName);
    }
}
