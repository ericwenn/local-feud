package com.chalmers.tda367.localfeud.services;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by ericwenn on 5/16/16.
 */
public interface IAuthentication {

    void startTracking(Context context, IAuthenticationListener listener );

    HashMap getRequestHeaders();

    boolean isLoggedIn();


    interface IAuthenticationListener {
        void onLogInSuccessful();
        void onLoginFailed( AuthenticationError err);
        void onLogOut();
    }


    enum AuthenticationError {
        DECLINED_PERMISSIONS
    }


}
