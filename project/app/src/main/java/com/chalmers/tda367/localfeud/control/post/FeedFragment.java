package com.chalmers.tda367.localfeud.control.post;


import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chalmers.tda367.localfeud.R;
import com.chalmers.tda367.localfeud.data.Position;
import com.chalmers.tda367.localfeud.data.Post;
import com.chalmers.tda367.localfeud.data.handler.DataHandlerFacade;
import com.chalmers.tda367.localfeud.data.handler.DataResponseError;
import com.chalmers.tda367.localfeud.data.handler.interfaces.AbstractDataResponseListener;

import java.util.Comparator;
import java.util.List;

public class FeedFragment extends Fragment implements PostFragment.FragmentCallback {

    private PostAdapter postAdapter;
    private ViewPager viewPager;
    private FeedPagerAdapter feedPagerAdapter;
    private CoordinatorLayout root;
    private PostFragment postFragment, postFragment2;

    private final static String VIEW_PAGER_KEY = "viewPagerKey";
    private PostAdapter postAdapter2;


    public static FeedFragment newInstance(Context context) {
        FeedFragment fragment = new FeedFragment();

        // Sort on date/ID
        fragment.postAdapter = new PostAdapter(context, new Comparator<Post>() {
            @Override
            public int compare(Post lhs, Post rhs) {
                return rhs.getId() - lhs.getId();
            }
        });

        // Sort on distance
        fragment.postAdapter2 = new PostAdapter(context, new Comparator<Post>() {
            @Override
            public int compare(Post lhs, Post rhs) {
                return lhs.getDistance() - rhs.getDistance();
            }
        });

        fragment.postFragment = PostFragment.newInstance(fragment.postAdapter, fragment);
        fragment.postFragment.setName(context.getResources().getString(R.string.latest_messages));
        fragment.postFragment2 = PostFragment.newInstance(fragment.postAdapter2, fragment);
        fragment.postFragment2.setName(context.getResources().getString(R.string.nearest_messages));

        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        feedPagerAdapter = new FeedPagerAdapter(getActivity().getSupportFragmentManager());
        setRetainInstance(true);
        updatePosts(null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view, savedInstanceState);
    }

    private void initViews(final View view, @Nullable Bundle savedInstanceState) {
        CollapsingToolbarLayout collapsingToolbarLayout =
                (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar_layout);
        collapsingToolbarLayout.setTitle(getResources().getString(R.string.app_name));

        FloatingActionButton createNewFab = (FloatingActionButton) view.findViewById(R.id.post_feed_create_new_fab);
        createNewFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), NewPostActivity.class);
                startActivity(i);
            }
        });

        root = (CoordinatorLayout) view.findViewById(R.id.feed_fragment_root);

        viewPager = (ViewPager) view.findViewById(R.id.post_feed_viewpager);

        if (savedInstanceState != null)
            viewPager.onRestoreInstanceState(savedInstanceState.getBundle(VIEW_PAGER_KEY));

        feedPagerAdapter = new FeedPagerAdapter(getActivity().getSupportFragmentManager());
        addPages(viewPager);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.main_tablayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void addPages(ViewPager viewPager) {
        feedPagerAdapter.addPage(postFragment);

//        TODO: Change to a diff fragment
        feedPagerAdapter.addPage(postFragment2);
        viewPager.setAdapter(feedPagerAdapter);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(VIEW_PAGER_KEY, viewPager.onSaveInstanceState());
        postFragment.onSaveInstanceState(outState);
        postFragment2.onSaveInstanceState(outState);
    }

    public void showSnackbar(String text) {
        Snackbar.make(root,
                text,
                Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void updatePosts(final SwipeRefreshLayout l) {
        Location loc = com.chalmers.tda367.localfeud.services.Location.getInstance().getLocation();
        DataHandlerFacade.getPostDataHandler().getList(new Position(loc), new AbstractDataResponseListener<List<Post>>() {
            @Override
            public void onSuccess(List<Post> data) {
                postAdapter.addPostListToAdapter(data);
                postAdapter2.addPostListToAdapter(data);
                if (l != null) {
                    l.setRefreshing(false);
                }
                else {
                    postFragment.swipeRefreshLayout.setRefreshing(false);
                    postFragment2.swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(DataResponseError error, String errormessage) {
//                TODO: Show snackbar onFailure
                if (l != null) {
                    l.setRefreshing(false);
                }
                else {
                    postFragment.swipeRefreshLayout.setRefreshing(false);
                    postFragment2.swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }
}
