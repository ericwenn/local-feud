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
public class RequestSinglePostResponseAction extends AbstractResponseAction {
    private Post post;
    private String responseBody;

    @Override
    public void onSuccess(String responseBody){
        // Convert string with JSON to a list with posts
        Post post = GsonHandler.getInstance().toPost(new String(responseBody));
        // Store the list
        this.setPost(post);
        //Notify the listeners
        this.notifySuccess();

    }

    @Override
    public void onFailure(ResponseError error, String responseBody){
        this.setResponseBody(responseBody);
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

    private void setPost(Post post){
        this.post = post;
    }

    public Post getPost(){
        if(post != null){
            return post;
        }
        else{
            throw new NullPointerException();
        }
    }
}
