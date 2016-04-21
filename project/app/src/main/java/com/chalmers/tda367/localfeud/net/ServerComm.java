package com.chalmers.tda367.localfeud.net;

import android.util.Log;

import com.chalmers.tda367.localfeud.data.Chat;
import com.chalmers.tda367.localfeud.data.Position;
import com.chalmers.tda367.localfeud.data.Post;
import com.chalmers.tda367.localfeud.data.User;
import com.chalmers.tda367.localfeud.net.responseActions.RequestPostsResponseAction;
import com.chalmers.tda367.localfeud.util.RestClient;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Alfred on 2016-04-12.
 */
public class ServerComm implements IServerComm {

    private static ServerComm instance;

    public static ServerComm getInstance(){
        if (instance == null){
            instance = new ServerComm();
        }
        return instance;
    }

    public void requestPosts(Position pos, IResponseListener listener) {
        // Init restClient with a responseAction and its listener
        IResponseAction action = new RequestPostsResponseAction();
        action.addListener(listener);
        RestClient restClient = new RestClient(action);

        // Store parameters
        HashMap<String, String> param = new HashMap<>();
        param.put("latitude", Double.toString(pos.getLatitude()));
        param.put("longitude", Double.toString(pos.getLongitude()));

        restClient.get("posts/", param);
    }

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
