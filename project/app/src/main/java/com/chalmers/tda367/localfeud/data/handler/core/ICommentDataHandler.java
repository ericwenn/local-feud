package com.chalmers.tda367.localfeud.data.handler.core;

import com.chalmers.tda367.localfeud.data.Comment;
import com.chalmers.tda367.localfeud.data.Post;

import java.util.List;

public interface ICommentDataHandler {

    /**
     * Get a list of Comments on a post
     * @param post The post to get Comments from
     * @param listener Listening to when the request is finished
     */
    void getList(Post post, DataResponseListener<List<Comment>> listener );


    /**
     * Get a single Comment
     * @param id ID of the Comment to get
     * @param listener Listening to when the request is finished
     */
    void getSingle( int id, DataResponseListener<Comment> listener);


    /**
     * Delete a Comment
     * @param comment The Comment to delete
     * @param listener Listening to when the request is finished
     */
    void delete( Comment comment, DataResponseListener<Void> listener);

    /**
     * Create a new Comment on Post
     * @param post The post to comment
     * @param comment The Comment
     * @param listener Listening to when the request is finished
     */
    void create( Post post, Comment comment, DataResponseListener<Comment> listener);

}
