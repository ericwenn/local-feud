package com.chalmers.tda367.localfeud.control.authentication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.chalmers.tda367.localfeud.control.MainActivity;
import com.chalmers.tda367.localfeud.data.AuthenticatedUser;
import com.chalmers.tda367.localfeud.data.Me;
import com.chalmers.tda367.localfeud.data.handler.DataHandlerFacade;
import com.chalmers.tda367.localfeud.data.handler.DataResponseError;
import com.chalmers.tda367.localfeud.data.handler.interfaces.AbstractDataResponseListener;
import com.chalmers.tda367.localfeud.util.TagHandler;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.FacebookSdk;
import com.github.paolorotolo.appintro.AppIntro;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Created by ericwenn on 5/5/16.
 */
public class AuthenticationFlowActivity extends AppIntro {
    private static final String TAG = "AuthFlow";

    @Override
    public void init(@Nullable Bundle savedInstanceState) {

        FacebookSdk.sdkInitialize(getApplicationContext());

        final AccessTokenTracker tokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken != null) {
                    // TODO Make sure all permissions are accepted
                    Log.i(TAG, "onCurrentAccessTokenChanged: " + currentAccessToken);


                    DataHandlerFacade.getMeDataHandler().get(new AbstractDataResponseListener<Me>() {
                        @Override
                        public void onSuccess(Me data) {
                            AuthenticatedUser.getInstance().setMe(data);
                        }

                        @Override
                        public void onFailure(DataResponseError error, String errormessage) {
                            Log.e(TagHandler.MAIN_TAG, "Couldn't fetch myself: " + errormessage);

                        }
                    });
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    try {
                        Activity activity = getActivity();
                        if (activity != null && activity.getClass() == MainActivity.class) {
                            activity.finish();
                            Intent i = getBaseContext().getPackageManager()
                                    .getLaunchIntentForPackage(getBaseContext().getPackageName());
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            finish();
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "onCurrentAccessTokenChanged: " + e);
                    }
                    Log.i(TAG, "onCurrentAccessTokenChanged: Not logged in");
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
