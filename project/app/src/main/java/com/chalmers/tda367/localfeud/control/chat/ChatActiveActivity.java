package com.chalmers.tda367.localfeud.control.chat;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chalmers.tda367.localfeud.R;
import com.chalmers.tda367.localfeud.control.notifications.IMessageListener;
import com.chalmers.tda367.localfeud.control.notifications.MessageHandler;
import com.chalmers.tda367.localfeud.data.Chat;
import com.chalmers.tda367.localfeud.data.ChatMessage;
import com.chalmers.tda367.localfeud.data.KnownUser;
import com.chalmers.tda367.localfeud.data.Me;
import com.chalmers.tda367.localfeud.data.User;
import com.chalmers.tda367.localfeud.data.handler.DataHandlerFacade;
import com.chalmers.tda367.localfeud.data.handler.MeDataHandler;
import com.chalmers.tda367.localfeud.data.handler.core.AbstractDataResponseListener;
import com.chalmers.tda367.localfeud.data.handler.core.DataResponseError;
import com.chalmers.tda367.localfeud.services.NotificationFacade;
import com.chalmers.tda367.localfeud.util.MapEntry;
import com.chalmers.tda367.localfeud.util.TagHandler;

import java.util.Calendar;
import java.util.List;
import java.util.Map;


/**
 * Created by Daniel Ahlqvist on 2016-05-03.
 */
public class ChatActiveActivity extends AppCompatActivity implements ChatActiveAdapter.AdapterCallback, IMessageListener {

    private static final String TAG = "ChatActiveActivity";

    private ImageButton postMessageButton, backButton;

    private RecyclerView chatMessageList;
    private EditText chatMessageInput;
    private ChatActiveAdapter chatActiveAdapter;
    private Chat chat;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        chat = (Chat) bundle.getSerializable("chat");
        setTheme(R.style.ChatAppTheme);
        setContentView(R.layout.activity_active_chat);
    }

    @Override
    protected void onResume() {
        super.onResume();

        registerAsMessageListener();

        initViews();
    }

    private void registerAsMessageListener(){
        int counterPartUserId = chat.getFirstCounterPart(MeDataHandler.getInstance().getMe().getId()).getId();

        //Register this as a listener for messages
        MapEntry<String, Object> data = new MapEntry<String, Object>(MessageHandler.CHAT_MESSAGE_SENDER_ID, counterPartUserId);
        NotificationFacade.getInstance().getMessageHandler().addMessageListener(MessageHandler.CHAT_MESSAGE_RECIEVED, data, this);
        Log.d(TagHandler.MAIN_TAG, "Register as message listener for user: " + counterPartUserId);
    }

    private void unregisterAsMessageListener(){
        int counterPartUserId = chat.getFirstCounterPart(MeDataHandler.getInstance().getMe().getId()).getId();

        //Unregister this as a listener for messages
        MapEntry<String, Object> data = new MapEntry<String, Object>(MessageHandler.CHAT_MESSAGE_SENDER_ID, counterPartUserId);
        NotificationFacade.getInstance().getMessageHandler().removeMessageListener(MessageHandler.CHAT_MESSAGE_RECIEVED, data, this);
        Log.d(TagHandler.MAIN_TAG, "Unregister as listener");
    }

    private void initViews() {
        chatMessageList = (RecyclerView) findViewById(R.id.chat_message_list);
        chatMessageInput = (EditText) findViewById(R.id.posttext);
        ImageButton postMessageButton = (ImageButton) findViewById(R.id.post_button);
        TextView chatTitle = (TextView) findViewById(R.id.chat_title);
        if (chatTitle != null) {
            chatTitle.setText(chat.getChatName());
        }
        ImageButton backButton = (ImageButton) findViewById(R.id.chat_view_back_btn);
        if (backButton != null) {
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        chatMessageList.setLayoutManager(new LinearLayoutManager(this));
        chatMessageList.setHasFixedSize(true);

        chatActiveAdapter = new ChatActiveAdapter(this);
        chatMessageList.setAdapter(chatActiveAdapter);

        if (postMessageButton != null) {
            postMessageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    String messageText = chatMessageInput.getText().toString().trim().replaceAll("(\r?\n){3,}", "\r\n\r\n");

                    if (!messageText.isEmpty())
                    {
                        final ChatMessage message = new ChatMessage(chat, messageText, new User(DataHandlerFacade.getMeDataHandler().getMe()));

                        chatMessageInput.setText("");
                        DataHandlerFacade.getChatMessageDataHandler().send(chat, message, new AbstractDataResponseListener<ChatMessage>() {
                            @Override
                            public void onSuccess(ChatMessage data) {
                                Chat oldChat = chat.clone();
                                chat.setLastMessage(message.getText());
                                chat.setLastActivity(chat.getStringFromDate(Calendar.getInstance()));
                                DataHandlerFacade.getChatDataHandler().triggerChange(oldChat, chat);
                                refreshMessages();
                            }

                            @Override
                            public void onFailure(DataResponseError error, String errormessage) {

                                Snackbar.make(chatMessageList,
                                        "Could not post message.",
                                        Snackbar.LENGTH_LONG)
                                        .show();
                            }
                        });
                    } else {
                        Snackbar.make(chatMessageList,
                                "Please write something first.",
                                Snackbar.LENGTH_LONG)
                                .show();
                    }
                }
            });
        }

        chatMessageInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollToBottom();
            }
        });
        refreshMessages();
    }

    public void refreshMessages() {
        DataHandlerFacade.getChatMessageDataHandler().getList(chat, new AbstractDataResponseListener<List<ChatMessage>>() {
            @Override
            public void onSuccess(List<ChatMessage> data) {
                chatActiveAdapter.addChatMessageListToAdapter(data);
                scrollToBottom();
            }

            @Override
            public void onFailure(DataResponseError error, String errormessage) {
                Log.e(TAG, "onFailure: " + errormessage);
                Snackbar.make(chatMessageList,
                        getErrorString(error),
                        Snackbar.LENGTH_LONG)
                        .show();
            }
        });
    }

    public void scrollToBottom() {
        chatMessageList.scrollToPosition(chatActiveAdapter.getItemCount() - 1);
    }

    private String getErrorString(DataResponseError error) {
        switch (error) {
            case NOTFOUND:
                return getString(R.string.chatlist_notfound_error_msg);
            case UNAUTHORIZED:
                return getString(R.string.unauthorized_error_msg);
            default:
                return getString(R.string.server_error_chatlist_msg);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterAsMessageListener();
    }

    @Override
    public void onMessageRecieved(Map<String, Object> data) {
        final ChatMessage chatMessage = (ChatMessage) data.get("object");

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chatActiveAdapter.addChatMessageToAdapter(chatMessage);
                scrollToBottom();
            }
        });
    }
}