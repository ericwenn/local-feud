package com.chalmers.tda367.localfeud.auth;

import com.loopj.android.http.AsyncHttpClient;

/**
 * Created by ericwenn on 4/22/16.
 */
interface IAuthenticatedUser {


    /**
     * Add headers to the request, to authenticate against server
     * @param client
     */
    void signRequest( AsyncHttpClient client );

}
