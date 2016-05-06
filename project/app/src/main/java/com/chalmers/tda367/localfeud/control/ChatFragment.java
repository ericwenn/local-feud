package com.chalmers.tda367.localfeud.control;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chalmers.tda367.localfeud.R;
import com.mypopsy.drawable.SearchArrowDrawable;
import com.mypopsy.widget.FloatingSearchView;

public class ChatFragment extends Fragment {

    private FloatingSearchView searchView;

    public ChatFragment() {

    }

    public static ChatFragment newInstance() {
        ChatFragment fragment = new ChatFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view, savedInstanceState);
    }

    private void initViews(View view, @Nullable Bundle savedInstanceState) {
        searchView = (FloatingSearchView) view.findViewById(R.id.chat_list_searchview);
        searchView.setIcon(new SearchArrowDrawable(getContext()));
    }

}
