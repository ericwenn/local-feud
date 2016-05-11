package com.chalmers.tda367.localfeud.control;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chalmers.tda367.localfeud.R;

public class MeFragment extends Fragment {

    private CoordinatorLayout root;
    private Toolbar toolbar;
    private ViewPager viewPager;

    private FeedPagerAdapter mePagerAdapter;
    private MainActivity activity;

    private NoticeFragment noticeFragment;
    private SettingsFragment settingsFragment;

    public MeFragment() {

    }

    public static MeFragment newInstance(MainActivity activity) {
        MeFragment fragment = new MeFragment();

        fragment.activity = activity;

        fragment.noticeFragment = NoticeFragment.newInstance(activity);
        fragment.settingsFragment = SettingsFragment.newInstance(activity);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mePagerAdapter = new FeedPagerAdapter(activity.getSupportFragmentManager());
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.MeAppTheme);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        return localInflater.inflate(R.layout.fragment_me, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view, savedInstanceState);
    }

    private void initViews(View view, @Nullable Bundle savedInstanceState) {
        root = (CoordinatorLayout) view.findViewById(R.id.me_root);

        CollapsingToolbarLayout collapsingToolbarLayout =
                (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar_layout);
        collapsingToolbarLayout.setTitle(getResources().getString(R.string.app_name));
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);

        viewPager = (ViewPager) view.findViewById(R.id.me_viewpager);
        addPages(viewPager);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.me_tablayout);
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
        mePagerAdapter.addPage(noticeFragment);
        mePagerAdapter.addPage(settingsFragment);
        viewPager.setAdapter(mePagerAdapter);
    }

    public static boolean isFragmentVisible() {
        return false;
    }
}
