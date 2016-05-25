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
import com.chalmers.tda367.localfeud.data.handler.core.AbstractDataResponseListener;
import com.chalmers.tda367.localfeud.data.handler.core.DataChangeListener;
import com.chalmers.tda367.localfeud.data.handler.core.DataResponseError;
import com.github.fabtransitionactivity.SheetLayout;

import java.util.Comparator;
import java.util.List;

/**
 *  A Fragment that's controlling both PostFragments
 *  in the TabLayout.
 */
public class FeedFragment extends Fragment implements PostFragment.FragmentCallback, SheetLayout.OnFabAnimationEndListener {

    private PostAdapter postAdapter;
    private ViewPager viewPager;
    private FeedPagerAdapter feedPagerAdapter;
    private CoordinatorLayout root;
    private PostFragment postFragment, postFragment2;

    private SheetLayout sheetLayout;

    private static final int REQUEST_CODE = 1;

    private final static String VIEW_PAGER_KEY = "viewPagerKey";
    private PostAdapter postAdapter2;

    /**
     *  Should be used for creating a new instance of FeedFragment,
     *  since constructors of Fragments can't have any arguments
     *  @param context from calling activity
     *  @return a new instance of FeedFragment
     */
    public static FeedFragment newInstance(Context context) {
        final FeedFragment fragment = new FeedFragment();

        // Sort on date/ID
        fragment.postAdapter = new PostAdapter(context, new Comparator<Post>() {
            @Override
            public int compare(Post lhs, Post rhs) {
                return rhs.getId() - lhs.getId();
            }
        }, "latest");

        // Sort on distance
        fragment.postAdapter2 = new PostAdapter(context, new Comparator<Post>() {
            @Override
            public int compare(Post lhs, Post rhs) {
                return lhs.getDistance() - rhs.getDistance();
            }
        }, "nearest");

//        Creating two postFragments with two different sorting behaviours
//        .setName is used for to set the name that TabLayout will use
        fragment.postFragment = PostFragment.newInstance(fragment.postAdapter, fragment);
        fragment.postFragment.setName(context.getResources().getString(R.string.latest_messages));
        fragment.postFragment2 = PostFragment.newInstance(fragment.postAdapter2, fragment);
        fragment.postFragment2.setName(context.getResources().getString(R.string.nearest_messages));

//        Adding ChangeListeners that will update the list of both PostAdapters
        DataHandlerFacade.getPostDataHandler().addChangeListener(new DataChangeListener<Post>() {
            @Override
            public void onChange(Post oldValue, Post newValue) {
                if( oldValue == null ) {
                    fragment.postAdapter.addPostToAdapter(newValue);
                    fragment.postAdapter2.addPostToAdapter(newValue);
                }
                else {
                    fragment.postAdapter.changePostInAdapter(oldValue, newValue);
                    fragment.postAdapter2.changePostInAdapter(oldValue, newValue);
                }
            }
        });
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

    /**
     *  Initializing the relevant components that layout contains.
     *  @param view the holder layout that's containing all components
     *  @param savedInstanceState saved state from last instance of fragment
     */
    private void initViews(final View view, @Nullable Bundle savedInstanceState) {
        CollapsingToolbarLayout collapsingToolbarLayout =
                (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar_layout);
        collapsingToolbarLayout.setTitle(getResources().getString(R.string.app_name));

        final FloatingActionButton createNewFab = (FloatingActionButton) view.findViewById(R.id.post_feed_create_new_fab);
        createNewFab.setTransitionName("PostNewFab");
        createNewFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetLayout.expandFab();
            }
        });

//        Using a SheetLayout that's used for transition to NewPostActivity
        sheetLayout = (SheetLayout) view.findViewById(R.id.bottom_sheet);
        sheetLayout.setFab(createNewFab);
        sheetLayout.setFabAnimationEndListener(this);

        root = (CoordinatorLayout) view.findViewById(R.id.feed_fragment_root);

        viewPager = (ViewPager) view.findViewById(R.id.post_feed_viewpager);

//        Resetting the viewPagers state
        if (savedInstanceState != null)
            viewPager.onRestoreInstanceState(savedInstanceState.getBundle(VIEW_PAGER_KEY));

        feedPagerAdapter = new FeedPagerAdapter(getActivity().getSupportFragmentManager());
        addPages(viewPager);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.main_tablayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                Changing the page to chosen tab
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

    /**
     *  Used for adding fragments to a ViewPager
     *  @param viewPager the ViewPager who should contain the fragments
     */
    private void addPages(ViewPager viewPager) {
        feedPagerAdapter.addPage(postFragment);
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

    /**
     *  Simplifies the way of showing a Snackbar
     *  @param text the text that should be displayed in the Snackbar
     */
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
//                Adding the new posts to both adapters
                postAdapter.addPostListToAdapter(data);
                postAdapter2.addPostListToAdapter(data);

//                Stopping the SwipeToRefresh spinner
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
//                Showing what went wrong in a Snackbar
                showSnackbar(getErrorString(error));

//                Stopping the SwipeToRefresh spinner
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

    /**
     *  Converting a DataResponseError to a String error mesage
     *  @param error given DataResponseError that should be converted
     *  @return the requested string
     */
    private String getErrorString(DataResponseError error) {
        switch (error) {
            case NOTFOUND:
                return getString(R.string.post_notfound_error_msg);
            case UNAUTHORIZED:
                return getString(R.string.unauthorized_error_msg);
            default:
                return getString(R.string.server_error_post_msg);
        }
    }

    @Override
    public void onFabAnimationEnd() {
        Intent intent = new Intent(getActivity(), NewPostActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            sheetLayout.contractFab();
        }
    }
}
