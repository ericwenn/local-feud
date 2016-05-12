package com.chalmers.tda367.localfeud.data.handler;

import android.util.Log;

import com.chalmers.tda367.localfeud.data.handler.interfaces.DataResponseListener;
import com.chalmers.tda367.localfeud.services.IResponseAction;
import com.chalmers.tda367.localfeud.services.RestClient;
import com.chalmers.tda367.localfeud.util.GsonHandler;

/**
 * Created by ericwenn on 5/12/16.
 */
public abstract class AbstractDataHandler {

    public class RestResponseAction implements IResponseAction {
        private DataResponseListener listener;
        public RestResponseAction(DataResponseListener listener) {
            this.listener = listener;
        }
        
        public void onSuccess( String responseBody ) {
            // Parse GSON

            Log.d("testst", GsonHandler.getInstance().fromJson( responseBody, listener.getType()).toString());
            listener.onSuccess(GsonHandler.getInstance().fromJson( responseBody, listener.getType()));


        }



        public void onFailure( int statusCode, String responseBody ) {
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

            listener.onFailure(err, responseBody);
        }
    }





    protected RestClient getClient() {
        return RestClient.getInstance();
    }



}
