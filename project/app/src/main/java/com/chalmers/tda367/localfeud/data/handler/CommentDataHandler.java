package com.chalmers.tda367.localfeud.data.handler;

import android.util.Log;

import com.chalmers.tda367.localfeud.data.Comment;
import com.chalmers.tda367.localfeud.data.Post;
import com.chalmers.tda367.localfeud.data.handler.interfaces.DataResponseListener;
import com.chalmers.tda367.localfeud.data.handler.interfaces.ICommentDataHandler;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ericwenn on 5/12/16.
 */
public class CommentDataHandler extends AbstractDataHandler implements ICommentDataHandler {

    private static final String TAG = "CommentDataHandler";

    private static CommentDataHandler instance = null;

    public synchronized static CommentDataHandler getInstance() {
        if( instance == null) {
            instance = new CommentDataHandler();
        }
        return instance;
    }

    private CommentDataHandler() {}



    @Override
    public void getList(Post post, DataResponseListener<List<Comment>> listener) {
        getClient().get( "posts/" + post.getId() + "/comments/", new RestResponseAction(listener));
    }

    @Override
    public void getSingle(int id, DataResponseListener<Comment> listener) {
        // TODO
    }

    @Override
    public void delete(Comment comment, DataResponseListener<Void> listener) {
        getClient().delete("comments/" + Integer.toString(comment.getId()) + "/", new RestResponseAction(listener));
    }

    @Override
    public void create(Post post, Comment comment, DataResponseListener<Comment> listener) {

        HashMap params = new HashMap();
        params.put("content", comment.getText());

        getClient().post("posts/" + post.getId() + "/comments/", params, new RestResponseAction(listener));
    }
}
