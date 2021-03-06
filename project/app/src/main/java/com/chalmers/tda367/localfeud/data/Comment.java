package com.chalmers.tda367.localfeud.data;

import android.util.Log;

import com.chalmers.tda367.localfeud.util.TagHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 *  A comment that's posted on a Post object.
 */
public class Comment extends GeneralPost {
    private int id, postid;
    private String content;
    private User user;
    private String date_posted;

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

    public int getPostId() {
        return postid;
    }

    public void setPostId(int newId) {
        postid = newId;
    }

    public String getText() {
        return content;
    }

    public void setText(String newText) {
        content = newText;
    }

    /**
     *  Converts the date_posted string to a calendar object and returns it
     *  @return a calendar object with the date
     */
    public Calendar getDatePosted() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ENGLISH);
        try {
            calendar.setTime(simpleDateFormat.parse(getStringDatePosted()));
        } catch (ParseException e) {
            Log.e(TagHandler.MAIN_TAG, "Can't parse " + getStringDatePosted() + " to SimpleDateFormat");
        }
        return calendar;
    }

    private String getStringDatePosted() {
        return date_posted;
    }
}
