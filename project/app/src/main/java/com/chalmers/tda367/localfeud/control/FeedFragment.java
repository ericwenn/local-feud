package com.chalmers.tda367.localfeud.control;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chalmers.tda367.localfeud.R;
import com.chalmers.tda367.localfeud.net.ServerComm;
import com.chalmers.tda367.localfeud.net.responseListeners.RequestPostsResponseListener;

public class FeedFragment extends Fragment {

    private FloatingActionButton createNewFab;
    private PostAdapter postAdapter;
    private ViewPager viewPager;
    private MainActivity activity;

    public static FeedFragment newInstance(MainActivity mainActivity) {
        FeedFragment fragment = new FeedFragment();
        fragment.activity = mainActivity;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        ServerComm.getInstance().requestPosts(new RequestPostsResponseListener(postAdapter));
    }

    private void initViews(final View view) {
        CollapsingToolbarLayout collapsingToolbarLayout =
                (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar_layout);
        collapsingToolbarLayout.setTitle(getResources().getString(R.string.app_name));

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);

        createNewFab = (FloatingActionButton) view.findViewById(R.id.post_feed_create_new_fab);
        createNewFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, NewPostActivity.class);
                startActivity(i);
            }
        });

        postAdapter = new PostAdapter(activity);

        viewPager = (ViewPager) view.findViewById(R.id.post_feed_viewpager);
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
        FeedPagerAdapter feedPagerAdapter = new FeedPagerAdapter(activity.getSupportFragmentManager());
        feedPagerAdapter.addPage(PostFragment.newInstance(postAdapter));

//        TODO: Change to a diff fragment
        feedPagerAdapter.addPage(PostFragment.newInstance(postAdapter));
        viewPager.setAdapter(feedPagerAdapter);
    }
}
