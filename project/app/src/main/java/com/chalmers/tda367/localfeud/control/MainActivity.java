package com.chalmers.tda367.localfeud.control;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageButton;

import com.chalmers.tda367.localfeud.R;
import com.chalmers.tda367.localfeud.data.Post;
import com.chalmers.tda367.localfeud.util.TagHandler;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

public class MainActivity extends AppCompatActivity implements PostAdapter.AdapterCallback {

    private BottomBar bottomBar;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TagHandler.MAIN_ACTIVITY_TAG, "onCreate, savedInstanceState: " + savedInstanceState);
        setContentView(R.layout.activity_main);
        initBottomBar(savedInstanceState);
        Log.d(TagHandler.MAIN_ACTIVITY_TAG, "onCreate done");
    }

    private void initBottomBar(final Bundle savedInstanceState){
        bottomBar = BottomBar.attach(this, savedInstanceState);
        bottomBar.noTopOffset();
        bottomBar.noResizeGoodness();
        bottomBar.noNavBarGoodness();
        bottomBar.setMaxFixedTabs(2);
        //TODO: Implement button functionality
        bottomBar.setItemsFromMenu(R.menu.bottombar_menu, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                switchFragment(menuItemId);
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.feed_item) {
                    // The user reselected item number one.
                }
                else if (menuItemId == R.id.chat_item) {
                    // The user reselected item number two.
                }
                else if (menuItemId == R.id.me_item) {
                    // The user reselected item number three.
                }
            }
        });

        bottomBar.mapColorForTab(0, ContextCompat.getColor(this, R.color.colorPrimary));
        bottomBar.mapColorForTab(1, ContextCompat.getColor(this, R.color.colorAccent));
        bottomBar.mapColorForTab(2, "#FF9800");
    }

    private void switchFragment(int menuItemId) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (menuItemId == R.id.feed_item) {
            if (currentFragment == null || currentFragment.getClass() != FeedFragment.class)
                currentFragment = FeedFragment.newInstance(this);
        }
        else if (menuItemId == R.id.chat_item) {
            if (currentFragment == null || currentFragment.getClass() != ChatFragment.class)
                currentFragment = ChatFragment.newInstance();
        }
        else if (menuItemId == R.id.me_item) {
            if (currentFragment == null || currentFragment.getClass() != MeFragment.class)
                currentFragment = MeFragment.newInstance();
        }
        transaction.replace(R.id.main_root, currentFragment);
        transaction.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TagHandler.MAIN_ACTIVITY_TAG, "onSaveInstanceState");
        bottomBar.onSaveInstanceState(outState);
        currentFragment.onSaveInstanceState(outState);
    }

    @Override
    public void onPostClick(Post post) {
        Intent i = new Intent(getApplicationContext(), PostClickedActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("post", post);
        i.putExtras(bundle);
        startActivity(i);
    }

    @Override
    public void onLikeClick(Post post, ImageButton imageButton) {
        Snackbar.make(bottomBar,
                "You like that huh?",
                Snackbar.LENGTH_LONG)
                .show();
//        Should check if post is liked
        imageButton.setImageResource(R.drawable.ic_favorite_black_24dp);
    }

    @Override
    public void onMoreClick(Post post) {
        Snackbar.make(bottomBar,
                "No more for you",
                Snackbar.LENGTH_LONG)
                .show();
    }
}