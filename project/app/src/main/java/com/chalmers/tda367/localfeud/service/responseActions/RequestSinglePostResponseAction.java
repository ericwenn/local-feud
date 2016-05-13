package com.chalmers.tda367.localfeud.service.responseActions;

import com.chalmers.tda367.localfeud.data.Post;
import com.chalmers.tda367.localfeud.util.GsonHandler;

/**
 * Created by Alfred on 2016-04-20.
 */
public class RequestSinglePostResponseAction extends ResponseAction {
    private Post post;

    @Override
    public void onSuccess(String responseBody){
        // Convert string with JSON to a list with posts
        Post post = GsonHandler.getInstance().toPost(responseBody);
        // Store the list
        this.setPost(post);
        //Notify the listeners
        this.notifySuccess();

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
