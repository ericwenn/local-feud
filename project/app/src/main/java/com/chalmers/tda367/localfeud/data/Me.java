package com.chalmers.tda367.localfeud.data;

import java.io.Serializable;

/**
 * Created by Alfred on 2016-05-11.
 */
public class Me implements Serializable {
    private int id, age;
    private String firstname, lastname, gender;

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

    public String getGender() {
        return gender;
    }
}
