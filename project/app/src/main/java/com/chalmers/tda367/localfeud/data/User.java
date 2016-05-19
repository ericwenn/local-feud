package com.chalmers.tda367.localfeud.data;

import java.io.Serializable;

/**
 * Created by Alfred on 2016-04-11.
 */
public class User implements Serializable {
    private final int id;
    private int age;
    private Gender gender;
    private String href;

    public enum Gender {
        male, female
    }

    public User (int id, int age, Gender gender){
        this.id = id;
        this.age = age;
        this.gender = gender;
    }

    public User( Me user ) {
        this.id = user.getId();
        this.age = user.getAge();
        this.gender = user.getGender();
    }

    public int getId(){
        return this.id;
    }

    public int getAge(){
        return this.age;
    }

    public Gender getGender(){
        return this.gender;
    }

    public String getGenderSymbol()
    {
        if(gender.equals(Gender.male))
        {
            return "♂";
        }
        else if(gender.equals(Gender.female))
        {
            return "♀";
        }
        else
        {
            return "Unknown gender";
        }
    }
    public boolean equals(Object o){
        if (o == null){
            return false;
        }
        else if (o == this){
            return true;
        }
        else if (o instanceof User){
            return ((User) o).getId() == this.getId();
        }
        else{
            return false;
        }
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

}
