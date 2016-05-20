package com.chalmers.tda367.localfeud.control.notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.chalmers.tda367.localfeud.R;
import com.chalmers.tda367.localfeud.control.MainActivity;
import com.chalmers.tda367.localfeud.util.MapEntry;
import com.chalmers.tda367.localfeud.util.TagHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Alfred on 2016-05-18.
 */
public class MessageHandler implements IMessageHandler {
    private Map<String, Map<MapEntry<String, Object>, List<IMessageListener>>> listeners;
    private Context context;

    // Message types
    public static final String CHAT_MESSAGE_RECIEVED = "chat_message_recieved";

    // Message data keys
    public static final String CHAT_MESSAGE_SENDER_ID = "";

    private static final MapEntry<String, Object> EMPTY_PAIR = new MapEntry<>(null, null);

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
                Map<String, Object> messageData = new HashMap<>();

                try{
                    messageData.put("from", data.getString("from"));
                    messageData.put("content", data.getString("content"));
                    messageData.put("user_id", data.getInt("user_id"));
                }catch(JSONException e){
                    Log.e(TagHandler.MAIN_TAG, e.getMessage());
                }

                // Send data to the right receiver
                if (hasListenersMap(type, messageData)){
                    notifyListeners(type, messageData);
                }else{
                    sendNotification(messageData.get("from") + ": " + messageData.get("content"));
                }
            break;

            default:

            break;
        }
    }


    public void addMessageListener(String type, IMessageListener listener){
        addMessageListener(type, null, listener);
    }

    public void addMessageListener(String type, @Nullable MapEntry<String, Object> data, IMessageListener listener){

        // If type is not in map. Add a new map to it and add the listener to the list
        if(!listeners.containsKey(type)){
            listeners.put(type, new HashMap<MapEntry<String, Object>, List<IMessageListener>>());
        }

        if (data == null){
            data = EMPTY_PAIR;
        }

        Map<MapEntry<String, Object>, List<IMessageListener>> listenerMappings = listeners.get(type);

        // If the current key/data pair does not exist as a key
        if (!listenerMappings.keySet().contains(data)){
            // Add a list to the key/data pair
            listenerMappings.put(data, new ArrayList<IMessageListener>());
        }
        // Add the listener to this pair of key/data
        listenerMappings.get(data).add(listener);
    }

    /**
     * Remove a message listener.
     * @param type
     * @param listener
     */
    public void removeMessageListener(String type, IMessageListener listener){
        removeMessageListener(type, null, listener);
    }

    public void removeMessageListener(String type, @Nullable MapEntry<String, Object> data, IMessageListener listener){
        // If type is not in map.
        if(!listeners.containsKey(type)){
            throw new Resources.NotFoundException("No listener exists for this type of message");
        }else{
            // Get the listener mappings for this type of message
            Map<MapEntry<String, Object>, List<IMessageListener>> listenerMappings = listeners.get(type);

            // If data is null, set it to represent an empty pair
            if (data == null){
                data = EMPTY_PAIR;
            }

            // If the current key/data pair does not exist as a key
            if (!listenerMappings.keySet().contains(data)){
                if (data.equals(EMPTY_PAIR)){
                    throw new Resources.NotFoundException("There is no listeners for only a type of this message");
                }else{
                    throw new Resources.NotFoundException("There is no listeners for this pair of key/data");
                }
            }else{
                List<IMessageListener> listeners = listenerMappings.get(data);

                // Check if the listener is mapped to this pair
                if (!listeners.contains(listener)){
                    throw new Resources.NotFoundException("The listener was not found");
                }else{
                    // Remove the listener
                    listeners.remove(listener);
                    listenerMappings.put(data, listeners);
                }

                // If the list of listeners is empty, remove the mapping completely
                if (listeners.isEmpty()){
                    listenerMappings.remove(data);
                    this.listeners.put(type, listenerMappings);
                }
            }
        }
    }

    private void notifyListeners(String type, Map<String, Object> data){
        // Get the mappings for this message type
        Map<MapEntry<String, Object>,List<IMessageListener>> listenerMappings = this.listeners.get(type);

        // Get the map entries
        Set<Map.Entry<String, Object>> entries = data.entrySet();

        // Loop through every key in the data mapping
        for (Map.Entry<String, Object> itEntry : entries) {

            MapEntry<String, Object> entry = new MapEntry<>(itEntry);

            if (listenerMappings.containsKey(entry)){
                List<IMessageListener> listeners = new ArrayList<>(listenerMappings.get(entry));

                if (listenerMappings.containsKey(keyListeningPair(entry.getKey()))){
                    // Add also the listeners from the mapEntry that corresponds to only listening for the key
                    listeners.addAll(listenerMappings.get(keyListeningPair(entry.getKey())));
                }
                if (listenerMappings.containsKey(EMPTY_PAIR)){
                    // Add also the listeners to only the type
                    listeners.addAll(listenerMappings.get(EMPTY_PAIR));
                }

                // Notify the listeners mapped to the mapEntry
                for (IMessageListener listener : listeners) {
                    listener.onMessageRecieved(data);
                }
            }
        }
    }

    public boolean hasListenersMap(String type, Map<String, Object> data){
        if (data == null){
            return hasListeners(type, null);
        }else{
            for (Map.Entry<String, Object> entry : data.entrySet()){
                MapEntry<String, Object> newEntry = new MapEntry<String, Object>(entry);
                if (hasListeners(type, newEntry)){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasListeners(String type){
        return hasListeners(type, null);
    }

    public boolean hasListeners(String type, @Nullable MapEntry<String, Object> mapEntry){
        if (!listeners.containsKey(type)){
            return false;
        }else{
            // Get the mappings for this message type
            Map<MapEntry<String, Object>,List<IMessageListener>> listenerMappings = this.listeners.get(type);

            if (mapEntry != null){
                // This is the mapEntry that corresponds to only listening for the key
                MapEntry<String, Object> keyListeningMapEntry = keyListeningPair(mapEntry.getKey());

                if (listenerMappings.containsKey(mapEntry)) {
                    // Return the value of isEmpty on the listener list
                    return !listenerMappings.get(mapEntry).isEmpty();
                }else if (listenerMappings.containsKey(keyListeningMapEntry)){
                    // This is if there are listeners for only the key
                    return !listenerMappings.get(keyListeningMapEntry).isEmpty();
                }else{
                    // Return false if the mapEntry is not assigned to any listener
                    return false;
                }
            }else{
                // If mapEntry == null we want to check if there are any listeners for the whole type
                mapEntry = EMPTY_PAIR;

                if (!listenerMappings.containsKey(mapEntry)){
                    // Return false if the mapEntry is not assigned to any listener
                    return false;
                }else{
                    // Return the value of isEmpty on the listener list
                    return !listenerMappings.get(mapEntry).isEmpty();
                }
            }
        }
    }

    /**
     * Returns a pair that corresponds to only listening for the key
     * @param key
     * @return
     */
    private MapEntry<String, Object> keyListeningPair(String key){
        return new MapEntry<String, Object>(key, null);
    }

    /**
     * Create and show a simple notification containing a text message.
     * @param message A message to display in the notification.
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
