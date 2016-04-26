package com.chalmers.tda367.localfeud.net.responseActions;

import com.chalmers.tda367.localfeud.net.ResponseError;

/**
 * Created by ericwenn on 4/26/16.
 */
public class LikePostResponseAction extends AbstractResponseAction {
    @Override
    public void onSuccess(String responseBody) {
        this.notifySuccess();
    }

    @Override
    public void onFailure(ResponseError err, String responseBody) {
        this.notifyFailure();
    }
}
