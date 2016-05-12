package com.chalmers.tda367.localfeud.services;

import java.lang.reflect.Type;

/**
 * Created by ericwenn on 5/12/16.
 */
public interface IResponseAction {
    void onSuccess( String responseBody );
    void onFailure( int statusCode, String responseBody );
}
