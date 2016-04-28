package com.chalmers.tda367.localfeud.net.responseActions;

import com.chalmers.tda367.localfeud.net.ResponseError;

/**
 * Created by Daniel Ahlqvist on 2016-04-28
 */
public class CommentPostResponseAction extends AbstractResponseAction {
    @Override
    public void onSuccess(String responseBody)
    {
        this.notifySuccess();
    }

    @Override
    public void onFailure(ResponseError err, String responseBody)
    {
        this.setResponseError(err);
        this.notifyFailure();
    }
}
