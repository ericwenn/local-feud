package com.chalmers.tda367.localfeud.data.handler;

import com.chalmers.tda367.localfeud.data.Position;
import com.chalmers.tda367.localfeud.data.Post;
import com.chalmers.tda367.localfeud.data.handler.interfaces.DataResponseListener;
import com.chalmers.tda367.localfeud.data.handler.interfaces.IPostDataHandler;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ericwenn on 5/12/16.
 */
public class PostDataHandler extends AbstractDataHandler implements IPostDataHandler {
    private static PostDataHandler instance = null;

    public synchronized static PostDataHandler getInstance() {
        if( instance == null) {
            instance = new PostDataHandler();
        }
        return instance;
    }

    private PostDataHandler() {

    }


    public void getList(Position pos, DataResponseListener<List<Post>> listener) {

        // Store parameters
        HashMap<String, String> param = new HashMap<>();
        param.put("latitude", Double.toString(pos.getLatitude()));
        param.put("longitude", Double.toString(pos.getLongitude()));


        getClient().get( "/posts/", param, new RestResponseAction(listener));


    }

    @Override
    public void getSingle(int id, DataResponseListener<Post> listener) {

    }

    @Override
    public void create(Post post, DataResponseListener<Post> listener) {

    }

    @Override
    public void delete(Post post, DataResponseListener<Void> listener) {

    }
}
