package com.chalmers.tda367.localfeud.service.responseListeners;

import com.chalmers.tda367.localfeud.control.post.PostAdapter;
import com.chalmers.tda367.localfeud.service.responseActions.IResponseAction;
import com.chalmers.tda367.localfeud.service.ResponseError;
import com.chalmers.tda367.localfeud.service.responseActions.RequestPostsResponseAction;

/**
 * Created by Alfred on 2016-04-21.
 */
public class RequestPostsResponseListener implements IResponseListener {

    private final PostAdapter adapter;

    public RequestPostsResponseListener(PostAdapter adapter){
        this.adapter = adapter;
    }

    public void onResponseSuccess(IResponseAction source){
        if (source instanceof RequestPostsResponseAction){
            RequestPostsResponseAction responseAction = (RequestPostsResponseAction) source;
            try {
                adapter.addPostListToAdapter(responseAction.getPosts());
            } catch (NullPointerException e) {
                ((RequestPostsResponseAction) source).setResponseError(ResponseError.REALLYBAD);
                onResponseFailure(source);
            }
        }
    }

    public void onResponseFailure(IResponseAction source){
        if (source instanceof RequestPostsResponseAction) {
            RequestPostsResponseAction responseAction = (RequestPostsResponseAction) source;
            if (adapter != null) {
                adapter.showError(responseAction.getResponseError());
            }
        }
    }
}
