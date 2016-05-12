package com.chalmers.tda367.localfeud.data.handler.interfaces;

import com.chalmers.tda367.localfeud.data.Me;

/**
 * Created by Alfred on 2016-05-12.
 */
public interface IMeDataHandler {
    void get(DataResponseListener<Me> listener);
}
