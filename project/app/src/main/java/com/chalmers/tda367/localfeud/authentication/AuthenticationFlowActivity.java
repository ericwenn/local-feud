package com.chalmers.tda367.localfeud.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.chalmers.tda367.localfeud.control.MainActivity;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.FacebookSdk;
import com.github.paolorotolo.appintro.AppIntro;

/**
 * Created by ericwenn on 5/5/16.
 */
public class AuthenticationFlowActivity extends AppIntro {
    private static final String TAG = "AuthFlow";

    @Override
    public void init(@Nullable Bundle savedInstanceState) {

        FacebookSdk.sdkInitialize( getApplicationContext() );

        AccessTokenTracker tokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if( currentAccessToken != null) {
                    Log.i(TAG, "onCurrentAccessTokenChanged: " + currentAccessToken);
                    Intent i = new Intent( getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        };
        AuthenticationFlowLoginSlide f = AuthenticationFlowLoginSlide.newInstance();
        addSlide(f);


        showSkipButton(false);
        setProgressButtonEnabled(false);

    }


    @Override
    public void onSkipPressed() {

    }

    @Override
    public void onDonePressed() {
    }

    @Override
    public void onNextPressed() {

    }

    @Override
    public void onSlideChanged() {

    }



}
