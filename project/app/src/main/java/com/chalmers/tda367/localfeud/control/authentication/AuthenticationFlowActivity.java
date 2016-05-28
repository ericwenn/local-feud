package com.chalmers.tda367.localfeud.control.authentication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.chalmers.tda367.localfeud.control.MainActivity;
import com.chalmers.tda367.localfeud.control.permission.PermissionFlow;
import com.chalmers.tda367.localfeud.data.Me;
import com.chalmers.tda367.localfeud.data.handler.DataHandlerFacade;
import com.chalmers.tda367.localfeud.data.handler.core.AbstractDataResponseListener;
import com.chalmers.tda367.localfeud.data.handler.core.DataResponseError;
import com.chalmers.tda367.localfeud.services.Authentication;
import com.chalmers.tda367.localfeud.services.IAuthentication;
import com.chalmers.tda367.localfeud.services.LocationHandler;
import com.chalmers.tda367.localfeud.services.LocationPermissionError;
import com.github.paolorotolo.appintro.AppIntro;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 *  Activity for authenticate user with Facebook.
 */
public class AuthenticationFlowActivity extends AppIntro {

    @Override
    public void init(@Nullable Bundle savedInstanceState) {

        final View v = this.getCurrentFocus();


//        Using a tracker that checks if user is logged in or not
        IAuthentication authService = Authentication.getInstance();
        authService.startTracking(getApplicationContext(), new IAuthentication.IAuthenticationListener() {
            @Override
            public void onLogInSuccessful() {
                DataHandlerFacade
                        .getMeDataHandler().get(new AbstractDataResponseListener<Me>() {
                    @Override
                    public void onSuccess(Me data) {
//                        Storing users data as Me
                        DataHandlerFacade.getMeDataHandler().setMe(data);
                    }

                    @Override
                    public void onFailure(DataResponseError error, String errormessage) {
                    }
                });


                try {
                    LocationHandler.getInstance().startTracking(getApplicationContext());
                } catch (LocationPermissionError locationPermissionError) {
                    Intent i = new Intent(getApplicationContext(), PermissionFlow.class);
                    startActivity(i);
                    finish();
                    return;
                }

//                Sending user to MainActivity
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
//                Logging out user
                Authentication.getInstance().logOut();

//                Finishing current activity and sending user to AuthenticationFlowActivity
                try {
                    getActivity().finish();
                } catch (Exception ignored) {}
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
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        }

    }

    /**
     *  Used for getting current activity
     *  @return current activity
     *  @throws ClassNotFoundException if no class with "android.app.ActivityThread" can be found
     *  @throws NoSuchMethodException if activityThreadClass doesn't have method "currentActivityThread"
     *  @throws InvocationTargetException when trying to invoke "currentActivityThread"
     *  @throws IllegalAccessException for all getters on fields/activities
     *  @throws NoSuchFieldException when trying to .getDeclaredField
     */
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

