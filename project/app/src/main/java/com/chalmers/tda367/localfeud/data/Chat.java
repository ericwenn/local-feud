package com.chalmers.tda367.localfeud.data;

/**
 * Created by Alfred on 2016-04-11.
 */
public class Chat {
    /*
     *  TILLFÄLLIGT INNEHÅLL I KLASS, SKA SEDAN MATCHAS MED API.
     *  ENBART FÖR ATT TESTA CHATLISTADAPTER
     */
    private String userName;
    private String msg;

    public Chat(String userName, String msg) {
        this.msg = msg;
        this.userName = userName;
    }

    public String getMsg() {
        return msg;
    }

    public String getUserName() {
        return userName;
    }
}
