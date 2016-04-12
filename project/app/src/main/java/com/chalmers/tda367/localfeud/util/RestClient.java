package com.chalmers.tda367.localfeud.util;

import android.util.Log;

import com.loopj.android.http.*;
import java.util.Map;
import cz.msebera.android.httpclient.Header;

/**
 * Created by Alfred on 2016-04-12.
 */
public class RestClient {
    private static final String BASE_URL = "http://api-local.ericwenn.se/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static String get(String url, Map<String, String> paramsMap) {
        RestResponseHandler responseHandler = new RestResponseHandler();
        RequestParams params = new RequestParams(paramsMap);
        
        client.get(getAbsoluteUrl(url), params, responseHandler);
        return responseHandler.getResponse();
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        Log.e(TagHandler.MAIN_TAG, BASE_URL + relativeUrl);
        return BASE_URL + relativeUrl;
    }

    /**
     * RestResponseHandler class that decides what to do with the response.
     */
    public static class RestResponseHandler extends AsyncHttpResponseHandler {
        private String response;

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            Log.e(TagHandler.MAIN_TAG, "Success");
            Log.e(TagHandler.MAIN_TAG, "Status code:" + Integer.toString(statusCode));
            if (statusCode == 200){
                response = new String(responseBody);
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Log.e(TagHandler.MAIN_TAG, "Failure");
        }

        public String getResponse() {
            if(response != null){
                return response;
            }
            else{
                return "No response";
            }
        }
    }
}
