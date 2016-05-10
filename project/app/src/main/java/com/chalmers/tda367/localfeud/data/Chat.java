package com.chalmers.tda367.localfeud.data;

import android.util.Log;

import com.chalmers.tda367.localfeud.util.TagHandler;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Alfred on 2016-04-11.
 */
public class Chat implements Serializable {

    /**
     * id : 1
     * status : accepted
     * users : [{"id":1,"firstname":"Karl","lastname":"Karlsson","href":"http://localhost/local-feud_backend/src/users/1/"},{"id":2,"firstname":"Johan","lastname":"Ulvgren","href":"http://localhost/local-feud_backend/src/users/2/"}]
     * date_started : 2016-05-09T16:23:58+02:00
     * number_of_unread_messages : 0
     * href : http://localhost/local-feud_backend/src/chats/1/
     */

    private int id;
    private Status status;
    private String date_started;
    private int number_of_unread_messages;
    private String href;
    /**
     * id : 1
     * firstname : Karl
     * lastname : Karlsson
     * href : http://localhost/local-feud_backend/src/users/1/
     */

    private List<KnownUser> users;

    private enum Status {
        ACCEPTED, PENDING
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getStringDateStarted() {
        return date_started;
    }

    public void setDateStarted(String dateStarted) {
        this.date_started = dateStarted;
    }

    public int getNumber_of_unread_messages() {
        return number_of_unread_messages;
    }

    public void setNumberOfUnreadMessages(int numberOfUnreadMessages) {
        this.number_of_unread_messages = numberOfUnreadMessages;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public List<KnownUser> getUsers() {
        return users;
    }

    public void setUsers(List<KnownUser> users) {
        this.users = users;
    }

    public String getChatName() {
        if (users.isEmpty()) {
            return "UNKNOWN";
        }
        else if (users.size() == 1) {
            return users.get(0).getFirstName() + " " + users.get(0).getLastName();
        }
        else {
            String chatName = "";
            for (KnownUser user : users) {
                chatName += (user.getFirstName() + ", ");
            }
            return chatName.substring(0, chatName.length() - 2);
        }
    }

    public Calendar getDateStarted() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ", Locale.ENGLISH);
        try {
            calendar.setTime(simpleDateFormat.parse(getStringDateStarted()));
        } catch (ParseException e) {
            Log.e(TagHandler.MAIN_TAG, "Can't parse " + getStringDateStarted() + " to SimpleDateFormat");
        }
        return calendar;
    }

    @Override
    public String toString() {
        return "Chat id: " + getId() + ", Users: " + getUsers() + ", href: " + getHref();
    }
}
