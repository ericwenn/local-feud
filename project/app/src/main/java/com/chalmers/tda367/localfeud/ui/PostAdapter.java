package com.chalmers.tda367.localfeud.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chalmers.tda367.localfeud.R;

import java.util.ArrayList;

/**
 * Text om klassen
 *
 * @author David Söderberg
 * @since 16-04-11
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private final Context context;
    private final LayoutInflater inflater;

//    TODO: Remove and replace with real data
    private ArrayList<String> dummyPostList = new ArrayList<>();

    public PostAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.post_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String text = dummyPostList.get(position);
        holder.postItemTextView.setText(text);
    }

    @Override
    public int getItemCount() {
        return dummyPostList.size();
    }

//    TODO: Remove and replace with real data
    public void addStringToDummy(String text) {
        dummyPostList.add(text);
        notifyItemChanged(dummyPostList.size());
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView postItemTextView;

        public ViewHolder(View itemView) {
            super(itemView);
//            Init views here
            postItemTextView = (TextView) itemView.findViewById(R.id.post_item_text);
        }
    }
}
