package com.chalmers.tda367.localfeud.data.handler;

import com.chalmers.tda367.localfeud.data.Chat;
import com.chalmers.tda367.localfeud.data.ChatMessage;
import com.chalmers.tda367.localfeud.data.handler.core.DataResponseListener;
import com.chalmers.tda367.localfeud.data.handler.core.IChatMessageDataHandler;

import java.util.HashMap;
import java.util.List;

public class ChatMessageDataHandler extends AbstractDataHandler implements IChatMessageDataHandler {

    private static ChatMessageDataHandler instance = null;

    public synchronized static ChatMessageDataHandler getInstance() {
        if( instance == null) {
            instance = new ChatMessageDataHandler();
        }
        return instance;
    }

    private ChatMessageDataHandler() {}


    /**
     * {@inheritDoc}
     */
    @Override
    public void getList(Chat chat, DataResponseListener<List<ChatMessage>> listener) {

        getClient().get("/chats/"+chat.getId()+"/messages/", new RestResponseAction(listener));
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void send(Chat chat, ChatMessage message, DataResponseListener<ChatMessage> listener) {
        HashMap<String, String> param = new HashMap<>();
        param.put("message", message.getText() );

        getClient().post("/chats/"+chat.getId()+"/messages/", param, new RestResponseAction(listener));
    }
}
