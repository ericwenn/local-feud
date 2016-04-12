package com.chalmers.tda367.localfeud.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.chalmers.tda367.localfeud.R;
import com.chalmers.tda367.localfeud.data.Post;
import com.chalmers.tda367.localfeud.util.TagHandler;
import com.google.gson.Gson;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private Gson gson;
    private String dummyJsonPost = "{\n" +
            "  \"id\": \"1\",\n" +
            "  \"location\": {\n" +
            "    \"latitude\": 32.1231,\n" +
            "    \"longitude\": 13.123123,\n" +
            "    \"distance\": 7\n" +
            "  },\n" +
            "  \"user\": {\n" +
            "    \"id\": 2,\n" +
            "    \"firstname\": \"Krune\",\n" +
            "    \"lastname\": \"Nilsson\",\n" +
            "    \"href\": \"http://localhost/local-feud_backend/src/users/2/\"\n" +
            "  },\n" +
            "  \"reach\": 5,\n" +
            "  \"content\": {\n" +
            "    \"type\": \"text\",\n" +
            "    \"text\": \"Lorem ipsum dolorem.\"\n" +
            "  },\n" +
            "  \"date_posted\": \"2016-04-12T16:11:43+02:00\",\n" +
            "  \"is_deleted\": false,\n" +
            "  \"number_of_comments\": 5,\n" +
            "  \"number_of_likes\": 10\n" +
            "}";
    private Post dummyPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        gson = new Gson();
        dummyPost = gson.fromJson(dummyJsonPost, Post.class);
        Log.d(TagHandler.MAIN_TAG, "Text: " +  dummyPost.getDatePosted().get(Calendar.DAY_OF_MONTH));
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.post_feed_recyclerview);
        if (recyclerView != null) {
            recyclerView.setHasFixedSize(true);
        }
        else {
            Log.e(TagHandler.MAIN_TAG, "No RecyclerView found in activity_main.xml");
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        postAdapter = new PostAdapter(this);
        recyclerView.setAdapter(postAdapter);
        for (int i = 0; i < 40; i ++) {
            postAdapter.addStringToDummy("Post " + i);
        }
    }
}
