package com.chalmers.tda367.localfeud.data;

import android.util.Log;

import com.chalmers.tda367.localfeud.util.TagHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Daniel Ahlqvist on 2016-04-13.
 */
public class Like
{
    private int id, postid, userid;
    private String date_added;

    public int getId()
    {
        return id;
    }
    public void setId(int newId)
    {
        id = newId;
    }

    public int getPostId()
    {
        return postid;
    }
    public void setPostId(int newId)
    {
        postid = newId;
    }

    public int getUserId()
    {
        return userid;
    }
    public void setUserId(int newId)
    {
        userid = newId;
    }

    public Calendar getDateAdded() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ", Locale.ENGLISH);
        try {
            calendar.setTime(simpleDateFormat.parse(getStringDateAdded()));
        } catch (ParseException e) {
            Log.e(TagHandler.MAIN_TAG, "Can't parse " + getStringDateAdded() + " to SimpleDateFormat");
        }
        return calendar;
    }

    public String getStringDateAdded() {
        return date_added;
    }
}
