package com.chalmers.tda367.localfeud.ui;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.CardView;
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
    private AdapterCallback adapterCallback;

    private final ArrayList<Post> postList = new ArrayList<>();

    public PostAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        try {
            adapterCallback = (AdapterCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("PostAdapter: Activity must implement AdapterCallback.");
        }

        View view = inflater.inflate(R.layout.post_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Post post = postList.get(position);
        holder.postItemMsgTextView.setText(post.getContent().getText());
        holder.postItemDistanceTextView.setText("" + post.getLocation().getDistance());
        holder.postItemTimeTextView.setText(post.getDatePosted().get(Calendar.HOUR_OF_DAY) + ":" +
                post.getDatePosted().get(Calendar.MINUTE));
        holder.postItemSenderTextView.setText("" + post.getUser().getId());
        holder.postItemCommentTextView.setText("" + post.getNumberOfComments());
        holder.holderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterCallback.onPostClick(post);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public void addPostToAdapter(Post post) {
        postList.add(post);
        notifyItemChanged(postList.size());
    }

    public void addPostListToAdapter(final ArrayList<Post> postList) {
        final int currentCount = this.postList.size();
        synchronized (this.postList) {
            this.postList.addAll(postList);
        }
        if (Looper.getMainLooper() == Looper.myLooper()) {
            notifyItemRangeInserted(currentCount, postList.size());
        }
        else {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    notifyItemRangeInserted(currentCount, postList.size());
                }
            });
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView postItemMsgTextView;
        private final TextView postItemSenderTextView;
        private final TextView postItemDistanceTextView;
        private final TextView postItemTimeTextView;
        private final TextView postItemCommentTextView;
        private final CardView holderLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            postItemSenderTextView = (TextView) itemView.findViewById(R.id.post_item_sender_textview);
            postItemMsgTextView = (TextView) itemView.findViewById(R.id.post_item_msg_textview);
            postItemDistanceTextView = (TextView) itemView.findViewById(R.id.post_item_distance_textview);
            postItemTimeTextView = (TextView) itemView.findViewById(R.id.post_item_time_textview);
            postItemCommentTextView = (TextView) itemView.findViewById(R.id.post_item_comment_textview);
            holderLayout = (CardView) itemView.findViewById(R.id.post_list_item);
        }
    }

    public interface AdapterCallback {
        void onPostClick(Post post);
    }
}
