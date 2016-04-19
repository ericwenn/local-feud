package com.chalmers.tda367.localfeud.net;

import android.util.Log;

import com.chalmers.tda367.localfeud.data.Chat;
import com.chalmers.tda367.localfeud.data.Position;
import com.chalmers.tda367.localfeud.data.Post;
import com.chalmers.tda367.localfeud.data.User;
import com.chalmers.tda367.localfeud.control.PostAdapter;
import com.chalmers.tda367.localfeud.util.GsonHandler;
import com.chalmers.tda367.localfeud.util.RestClient;
import com.chalmers.tda367.localfeud.util.TagHandler;
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

    public static void updatePostFeed(final PostAdapter adapter) {
        RestClient restClient = new RestClient();
        HashMap<String, String> tempMap = new HashMap<>(); // TEMP CODE
        tempMap.put("test", "test"); // TEMP CODE
        try {
            restClient.get("posts/", tempMap, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    ArrayList<Post> posts = GsonHandler.getInstance().toPostList(new String(responseBody));
                    adapter.addPostListToAdapter(posts);
                    Log.d(TagHandler.MAIN_TAG, "onSuccess in serverComm. Posts: " + posts.size());
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.e(TagHandler.MAIN_TAG, error.getMessage());
                }
            });
        } catch (HttpResponseException e) {
            Log.e(TagHandler.MAIN_TAG, e.getMessage());
        }
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
