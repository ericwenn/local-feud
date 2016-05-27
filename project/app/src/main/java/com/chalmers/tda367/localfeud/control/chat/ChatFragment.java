package com.chalmers.tda367.localfeud.control.chat;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chalmers.tda367.localfeud.R;
import com.chalmers.tda367.localfeud.data.Chat;
import com.chalmers.tda367.localfeud.data.handler.DataHandlerFacade;
import com.chalmers.tda367.localfeud.data.handler.core.AbstractDataResponseListener;
import com.chalmers.tda367.localfeud.data.handler.core.DataChangeListener;
import com.chalmers.tda367.localfeud.data.handler.core.DataResponseError;
import com.chalmers.tda367.localfeud.util.TagHandler;

import java.util.Collections;
import java.util.List;

/**
 *  A fragment which works as one of the tabs for MainActivity.
 *  The fragment holds the activities used for chats and chat
 *  messages.
 */
public class ChatFragment extends Fragment {
    private static final String TAG = "ChatFragment";
    private CoordinatorLayout root;
    private ChatListAdapter chatListAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    /**
     * Method run when a new instance of the fragment is created.
     *
     * @param context the current state of the object it is called from.
     * @return a new ChatFragment object
     */
    public static ChatFragment newInstance(Context context) {
        final ChatFragment fragment = new ChatFragment();
        fragment.chatListAdapter = new ChatListAdapter(context);


        DataHandlerFacade.getChatDataHandler().addChangeListener(new DataChangeListener<Chat>() {
            @Override
            public void onChange(Chat oldValue, Chat newValue) {
                fragment.chatListAdapter.changeChatInAdapter(oldValue, newValue);
            }
        });
        return fragment;
    }


    /**
     * Creates the view which the fragment will hold. The view is created using
     * the fragment_chat layout XML file.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState an old state of the activity, used to resume
     *                           a previous instance.
     * @return the view the fragment will hold
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.ChatAppTheme);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        return localInflater.inflate(R.layout.fragment_chat, container, false);
    }

    /**
     * Calls the method to initialize the view of the fragment and tries to get
     * a list of chats
     *
     * @param view the view to initialize
     * @param savedInstanceState an old state of the fragment, used to resume
     *                           a previous instance.
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        DataHandlerFacade.getChatDataHandler().getList(new AbstractDataResponseListener<List<Chat>>() {
            @Override
            public void onSuccess(List<Chat> data) {
                swipeRefreshLayout.setRefreshing(false);
                Collections.reverse(data);
                chatListAdapter.addChatListToAdapter( data );
            }

            @Override
            public void onFailure(DataResponseError error, String errormessage) {
                swipeRefreshLayout.setRefreshing(false);
                Log.e(TAG, "onFailure: "+ errormessage);
                showSnackbar(getErrorString(error));
            }
        });

    }

    /**
     * Initializing the view of the fragment. Binds the objects from the
     * layout XML file to variables.
     *
     * @param view the view to initialize
     */
    private void initViews(View view) {
        root = (CoordinatorLayout) view.findViewById(R.id.chat_list_root);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.chat_list_refresh_layout);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.chat_list_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(chatListAdapter);

        swipeRefreshLayout.setColorSchemeResources(R.color.chatColorPrimary,
                R.color.chatColorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            /**
             * Called when a swipe down refresh is made. Tries to update the
             * list of chats.
             */
            @Override
            public void onRefresh() {
                DataHandlerFacade.getChatDataHandler().getList(new AbstractDataResponseListener<List<Chat>>() {
                    @Override
                    public void onSuccess(List<Chat> data) {
                        Collections.reverse(data);
                        chatListAdapter.addChatListToAdapter(data);
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onFailure(DataResponseError error, String errormessage) {
                        swipeRefreshLayout.setRefreshing(false);
                        Log.e(TAG, "onFailure: "+ errormessage);
                    }
                });
            }
        });

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    /**
     * Returns an error string if something goes wrong.
     *
     * @param error the error to display
     * @return a string with an error message
     */
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
     * Shows a snackbar with information.
     *
     * @param text the text to display in the snackbar
     */
    public void showSnackbar(String text) {
        Snackbar.make(root,
                text,
                Snackbar.LENGTH_LONG)
                .show();
    }
}