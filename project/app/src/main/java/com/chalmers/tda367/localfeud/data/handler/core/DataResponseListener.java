package com.chalmers.tda367.localfeud.data.handler.core;

import java.lang.reflect.Type;

/**
 * Created by ericwenn on 5/12/16.
 */
public interface DataResponseListener<D> {
    void onSuccess( D data );
    void onFailure(DataResponseError error, String errormessage );

    Type getType();
}
