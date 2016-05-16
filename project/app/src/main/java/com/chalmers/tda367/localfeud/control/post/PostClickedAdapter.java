package com.chalmers.tda367.localfeud.control.post;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chalmers.tda367.localfeud.R;
import com.chalmers.tda367.localfeud.data.Comment;
import com.chalmers.tda367.localfeud.data.GeneralPost;
import com.chalmers.tda367.localfeud.data.Post;
import com.chalmers.tda367.localfeud.util.DateString;
import com.chalmers.tda367.localfeud.util.DistanceColor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Ahlqvist on 2016-04-18.
 */
public class PostClickedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final ArrayList<? super GeneralPost> comments = new ArrayList<>();
    private final Context context;
    private final LayoutInflater inflater;
    private AdapterCallback adapterCallback;
    private final Post post;

    public PostClickedAdapter(Context context, Post post) {
        this.context = context;
        this.post = post;
        clearAdapter();
        inflater = LayoutInflater.from(context);

        try {
            adapterCallback = (AdapterCallback) context;
        }
        catch (ClassCastException e) {
            throw new ClassCastException("PostClickedAdapter: Activity must implement AdapterCallback.");
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                return new PostViewHolder(inflater.inflate(R.layout.post_list_item, parent, false));
            case 1:
                return new CommentViewHolder(inflater.inflate(R.layout.comment_list_item, parent, false));
            default:
                throw new IndexOutOfBoundsException("ViewType not found");
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder.getClass() == PostViewHolder.class) {
            final Post post = (Post) comments.get(position);
            final PostViewHolder viewHolder = (PostViewHolder) holder;
            int distanceColor = DistanceColor.distanceColor(post.getDistance());
            int distanceTextColor = DistanceColor.distanceTextColor(distanceColor);
            viewHolder.postItemMsgTextView.setText(post.getContent().getText());
            viewHolder.postItemTopbar.setBackgroundColor(ContextCompat.getColor(context, distanceColor));
            viewHolder.postItemDistanceTextView.setText("" + post.getDistance());
            viewHolder.postItemDistanceTextView.setTextColor(ContextCompat.getColor(context, distanceTextColor));
            viewHolder.postItemTimeTextView.setText(DateString.convert(post.getDatePosted()));


            viewHolder.postItemSenderTextView.setText(post.getUser().getGenderSymbol() + " " + post.getUser().getAge());
            viewHolder.postItemSenderTextView.setTextColor(ContextCompat.getColor(context, distanceTextColor));
            viewHolder.postItemCommentTextView.setText("" + post.getNumberOfComments());
            viewHolder.postItemLikeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapterCallback.onLikeClick(post, viewHolder.postItemLikeButton);
                }
            });
            viewHolder.postItemMoreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapterCallback.onMoreClick(viewHolder.postItemMoreButton);
                }
            });
            if (post.isLiked())
                viewHolder.postItemLikeButton.setImageResource(R.drawable.ic_favorite_black_24dp);
            else viewHolder.postItemLikeButton.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }
        else if (holder.getClass() == CommentViewHolder.class) {
            final Comment comment = (Comment) comments.get(position);
            final CommentViewHolder viewHolder = (CommentViewHolder) holder;
            viewHolder.commentItemMsgTextView.setText(comment.getText());
            viewHolder.commentItemTimeTextView.setText(DateString.convert(comment.getDatePosted()));
            viewHolder.commentItemSenderTextView.setText(comment.getUser().getGenderSymbol() + " " + comment.getUser().getAge());
            viewHolder.commentItemMoreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapterCallback.onCommentMoreClick(comment, viewHolder.commentItemMoreButton);
                }
            });
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (comments.get(position).getClass() == Post.class) {
            return 0;
        }
        else if (comments.get(position).getClass() == Comment.class) {
            return 1;
        }
        return -1;
    }

    public void addCommentToAdapter(Comment comment) {
        comments.add(comment);
        notifyItemChanged(comments.size());
    }

    private void clearAdapter() {
        comments.clear();
        comments.add(post);
        notifyDataSetChanged();
    }

    public void addCommentListToAdapter(final List<Comment> comments) {
        final int currentCount = this.comments.size();
        synchronized (this.comments) {
            clearAdapter();
            this.comments.addAll(comments);
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
        private final ImageButton commentItemMoreButton;

        public CommentViewHolder(View itemView)
        {
            super(itemView);
            commentItemSenderTextView = (TextView) itemView.findViewById(R.id.comment_item_sender_textview);
            commentItemMsgTextView = (TextView) itemView.findViewById(R.id.comment_item_msg_textview);
            commentItemTimeTextView = (TextView) itemView.findViewById(R.id.comment_item_time_textview);
            commentItemMoreButton = (ImageButton) itemView.findViewById(R.id.comment_item_more_button);
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

    public interface AdapterCallback {
        void onLikeClick(Post post, ImageButton imageButton);

        void onMoreClick(ImageButton imageButton);

        void onCommentMoreClick(Comment comment, ImageButton imageButton);

        void onShowSnackbar(String text);
    }
}