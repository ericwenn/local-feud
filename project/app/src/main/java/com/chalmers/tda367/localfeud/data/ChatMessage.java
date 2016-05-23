package com.chalmers.tda367.localfeud.data;

import android.util.Log;

import com.chalmers.tda367.localfeud.util.TagHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Daniel Ahlqvist on 2016-05-08.
 */
public class ChatMessage
{
    private int id;
    private Chat chatid;
    private String message;
    private User user;
    private String timesent;

    public ChatMessage(Chat chatid, String message, User user) {
        this.chatid = chatid;
        this.message = message;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int newId) {
        id = newId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User newUser) {
        user = newUser;
    }

    public Chat getChatId() {
        return chatid;
    }

    public void setChatId(Chat newId) {
        chatid = newId;
    }

    public String getText() {
        return message;
    }

    public void setText(String newText) {
        message = newText;
    }

    public Calendar getDatePosted() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ", Locale.ENGLISH);
        try {
            calendar.setTime(simpleDateFormat.parse(getStringDatePosted()));
        } catch (ParseException e) {
            Log.e(TagHandler.MAIN_TAG, "Can't parse " + getStringDatePosted() + " to SimpleDateFormat");
        }
        return calendar;
    }

    public String getStringDatePosted() {
        return timesent.substring(0,10) + " " + timesent.substring(11,16);
    }
}
