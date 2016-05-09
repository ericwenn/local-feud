package com.chalmers.tda367.localfeud.control;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chalmers.tda367.localfeud.R;

public class ChatFragment extends Fragment {

    private CoordinatorLayout root;
    private RecyclerView recyclerView;
    private MainActivity activity;
    private ChatListAdapter chatListAdapter;
    private Toolbar toolbar;

    public ChatFragment() {

    }

    public static ChatFragment newInstance(MainActivity activity) {
        ChatFragment fragment = new ChatFragment();
        fragment.activity = activity;
        fragment.chatListAdapter = new ChatListAdapter(fragment.activity);
        return fragment;
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
    }

    private void initViews(View view, @Nullable Bundle savedInstanceState) {
        root = (CoordinatorLayout) view.findViewById(R.id.chat_list_root);

//        CollapsingToolbarLayout collapsingToolbarLayout =
//                (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar_layout);
//        collapsingToolbarLayout.setTitle(getResources().getString(R.string.app_name));
//        toolbar = (Toolbar) view.findViewById(R.id.toolbar);

        recyclerView = (RecyclerView) view.findViewById(R.id.chat_list_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(chatListAdapter);
    }

    public void showSnackbar(String text) {
        Snackbar.make(root,
                text,
                Snackbar.LENGTH_LONG)
                .show();
    }
}