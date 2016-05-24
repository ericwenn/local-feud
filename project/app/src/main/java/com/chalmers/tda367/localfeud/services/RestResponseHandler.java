package com.chalmers.tda367.localfeud.services;

import android.util.Log;

import com.chalmers.tda367.localfeud.util.TagHandler;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.ResponseHandlerInterface;

import cz.msebera.android.httpclient.Header;

/**
 * RestResponseHandler class that decides what to do with the response.
 */
public class RestResponseHandler extends AsyncHttpResponseHandler implements ResponseHandlerInterface {
    private final IResponseAction action;

    public RestResponseHandler(IResponseAction action){
        super();
        this.action = action;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        this.action.onSuccess(new String(responseBody));
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        Log.e(TagHandler.MAIN_TAG, "Failure. Status code:" + statusCode);

        String responseString;

        if(responseBody == null || responseBody.length == 0){
            responseString = "";
        }else{
            responseString = new String(responseBody);
            JsonParser parser = new JsonParser();
            JsonObject obj = parser.parse(responseString).getAsJsonObject();
            responseString = obj.get("message").getAsString();
        }

        this.action.onFailure(statusCode, responseString);
    }
}
