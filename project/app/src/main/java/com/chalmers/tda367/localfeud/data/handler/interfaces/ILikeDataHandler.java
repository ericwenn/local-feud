package com.chalmers.tda367.localfeud.data.handler.interfaces;

import com.chalmers.tda367.localfeud.data.Like;
import com.chalmers.tda367.localfeud.data.Post;

import java.util.List;

/**
 * Created by ericwenn on 5/12/16.
 */
public interface ILikeDataHandler {
    void getList( Post post, DataResponseListener<List<Like>> listener);

    void create( Post post, DataResponseListener<Like> listener);

    void delete( Post post, DataResponseListener<Void> listener);
}
