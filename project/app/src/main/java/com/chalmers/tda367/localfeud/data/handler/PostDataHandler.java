package com.chalmers.tda367.localfeud.data.handler;

import com.chalmers.tda367.localfeud.data.Position;
import com.chalmers.tda367.localfeud.data.Post;
import com.chalmers.tda367.localfeud.service.RestClient;
import com.chalmers.tda367.localfeud.service.responseActions.IResponseAction;
import com.chalmers.tda367.localfeud.service.responseActions.RequestPostsResponseAction;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ericwenn on 5/12/16.
 */
public class PostDataHandler extends AbstractDataHandler {
    public static void getList(Position pos, DataResponseListener<List<Post>> listener) {

        // Init restClient with a responseAction and its listener
        IResponseAction action = new RequestPostsResponseAction();
        action.addListener(listener);
        RestClient restClient = new RestClient(action);

        // Store parameters
        HashMap<String, String> param = new HashMap<>();
        param.put("latitude", Double.toString(pos.getLatitude()));
        param.put("longitude", Double.toString(pos.getLongitude()));

        restClient.get("posts/", param);




    }
}
