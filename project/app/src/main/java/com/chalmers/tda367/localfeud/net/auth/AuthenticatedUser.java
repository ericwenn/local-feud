package com.chalmers.tda367.localfeud.net.auth;

import com.facebook.AccessToken;

import java.util.HashMap;

/**
 * Created by ericwenn on 4/22/16.
 */
public class AuthenticatedUser implements IAuthenticatedUser  {

    private static AuthenticatedUser instance = null;
    public static AuthenticatedUser getInstance() {
        if( instance == null ) {
            instance = new AuthenticatedUser();
        }
        return instance;
    }


    private AuthenticatedUser() {

    }

    @Override
    public boolean isLoggedIn() {
        AccessToken at = AccessToken.getCurrentAccessToken();
        return at != null;
    }


    @Override
    public HashMap requestHeaders() {
        HashMap map = new HashMap<String, String>();

        AccessToken at = AccessToken.getCurrentAccessToken();

        map.put( "user_id", at.getUserId() );
        map.put( "token", at.getToken() );
        // TODO
        return map;
    }
}
