package com.chalmers.tda367.localfeud.services;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.chalmers.tda367.localfeud.control.notifications.IMessageHandler;
import com.chalmers.tda367.localfeud.control.notifications.MessageHandler;
import com.chalmers.tda367.localfeud.services.gcm.GCMPreferences;
import com.chalmers.tda367.localfeud.services.gcm.RegistrationIntentService;
import com.chalmers.tda367.localfeud.util.TagHandler;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.HashMap;

/**
 * Created by Alfred on 2016-05-17.
 */
public class NotificationFacade {

    private static NotificationFacade instance;
    private boolean isReceiverRegistered;
    private MessageHandler messageHandler;
    private Context context;

    public NotificationFacade(){
        isReceiverRegistered = false;
    }

    public static NotificationFacade getInstance(){
        if(instance == null){
            instance = new NotificationFacade();
        }

        return instance;
    }

    /**
     * Used to register this application for receiving messages from GCM.
     * @param context The application context
     */
    public void registerForNotifications(Context context){
        this.context = context;

        BroadcastReceiver mRegistrationBroadcastReceiver;

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(GCMPreferences.SENT_TOKEN_TO_SERVER, false);
                if (sentToken) {
                    Log.d(TagHandler.MAIN_TAG, "Token sent");

                    String token = sharedPreferences.getString(GCMPreferences.GCM_TOKEN, "token");

                    sendRegistrationToServer(token);
                } else {
                    Log.d(TagHandler.MAIN_TAG, "Token not sent");
                }
            }
        };

        if(!isReceiverRegistered) {
            LocalBroadcastManager.getInstance(context).registerReceiver(mRegistrationBroadcastReceiver,
                    new IntentFilter(GCMPreferences.REGISTRATION_COMPLETE));
            isReceiverRegistered = true;
        }

        Intent intent = new Intent(context, RegistrationIntentService.class);
        context.startService(intent);
    }

    public IMessageHandler getMessageHandler(){
        if (this.messageHandler == null){
            if (context != null){
                this.messageHandler = new MessageHandler(context);
            }else{
                throw new NullPointerException("Application not registered for notifications");
            }
        }
        return messageHandler;
    }

    /**
     * Persist registration to the edda server.
     * @param token The new token.
     */
    protected void sendRegistrationToServer(String token) {
        HashMap params = new HashMap<String, String>();
        params.put("token", token);

        RestClient.getInstance().post("gcm-register/", params, new IResponseAction() {
            @Override
            public void onSuccess(String responseBody) {
                Log.d(TagHandler.MAIN_TAG, "GCM token succesfully submitted to App server");
            }

            @Override
            public void onFailure(int statusCode, String responseBody) {
                Log.e(TagHandler.MAIN_TAG, "GCM token failed to submit to server: " + responseBody);
            }
        });
    }

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    public static boolean checkPlayServices(Activity activity) {
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
