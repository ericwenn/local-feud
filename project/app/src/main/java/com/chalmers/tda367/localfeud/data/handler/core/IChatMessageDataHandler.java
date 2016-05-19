package com.chalmers.tda367.localfeud.data.handler.core;

import com.chalmers.tda367.localfeud.data.Chat;
import com.chalmers.tda367.localfeud.data.ChatMessage;

import java.util.List;

/**
 * Created by Alfred on 2016-05-12.
 */
public interface IChatMessageDataHandler {

    /**
     * Get a list of ChatMessages in Chat
     * @param chat The chat to get ChatMessages from
     * @param listener Listening to when the request is finished
     */
    void getList(Chat chat, DataResponseListener<List<ChatMessage>> listener);


    /**
     * Send ChatMessage to Chat
     * @param chat The chat to send to
     * @param message The message to send
     * @param listener Listening to when the request is finished
     */
    void send(Chat chat, ChatMessage message, DataResponseListener<ChatMessage> listener );
}
