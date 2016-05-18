package com.chalmers.tda367.localfeud.services.gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.JsonReader;
import android.util.Log;

import com.chalmers.tda367.localfeud.R;
import com.chalmers.tda367.localfeud.control.MainActivity;
import com.chalmers.tda367.localfeud.util.TagHandler;
import com.google.android.gms.gcm.GcmListenerService;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by Alfred on 2016-05-16.
 */
public class EddaGcmListenerService extends GcmListenerService {

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {
        Log.d(TagHandler.MAIN_TAG, "MESSAGE RECIEVED");

        String type = data.getString("title");
        String message = data.getString("message");
        JSONObject json;
        String sender;
        String content;

        try{
            json = new JSONObject(message);
            sender = json.getString("from");
            content = json.getString("content");
            Log.d(TagHandler.MAIN_TAG, "Type: " + type);
            Log.d(TagHandler.MAIN_TAG, "From: " + sender);
            Log.d(TagHandler.MAIN_TAG, "Message: " + content);

            sendNotification(sender + ": " + content);
        }catch (JSONException e){
            Log.e(TagHandler.MAIN_TAG, e.getMessage());
        }

        if (from.startsWith("/topics/")) {
            // message received from some topic.
        } else {
            // normal downstream message.
        }

        // [START_EXCLUDE]
        /**
         * Production applications would usually process the message here.
         * Eg: - Syncing with server.
         *     - Store message in local database.
         *     - Update UI.
         */

        /**
         * In some cases it may be useful to show a notification indicating to the user
         * that a message was received.
         */
        // [END_EXCLUDE]
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param message GCM message received.
     */
    private void sendNotification(String message) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setSmallIcon(R.drawable.edda_icon);
        notificationBuilder.setContentTitle(getString(R.string.app_name));
        notificationBuilder.setContentText(message);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSound(defaultSoundUri);
        notificationBuilder.setContentIntent(pendingIntent);
        notificationBuilder.setPriority(NotificationCompat.PRIORITY_MAX);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
