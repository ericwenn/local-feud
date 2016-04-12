package com.chalmers.tda367.localfeud.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chalmers.tda367.localfeud.R;
import com.chalmers.tda367.localfeud.data.Post;

import java.util.ArrayList;
import java.util.Calendar;

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
    private ArrayList<Post> postList = new ArrayList<>();

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
        Post post = postList.get(position);
        holder.postItemMsgTextView.setText(post.getContent().getText());
        holder.postItemDistanceTextView.setText("" + post.getLocation().getDistance());
        holder.postItemTimeTextView.setText(post.getDatePosted().get(Calendar.HOUR_OF_DAY) + ":" +
                post.getDatePosted().get(Calendar.MINUTE));
        holder.postItemSenderTextView.setText("" + post.getUser().getId());
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public void addPostToAdapter(Post post) {
        postList.add(post);
        notifyItemChanged(postList.size());
    }

    public void addPostListToAdapter(ArrayList<Post> postList) {
        postList.addAll(postList);
//        TODO: NOTIFY
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView postItemMsgTextView;
        private final TextView postItemSenderTextView;
        private final TextView postItemDistanceTextView;
        private final TextView postItemTimeTextView;

        public ViewHolder(View itemView) {
            super(itemView);
//            Init views here
            postItemSenderTextView = (TextView) itemView.findViewById(R.id.post_item_sender_textview);
            postItemMsgTextView = (TextView) itemView.findViewById(R.id.post_item_msg_textview);
            postItemDistanceTextView = (TextView) itemView.findViewById(R.id.post_item_distance_textview);
            postItemTimeTextView = (TextView) itemView.findViewById(R.id.post_item_time_textview);
        }
    }
}
