package com.chalmers.tda367.localfeud.control.post;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chalmers.tda367.localfeud.R;
import com.chalmers.tda367.localfeud.control.MainActivity;

public class FeedFragment extends Fragment {

    private PostAdapter postAdapter;
    private ViewPager viewPager;
    private MainActivity activity;
    private FeedPagerAdapter feedPagerAdapter;
    private CoordinatorLayout root;
    private PostFragment postFragment, postFragment2;

    private final static String VIEW_PAGER_KEY = "viewPagerKey";

    public static FeedFragment newInstance(MainActivity mainActivity) {
        FeedFragment fragment = new FeedFragment();
        fragment.activity = mainActivity;
        fragment.postAdapter = new PostAdapter(fragment.activity);

        fragment.postFragment = PostFragment.newInstance(fragment.postAdapter);
        fragment.postFragment2 = PostFragment.newInstance(fragment.postAdapter);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        feedPagerAdapter = new FeedPagerAdapter(activity.getSupportFragmentManager());
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
                Intent i = new Intent(activity, NewPostActivity.class);
                startActivity(i);
            }
        });

        root = (CoordinatorLayout) view.findViewById(R.id.feed_fragment_root);

        viewPager = (ViewPager) view.findViewById(R.id.post_feed_viewpager);

        if (savedInstanceState != null)
            viewPager.onRestoreInstanceState(savedInstanceState.getBundle(VIEW_PAGER_KEY));

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
}
