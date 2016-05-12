package com.chalmers.tda367.localfeud.data.handler.interfaces;

import com.chalmers.tda367.localfeud.data.Position;
import com.chalmers.tda367.localfeud.data.Post;

import java.util.List;

/**
 * Created by ericwenn on 5/12/16.
 */
public interface IPostDataHandler {

    void getList( Position pos, DataResponseListener<List<Post>> listener );

    void getSingle( int id, DataResponseListener<Post> listener );

    void create( Post post, DataResponseListener<Post> listener );

    void delete( Post post, DataResponseListener<Void> listener );


}
