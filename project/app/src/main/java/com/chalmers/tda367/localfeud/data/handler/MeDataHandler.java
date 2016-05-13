package com.chalmers.tda367.localfeud.data.handler;

import com.chalmers.tda367.localfeud.data.Me;
import com.chalmers.tda367.localfeud.data.handler.interfaces.DataResponseListener;
import com.chalmers.tda367.localfeud.data.handler.interfaces.IMeDataHandler;

/**
 * Created by ericwenn on 5/13/16.
 */
public class MeDataHandler extends AbstractDataHandler implements IMeDataHandler {
    private static MeDataHandler instance = null;

    public synchronized static MeDataHandler getInstance() {
        if( instance == null) {
            instance = new MeDataHandler();
        }
        return instance;
    }

    private MeDataHandler() {}

    /**
     * {@inheritDoc}
     */
    @Override
    public void get(DataResponseListener<Me> listener) {
        getClient().get("me/", new RestResponseAction(listener));
    }
}
