package com.chalmers.tda367.localfeud.data.handler;

import com.chalmers.tda367.localfeud.data.Like;
import com.chalmers.tda367.localfeud.data.Post;
import com.chalmers.tda367.localfeud.data.handler.interfaces.DataResponseListener;
import com.chalmers.tda367.localfeud.data.handler.interfaces.ILikeDataHandler;

import java.util.List;

/**
 * Created by ericwenn on 5/13/16.
 */
public class LikeDataHandler extends AbstractDataHandler implements ILikeDataHandler {

    private static LikeDataHandler instance = null;

    public synchronized static LikeDataHandler getInstance() {
        if( instance == null) {
            instance = new LikeDataHandler();
        }
        return instance;
    }

    private LikeDataHandler() {}



    @Override
    public void getList(Post post, DataResponseListener<List<Like>> listener) {
        // TODO
    }

    @Override
    public void create(Post post, DataResponseListener<Like> listener) {

        int postID = post.getId();

        getClient().post("posts/"+postID+"/likes/", new RestResponseAction(listener));
    }

    @Override
    public void delete(Post post, DataResponseListener<Void> listener) {
        int postID = post.getId();

        getClient().delete("posts/"+postID+"/likes/", new RestResponseAction(listener));
    }
}
