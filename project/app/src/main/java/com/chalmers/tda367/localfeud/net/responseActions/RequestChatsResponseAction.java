package com.chalmers.tda367.localfeud.net.responseActions;

import android.util.Log;

import com.chalmers.tda367.localfeud.data.Chat;
import com.chalmers.tda367.localfeud.net.ResponseError;
import com.chalmers.tda367.localfeud.util.GsonHandler;
import com.chalmers.tda367.localfeud.util.TagHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Text om klassen
 *
 * @author David Söderberg
 * @since 16-05-09
 */
public class RequestChatsResponseAction extends AbstractResponseAction {
    private List<Chat> chats;
    private String responseBody;

    @Override
    public void onSuccess(String responseBody) {
        ArrayList<Chat> chats = GsonHandler.getInstance().toChatList(responseBody);
        for (Chat chat : chats) {
            Log.d(TagHandler.MAIN_TAG, chat.getId() + ", " + chat.getChatName());
        }
        setChats(chats);
        notifySuccess();
    }

    @Override
    public void onFailure(ResponseError error, String responseBody) {
        setResponseBody(responseBody);
        setResponseError(error);
        notifyFailure();
    }

    public List<Chat> getChats() {
        if(chats != null){
            return chats;
        }
        else{
            throw new NullPointerException();
        }
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public void setChats(List<Chat> chats) {
        this.chats = chats;
    }
}
