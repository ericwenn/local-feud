package com.chalmers.tda367.localfeud.control;

import android.os.Bundle;
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
import com.chalmers.tda367.localfeud.net.ServerComm;
import com.chalmers.tda367.localfeud.util.TagHandler;

/**
 * Text om klassen
 *
 * @author David Söderberg
 * @since 16-04-18
 */
public class PostFragment extends Fragment {

    private static PostAdapter postAdapter;

    public PostFragment() {

    }

    public static PostFragment newInstance(PostAdapter adapter) {
        postAdapter = adapter;
        return new PostFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TagHandler.MAIN_TAG, "Created PostFragment");
        View view = inflater.inflate(R.layout.post_feed_fragment, null);
        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.post_feed_refresh_layout);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.post_feed_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(postAdapter);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ServerComm.getInstance().updatePostFeed(postAdapter, swipeRefreshLayout);
            }
        });
        return view;
    }

    @Override
    public String toString() {
        return "Post Feed";
    }
}
