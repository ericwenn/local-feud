package com.chalmers.tda367.localfeud.control.post;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 *  A FragmentStatePagerAdapter with
 *  a custom addPage function
 *  for adding Fragments to the adapter.
 */
public class FeedPagerAdapter extends FragmentStatePagerAdapter {

//    List of fragments being hold by the adapter
    private final ArrayList<Fragment> pages = new ArrayList<>();

    public FeedPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return pages.get(position);
    }

    @Override
    public int getCount() {
        return pages.size();
    }

    /**
     *  Function for adding a new fragment to the adapter.
     *  @param fragment the page that should be added
     */
    public void addPage(Fragment fragment) {
        pages.add(fragment);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pages.get(position).toString();
    }
}
