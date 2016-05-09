package com.chalmers.tda367.localfeud.net.responseActions;

import com.chalmers.tda367.localfeud.data.Chat;
import com.chalmers.tda367.localfeud.net.IResponseAction;
import com.chalmers.tda367.localfeud.net.IResponseListener;
import com.chalmers.tda367.localfeud.net.ResponseError;
import com.chalmers.tda367.localfeud.util.GsonHandler;

import java.lang.reflect.Array;
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
        setChats(chats);
        notifySuccess();

    }

    @Override
    public void onFailure(ResponseError error, String responseBody) {

    }

    @Override
    public void addListener(IResponseListener listener) {

    }

    @Override
    public void removeListener(IResponseListener listener) {

    }

    public List<Chat> getChats() {
        return chats;
    }

    public void setChats(List<Chat> chats) {
        this.chats = chats;
    }
}
