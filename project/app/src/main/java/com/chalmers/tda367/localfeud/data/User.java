package com.chalmers.tda367.localfeud.data;

import java.io.Serializable;

/**
 *  A User object containing all required data.
 */
public class User implements Serializable {
    private final int id;
    private int age;
    private Gender gender;
    private String href;
    private String firstname, lastname;

    public enum Gender {
        male, female
    }

    public User (int id, int age, Gender gender, String firstname, String lastname){
        this.id = id;
        this.age = age;
        this.gender = gender;
        this.firstname = firstname;
        this.lastname = lastname;
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

    public String getFirstname()
    {
        return firstname;
    }
    public void setFirstname(String lastname)
    {
        this.firstname = firstname;
    }
    public String getLastname()
    {
        return lastname;
    }
    public void setLastname(String lastname)
    {
        this.lastname = lastname;
    }

    /**
     *  Converts a gender into a gender symbol, which is displayed
     *  in the application
     *  @return a string with a gender symbol
     */
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

    @Override
    public String toString(){
        return "First name:" + firstname + "\n" +
                "Last name:" + lastname + "\n" +
                "Gender:" + gender + "\n" +
                "Age:" + age + "\n" +
                "Id:" + id + "\n" +
                "Href:" + href;
    }
}
