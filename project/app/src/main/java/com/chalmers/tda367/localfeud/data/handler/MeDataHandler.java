package com.chalmers.tda367.localfeud.data.handler;

import com.chalmers.tda367.localfeud.data.Me;
import com.chalmers.tda367.localfeud.data.handler.core.DataResponseListener;
import com.chalmers.tda367.localfeud.data.handler.core.IMeDataHandler;

public class MeDataHandler extends AbstractDataHandler implements IMeDataHandler {
    private static MeDataHandler instance = null;

    private Me me = null;

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



    @Override
    public void setMe(Me me) {
        this.me = me;
    }

    @Override
    public Me getMe() throws NullPointerException {
        if( me == null) {
            throw new NullPointerException();
        } else {
            return me;
        }
    }
}
