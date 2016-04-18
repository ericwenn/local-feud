package com.chalmers.tda367.localfeud.net;

import android.util.Log;

import com.chalmers.tda367.localfeud.data.Chat;
import com.chalmers.tda367.localfeud.data.Position;
import com.chalmers.tda367.localfeud.data.Post;
import com.chalmers.tda367.localfeud.data.User;
import com.chalmers.tda367.localfeud.ui.PostAdapter;
import com.chalmers.tda367.localfeud.util.GsonHandler;
import com.chalmers.tda367.localfeud.util.RestClient;
import com.chalmers.tda367.localfeud.util.TagHandler;
import com.chalmers.tda367.localfeud.util.responseActions.IResponseAction;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.HttpResponseException;

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

    public void updatePostFeed(final PostAdapter adapter) {

        class UpdatePostFeedResponseAction implements IResponseAction{
            public void onSuccess(String responseBody){
                ArrayList<Post> posts = GsonHandler.getInstance().toPostList(new String(responseBody));
                adapter.addPostListToAdapter(posts);
                Log.d(TagHandler.MAIN_TAG, "onSuccess in serverComm. Posts: " + posts.size());
            }

            public void onFailure(String responseBody){
                // TODO: se till att något görs här
            }
        }

        RestClient restClient = new RestClient(new UpdatePostFeedResponseAction());
        HashMap<String, String> param = new HashMap<>();
        param.put("test", "test");

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
