package com.chalmers.tda367.localfeud.data.handler;

import com.chalmers.tda367.localfeud.data.Position;
import com.chalmers.tda367.localfeud.data.Post;
import com.chalmers.tda367.localfeud.data.handler.interfaces.DataResponseListener;
import com.chalmers.tda367.localfeud.data.handler.interfaces.IPostDataHandler;
import com.chalmers.tda367.localfeud.services.RestClient;
import com.chalmers.tda367.localfeud.service.responseActions.IResponseAction;
import com.chalmers.tda367.localfeud.service.responseActions.RequestPostsResponseAction;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ericwenn on 5/12/16.
 */
public class PostDataHandler extends AbstractDataHandler implements IPostDataHandler {

    public void getList(Position pos, DataResponseListener<List<Post>> listener) {




        /**
         * Göra RestClient till Singleton och den ska fortfrande var i services paketet.
         */




        // Store parameters
        HashMap<String, String> param = new HashMap<>();
        param.put("latitude", Double.toString(pos.getLatitude()));
        param.put("longitude", Double.toString(pos.getLongitude()));


        getClient().get( "asdasdasd", new RestResponseAction(listener));






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
