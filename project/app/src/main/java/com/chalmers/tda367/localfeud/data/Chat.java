package com.chalmers.tda367.localfeud.data;

import android.content.res.Resources;
import android.util.Log;

import com.chalmers.tda367.localfeud.data.handler.MeDataHandler;
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

    private int id;
    private Status status;
    private String date_started;
    private int number_of_unread_messages;
    private String href;
    private String last_message;
    private String last_activity;

    private List<KnownUser> users;

    public Chat() {
    }

    private Chat(String date_started, String href, int id,
                 String last_activity, String last_message,
                 int number_of_unread_messages, Status status, List<KnownUser> users) {
        this.date_started = date_started;
        this.href = href;
        this.id = id;
        this.last_activity = last_activity;
        this.last_message = last_message;
        this.number_of_unread_messages = number_of_unread_messages;
        this.status = status;
        this.users = users;
    }

    public String getLastMessage() {
        if (last_message != null) return last_message;
        else return "";
    }

    public void setLastMessage(String lastMessageSent) {
        this.last_message = lastMessageSent;
    }

    private enum Status {
        accepted, pending
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

    private String getStringDateStarted() {
        return date_started;
    }

    public void setDateStarted(String dateStarted) {
        this.date_started = dateStarted;
    }

    public int getNumberOfUnreadMessages() {
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

    public KnownUser getFirstCounterPart(int myUserId){
        for (KnownUser user : getUsers()){
            if (user.getId() != MeDataHandler.getInstance().getMe().getId()){
                return user;
            }
        }
        throw new Resources.NotFoundException("First counterpart of chat not found.");
    }

    public void setUsers(List<KnownUser> users) {
        this.users = users;
    }

    public String getChatName() {
        if (users.isEmpty()) {
            return "UNKNOWN";
        }
        else if (users.size() == 1) {
            return users.get(0).getFirstname() + ", " + users.get(0).getAge();
        }
        else {
            String chatName = "";
            for (KnownUser user : users) {
                chatName += (user.getFirstname() + ", ");
            }
            return chatName.substring(0, chatName.length() - 2);
        }
    }

    public String getLastActivity() {
        if (last_message != null) return last_activity;
        else return date_started;
    }

    public Calendar getDate(String dateString) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ENGLISH);
        try {
            calendar.setTime(simpleDateFormat.parse(dateString));
        } catch (ParseException e) {
            Log.e(TagHandler.MAIN_TAG, "Can't parse " + dateString + " to SimpleDateFormat");
        }
        return calendar;
    }

    public String getStringFromDate(Calendar date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ENGLISH);
        return simpleDateFormat.format(date.getTime());
    }

    public void setLastActivity(String lastActivity) {
        this.last_activity = lastActivity;
    }

    @Override
    public boolean equals(Object o) {
        return o.getClass() == Chat.class && ((Chat) o).getId() == getId();
    }

    @Override
    public String toString() {
        return "Chat id: " + getId() + ", " + getStatus() + ".\nUsers: " + getChatName() + ", href: " + getHref();
    }

    @Override
    public Chat clone() {
        return new Chat(date_started, href, id, last_activity,
                last_message, number_of_unread_messages,
                status, users);
    }
}
