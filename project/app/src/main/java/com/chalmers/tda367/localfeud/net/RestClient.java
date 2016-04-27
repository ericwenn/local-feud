package com.chalmers.tda367.localfeud.net;

import android.util.Log;

import com.chalmers.tda367.localfeud.util.TagHandler;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import java.util.Map;

import cz.msebera.android.httpclient.client.HttpResponseException;

/**
 * Created by Alfred on 2016-04-12.
 */
public class RestClient {
    private final String BASE_URL = "http://api-local.ericwenn.se/";
    private AsyncHttpClient client;
    private RestResponseHandler responseHandler;

    public RestClient(IResponseAction action){

        client = new AsyncHttpClient();
        responseHandler = new RestResponseHandler(action);
    }

    public void addHeader(String header, String value){
        client.addHeader(header, value);
    }

    public void get(String url){
        client.get(getAbsoluteUrl(url), null, responseHandler);
    }

    public void get(String url, Map<String, String> paramsMap){
        RequestParams params = new RequestParams(paramsMap);
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public void post(String url, Map<String, String> paramsMap){
        RequestParams params = new RequestParams(paramsMap);
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public void delete(String url, Map<String, String> paramsMap){
        RequestParams params = new RequestParams(paramsMap);
        client.delete(getAbsoluteUrl(url), params, responseHandler);
    }

    public void put(String url, Map<String, String> paramsMap) throws HttpResponseException{
        RequestParams params = new RequestParams(paramsMap);
        client.put(getAbsoluteUrl(url), params, responseHandler);
    }

    private String getAbsoluteUrl(String relativeUrl) {
        Log.e(TagHandler.MAIN_TAG, BASE_URL + relativeUrl);
        return BASE_URL + relativeUrl;
    }
}