package com.chalmers.tda367.localfeud.service.responseListeners;

import com.chalmers.tda367.localfeud.control.post.PostClickedAdapter;
import com.chalmers.tda367.localfeud.service.responseActions.IResponseAction;
import com.chalmers.tda367.localfeud.service.responseActions.RequestCommentsResponseAction;

/**
 * Created by Daniel Ahlqvist on 2016-04-26.
 */
public class RequestCommentsResponseListener implements IResponseListener {

    private final PostClickedAdapter adapter;

    public RequestCommentsResponseListener(PostClickedAdapter adapter)
    {
        this.adapter = adapter;
    }

    public void onResponseSuccess(IResponseAction source){
        if (source instanceof RequestCommentsResponseAction){
            RequestCommentsResponseAction responseAction = (RequestCommentsResponseAction) source;
            adapter.addCommentListToAdapter(responseAction.getComments());
        }
    }

    public void onResponseFailure(IResponseAction source){

    }
}
