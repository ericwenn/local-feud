package com.chalmers.tda367.localfeud.data.handler.interfaces;

import com.chalmers.tda367.localfeud.data.Chat;
import com.chalmers.tda367.localfeud.data.Post;

import java.util.List;

/**
 * Created by Alfred on 2016-05-12.
 */
public interface IChatDataHandler {
    void sendRequest(Post post, int userID, DataResponseListener<Chat> listener);

    void getList(DataResponseListener<List<Chat>> listener);
}
