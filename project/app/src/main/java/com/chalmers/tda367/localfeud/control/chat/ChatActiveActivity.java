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
import com.chalmers.tda367.localfeud.services.notifications.IMessageListener;
import com.chalmers.tda367.localfeud.control.notifications.MessageHandler;
import com.chalmers.tda367.localfeud.data.Chat;
import com.chalmers.tda367.localfeud.data.ChatMessage;
import com.chalmers.tda367.localfeud.data.User;
import com.chalmers.tda367.localfeud.data.handler.DataHandlerFacade;
import com.chalmers.tda367.localfeud.data.handler.MeDataHandler;
import com.chalmers.tda367.localfeud.data.handler.core.AbstractDataResponseListener;
import com.chalmers.tda367.localfeud.data.handler.core.DataResponseError;
import com.chalmers.tda367.localfeud.control.notifications.NotificationFacade;
import com.chalmers.tda367.localfeud.util.MapEntry;
import com.chalmers.tda367.localfeud.util.TagHandler;
import com.facebook.login.widget.ProfilePictureView;

import java.util.Calendar;
import java.util.List;
import java.util.Map;


/**
 * Activity used to show a chat with another user. The activity contains a
 * recycler view, containing ChatActiveAdapter objects. The activity is
 * bound to the activity_active_chat layout XML file.
 */
public class ChatActiveActivity extends AppCompatActivity implements ChatActiveAdapter.AdapterCallback, IMessageListener {

    private static final String TAG = "ChatActiveActivity";

    private ImageButton postMessageButton, backButton;

    private RecyclerView chatMessageList;
    private EditText chatMessageInput;
    private ChatActiveAdapter chatActiveAdapter;
    private Chat chat;
    private ProfilePictureView profilePictureView;

    /**
     * Binds a layout XML file to the activity and receives the active chat
     * from the object it is created from (ChatListAdapter).
     *
     * @param savedInstanceState an old state of the activity, used to resume a previous instance.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        chat = (Chat) bundle.getSerializable("chat");

        setTheme(R.style.ChatAppTheme);
        setContentView(R.layout.activity_active_chat);
    }

    /**
     * Used to resume the activity. It will start to listen for messages incoming.
     */
    @Override
    protected void onResume() {
        super.onResume();
        registerAsMessageListener();
        initViews();
    }

    /**
     * Used to start listening for incoming messages, to make sure that a message
     * will show up instantly when it is received.
     */
    private void registerAsMessageListener(){
        int counterPartUserId = chat.getFirstCounterPart(MeDataHandler.getInstance().getMe().getId()).getId();

        //Register this as a listener for messages
        MapEntry<String, Object> data = new MapEntry<String, Object>(MessageHandler.CHAT_MESSAGE_SENDER_ID, counterPartUserId);
        NotificationFacade.getInstance().getMessageHandler().addMessageListener(MessageHandler.CHAT_MESSAGE_RECIEVED, data, this);
        Log.d(TagHandler.MAIN_TAG, "Register as message listener for user: " + counterPartUserId);
    }

    /**
     * Stops the activity from listening for incoming messages. Messages will no longer
     * show up instantly.
     */
    private void unregisterAsMessageListener(){
        int counterPartUserId = chat.getFirstCounterPart(MeDataHandler.getInstance().getMe().getId()).getId();

        //Unregister this as a listener for messages
        MapEntry<String, Object> data = new MapEntry<String, Object>(MessageHandler.CHAT_MESSAGE_SENDER_ID, counterPartUserId);
        NotificationFacade.getInstance().getMessageHandler().removeMessageListener(MessageHandler.CHAT_MESSAGE_RECIEVED, data, this);
        Log.d(TagHandler.MAIN_TAG, "Unregister as listener");
    }

    /**
     * Binds the objects in the layout XML file to variables in the activity class.
     * Listeners are set to the buttons.
     */
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

        /*profilePictureView = (ProfilePictureView) findViewById(R.id.profilepic2);

        profilePictureView.setProfileId("694967503939103");*/

        chatMessageList.setLayoutManager(new LinearLayoutManager(this));
        chatMessageList.setHasFixedSize(true);

        chatActiveAdapter = new ChatActiveAdapter(this);
        chatMessageList.setAdapter(chatActiveAdapter);

        if (postMessageButton != null) {
            postMessageButton.setOnClickListener(new View.OnClickListener() {

                /**
                 * Sends a message when the send button is clicked, if nothing is wrong.
                 *
                 * @param v the current view.
                 */
                @Override
                public void onClick(View v)
                {
                    String messageText = chatMessageInput.getText().toString().trim().replaceAll("(\r?\n){3,}", "\r\n\r\n");

                    if (!messageText.isEmpty())
                    {
                        final ChatMessage message = new ChatMessage(chat.getId(), messageText, new User(DataHandlerFacade.getMeDataHandler().getMe()));

                        chatMessageInput.setText("");
                        DataHandlerFacade.getChatMessageDataHandler().send(chat, message, new AbstractDataResponseListener<ChatMessage>() {
                            @Override
                            public void onSuccess(ChatMessage data) {
                                Log.d(TagHandler.MAIN_TAG, data.toString());
                                Chat oldChat = chat.clone();
                                chat.setLastMessage(data.getText());
                                chat.setLastActivity(chat.getStringFromDate(Calendar.getInstance()));
                                DataHandlerFacade.getChatDataHandler().triggerChange(oldChat, chat);
                                addChatMessageToAdapter(data);
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

    /**
     * Reloads and downloads possible new messages from the server.
     */
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

    /**
     * Scrolls the recycler view to the bottom to show the latest message.
     */
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

    /**
     * When the activity is taken out of focus, the unregisterAsMessageListener
     * method is called to stop the activity from listening for incoming messages.
     */
    @Override
    protected void onPause() {
        super.onPause();
        unregisterAsMessageListener();
    }

    /**
     * Used to add a chat message to the ChatActiveAdapter. The method with the
     * same name in the adapter is called.
     *
     * @param chatMessage the chat message which will be added.
     */
    protected void addChatMessageToAdapter(final ChatMessage chatMessage){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chatActiveAdapter.addChatMessageToAdapter(chatMessage);
                scrollToBottom();
            }
        });
    }

    /**
     * Determines what will happen when a chat message is received.
     *
     * @param data a map containing a chat message and a key
     */
    @Override
    public void onMessageReceived(Map<String, Object> data) {
        final ChatMessage chatMessage = (ChatMessage) data.get("object");

        Chat oldChat = chat.clone();
        chat.setLastMessage(chatMessage.getText());
        chat.setLastActivity(chat.getStringFromDate(Calendar.getInstance()));
        DataHandlerFacade.getChatDataHandler().triggerChange(oldChat, chat);

        addChatMessageToAdapter(chatMessage);
    }
}