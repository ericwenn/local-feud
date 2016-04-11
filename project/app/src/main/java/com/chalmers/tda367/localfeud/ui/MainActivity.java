package com.chalmers.tda367.localfeud.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.chalmers.tda367.localfeud.R;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    PostAdapter postAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.post_feed_recyclerview);
        recyclerView.setHasFixedSize(true);
        postAdapter = new PostAdapter(this);
        recyclerView.setAdapter(postAdapter);
    }
}
