package com.chalmers.tda367.localfeud.control;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chalmers.tda367.localfeud.R;
import com.chalmers.tda367.localfeud.data.Comment;
import com.chalmers.tda367.localfeud.util.TagHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Daniel Ahlqvist on 2016-04-18.
 */
public class PostClickedAdapter extends RecyclerView.Adapter<PostClickedAdapter.CommentViewHolder> {
    private ArrayList<Comment> comments = new ArrayList<>();
    private final Context context;
    private final LayoutInflater inflater;

    public PostClickedAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TagHandler.MAIN_TAG, "ViewType: " + viewType);
//        Behöver välja om post_list_item eller comment_list_item ska väljas, beroende på viewType
        View view = inflater.inflate(R.layout.comment_list_item, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        final Comment comment = comments.get(position);
        holder.commentItemMsgTextView.setText(comment.getText());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        holder.commentItemTimeTextView.setText(simpleDateFormat.format(comment.getDatePosted().getTime()));
        holder.commentItemSenderTextView.setText("" + comment.getUser().getId());
        Log.d(TagHandler.MAIN_TAG, "Comment: " + comment.getText());
    }

    public void addCommentToAdapter(Comment comment) {
        comments.add(comment);
        notifyItemChanged(comments.size());
    }

    private void clearAdapter() {
        comments.clear();
        notifyDataSetChanged();
    }

    public void addCommentListToAdapter(final List<Comment> comments) {
        final int currentCount = this.comments.size();
        synchronized (this.comments) {
            Log.d(TagHandler.MAIN_TAG, "Uppdaterar inlägg...");
            if (this.comments.containsAll(comments)) {
                Log.d(TagHandler.MAIN_TAG, "Inga kommentarer");
            } else {
                clearAdapter();
                this.comments.addAll(comments);
            }
        }
        if (Looper.getMainLooper() == Looper.myLooper()) {
            notifyItemRangeInserted(currentCount, comments.size());
        } else {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    notifyItemRangeInserted(currentCount, comments.size());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    class CommentViewHolder extends RecyclerView.ViewHolder {
        private final TextView commentItemMsgTextView;
        private final TextView commentItemSenderTextView;
        private final TextView commentItemTimeTextView;
        private final CardView holderLayout;

        public CommentViewHolder(View itemView) {
            super(itemView);
            commentItemSenderTextView = (TextView) itemView.findViewById(R.id.comment_item_sender_textview);
            commentItemMsgTextView = (TextView) itemView.findViewById(R.id.comment_item_msg_textview);
            commentItemTimeTextView = (TextView) itemView.findViewById(R.id.comment_item_time_textview);
            holderLayout = (CardView) itemView.findViewById(R.id.comment_list_item);
        }
    }

    class PostViewHolder extends RecyclerView.ViewHolder {

//        ViewHolder för översta inlägget
        private final TextView postItemMsgTextView;
        private final TextView postItemSenderTextView;
        private final TextView postItemDistanceTextView;
        private final TextView postItemTimeTextView;
        private final TextView postItemCommentTextView;
        private final CardView holderLayout;
        private final ImageButton postItemLikeButton;
        private final ImageButton postItemMoreButton;
        private final RelativeLayout postItemTopbar;

        public PostViewHolder(View itemView) {
            super(itemView);

            postItemSenderTextView = (TextView) itemView.findViewById(R.id.post_item_sender_textview);
            postItemMsgTextView = (TextView) itemView.findViewById(R.id.post_item_msg_textview);
            postItemDistanceTextView = (TextView) itemView.findViewById(R.id.post_item_distance_textview);
            postItemTimeTextView = (TextView) itemView.findViewById(R.id.post_item_time_textview);
            postItemCommentTextView = (TextView) itemView.findViewById(R.id.post_item_comment_textview);
            holderLayout = (CardView) itemView.findViewById(R.id.post_list_item);
            postItemLikeButton = (ImageButton) itemView.findViewById(R.id.post_item_like_button);
            postItemMoreButton = (ImageButton) itemView.findViewById(R.id.post_item_more_button);
            postItemTopbar = (RelativeLayout) itemView.findViewById(R.id.post_item_topbar);
        }
    }
}