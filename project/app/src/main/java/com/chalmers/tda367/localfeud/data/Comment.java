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
public class Comment
{
    private int id, authorid, postid;
    private String content;
    private String date_posted;

    public int getId()
    {
        return id;
    }
    public void setId(int newId)
    {
        id = newId;
    }

    public int getAuthor()
    {
        return authorid;
    }
    public void setAuthor(int newId)
    {
        authorid = newId;
    }

    public int getPostId()
    {
        return postid;
    }
    public void setPostId(int newId)
    {
        postid = newId;
    }

    public String getText()
    {
        return content;
    }
    public void setText(String newText)
    {
        content = newText;
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
        return date_posted;
    }
}
