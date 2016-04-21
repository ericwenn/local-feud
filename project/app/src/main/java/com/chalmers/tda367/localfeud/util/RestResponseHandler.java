package com.chalmers.tda367.localfeud.util;

import android.util.Log;

import com.chalmers.tda367.localfeud.net.IResponseAction;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.ResponseHandlerInterface;

import cz.msebera.android.httpclient.Header;

/**
 * RestResponseHandler class that decides what to do with the response.
 */
public class RestResponseHandler extends AsyncHttpResponseHandler implements ResponseHandlerInterface {
    private IResponseAction action;

    public RestResponseHandler(IResponseAction action){
        super();
        this.action = action;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        Log.e(TagHandler.MAIN_TAG, "Success");
        Log.e(TagHandler.MAIN_TAG, "Status code:" + Integer.toString(statusCode));
        this.action.onSuccess(new String(responseBody));
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        Log.e(TagHandler.MAIN_TAG, "Failure");
        Log.e(TagHandler.MAIN_TAG, "Status code:" + Integer.toString(statusCode));
        this.action.onFailure(new String(responseBody));
    }
}
