package com.chalmers.tda367.localfeud.data.handler.core;

import com.chalmers.tda367.localfeud.data.Like;
import com.chalmers.tda367.localfeud.data.Post;

import java.util.List;

/**
 * Created by ericwenn on 5/12/16.
 */
public interface ILikeDataHandler {

    /**
     * Get a list of Likes on a Post
     * @param post The post to get likes on
     * @param listener Listening to when the request is finished
     */
    void getList( Post post, DataResponseListener<List<Like>> listener);

    /**
     * Create a new Like on Post
     * @param post The Post to like
     * @param listener Listening to when the request is finished
     */
    void create( Post post, DataResponseListener<Like> listener);

    /**
     * Delete a Like on Post
     * @param post The post to unlike
     * @param listener Listening to when the request is finished
     */
    void delete( Post post, DataResponseListener<Void> listener);
}
