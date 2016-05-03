package com.chalmers.tda367.localfeud.net.responseActions;

import android.util.Log;

import com.chalmers.tda367.localfeud.data.Comment;
import com.chalmers.tda367.localfeud.net.ResponseError;
import com.chalmers.tda367.localfeud.util.GsonHandler;
import com.chalmers.tda367.localfeud.util.TagHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Ahlqvist on 2016-04-24.
 */
public class RequestCommentsResponseAction extends AbstractResponseAction {
    private List<Comment> comments;
    private String responseBody;

    @Override
    public void onSuccess(String responseBody){
        // Convert string with JSON to a list with comments
        ArrayList<Comment> comments = GsonHandler.getInstance().toCommentList(new String(responseBody));
        // Store the list
        this.setComments(comments);
        //Notify the listeners
        this.notifySuccess();

        Log.d(TagHandler.MAIN_TAG, "onSuccess in serverComm. Comments: " + comments.size());
    }

    @Override
    public void onFailure(ResponseError err, String responseBody) {
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

    private void setComments(List<Comment> comments){
        this.comments = comments;
    }

    public List<Comment> getComments(){
        if(comments != null){
            return comments;
        }
        else{
            throw new NullPointerException();
        }
    }
}
