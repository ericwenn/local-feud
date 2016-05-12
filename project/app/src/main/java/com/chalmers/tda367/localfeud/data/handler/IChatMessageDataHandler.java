package com.chalmers.tda367.localfeud.data.handler;

import com.chalmers.tda367.localfeud.data.Chat;
import com.chalmers.tda367.localfeud.data.ChatMessage;

import java.util.List;

/**
 * Created by Alfred on 2016-05-12.
 */
public interface IChatMessageDataHandler {
    void getList(Chat chat, DataResponseListener<List<ChatMessage>> listener);

    void send(Chat chat, ChatMessage message, DataResponseListener<ChatMessage> listener );
}
