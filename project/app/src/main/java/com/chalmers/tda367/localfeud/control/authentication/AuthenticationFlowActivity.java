package com.chalmers.tda367.localfeud.control.authentication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.chalmers.tda367.localfeud.control.MainActivity;
import com.chalmers.tda367.localfeud.control.permission.PermissionFlow;
import com.chalmers.tda367.localfeud.data.Me;
import com.chalmers.tda367.localfeud.data.handler.DataHandlerFacade;
import com.chalmers.tda367.localfeud.data.handler.core.AbstractDataResponseListener;
import com.chalmers.tda367.localfeud.data.handler.core.DataResponseError;
import com.chalmers.tda367.localfeud.services.Authentication;
import com.chalmers.tda367.localfeud.services.IAuthentication;
import com.chalmers.tda367.localfeud.services.Location;
import com.chalmers.tda367.localfeud.services.LocationPermissionError;
import com.chalmers.tda367.localfeud.util.TagHandler;
import com.github.paolorotolo.appintro.AppIntro;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Created by ericwenn on 5/5/16.
 */
public class AuthenticationFlowActivity extends AppIntro {

    @Override
    public void init(@Nullable Bundle savedInstanceState) {

        final View v = this.getCurrentFocus();


        IAuthentication authService = Authentication.getInstance();
        Log.d(TagHandler.MAIN_TAG, "New init");
        authService.startTracking(getApplicationContext(), new IAuthentication.IAuthenticationListener() {
            @Override
            public void onLogInSuccessful() {
                Log.d(TagHandler.MAIN_TAG, "Logging in!");
                DataHandlerFacade
                        .getMeDataHandler().get(new AbstractDataResponseListener<Me>() {
                    @Override
                    public void onSuccess(Me data) {
                        DataHandlerFacade.getMeDataHandler().setMe(data);
                    }

                    @Override
                    public void onFailure(DataResponseError error, String errormessage) {
                    }
                });


                try {
                    Location.getInstance().startTracking(getApplicationContext());
                } catch (LocationPermissionError locationPermissionError) {
                    Intent i = new Intent(getApplicationContext(), PermissionFlow.class);
                    startActivity(i);
                    finish();
                    return;
                }
                Log.d(TagHandler.MAIN_TAG, "Sending to Main");
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }

            @Override
            public void onLoginFailed(IAuthentication.AuthenticationError err) {
                if (v != null) {
                    Snackbar.make(v, "All permission must be accepted", Snackbar.LENGTH_LONG);
                }
            }

            @Override
            public void onLogOut() {
                Authentication.getInstance().logOut();
                try {
                    getActivity().finish();
                } catch (Exception e) {}
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
        if (Authentication.getInstance().isLoggedIn()) {
            Log.d(TagHandler.MAIN_TAG, "User is logged in");
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        }

    }

    private static Activity getActivity() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        Class activityThreadClass = Class.forName("android.app.ActivityThread");
        Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);
        Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
        activitiesField.setAccessible(true);
        Map activities = (Map) activitiesField.get(activityThread);
        for (Object activityRecord : activities.values()) {
            Class activityRecordClass = activityRecord.getClass();
            Field pausedField = activityRecordClass.getDeclaredField("paused");
            pausedField.setAccessible(true);
            if (!pausedField.getBoolean(activityRecord)) {
                Field activityField = activityRecordClass.getDeclaredField("activity");
                activityField.setAccessible(true);
                return (Activity) activityField.get(activityRecord);
            }
        }
        return null;
    }
}

