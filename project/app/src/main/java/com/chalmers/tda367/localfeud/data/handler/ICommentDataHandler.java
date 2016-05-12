package com.chalmers.tda367.localfeud.data.handler;

import com.chalmers.tda367.localfeud.data.Comment;
import com.chalmers.tda367.localfeud.data.Post;

import java.util.List;

/**
 * Created by ericwenn on 5/12/16.
 */
public interface ICommentDataHandler {

    void getList(Post post, DataResponseListener<List<Comment>> listener );

    void getSingle( int id, DataResponseListener<Comment> listener);

    void delete( Comment comment, DataResponseListener<Void> listener);

    void create( Post post, Comment comment, DataResponseListener<Comment> listener);

}
