package com.chalmers.tda367.localfeud.util;

import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * RestResponseHandler class that decides what to do with the response.
 */
public class RestResponseHandler extends AsyncHttpResponseHandler {
    private String response;
    private int statusCode;

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        Log.e(TagHandler.MAIN_TAG, "Success");
        Log.e(TagHandler.MAIN_TAG, "Status code:" + Integer.toString(statusCode));
        this.setResponse(new String(responseBody));
        this.setStatusCode(statusCode);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        Log.e(TagHandler.MAIN_TAG, "Failure");
        this.setResponse(error.getMessage());
        this.setStatusCode(statusCode);
    }

    public String getResponse() {
        if(response != null){
            return response;
        }
        else{
            return "No response";
        }
    }

    public int getStatusCode(){
        return statusCode;
    }

    private void setResponse(String response){
        this.response = response;
    }

    private void setStatusCode(int statusCode){
        this.statusCode = statusCode;
    }
}
