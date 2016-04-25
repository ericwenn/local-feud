package com.chalmers.tda367.localfeud.control;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chalmers.tda367.localfeud.R;
import com.chalmers.tda367.localfeud.net.IResponseAction;
import com.chalmers.tda367.localfeud.net.ServerComm;
import com.chalmers.tda367.localfeud.net.responseListeners.RequestPostsResponseListener;
import com.chalmers.tda367.localfeud.util.TagHandler;

/**
 * Text om klassen
 *
 * @author David Söderberg
 * @since 16-04-18
 */
public class PostFragment extends Fragment {

    private static PostAdapter postAdapter;
    private LinearLayoutManager linearLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Parcelable listState;
    private static final String LIST_STATE_KEY = "ListStateKey";

    public PostFragment() {
        linearLayoutManager = new LinearLayoutManager(this.getActivity());
    }

    public static PostFragment newInstance(PostAdapter adapter) {
        postAdapter = adapter;
        Log.d(TagHandler.FEED_FRAGMENT_TAG, "PostFragment: New Instance");
        return new PostFragment();
    }

    public class RefreshPostsResponseListener extends RequestPostsResponseListener{

        public RefreshPostsResponseListener(PostAdapter adapter){
            super(adapter);
        }

        @Override
        public void onResponseSuccess(IResponseAction source){
            super.onResponseSuccess(source);
            swipeRefreshLayout.setRefreshing(false);
        }

        @Override
        public void onResponseFailure(IResponseAction source){
            super.onResponseFailure(source);
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TagHandler.MAIN_TAG, "Created PostFragment");
        View view = inflater.inflate(R.layout.post_feed_fragment, null);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.post_feed_refresh_layout);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.post_feed_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(postAdapter);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ServerComm.getInstance().requestPosts(new RefreshPostsResponseListener(postAdapter));
            }
        });
        Log.d(TagHandler.FEED_FRAGMENT_TAG, "PostFragment: On Create View");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TagHandler.FEED_FRAGMENT_TAG, "PostFragment: On View Created");
    }

    @Override
    public void onResume() {
        super.onResume();

        if (listState != null) {
            linearLayoutManager.onRestoreInstanceState(listState);
        }
        Log.d(TagHandler.FEED_FRAGMENT_TAG, "PostFragment: On Resume");
    }


    @Override
    public String toString() {
        return "Post Feed";
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        listState = linearLayoutManager.onSaveInstanceState();
        outState.putParcelable(LIST_STATE_KEY, listState);

        Log.d(TagHandler.MAIN_TAG, "PostFragment: OnSaveInstanceState");
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            listState = savedInstanceState.getParcelable(LIST_STATE_KEY);
        }
        Log.d(TagHandler.MAIN_TAG, "PostFragment: OnViewStateRestored");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TagHandler.MAIN_TAG, "PostFragment: OnActivityCreated");
    }
}
