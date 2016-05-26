package com.chalmers.tda367.localfeud.data;

import android.util.Log;

import com.chalmers.tda367.localfeud.services.LocationHandler;
import com.chalmers.tda367.localfeud.util.TagHandler;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Alfred on 2016-04-11.
 */
public class Post extends GeneralPost implements Serializable, Cloneable {

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

    public Post() {

    }

    private Post(Content content, boolean current_user_has_liked, String date_posted, int distance,
                 String href, int id, boolean is_deleted, Position location, int number_of_comments,
                 int number_of_likes, double reach, User user) {
        this.content = content;
        this.current_user_has_liked = current_user_has_liked;
        this.date_posted = date_posted;
        this.distance = distance;
        this.href = href;
        this.id = id;
        this.is_deleted = is_deleted;
        this.location = location;
        this.number_of_comments = number_of_comments;
        this.number_of_likes = number_of_likes;
        this.reach = reach;
        this.user = user;
    }

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
        return location;
    }

    public void setLocation(Position location) {
        this.location = location;
    }


    public void setDistance()
    {
        double myLatitude = LocationHandler.getInstance().getLocation().getLatitude();
        double myLongitude = LocationHandler.getInstance().getLocation().getLongitude();

        float[] dist = new float[1];
        android.location.Location.distanceBetween(myLatitude, myLongitude, location.getLatitude(), location.getLongitude(), dist);
        distance = Math.round(dist[0]);
    }

    public int getDistance()
    {
        setDistance();
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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ENGLISH);
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

    public Post clone() {
        return new Post(getContent(), current_user_has_liked, getStringDatePosted(), getDistance(),
                getHref(), getId(), isIsDeleted(), getLocation(), getNumberOfComments(), getNumberOfLikes(),
                getReach(), getUser());
    }

    @Override
    public boolean equals(Object o) {
        return o.getClass() == Post.class && ((Post) o).getId() == getId();
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
