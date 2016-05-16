package com.chalmers.tda367.localfeud.services;

import android.os.Looper;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alfred on 2016-04-12.
 */
public class RestClient {
    private final String BASE_URL = "http://api-local.ericwenn.se/";
    private AsyncHttpClient asyncClient;
    private AsyncHttpClient syncClient;
    private RestResponseHandler responseHandler;

    private static RestClient instance = null;

    public synchronized static RestClient getInstance() {
        if( instance == null) {
            instance = new RestClient();
        }

        return instance;
    }






    public RestClient(){
        asyncClient = new AsyncHttpClient();
        syncClient = new SyncHttpClient();

        HashMap<String,String> h = Authentication.getInstance().getRequestHeaders();
        for(Map.Entry<String, String> m : h.entrySet()) {
            asyncClient.addHeader(m.getKey(),m.getValue());
        }

    }

    public void addHeader(String header, String value){
        getClient().addHeader(header, value);
    }








    public void get(String url, IResponseAction action ) {
        getClient().get(getAbsoluteUrl(url), null, new RestResponseHandler(action));
    }






    public void get(String url, Map<String, String> paramsMap, IResponseAction action){
        RequestParams params = new RequestParams(paramsMap);
        getClient().get(getAbsoluteUrl(url), params, new RestResponseHandler(action));
    }



    public void post(String url, Map<String, String> paramsMap, IResponseAction action){
        RequestParams params = new RequestParams(paramsMap);
        getClient().post(getAbsoluteUrl(url), params, new RestResponseHandler(action));
    }

    public void post(String url, IResponseAction action) {
        getClient().post(getAbsoluteUrl(url), null, new RestResponseHandler(action));
    }

    public void delete(String url, Map<String, String> paramsMap, IResponseAction action){
        RequestParams params = new RequestParams(paramsMap);
        getClient().delete(getAbsoluteUrl(url), params, new RestResponseHandler(action));
    }

    public void delete(String url, IResponseAction action){
        getClient().delete(getAbsoluteUrl(url), null, new RestResponseHandler(action));
    }

    public void put(String url, Map<String, String> paramsMap, IResponseAction action){
        RequestParams params = new RequestParams(paramsMap);
        getClient().put(getAbsoluteUrl(url), params, new RestResponseHandler(action));
    }

    /**
     * @return an async client when calling from the main thread, otherwise a sync client.
     */
    private AsyncHttpClient getClient()
    {
        // Return the synchronous HTTP client when the thread is not prepared
        if (Looper.myLooper() == null)
            return syncClient;
        return asyncClient;
    }

    private String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}