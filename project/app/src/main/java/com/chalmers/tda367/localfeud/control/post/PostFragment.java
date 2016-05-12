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
import com.chalmers.tda367.localfeud.data.Position;
import com.chalmers.tda367.localfeud.data.Post;
import com.chalmers.tda367.localfeud.data.handler.DataHandlerFacade;
import com.chalmers.tda367.localfeud.data.handler.DataResponseError;
import com.chalmers.tda367.localfeud.data.handler.interfaces.AbstractDataResponseListener;
import com.chalmers.tda367.localfeud.data.handler.interfaces.DataResponseListener;
import com.chalmers.tda367.localfeud.service.responseActions.IResponseAction;
import com.chalmers.tda367.localfeud.service.ServerComm;
import com.chalmers.tda367.localfeud.service.responseListeners.RequestPostsResponseListener;
import com.chalmers.tda367.localfeud.util.TagHandler;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Text om klassen
 *
 * @author David Söderberg
 * @since 16-04-18
 */
public class PostFragment extends Fragment {

    private static PostAdapter postAdapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Parcelable listState;
    private static final String LIST_STATE_KEY = "ListStateKey";


    private DataResponseListener<List<Post>> requestPostsResponseListener;



    public PostFragment() {

    }

    public static PostFragment newInstance(PostAdapter adapter) {
        postAdapter = adapter;
        return new PostFragment();
    }

    public class RefreshPostsResponseListener extends RequestPostsResponseListener {

        public RefreshPostsResponseListener(PostAdapter adapter) {
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.post_feed_fragment, null);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.post_feed_refresh_layout);




        requestPostsResponseListener = new AbstractDataResponseListener<List<Post>>() {
            @Override
            public void onSuccess(List<Post> data) {
                postAdapter.addPostListToAdapter(data);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(DataResponseError error, String errormessage) {
                swipeRefreshLayout.setRefreshing(false);
            }
        };




        recyclerView = (RecyclerView) view.findViewById(R.id.post_feed_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(postAdapter);
        swipeRefreshLayout.setColorSchemeResources(R.color.feedColorPrimary,
                R.color.feedColorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //ServerComm.getInstance().requestPosts(new RefreshPostsResponseListener(postAdapter));
                DataHandlerFacade.getPostDataHandler().getList(new Position(53.123123, 11.123123), requestPostsResponseListener);
            }
        });
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        try {
            DataHandlerFacade.getPostDataHandler().getList(new Position(52.123, 42.123123), requestPostsResponseListener);

        } catch (NullPointerException e) {
            getActivity().finish();
        }
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (listState != null) {
            recyclerView.getLayoutManager().onRestoreInstanceState(listState);
        }
    }


    @Override
    public String toString() {
        return "Post Feed";
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
}
