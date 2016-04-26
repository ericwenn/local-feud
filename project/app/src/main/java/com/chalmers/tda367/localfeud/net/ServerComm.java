package com.chalmers.tda367.localfeud.net;

import android.util.Log;

import com.chalmers.tda367.localfeud.control.PostClickedAdapter;
import com.chalmers.tda367.localfeud.data.Chat;
import com.chalmers.tda367.localfeud.data.Position;
import com.chalmers.tda367.localfeud.data.Post;
import com.chalmers.tda367.localfeud.data.User;
import com.chalmers.tda367.localfeud.control.PostAdapter;
import com.chalmers.tda367.localfeud.net.responseActions.RequestCommentsResponseAction;
import com.chalmers.tda367.localfeud.util.GsonHandler;
import com.chalmers.tda367.localfeud.data.Position;
import com.chalmers.tda367.localfeud.data.Post;
import com.chalmers.tda367.localfeud.net.responseActions.RequestPostsResponseAction;
import com.chalmers.tda367.localfeud.util.RestClient;
import com.chalmers.tda367.localfeud.util.TagHandler;

import java.util.HashMap;

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

    public void requestPosts(IResponseListener listener){
        //Dummy position
        requestPosts(new Position(53.123123, 11.123123), listener);
    }

    public void requestSinglePost(Post post, IResponseListener listener){

    }

    public void createPost(Post post, IResponseListener listener){

    }

    /**
     * Sends a request to like a post
     * @param post The Post to like
     */
    public void likePost(Post post, IResponseListener listener){

    }

    /**
     * Sends a request to unlike a post
     * @param post The Post to unlike
     */
    public void unlikePost(Post post, IResponseListener listener){

    }

    public void requestLikes(Post post, IResponseListener listener){

    }

    /**
     * Sends a request to comment a post
     * @param post
     * @param comment
     */
    public void commentPost(Post post, String comment, IResponseListener listener)
    {

    }

    public void requestComments(Post post, IResponseListener listener)
    {
        IResponseAction action = new RequestCommentsResponseAction();
        action.addListener(listener);
        RestClient restClient = new RestClient(action);

        // Store parameters
        HashMap<String, String> param = new HashMap<>();
        /*param.put("id", Double.toString(post.getId()));
        Log.d(TagHandler.MAIN_TAG, "The hashmap: " + param.get("id"));*/

        restClient.get("posts/" + post.getId() + "/comments/", param);
    }
}
