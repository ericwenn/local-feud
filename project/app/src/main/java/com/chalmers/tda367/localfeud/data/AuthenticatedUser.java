package com.chalmers.tda367.localfeud.data;

import com.facebook.AccessToken;

import java.util.HashMap;

/**
 * Created by ericwenn on 4/22/16.
 */
public class AuthenticatedUser implements IAuthenticatedUser  {

    private Me me;
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
    public HashMap<String,String> requestHeaders() {
        HashMap map = new HashMap<String, String>();

        AccessToken at = AccessToken.getCurrentAccessToken();

        map.put( "user-id", at.getUserId() );
        map.put( "token", at.getToken() );
        // TODO
        return map;
    }

    public void setMe(Me me){
        this.me = me;
    }

    public Me getMe(){
        return this.me;
    }
}
