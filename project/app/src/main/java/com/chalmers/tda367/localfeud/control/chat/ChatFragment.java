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
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chalmers.tda367.localfeud.R;
import com.chalmers.tda367.localfeud.control.MainActivity;
import com.chalmers.tda367.localfeud.service.responseActions.IResponseAction;
import com.chalmers.tda367.localfeud.service.ServerComm;
import com.chalmers.tda367.localfeud.service.responseListeners.RequestChatListResponseListener;

public class ChatFragment extends Fragment {

    private CoordinatorLayout root;
    private RecyclerView recyclerView;
    private MainActivity activity;
    private ChatListAdapter chatListAdapter;
    private Toolbar toolbar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RequestChatListResponseListener requestChatListResponseListener;
    private TextView textView;

    public ChatFragment() {

    }

    public static ChatFragment newInstance(MainActivity activity) {
        ChatFragment fragment = new ChatFragment();
        fragment.activity = activity;
        fragment.chatListAdapter = new ChatListAdapter(fragment.activity);
        return fragment;
    }

    public class RefreshChatListResponseListener extends RequestChatListResponseListener {

        public RefreshChatListResponseListener(ChatListAdapter adapter) {
            super(adapter);
        }

        @Override
        public void onResponseSuccess(IResponseAction source) {
            super.onResponseSuccess(source);
            swipeRefreshLayout.setRefreshing(false);
        }

        @Override
        public void onResponseFailure(IResponseAction source) {
            super.onResponseFailure(source);
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.ChatAppTheme);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        return localInflater.inflate(R.layout.fragment_chat, container, false);
//        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view, savedInstanceState);
        ServerComm.getInstance().requestChats(requestChatListResponseListener);
    }

    private void initViews(View view, @Nullable Bundle savedInstanceState) {
        root = (CoordinatorLayout) view.findViewById(R.id.chat_list_root);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.chat_list_refresh_layout);
        requestChatListResponseListener = new RefreshChatListResponseListener(chatListAdapter);
        textView = (TextView) view.findViewById(R.id.chat_list_toolbar_title_textview);

        recyclerView = (RecyclerView) view.findViewById(R.id.chat_list_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(chatListAdapter);

        swipeRefreshLayout.setColorSchemeResources(R.color.chatColorPrimary,
                R.color.chatColorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ServerComm.getInstance().requestPosts(new RefreshChatListResponseListener(chatListAdapter));
            }
        });

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    public void showSnackbar(String text) {
        Snackbar.make(root,
                text,
                Snackbar.LENGTH_LONG)
                .show();
    }
}