package com.chalmers.tda367.localfeud.service.responseListeners;

import com.chalmers.tda367.localfeud.service.responseActions.IResponseAction;

/**
 * Created by Alfred on 2016-04-20.
 */
public interface IResponseListener {
    void onResponseSuccess(IResponseAction source);
    void onResponseFailure(IResponseAction source);
}
