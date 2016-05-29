package com.chalmers.tda367.localfeud.data;

import java.io.Serializable;

/**
 *  All required data about user.
 */
public class Me implements Serializable {
    private int id, age;
    private String firstname, lastname;
    private User.Gender gender;

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public int getAge() {
        return age;
    }

    public int getId() {
        return id;
    }

    public User.Gender getGender() {
        return gender;
    }
}
