package com.chalmers.tda367.localfeud.control.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.chalmers.tda367.localfeud.control.MainActivity;
import com.chalmers.tda367.localfeud.data.Me;
import com.chalmers.tda367.localfeud.data.handler.DataHandlerFacade;
import com.chalmers.tda367.localfeud.data.handler.core.DataResponseError;
import com.chalmers.tda367.localfeud.data.handler.core.AbstractDataResponseListener;
import com.chalmers.tda367.localfeud.services.Authentication;
import com.chalmers.tda367.localfeud.services.IAuthentication;
import com.chalmers.tda367.localfeud.services.Location;
import com.github.paolorotolo.appintro.AppIntro;

/**
 * Created by ericwenn on 5/5/16.
 */
public class AuthenticationFlowActivity extends AppIntro {
    private static final String TAG = "AuthFlow";

    @Override
    public void init(@Nullable Bundle savedInstanceState) {

        final View v = this.getCurrentFocus();

        Location.getInstance().startTracking(getApplicationContext());

        IAuthentication authService = Authentication.getInstance( );
        authService.startTracking(getApplicationContext(), new IAuthentication.IAuthenticationListener() {
            @Override
            public void onLogInSuccessful() {

                DataHandlerFacade
                        .getMeDataHandler().get(new AbstractDataResponseListener<Me>() {
                    @Override
                    public void onSuccess(Me data) {
                        DataHandlerFacade.getMeDataHandler().setMe( data );
                    }

                    @Override
                    public void onFailure(DataResponseError error, String errormessage) {
                    }
                });


                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }

            @Override
            public void onLoginFailed(IAuthentication.AuthenticationError err) {
                if (v != null) {
                    Snackbar.make( v, "All permission must be accepted", Snackbar.LENGTH_LONG);
                }
            }

            @Override
            public void onLogOut() {
                Intent i = new Intent(getApplicationContext(), AuthenticationFlowActivity.class);
                startActivity(i);
                finish();
            }
        });

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


    @Override
    protected void onResume() {
        super.onResume();
        if(Authentication.getInstance().isLoggedIn()) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        }

    }
}
