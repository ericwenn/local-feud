package com.chalmers.tda367.localfeud.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chalmers.tda367.localfeud.R;
import com.chalmers.tda367.localfeud.util.TagHandler;

/**
 * Text om klassen
 *
 * @author David Söderberg
 * @since 16-04-18
 */
public class PostFragment extends Fragment {

    private PostAdapter postAdapter;

    public PostFragment(PostAdapter postAdapter) {
        this.postAdapter = postAdapter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TagHandler.MAIN_TAG, "Created PostFragment");
        View view = inflater.inflate(R.layout.post_feed_fragment, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.post_feed_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(postAdapter);
        return view;
    }

    @Override
    public String toString() {
        return "Post Feed";
    }
}
