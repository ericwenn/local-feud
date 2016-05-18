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
import com.chalmers.tda367.localfeud.services.NotificationFacade;
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

        try{
            JSONObject json = new JSONObject(message);
            NotificationFacade.getInstance().getMessageHandler().handleMessage(type, json);
        }catch (JSONException e){
            Log.e(TagHandler.MAIN_TAG, e.getMessage());
        }

        if (from.startsWith("/topics/")) {
            // message received from some topic.
        } else {
            // normal downstream message.
        }
    }
    // [END receive_message]
}
