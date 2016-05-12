package com.chalmers.tda367.localfeud.util;

import com.chalmers.tda367.localfeud.data.ChatMessage;
import android.util.Log;

import com.chalmers.tda367.localfeud.data.Chat;
import com.chalmers.tda367.localfeud.data.Comment;
import com.chalmers.tda367.localfeud.data.Me;
import com.chalmers.tda367.localfeud.data.Post;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Text om klassen
 *
 * @author David Söderberg
 * @since 16-04-12
 */
public class GsonHandler
{
    private Gson gson;
    private static GsonHandler instance = null;

    private GsonHandler() {
        gson = new Gson();
    }

    public synchronized static GsonHandler getInstance() {
        if (instance == null) {
            instance = new GsonHandler();
        }
        return instance;
    }

    public Post toPost(String json) {
        return gson.fromJson(json, Post.class);
    }

    public ArrayList<Post> toPostList(String json) {
        return gson.fromJson(json, new TypeToken<List<Post>>() {
        }.getType());
    }

    public ArrayList<Comment> toCommentList(String json) {
        return gson.fromJson(json, new TypeToken<List<Comment>>() {
        }.getType());
    }

    public ArrayList<ChatMessage> toChatMessagesList(String json) {
        return gson.fromJson(json, new TypeToken<List<ChatMessage>>() {
        }.getType());
    }
    public ArrayList<Chat> toChatList(String json)
    {
        Log.d(TagHandler.MAIN_TAG, "JSON RESPONSE ON CHAT: \n\n" + json + "\n\n\n");
        ArrayList<Chat> testList = gson.fromJson(json, new TypeToken<List<Chat>>() {
        }.getType());
        return gson.fromJson(json, new TypeToken<List<Chat>>() {
        }.getType());
    }

    public Me toMe(String json){
        return gson.fromJson(json, new TypeToken<Me>(){}.getType());
    }

    // Kanske använda E istället för Type type.
    public <E> E fromJson(String json, Type type) {
        return gson.fromJson(json, type);
    }
}
