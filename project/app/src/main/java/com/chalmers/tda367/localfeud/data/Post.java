package com.chalmers.tda367.localfeud.data;

import android.util.Log;

import com.chalmers.tda367.localfeud.services.Location;
import com.chalmers.tda367.localfeud.util.TagHandler;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Alfred on 2016-04-11.
 */
public class Post extends GeneralPost implements Serializable {

    private int id;
    private Position location;
    private User user;
    private double reach;
    private int distance;
    private Content content;
    private String date_posted;
    private boolean is_deleted;
    private int number_of_comments;
    private int number_of_likes;
    private String href;
    private boolean current_user_has_liked;


    public boolean isLiked() {
        return current_user_has_liked;
    }

    public void setIsLiked(boolean currentUserHasLiked) {
        this.current_user_has_liked = currentUserHasLiked;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Position getLocation() {
        setDistance();
        return location;
    }

    public void setLocation(Position location) {
        this.location = location;
    }


    public void setDistance()
    {
        int globeRadius = 6371000;
        double myLatitude = Location.getInstance().getLocation().getLatitude();
        double myLongitude = Location.getInstance().getLocation().getLongitude();

        double phi1 = Math.toRadians(location.getLatitude());
        double phi2 = Math.toRadians(Location.getInstance().getLocation().getLatitude());

        double delta_lat = Math.toRadians(myLatitude - location.getLatitude());
        double delta_lon = Math.toRadians(myLongitude - location.getLongitude());

        double a = Math.abs(Math.sin(delta_lat/2) * Math.sin(delta_lon/2)
                + Math.cos(phi1) * Math.cos(phi2) *
                Math.sin(delta_lon/2) * Math.sin(delta_lat/2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        double distanceDouble = globeRadius * c;
        distance = (int) Math.round(distanceDouble);

        Log.d(TagHandler.MAIN_TAG, " myLat: " + myLatitude + " myLong: " + myLongitude + " phi1: " + phi1 + " phi2: " + phi2 + " delta_lat: " + delta_lat + " delta_long: " + delta_lon + " a: " + a + " c: " + c+ " distance: " + distance);
    }

    public int getDistance()
    {
        return distance;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }




    public double getReach() {
        return reach;
    }

    public void setReach(int reach) {
        this.reach = reach;
    }




    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
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

    private String getStringDatePosted() {
        return date_posted;
    }

    public void setDatePosted(String date_posted) {
        this.date_posted = date_posted;
    }






    public boolean isIsDeleted() {
        return is_deleted;
    }

    public void setIsDeleted(boolean is_deleted) {
        this.is_deleted = is_deleted;
    }





    public int getNumberOfComments() {
        return number_of_comments;
    }

    public void setNumberOfComments(int number_of_comments) {
        this.number_of_comments = number_of_comments;
    }



    public int getNumberOfLikes() {
        return number_of_likes;
    }

    public void setNumberOfLikes(int number_of_likes) {
        this.number_of_likes = number_of_likes;
    }



    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }





    public static class Content implements Serializable {
        private String type;
        private String text;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

}
