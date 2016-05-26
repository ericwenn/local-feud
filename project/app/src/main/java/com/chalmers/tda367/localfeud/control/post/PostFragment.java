package com.chalmers.tda367.localfeud.control.post;

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
import com.chalmers.tda367.localfeud.util.TagHandler;

/**
 *  A Fragment that's controlling a RecyclerView
 *  used for displaying all posts in a list.
 */
public class PostFragment extends Fragment {

    private PostAdapter postAdapter;

    private RecyclerView recyclerView;
    public SwipeRefreshLayout swipeRefreshLayout;
    private Parcelable listState;
    private static final String LIST_STATE_KEY = "ListStateKey";
    private FragmentCallback callback;
    private String name;

    public PostFragment() {

    }

    /**
     *  Should be used for creating a new instance of PostFragment,
     *  since constructors of Fragments can't have any arguments
     *  @param adapter the PostAdapter that's controlling RecyclerView
     *  @param feedFragment calling FeedFragment
     *  @return a new instance of PostFragment
     */
    public static PostFragment newInstance(PostAdapter adapter, Fragment feedFragment) {
        PostFragment fragment = new PostFragment();
        fragment.postAdapter = adapter;

//        Creating a FragmentCallback which will send all
//        update requests to FeedFragment
        try {
            fragment.callback = (FragmentCallback) feedFragment;
        } catch (ClassCastException e) {
            Log.e(TagHandler.MAIN_TAG, fragment.getClass() + " must implement PostFragment.FragmentCallback");
        }

        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.post_feed_fragment, null);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.post_feed_refresh_layout);
        SwipeRefreshLayout.OnRefreshListener listener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callback.updatePosts(swipeRefreshLayout);
            }
        };

        recyclerView = (RecyclerView) view.findViewById(R.id.post_feed_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(postAdapter);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.black,
                R.color.feedColorAccent);
        swipeRefreshLayout.setOnRefreshListener(listener);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

//        Restoring the state of RecyclerView
        if (listState != null) {
            recyclerView.getLayoutManager().onRestoreInstanceState(listState);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (recyclerView != null) {
            listState = recyclerView.getLayoutManager().onSaveInstanceState();
            outState.putParcelable(LIST_STATE_KEY, listState);
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            listState = savedInstanceState.getParcelable(LIST_STATE_KEY);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }

    public interface FragmentCallback {
        /**
         *  Requesting new posts.
         *  @param l the SwipeRefreshLayout connected to RecyclerView
         */
        void updatePosts(SwipeRefreshLayout l);
    }
}
