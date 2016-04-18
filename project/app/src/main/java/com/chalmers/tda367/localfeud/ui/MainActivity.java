package com.chalmers.tda367.localfeud.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.chalmers.tda367.localfeud.R;
import com.chalmers.tda367.localfeud.data.Post;
import com.chalmers.tda367.localfeud.net.ServerComm;
import com.chalmers.tda367.localfeud.util.TagHandler;

public class MainActivity extends AppCompatActivity implements PostAdapter.AdapterCallback {

//    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private FloatingActionButton createNewFab;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initViews();
        ServerComm.updatePostFeed(postAdapter);
    }

    private void initViews() {
        createNewFab = (FloatingActionButton) findViewById(R.id.post_feed_create_new_fab);
        createNewFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,
                        "Create new post",
                        Snackbar.LENGTH_SHORT)
                        .show();
                Intent i = new Intent(getApplicationContext(), NewPostActivity.class);
                startActivity(i);
            }
        });
//        recyclerView = (RecyclerView) findViewById(R.id.post_feed_recyclerview);
//        if (recyclerView != null) {
//            recyclerView.setHasFixedSize(true);
//        } else {
//            Log.e(TagHandler.MAIN_TAG, "No RecyclerView found in activity_main.xml");
//        }
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        postAdapter = new PostAdapter(this);
        Log.d(TagHandler.MAIN_TAG, "Creating postAdapter " + postAdapter);
        viewPager = (ViewPager) findViewById(R.id.post_feed_viewpager);
        addPages(viewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.main_tablayout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
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
//        recyclerView.setAdapter(postAdapter);
    }

    private void addPages(ViewPager viewPager) {
        FeedPagerAdapter feedPagerAdapter = new FeedPagerAdapter(getSupportFragmentManager());
        feedPagerAdapter.addPage(new PostFragment(postAdapter));
        viewPager.setAdapter(feedPagerAdapter);
    }



    @Override
    public void onPostClick(Post post) {
        Snackbar.make(viewPager,
                "ID " + post.getId() + ": " + post.getContent().getText(),
                Snackbar.LENGTH_LONG)
                .show();
    }
}