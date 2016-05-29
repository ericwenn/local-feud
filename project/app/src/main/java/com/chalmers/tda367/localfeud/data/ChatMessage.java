package com.chalmers.tda367.localfeud.data;

import android.util.Log;

import com.chalmers.tda367.localfeud.util.TagHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 *  A ChatMessage from a Chat.
 *  Holding data about a single message.
 */
public class ChatMessage {
    private int id;
    private int chatid;
    private String message;
    private User user;
    private String timesent;

    public ChatMessage(int chatid, String message, User user) {
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

    public int getChatId() {
        return chatid;
    }

    public void setChatId(int newId) {
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
        return timesent.substring(0, 10) + " " + timesent.substring(11, 16);
    }

    @Override
    public String toString() {
        return "Id: " + id + "\n" +
                "Chat-id: " + chatid + "\n" +
                "Message: " + message + "\n" +
                "User: " + user + "\n" +
                "String: " + timesent;
    }
}
