package com.chalmers.tda367.localfeud.control.notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.chalmers.tda367.localfeud.R;
import com.chalmers.tda367.localfeud.control.MainActivity;
import com.chalmers.tda367.localfeud.util.TagHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Alfred on 2016-05-18.
 */
public class MessageHandler implements IMessageHandler {
    private Map<String, List<IMessageListener>> listeners;
    private Context context;

    // Message types
    public static final String CHAT_MESSAGE_RECIEVED = "chat_message_recieved";

    public MessageHandler(Context context){
        this.listeners = new HashMap<>();
        this.context = context;
    }

    /**
     * This method takes all messages for the application and forwards it to the right component of the app
     */
    public void handleMessage(String type, JSONObject data){
        switch(type){
            case CHAT_MESSAGE_RECIEVED:

                // Store relevant data
                Bundle messageData = new Bundle();
                try{
                    messageData.putString("from", data.getString("from"));
                    messageData.putString("content", data.getString("content"));
                }catch(JSONException e){
                    Log.e(TagHandler.MAIN_TAG, e.getMessage());
                }

                // Send data to the right receiver
                if (hasListeners(type)){
                    notifyListeners(type, messageData);
                }else{
                    sendNotification(messageData.getString("from") + ": " + messageData.getString("content"));
                }
            break;

            default:

            break;
        }
    }

    /**
     * Add a listener for messages of a certain type.
     * @param type The type of messages to listen for
     * @param listener The listener
     */
    public void addMessageListener(String type, IMessageListener listener){
        // If type already is in map just add listener to list
        if(listeners.containsKey(type)){
            listeners.get(type).add(listener);
        }

        // If type is not in map. Add a new list to it and add the listener to the list
        else{
            listeners.put(type, new ArrayList<IMessageListener>());
            listeners.get(type).add(listener);
        }
    }

    /**
     * Remove a message listener.
     * @param type
     * @param listener
     */
    public void removeMessageListener(String type, IMessageListener listener){
        if(listeners.containsKey(type)){
            listeners.get(type).remove(listener);

            // Remove list if empty
            if(listeners.get(type).isEmpty()){
                listeners.remove(type);
            }
        }
    }

    private void notifyListeners(String type, Bundle data){
        List<IMessageListener> listeners = this.listeners.get(type);
        for (IMessageListener listener : listeners) {
            listener.onMessageRecieved(data);
        }
    }

    public boolean hasListeners(String type){
        return listeners.containsKey(type);
    }

    /**
     * Create and show a simple notification containing the received message.
     * @param message GCM message received.
     */
    private void sendNotification(String message) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
        notificationBuilder.setSmallIcon(R.drawable.edda_icon);
        notificationBuilder.setContentTitle(context.getString(R.string.app_name));
        notificationBuilder.setContentText(message);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSound(defaultSoundUri);
        notificationBuilder.setContentIntent(pendingIntent);
        notificationBuilder.setPriority(NotificationCompat.PRIORITY_MAX);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
