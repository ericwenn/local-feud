package com.chalmers.tda367.localfeud.services;

public interface IResponseAction {
    void onSuccess( String responseBody );
    void onFailure( int statusCode, String responseBody );
}
