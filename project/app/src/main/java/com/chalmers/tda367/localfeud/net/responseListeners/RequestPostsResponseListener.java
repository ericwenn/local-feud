package com.chalmers.tda367.localfeud.net.responseListeners;

import com.chalmers.tda367.localfeud.control.PostAdapter;
import com.chalmers.tda367.localfeud.data.Post;
import com.chalmers.tda367.localfeud.net.IResponseAction;
import com.chalmers.tda367.localfeud.net.IResponseListener;
import com.chalmers.tda367.localfeud.net.responseActions.RequestPostsResponseAction;

import java.util.Collections;
import java.util.List;

/**
 * Created by Alfred on 2016-04-21.
 */
public class RequestPostsResponseListener implements IResponseListener {

    private PostAdapter adapter;

    public RequestPostsResponseListener(PostAdapter adapter){
        this.adapter = adapter;
    }

    public void onResponseSuccess(IResponseAction source){
        if (source instanceof RequestPostsResponseAction){
            RequestPostsResponseAction responseAction = (RequestPostsResponseAction) source;
            List<Post> postList = responseAction.getPosts();
            Collections.reverse(postList);
            adapter.addPostListToAdapter(postList);
        }
    }

    public void onResponseFailure(IResponseAction source){
        if (source instanceof RequestPostsResponseAction) {
            RequestPostsResponseAction responseAction = (RequestPostsResponseAction) source;
            adapter.showError(responseAction.getResponseError());
        }
    }
}
