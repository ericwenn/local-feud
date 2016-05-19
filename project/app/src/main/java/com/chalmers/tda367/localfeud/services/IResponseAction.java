package com.chalmers.tda367.localfeud.services;

/**
 * Created by ericwenn on 5/12/16.
 */
public interface IResponseAction {
    void onSuccess( String responseBody );
    void onFailure( int statusCode, String responseBody );
}
