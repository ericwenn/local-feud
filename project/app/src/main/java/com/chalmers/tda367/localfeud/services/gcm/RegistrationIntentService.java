package com.chalmers.tda367.localfeud.services.gcm;

/**
 * Created by Alfred on 2016-05-16.
 */
import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.chalmers.tda367.localfeud.R;
import com.chalmers.tda367.localfeud.util.TagHandler;
import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

public class RegistrationIntentService extends IntentService {

    private static final String TAG = "RegIntentService";
    private static final String[] TOPICS = {"global"};

    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TagHandler.MAIN_TAG, "HANDLE INTENT");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        try {
            // Retrieve device token from GCM
            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

            // Store token for the method that sends the token to third party server
            sharedPreferences.edit().putString(GCMPreferences.GCM_TOKEN, token).apply();
            Log.d(TagHandler.MAIN_TAG, "GCM Registration Token: " + token);


            sharedPreferences.edit().putBoolean(GCMPreferences.SENT_TOKEN_TO_SERVER, true).apply();

        } catch (Exception e) {
            Log.e(TagHandler.MAIN_TAG, "Failed to complete token refresh", e);

            sharedPreferences.edit().putBoolean(GCMPreferences.SENT_TOKEN_TO_SERVER, false).apply();
        }
        Intent registrationComplete = new Intent(GCMPreferences.REGISTRATION_COMPLETE);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }



    /**
     * Subscribe to any GCM topics of interest, as defined by the TOPICS constant.
     *
     * @param token GCM token
     * @throws IOException if unable to reach the GCM PubSub service
     */
    private void subscribeTopics(String token) throws IOException {
        GcmPubSub pubSub = GcmPubSub.getInstance(this);
        for (String topic : TOPICS) {
            pubSub.subscribe(token, "/topics/" + topic, null);
        }
    }

}
