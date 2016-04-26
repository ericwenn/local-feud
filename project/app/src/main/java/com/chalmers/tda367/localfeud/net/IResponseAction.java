package com.chalmers.tda367.localfeud.net;

/**
 * Created by Alfred on 2016-04-18.
 */
public interface IResponseAction {
    void onSuccess(String responseBody);
    void onFailure(ResponseError error, String responseBody);
    void addListener(IResponseListener listener);
    void removeListener(IResponseListener listener);
}
