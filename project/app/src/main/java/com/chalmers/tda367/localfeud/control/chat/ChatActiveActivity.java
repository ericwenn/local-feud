package com.chalmers.tda367.localfeud.control.chat;

import android.content.Intent;
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
import com.chalmers.tda367.localfeud.data.Chat;
import com.chalmers.tda367.localfeud.data.ChatMessage;
import com.chalmers.tda367.localfeud.data.User;
import com.chalmers.tda367.localfeud.data.handler.DataHandlerFacade;
import com.chalmers.tda367.localfeud.data.handler.DataResponseError;
import com.chalmers.tda367.localfeud.data.handler.interfaces.AbstractDataResponseListener;

import java.util.List;


/**
 * Created by Daniel Ahlqvist on 2016-05-03.
 */
public class ChatActiveActivity extends AppCompatActivity implements ChatActiveAdapter.AdapterCallback {

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
        initViews();
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
                public void onClick(View v) {

                    if (!chatMessageInput.getText().toString().isEmpty()) {
                        String messageText = chatMessageInput.getText().toString();

                        ChatMessage message = new ChatMessage(chat, messageText, new User(DataHandlerFacade.getMeDataHandler().getMe()));

                        chatMessageInput.setText("");
                        DataHandlerFacade.getChatMessageDataHandler().send(chat, message, new AbstractDataResponseListener<ChatMessage>() {
                            @Override
                            public void onSuccess(ChatMessage data) {
                                refreshMessages();
                            }

                            @Override
                            public void onFailure(DataResponseError error, String errormessage) {

                                Snackbar.make(chatMessageList,
                                        "Could not post message",
                                        Snackbar.LENGTH_LONG)
                                        .show();
                            }
                        });
                    } else {
                        Snackbar.make(chatMessageList,
                                "Are you sick in your brain?! You must enter something in the box you know!",
                                Snackbar.LENGTH_LONG)
                                .show();
                    }
                }
            });
        }

        chatMessageInput.setOnClickListener(new View.OnClickListener()
        {
             @Override
             public void onClick(View v) {
                scrollToBottom();
             }
         });
        refreshMessages();
    }

    public void refreshMessages()
    {
        DataHandlerFacade.getChatMessageDataHandler().getList(chat, new AbstractDataResponseListener<List<ChatMessage>>() {
            @Override
            public void onSuccess(List<ChatMessage> data) {
                chatActiveAdapter.addChatMessageListToAdapter(data);
                scrollToBottom();
            }

            @Override
            public void onFailure(DataResponseError error, String errormessage) {
                Log.e(TAG, "onFailure: "+ errormessage);
            }
        });
    }
    public void scrollToBottom()
    {
        chatMessageList.scrollToPosition(chatActiveAdapter.getItemCount()-1);
    }


}