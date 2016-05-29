package com.chalmers.tda367.localfeud.data.handler;

import com.chalmers.tda367.localfeud.data.Position;
import com.chalmers.tda367.localfeud.data.Post;
import com.chalmers.tda367.localfeud.data.handler.core.DataChangeListener;
import com.chalmers.tda367.localfeud.data.handler.core.DataResponseListener;
import com.chalmers.tda367.localfeud.data.handler.core.IPostDataHandler;

import java.util.HashMap;
import java.util.List;

public class PostDataHandler extends AbstractDataHandler implements IPostDataHandler {
    private static PostDataHandler instance = null;

    public synchronized static PostDataHandler getInstance() {
        if (instance == null) {
            instance = new PostDataHandler();
        }
        return instance;
    }

    private PostDataHandler() {

    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void getList(Position pos, DataResponseListener<List<Post>> listener) {

        // Store parameters
        HashMap<String, String> param = new HashMap<>();
        param.put("lat", Double.toString(pos.getLatitude()));
        param.put("lon", Double.toString(pos.getLongitude()));


        getClient().get("posts/", param, new RestResponseAction(listener));


    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void getSingle(int id, DataResponseListener<Post> listener) {
        // TODO
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void create(Post post, DataResponseListener<Post> listener) {

        HashMap<String, String> params = new HashMap<>();
        params.put("latitude", Double.toString(post.getLocation().getLatitude()));
        params.put("longitude", Double.toString(post.getLocation().getLongitude()));
        params.put("content_type", post.getContent().getType());
        params.put("text", post.getContent().getText());

        getClient().post("/posts/", params, new RestResponseAction(listener));

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Post post, DataResponseListener<Void> listener) {
        // TODO
    }


    @Override
    public void addChangeListener(DataChangeListener<Post> listener) {
        this.listeners.add(listener);
    }


    @Override
    public void triggerChange(Post oldValue, Post newValue) {
        for (DataChangeListener<Post> listener : listeners) {
            listener.onChange(oldValue, newValue);
        }
    }


}
