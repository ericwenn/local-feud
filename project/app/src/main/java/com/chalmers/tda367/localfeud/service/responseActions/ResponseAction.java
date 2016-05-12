package com.chalmers.tda367.localfeud.service.responseActions;

import android.util.Log;

import com.chalmers.tda367.localfeud.service.responseListeners.IResponseListener;
import com.chalmers.tda367.localfeud.service.ResponseError;
import com.chalmers.tda367.localfeud.util.TagHandler;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

/**
 * Created by Alfred on 2016-04-20.
 * The subclasses of this class is meant to handle the conversion of the response String to whatever object is required.
 */
public class ResponseAction implements IResponseAction {
    private ArrayList<IResponseListener> listeners;
    private ResponseError error;
    private String responseBody;
    private String errorMessage;

    public ResponseAction(){
        this.listeners = new ArrayList<>();
    }

    public void addListener(IResponseListener listener){
        this.listeners.add(listener);
    }

    public void removeListener(IResponseListener listener){
        this.listeners.remove(listener);
    }

    protected void notifySuccess(){
        for (IResponseListener listener : listeners) {
            listener.onResponseSuccess(this);
        }
    }

    protected void notifyFailure(){
        for (IResponseListener listener : listeners) {
            listener.onResponseFailure(this);
        }
    }

    public synchronized void setResponseError(ResponseError error){
        this.error = error;
    }

    public synchronized ResponseError getResponseError(){
        return this.error;
    }

    protected synchronized void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public synchronized String getErrorMessage() {
        return errorMessage;
    }

    protected synchronized void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public synchronized String getResponseBody() {
        return responseBody;
    }

    /**
     * A method that is called when the request was successful.
     * @param responseBody The body of the response (to be converted to an object through Gson or whatever)
     */
    public void onSuccess(String responseBody){
        setResponseBody(responseBody);
        notifySuccess();
    }
    public void onFailure(ResponseError err, String responseBody){
        setResponseBody(responseBody);
        Log.e(TagHandler.MAIN_TAG, "ResponseBody: " + responseBody);
        JsonParser parser = new JsonParser();
        JsonObject obj = parser.parse(responseBody).getAsJsonObject();
        String errormsg = obj.get("message").getAsString();
        setErrorMessage(errormsg);

        setResponseError(err);
        notifyFailure();
    }
}
