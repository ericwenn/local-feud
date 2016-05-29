package com.chalmers.tda367.localfeud.services;

import android.content.Context;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.FacebookSdk;

import java.util.HashMap;

public class Authentication implements IAuthentication{

    private static Authentication instance = null;
    private AccessTokenTracker tokenTracker = null;
    private IAuthenticationListener listener = null;
    private AccessToken token = null;


    public synchronized static Authentication getInstance() {
        if( instance == null ) {
            instance = new Authentication();
        }
        return instance;

    }

    private Authentication() {
    }


    @Override
    public void startTracking(Context ctx, final IAuthenticationListener l) {
        FacebookSdk.sdkInitialize(ctx);
        if( tokenTracker == null ) {
            listener = l;
            tokenTracker = new AccessTokenTracker() {
                @Override
                protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                    if( currentAccessToken != null) {
                        // Log in was successful, but all permissions might not be accepted/granted.

                        if( currentAccessToken.getDeclinedPermissions().size() > 0 ) {
                            listener.onLoginFailed( AuthenticationError.DECLINED_PERMISSIONS);
                        } else {
                            token = currentAccessToken;
                            listener.onLogInSuccessful();
                        }


                    } else {
                        listener.onLogOut();
                    }
                }
            };
        }
    }

    @Override
    public HashMap getRequestHeaders() {
        if( token == null) {
            return null;
        }

        HashMap<String, String> map = new HashMap<>();

        map.put( "user-id", token.getUserId() );
        map.put( "token", token.getToken() );
        return map;
    }

    public void logOut() {
        token = null;
    }
    @Override
    public boolean isLoggedIn() {
        return token != null;
    }
}
