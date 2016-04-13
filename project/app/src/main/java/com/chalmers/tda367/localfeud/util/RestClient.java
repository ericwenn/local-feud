package com.chalmers.tda367.localfeud.util;

import android.util.Log;

import com.loopj.android.http.*;

import java.net.HttpURLConnection;
import java.util.Map;

import cz.msebera.android.httpclient.client.HttpResponseException;

/**
 * Created by Alfred on 2016-04-12.
 */
public class RestClient {
    private final String BASE_URL = "http://api-local.ericwenn.se/";

    private AsyncHttpClient client = new AsyncHttpClient();

    public String get(String url, Map<String, String> paramsMap) throws HttpResponseException {
        RestResponseHandler responseHandler = new RestResponseHandler();
        RequestParams params = new RequestParams(paramsMap);
        
        client.get(getAbsoluteUrl(url), params, responseHandler);
        if (responseHandler.getStatusCode() == 200){
            return responseHandler.getResponse();
        }else{
            throw new HttpResponseException(responseHandler.getStatusCode(), responseHandler.getResponse());
        }

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

    private String getAbsoluteUrl(String relativeUrl) {
        Log.e(TagHandler.MAIN_TAG, BASE_URL + relativeUrl);
        return BASE_URL + relativeUrl;
    }
}
