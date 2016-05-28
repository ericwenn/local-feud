package com.chalmers.tda367.localfeud.services;

import android.os.Looper;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import java.util.HashMap;
import java.util.Map;

public class RestClient implements IRestClient {
    private final String BASE_URL = "http://api-local.ericwenn.se/";
    private final AsyncHttpClient asyncClient;
    private final AsyncHttpClient syncClient;
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



    }

    public void addHeader(String header, String value){
        getClient().addHeader(header, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void get(String url, IResponseAction action ) {
        getClient().get(getAbsoluteUrl(url), null, new RestResponseHandler(action));
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void get(String url, Map<String, String> paramsMap, IResponseAction action){
        RequestParams params = new RequestParams(paramsMap);
        getClient().get(getAbsoluteUrl(url), params, new RestResponseHandler(action));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void post(String url, Map<String, String> paramsMap, IResponseAction action){
        RequestParams params = new RequestParams(paramsMap);
        getClient().post(getAbsoluteUrl(url), params, new RestResponseHandler(action));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void post(String url, IResponseAction action) {
        getClient().post(getAbsoluteUrl(url), null, new RestResponseHandler(action));
    }




    /**
     * {@inheritDoc}
     */
    @Override
    public void put(String url, IResponseAction action) {
        getClient().post(getAbsoluteUrl(url), null, new RestResponseHandler(action));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(String url, Map<String, String> paramsMap, IResponseAction action){
        RequestParams params = new RequestParams(paramsMap);
        getClient().delete(getAbsoluteUrl(url), params, new RestResponseHandler(action));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(String url, IResponseAction action){
        getClient().delete(getAbsoluteUrl(url), null, new RestResponseHandler(action));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void put(String url, Map<String, String> paramsMap, IResponseAction action){
        RequestParams params = new RequestParams(paramsMap);
        getClient().put(getAbsoluteUrl(url), params, new RestResponseHandler(action));
    }

    /**
     * @return an async client when calling from the main thread, otherwise a sync client.
     */
    private AsyncHttpClient getClient()
    {
        HashMap<String,String> h = Authentication.getInstance().getRequestHeaders();

        // Return the synchronous HTTP client when the thread is not prepared
        if (Looper.myLooper() == null){
            for(Map.Entry<String, String> m : h.entrySet()) {
                syncClient.addHeader(m.getKey(),m.getValue());
            }
            return syncClient;
        }

        for(Map.Entry<String, String> m : h.entrySet()) {
            asyncClient.addHeader(m.getKey(),m.getValue());
        }

        return asyncClient;
    }

    private String getAbsoluteUrl(String relativeUrl) {
        String BASE_URL = "http://api-local.ericwenn.se/";
        return BASE_URL + relativeUrl;
    }
}