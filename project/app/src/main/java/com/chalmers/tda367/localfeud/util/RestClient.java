package com.chalmers.tda367.localfeud.util;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.Map;

import cz.msebera.android.httpclient.client.HttpResponseException;

/**
 * Created by Alfred on 2016-04-12.
 */
public class RestClient {
    private final String BASE_URL = "http://api-local.ericwenn.se/";

    private AsyncHttpClient client = new AsyncHttpClient();

    public void get(String url, Map<String, String> paramsMap, AsyncHttpResponseHandler responseHandler) throws HttpResponseException {
        RequestParams params = new RequestParams(paramsMap);
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public void post(String url, Map<String, String> paramsMap) throws HttpResponseException {
        RestResponseHandler responseHandler = new RestResponseHandler();
        RequestParams params = new RequestParams(paramsMap);

        client.post(getAbsoluteUrl(url), params, responseHandler);
        if (responseHandler.getStatusCode() == 200){
            // Everything is fine
        }else{
            throw new HttpResponseException(responseHandler.getStatusCode(), responseHandler.getResponse());
        }
    }

    public void delete(String url, Map<String, String> paramsMap) throws HttpResponseException{
        RestResponseHandler responseHandler = new RestResponseHandler();
        RequestParams params = new RequestParams(paramsMap);

        client.delete(getAbsoluteUrl(url), params, responseHandler);
        if (responseHandler.getStatusCode() == 200){
            // Everything is fine
        }else{
            throw new HttpResponseException(responseHandler.getStatusCode(), responseHandler.getResponse());
        }
    }

    public void put(String url, Map<String, String> paramsMap) throws HttpResponseException{
        RestResponseHandler responseHandler = new RestResponseHandler();
        RequestParams params = new RequestParams(paramsMap);

        client.put(getAbsoluteUrl(url), params, responseHandler);
        if (responseHandler.getStatusCode() == 200){
            // Everything is fine
        }else{
            throw new HttpResponseException(responseHandler.getStatusCode(), responseHandler.getResponse());
        }
    }

    private String getAbsoluteUrl(String relativeUrl) {
        Log.e(TagHandler.MAIN_TAG, BASE_URL + relativeUrl);
        return BASE_URL + relativeUrl;
    }
}