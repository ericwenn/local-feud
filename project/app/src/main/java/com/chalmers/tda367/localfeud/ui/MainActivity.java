package com.chalmers.tda367.localfeud.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.chalmers.tda367.localfeud.R;
import com.chalmers.tda367.localfeud.data.Post;
import com.chalmers.tda367.localfeud.util.GsonHandler;
import com.chalmers.tda367.localfeud.util.TagHandler;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PostAdapter postAdapter;

    private ArrayList<Post> dummyPostList;
    private Post dummyPost;
    private String dummyJsonPost = "// 20160413151533\n" +
            "// http://api-local.ericwenn.se/posts/1/\n" +
            "\n" +
            "{\n" +
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
            "  \"date_posted\": \"2016-04-13T15:15:33+02:00\",\n" +
            "  \"is_deleted\": false,\n" +
            "  \"number_of_comments\": 5,\n" +
            "  \"number_of_likes\": 10\n" +
            "}";
    private String dummyJsonPostList = "// 20160413150946\n" +
            "// http://api-local.ericwenn.se/posts/\n" +
            "\n" +
            "[\n" +
            "  {\n" +
            "    \"id\": \"1\",\n" +
            "    \"reach\": \"500\",\n" +
            "    \"date_posted\": \"2016-04-12T13:37:18+02:00\",\n" +
            "    \"location\": {\n" +
            "      \"distance\": 275780.74151391\n" +
            "    },\n" +
            "    \"user\": {\n" +
            "      \"id\": \"1\",\n" +
            "      \"href\": \"/users/1/\"\n" +
            "    },\n" +
            "    \"number_of_comments\": 4,\n" +
            "    \"number_of_likes\": 10,\n" +
            "    \"content\": {\n" +
            "      \"type\": \"text\",\n" +
            "      \"text\": \"Lorem ipsum\"\n" +
            "    },\n" +
            "    \"href\": \"/posts/1/\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"2\",\n" +
            "    \"reach\": \"1000\",\n" +
            "    \"date_posted\": \"2016-04-12T13:42:29+02:00\",\n" +
            "    \"location\": {\n" +
            "      \"distance\": 276875.61419316\n" +
            "    },\n" +
            "    \"user\": {\n" +
            "      \"id\": \"1\",\n" +
            "      \"href\": \"/users/1/\"\n" +
            "    },\n" +
            "    \"number_of_comments\": 4,\n" +
            "    \"number_of_likes\": 10,\n" +
            "    \"content\": {\n" +
            "      \"type\": \"text\",\n" +
            "      \"text\": \"Lorem ipsum\"\n" +
            "    },\n" +
            "    \"href\": \"/posts/2/\"\n" +
            "  }\n" +
            "]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        long currentTime = System.currentTimeMillis();
        dummyPost = GsonHandler.getInstance().toPost(dummyJsonPost);
        Log.d(TagHandler.MAIN_TAG, "Created post object in " + (System.currentTimeMillis() - currentTime) + " ms");
        currentTime = System.currentTimeMillis();
        dummyPostList = GsonHandler.getInstance().toPostList(dummyJsonPostList);
        Log.d(TagHandler.MAIN_TAG, "Created post object list in " + (System.currentTimeMillis() - currentTime) + " ms");
        Log.d(TagHandler.MAIN_TAG, dummyPostList.get(0).getHref());
        initViews();
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
        for (Post post : dummyPostList) {
            postAdapter.addPostToAdapter(post);
        }
    }
}
