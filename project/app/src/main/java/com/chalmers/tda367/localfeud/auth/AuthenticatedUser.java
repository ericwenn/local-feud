package com.chalmers.tda367.localfeud.auth;

import android.app.Activity;
import android.app.Application;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.loopj.android.http.AsyncHttpClient;

import java.util.Arrays;

/**
 * Created by ericwenn on 4/22/16.
 */
public class AuthenticatedUser extends Application implements IAuthenticatedUser  {

    private CallbackManager callbackManager;

    private static AuthenticatedUser instance = null;
    public static IAuthenticatedUser getInstance() {
        if( instance == null ) {
            instance = new AuthenticatedUser();
        }
        return instance;
    }


    private AuthenticatedUser() {

        FacebookSdk.sdkInitialize(this.getApplicationContext());

        callbackManager = CallbackManager.Factory.create();

        this.registerCallback();
        this.login();

    }


    private void registerCallback() {
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                System.out.println(loginResult);
                System.out.println(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }


    private void login() {
        LoginManager.getInstance().logInWithReadPermissions((Activity) this.getApplicationContext(), Arrays.asList("public_profile"));
    }




    @Override
    public void signRequest(AsyncHttpClient client) {
        // TODO
    }
}
