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
    private String dummyJsonPost = "// 20160412174501\n" +
            "// http://api-local.ericwenn.se/posts/\n" +
            "\n" +
            "[\n" +
            "  {\n" +
            "    \"id\": 1,\n" +
            "    \"location\": {\n" +
            "      \"distance\": 5\n" +
            "    },\n" +
            "    \"user\": {\n" +
            "      \"id\": 1,\n" +
            "      \"firstname\": \"Karl\",\n" +
            "      \"lastname\": \"Karlsson\",\n" +
            "      \"href\": \"http://localhost/local-feud_backend/src/users/1/\"\n" +
            "    },\n" +
            "    \"reach\": 5,\n" +
            "    \"content\": {\n" +
            "      \"type\": \"text\",\n" +
            "      \"text\": \"Lorem ipsum dolorem.\"\n" +
            "    },\n" +
            "    \"number_of_comments\": 10,\n" +
            "    \"number_of_likes\": 20,\n" +
            "    \"date_posted\": \"2016-04-12T17:45:01+02:00\",\n" +
            "    \"href\": \"http://localhost/local-feud_backend/src/posts/1/\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 2,\n" +
            "    \"location\": {\n" +
            "      \"distance\": 7\n" +
            "    },\n" +
            "    \"user\": {\n" +
            "      \"id\": 2,\n" +
            "      \"firstname\": \"Krune\",\n" +
            "      \"lastname\": \"Nilsson\",\n" +
            "      \"href\": \"http://localhost/local-feud_backend/src/users/2/\"\n" +
            "    },\n" +
            "    \"reach\": 5,\n" +
            "    \"content\": {\n" +
            "      \"type\": \"text\",\n" +
            "      \"text\": \"Lorem ipsum dolorem.\"\n" +
            "    },\n" +
            "    \"number_of_comments\": 10,\n" +
            "    \"number_of_likes\": 20,\n" +
            "    \"date_posted\": \"2016-04-12T17:45:01+02:00\",\n" +
            "    \"href\": \"http://localhost/local-feud_backend/src/posts/2/\"\n" +
            "  }\n" +
            "]";
    private ArrayList<Post> dummyPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dummyPost = GsonHandler.getInstance().toPostList(dummyJsonPost);
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
        for (Post post : dummyPost) {
            postAdapter.addStringToDummy(post.getContent().getText());
        }
    }
}
