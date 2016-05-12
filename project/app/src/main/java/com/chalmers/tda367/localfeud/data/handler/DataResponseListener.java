package com.chalmers.tda367.localfeud.data.handler;

/**
 * Created by ericwenn on 5/12/16.
 */
interface DataResponseListener<D> {
    void onSuccess( D data );
    void onFailure( DataResponseError error, String errormessage );
}
