package com.chalmers.tda367.localfeud.net;

import com.chalmers.tda367.localfeud.data.Chat;
import com.chalmers.tda367.localfeud.data.Position;
import com.chalmers.tda367.localfeud.data.Post;
import com.chalmers.tda367.localfeud.data.User;

import java.util.List;

/**
 * Created by Alfred on 2016-04-12.
 */
public class ServerComm implements IServerComm {

    public List<Post> getPosts(Position pos, int radius, String order) {
        return null;
    }

    public Post getPost(int id) {
        return null;
    }

    public void likePost(Post post) {

    }

    public void unlikePost(Post post) {

    }

    public void commentPost(Post post, String comment) {

    }

    public List<Chat> getChats(String status) {
        return null;
    }

    public Chat createChat(User user) {
        return null;
    }
}
