package com.chalmers.tda367.localfeud.data.handler;

import android.util.Log;

import com.chalmers.tda367.localfeud.data.handler.core.DataChangeListener;
import com.chalmers.tda367.localfeud.data.handler.core.DataResponseError;
import com.chalmers.tda367.localfeud.data.handler.core.DataResponseListener;
import com.chalmers.tda367.localfeud.services.IResponseAction;
import com.chalmers.tda367.localfeud.services.IRestClient;
import com.chalmers.tda367.localfeud.services.RestClient;
import com.chalmers.tda367.localfeud.util.GsonHandler;
import com.chalmers.tda367.localfeud.util.TagHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ericwenn on 5/12/16.
 */
public abstract class AbstractDataHandler {

    protected final List<DataChangeListener> listeners = new ArrayList<>();



    public class RestResponseAction implements IResponseAction {
        private final DataResponseListener listener;
        public RestResponseAction(DataResponseListener listener) {
            this.listener = listener;
        }
        
        public void onSuccess( String responseBody ) {
            listener.onSuccess(GsonHandler.getInstance().fromJson( responseBody, listener.getType()));
        }



        public void onFailure( int statusCode, String responseError ) {
            // översätta statuscode till ett DataResponseError
            // Eventuellt läsa av error message från responsebody

            DataResponseError err;
            switch (statusCode) {
                case 404:
                    err = DataResponseError.NOTFOUND;
                    break;
                case 400:
                    err = DataResponseError.BADREQUEST;
                    break;
                case 500:
                    err = DataResponseError.REALLYBAD;
                    break;
                case 401:
                    err = DataResponseError.UNAUTHORIZED;
                    break;
                default:
                    err = DataResponseError.REALLYBAD;
            }

            listener.onFailure(err, responseError);
        }
    }





    protected IRestClient getClient() {
        return RestClient.getInstance();
    }




}
