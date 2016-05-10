package com.chalmers.tda367.localfeud.util;

import com.chalmers.tda367.localfeud.data.ChatMessage;
import com.chalmers.tda367.localfeud.data.Comment;
import com.chalmers.tda367.localfeud.data.Post;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Text om klassen
 *
 * @author David Söderberg
 * @since 16-04-12
 */
public class GsonHandler {
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
        return gson.fromJson(json, new TypeToken<List<Post>>(){}.getType());
    }
    public ArrayList<Comment> toCommentList(String json) {
        return gson.fromJson(json, new TypeToken<List<Comment>>(){}.getType());
    }
    public ArrayList<ChatMessage> toChatMessagesList(String json) {
        return gson.fromJson(json, new TypeToken<List<ChatMessage>>(){}.getType());
    }
}
