package com.chalmers.tda367.localfeud.net.responseActions;

import com.chalmers.tda367.localfeud.net.IResponseAction;
import com.chalmers.tda367.localfeud.net.IResponseListener;
import com.chalmers.tda367.localfeud.net.ResponseError;

import java.util.ArrayList;

/**
 * Created by Alfred on 2016-04-20.
 * The subclasses of this class is meant to handle the conversion of the response String to whatever object is required.
 */
public abstract class AbstractResponseAction implements IResponseAction {
    private ArrayList<IResponseListener> listeners;
    private ResponseError error;

    public AbstractResponseAction(){
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

    protected synchronized void setResponseError(ResponseError error){
        this.error = error;
    }

    public synchronized ResponseError getResponseError(){
        return this.error;
    }

    /**
     * A method that is called when the request was successful.
     * @param responseBody The body of the response (to be converted to an object through Gson or whatever)
     */
    public abstract void onSuccess(String responseBody);
    public abstract void onFailure(ResponseError err, String responseBody);
}
