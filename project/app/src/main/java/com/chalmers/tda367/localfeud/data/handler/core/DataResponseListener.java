package com.chalmers.tda367.localfeud.data.handler.core;

import java.lang.reflect.Type;

/**
 *  Listener that checks if the receiving of data
 *  was successful or not.
 */
public interface DataResponseListener<D> {
    void onSuccess( D data );
    void onFailure(DataResponseError error, String errormessage );

    Type getType();
}
