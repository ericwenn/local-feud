package com.chalmers.tda367.localfeud.util.responseActions;

/**
 * Created by Alfred on 2016-04-18.
 */
public interface IResponseAction {
    void onSuccess(String responseBody);
    void onFailure(String responseBody);
}
