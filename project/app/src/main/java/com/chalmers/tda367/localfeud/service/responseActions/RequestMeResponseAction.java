package com.chalmers.tda367.localfeud.service.responseActions;

import com.chalmers.tda367.localfeud.data.Me;
import com.chalmers.tda367.localfeud.util.GsonHandler;

/**
 * Created by Alfred on 2016-05-11.
 */
public class RequestMeResponseAction extends ResponseAction {

    private Me me;

    @Override
    public void onSuccess(String responseBody){
        // Convert string with JSON to a list with posts
        Me me = GsonHandler.getInstance().toMe(responseBody);
        // Store the list
        this.setMe(me);
        //Notify the listeners
        this.notifySuccess();
    }

    public void setMe(Me me){
        this.me = me;
    }

    public Me getMe(){
        return this.me;
    }
}
