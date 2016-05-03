package com.chalmers.tda367.localfeud.net.auth;

import java.util.HashMap;

/**
 * Created by ericwenn on 4/22/16.
 */
interface IAuthenticatedUser {


    boolean isLoggedIn();

    /**
     * Returns headers in the form of a HashMap to authenticate against server.
     * @return
     */
    HashMap requestHeaders();

}
