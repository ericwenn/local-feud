package com.chalmers.tda367.localfeud.data.handler.core;

import com.chalmers.tda367.localfeud.data.Me;

/**
 * Created by Alfred on 2016-05-12.
 */
public interface IMeDataHandler {
    /**
     * Get information about currently logged in user
     * @param listener Listening to when the request is finished
     */
    void get(DataResponseListener<Me> listener);



    void setMe( Me me );

    Me getMe() throws NullPointerException;
}
