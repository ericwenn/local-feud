package com.chalmers.tda367.localfeud.data.handler.core;

import com.chalmers.tda367.localfeud.data.Chat;
import com.chalmers.tda367.localfeud.data.Post;

import java.util.List;

/**
 *  Handles all necessary functions for chats.
 */
public interface IChatDataHandler {
    /**
     * Requests a chat with another User
     * @param post The post where the Chat is initiated from
     * @param userID The ID of user whom to start chat with
     * @param listener Listening to when the request is finished
     */
    void sendRequest(Post post, int userID, DataResponseListener<Chat> listener);

    /**
     * Get logged in users chats
     * @param listener Listening to when the request is finished
     */
    void getList(DataResponseListener<List<Chat>> listener);

    void getSingle(int chatid, DataResponseListener<Chat> listener);

    void addChangeListener( DataChangeListener<Chat> listener );

    void triggerChange( Chat oldValue, Chat newValue );
}
