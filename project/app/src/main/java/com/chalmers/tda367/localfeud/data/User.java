package com.chalmers.tda367.localfeud.data;

/**
 * Created by Alfred on 2016-04-11.
 */
public class User {
    private int id;
    private int age;
    private Sex sex;
    private String href;

    public enum Sex{
        MALE, FEMALE
    }

    public User (int id, int age, Sex sex){
        this.id = id;
        this.age = age;
        this.sex = sex;
    }

    public int getId(){
        return this.id;
    }

    public int getAge(){
        return this.age;
    }

    public Sex getSex(){
        return this.sex;
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
