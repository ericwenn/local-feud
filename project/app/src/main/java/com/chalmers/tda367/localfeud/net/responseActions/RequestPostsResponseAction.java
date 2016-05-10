package com.chalmers.tda367.localfeud.net.responseActions;

import android.util.Log;

import com.chalmers.tda367.localfeud.data.Post;
import com.chalmers.tda367.localfeud.net.ResponseError;
import com.chalmers.tda367.localfeud.util.GsonHandler;
import com.chalmers.tda367.localfeud.util.TagHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alfred on 2016-04-20.
 */
public class RequestPostsResponseAction extends AbstractResponseAction {
    private List<Post> posts;
    private String responseBody;

    @Override
    public void onSuccess(String responseBody){
        // Convert string with JSON to a list with posts
        ArrayList<Post> posts = GsonHandler.getInstance().toPostList(responseBody);
        // Store the list
        this.setPosts(posts);
        //Notify the listeners
        this.notifySuccess();

        Log.d(TagHandler.MAIN_TAG, "onSuccess in serverComm. Posts: " + posts.size());
    }

    @Override
    public void onFailure(ResponseError err, String responseBody){
        this.setResponseBody(responseBody);
        this.setResponseError(err);
        this.notifyFailure();
    }

    private void setResponseBody(String responseBody){
        this.responseBody = responseBody;
    }

    public String getResponseBody() {
        if(responseBody != null){
            return responseBody;
        }
        else{
            throw new NullPointerException();
        }
    }

    private void setPosts(List<Post> posts){
        this.posts = posts;
    }

    public List<Post> getPosts(){
        if(posts != null){
            return posts;
        }
        else{
            throw new NullPointerException();
        }
    }
}
