package com.chalmers.tda367.localfeud.services;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.chalmers.tda367.localfeud.services.gcm.RegistrationIntentService;
import com.chalmers.tda367.localfeud.util.TagHandler;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

/**
 * Created by Alfred on 2016-05-17.
 */
public class NotificationFacade {

    public static void registerForNotifications(Activity activity){
        if (checkPlayServices(activity)) {
            // Start IntentService to register this application with GCM.
            Intent intent;
            activity.startService(new Intent(activity, RegistrationIntentService.class));
            activity.bindService(new Intent(activity, RegistrationIntentService.class), new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    Log.d(TagHandler.MAIN_TAG, "Service connected");
                }

                @Override
                public void onServiceDisconnected(ComponentName componentName) {
                    Log.d(TagHandler.MAIN_TAG, "Service disconnected");
                }
            }, Context.BIND_AUTO_CREATE);
        }
    }

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private static boolean checkPlayServices(Activity activity) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(activity);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(activity, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TagHandler.MAIN_TAG, "This device does not support GCM.");
                activity.finish();
            }
            return false;
        }
        Log.i(TagHandler.MAIN_TAG, "This device does support GCM");
        return true;
    }
}
