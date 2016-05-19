package com.chalmers.tda367.localfeud.data.handler.core;

import com.chalmers.tda367.localfeud.data.Position;
import com.chalmers.tda367.localfeud.data.Post;

import java.util.List;

/**
 * Created by ericwenn on 5/12/16.
 */
public interface IPostDataHandler {


    /**
     * Get a list of Posts around a Position
     * @param pos A position around which the posts will be
     * @param listener Listener for when the fetch is completed
     */
    void getList( Position pos, DataResponseListener<List<Post>> listener );

    /**
     * Get a single Post
     * @param id ID of the Post to get
     * @param listener Listening to when the request is finished
     */
    void getSingle( int id, DataResponseListener<Post> listener );

    /**
     * Store a new Post
     * @param post The post to store
     * @param listener Listener for when the store is completed
     */
    void create( Post post, DataResponseListener<Post> listener );


    /**
     * Delete a Post
     * @param post The post to delete
     * @param listener Listener for when the deletion is completed
     */
    void delete( Post post, DataResponseListener<Void> listener );



    void addChangeListener( DataChangeListener<Post> listener );

    void triggerChange( Post oldValue, Post newValue );
}
