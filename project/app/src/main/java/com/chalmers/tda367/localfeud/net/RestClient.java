package com.chalmers.tda367.localfeud.net;

import android.os.Looper;
import android.util.Log;

import com.chalmers.tda367.localfeud.authentication.AuthenticatedUser;
import com.chalmers.tda367.localfeud.util.TagHandler;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.client.HttpResponseException;

/**
 * Created by Alfred on 2016-04-12.
 */
public class RestClient {
    private final String BASE_URL = "http://api-local.ericwenn.se/";
    private AsyncHttpClient asyncClient;
    private AsyncHttpClient syncClient;
    private RestResponseHandler responseHandler;

    public RestClient(IResponseAction action){
        asyncClient = new AsyncHttpClient();

        HashMap<String,String> h = AuthenticatedUser.getInstance().requestHeaders();
        for(Map.Entry<String, String> m : h.entrySet()) {
            asyncClient.addHeader(m.getKey(),m.getValue());
        }
        syncClient = new SyncHttpClient();
        responseHandler = new RestResponseHandler(action);
    }

    public void addHeader(String header, String value){
        getClient().addHeader(header, value);
    }

    public void get(String url){
        getClient().get(getAbsoluteUrl(url), null, responseHandler);
    }

    public void get(String url, Map<String, String> paramsMap){
        RequestParams params = new RequestParams(paramsMap);
        getClient().get(getAbsoluteUrl(url), params, responseHandler);
    }

    public void post(String url, Map<String, String> paramsMap){
        RequestParams params = new RequestParams(paramsMap);
        getClient().post(getAbsoluteUrl(url), params, responseHandler);
    }

    public void delete(String url, Map<String, String> paramsMap){
        RequestParams params = new RequestParams(paramsMap);
        getClient().delete(getAbsoluteUrl(url), params, responseHandler);
    }

    public void put(String url, Map<String, String> paramsMap) throws HttpResponseException{
        RequestParams params = new RequestParams(paramsMap);
        getClient().put(getAbsoluteUrl(url), params, responseHandler);
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
        Log.e(TagHandler.MAIN_TAG, BASE_URL + relativeUrl);
        return BASE_URL + relativeUrl;
    }
}