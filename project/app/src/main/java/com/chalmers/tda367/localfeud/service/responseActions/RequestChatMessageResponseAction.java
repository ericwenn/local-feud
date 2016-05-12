package com.chalmers.tda367.localfeud.service.responseActions;

import android.util.Log;

import com.chalmers.tda367.localfeud.data.ChatMessage;
import com.chalmers.tda367.localfeud.util.GsonHandler;
import com.chalmers.tda367.localfeud.util.TagHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Ahlqvist on 2016-05-10.
 */
public class RequestChatMessageResponseAction extends ResponseAction {
    private List<ChatMessage> messages;
    private String responseBody;

    @Override
    public void onSuccess(String responseBody) {
        // Convert string with JSON to a list with messages
        ArrayList<ChatMessage> messages = GsonHandler.getInstance().toChatMessagesList(responseBody);
        // Store the list
        this.setMessages(messages);
        //Notify the listeners
        this.notifySuccess();

        Log.d(TagHandler.MAIN_TAG, "onSuccess in serverComm. Chat messages: " + messages.size());
    }

    private void setMessages(List<ChatMessage> messages) {
        this.messages = messages;
    }

    public List<ChatMessage> getMessages() {
        if (messages != null) {
            return messages;
        } else {
            throw new NullPointerException();
        }
    }
}
