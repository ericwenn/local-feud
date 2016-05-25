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
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chalmers.tda367.localfeud.R;
import com.chalmers.tda367.localfeud.data.Comment;
import com.chalmers.tda367.localfeud.data.GeneralPost;
import com.chalmers.tda367.localfeud.data.Post;
import com.chalmers.tda367.localfeud.util.DateString;
import com.chalmers.tda367.localfeud.util.DistanceColor;
import com.chalmers.tda367.localfeud.util.DistanceString;

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
    private Post post;

    /**
     * Constructor.
     *
     * @param context the current state of the object it is called from.
     * @param post the post from which the adapter will show data
     */
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

    /**
     * Determines if the adapter will be used for a post or for a comment.
     *
     * @param parent the view group in which the adapter will be placed
     * @param viewType what kind of object the adapter will show.
     *                 0 represents a post and 1 represents a comment.
     * @return view holder for post or comment, which will be used in a recycler view.
     */
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

    /**
     * Binds the data of a comment or post object to a view holder.
     *
     * @param holder the basic view holder in which the data will be placed.
     *               There are two different types: one for posts and one for comments.
     * @param position the position of the post or comment in the comments list.
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder.getClass() == PostViewHolder.class) {
            final Post post = (Post) comments.get(position);
            final PostViewHolder viewHolder = (PostViewHolder) holder;
            int distanceColor = DistanceColor.distanceColor(post.getDistance());
            int distanceTextColor = DistanceColor.distanceTextColor(distanceColor);
            viewHolder.holderLayout.setTransitionName(context.getString(R.string.post_transition_target));
            viewHolder.postItemMsgTextView.setText(post.getContent().getText());
            viewHolder.postItemTopbar.setBackgroundColor(ContextCompat.getColor(context, distanceColor));
            viewHolder.postItemDistanceTextView.setText(DistanceString.getDistanceString(context, post.getDistance()));
            viewHolder.postItemDistanceTextView.setTextColor(ContextCompat.getColor(context, distanceTextColor));
            viewHolder.postItemTimeTextView.setText(DateString.convert(post.getDatePosted()));
            viewHolder.postItemNbrOfLikes.setText(post.getNumberOfLikes() + "");

            viewHolder.postItemSenderTextView.setText(post.getUser().getGenderSymbol() + " " + post.getUser().getAge());
            viewHolder.postItemSenderTextView.setTextColor(ContextCompat.getColor(context, distanceTextColor));
            viewHolder.postItemCommentTextView.setText("" + post.getNumberOfComments());
            viewHolder.postItemLikeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapterCallback.onLikeClick(post, viewHolder.postItemLikeButton, viewHolder.postItemNbrOfLikes);
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
            viewHolder.commentItemSenderTextView.setText(comment.getUser().getGenderSymbol() + " " + comment.getUser().getAge() + " " + comment.getUser().getFirstname() + " " + comment.getUser().getLastname());
            if(comment.getUser().getId() == post.getUser().getId())
            {
                viewHolder.commentItemSenderTextView.append(" (TC)");
            }
            viewHolder.commentItemMoreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapterCallback.onCommentMoreClick(comment, viewHolder.commentItemMoreButton);
                }
            });
        }
    }


    /**
     * Determines it the object at a given position in the comments list is an instance of
     * post or comment.
     *
     * @param position the position in the list that will be checked.
     * @return an integer value. 0 represents a post and 1 represents a comment.
     */
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

    /**
     * Replaces the post in the adapter with another post. Used to update the data in the
     * post view holder, when a change is made in the comments list.
     *
     * @param newPost the (updated) post which will replace the current post of the adapter.
     */
    public void changePostInAdapter(Post newPost) {
        post = newPost;
        comments.set(0, newPost);
        notifyItemChanged(0);
    }

    /**
     * Adds a new comment to the comments list
     *
     * @param comment the comment which will be added to the comments list.
     */
    public void addCommentToAdapter(Comment comment) {
        comments.add(comment);
        notifyItemChanged(comments.size());
    }

    /**
     * Removes everything from the recycler view and adds the current post.
     */
    private void clearAdapter() {
        comments.clear();
        comments.add(post);
        notifyDataSetChanged();
    }

    /**
     * Used to add all comments of a post to the comments list at the same time.
     *
     * @param comments the list of all comments on a post
     */
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


    /**
     * Counts the number of objects in the comments list.
     *
     * @return the number of objects in the comments list.
     */
    @Override
    public int getItemCount() {
        return comments.size();
    }

    /**
     * A class which is used to model a view holder for a comment. The variables are connected
     * to the id values from the corresponding layout XML file.
     */
    class CommentViewHolder extends RecyclerView.ViewHolder {
        private final TextView commentItemMsgTextView;
        private final TextView commentItemSenderTextView;
        private final TextView commentItemTimeTextView;
        private final CardView holderLayout;
        private final ImageButton commentItemMoreButton;

        /**
         * Constructor.
         *
         * @param itemView the comment view which has been created using the layout XML file.
         */
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

    /**
     * A class which is used to model a view holder for a post. The variables are connected
     * to the id values from the corresponding layout XML file.
     */
    class PostViewHolder extends RecyclerView.ViewHolder {
        private final TextView postItemMsgTextView;
        private final TextView postItemSenderTextView;
        private final TextView postItemDistanceTextView;
        private final TextView postItemTimeTextView;
        private final TextView postItemCommentTextView;
        private final TextView postItemNbrOfLikes;
        private final CardView holderLayout;
        private final ImageButton postItemLikeButton;
        private final ImageButton postItemMoreButton;
        private final FrameLayout postItemTopbar;

        /**
         * Constructor.
         *
         * @param itemView the post view which has been created using the layout XML file.
         */
        public PostViewHolder(View itemView) {
            super(itemView);

            postItemSenderTextView = (TextView) itemView.findViewById(R.id.post_item_sender_textview);
            postItemMsgTextView = (TextView) itemView.findViewById(R.id.post_item_msg_textview);
            postItemDistanceTextView = (TextView) itemView.findViewById(R.id.post_item_distance_textview);
            postItemTimeTextView = (TextView) itemView.findViewById(R.id.post_item_time_textview);
            postItemCommentTextView = (TextView) itemView.findViewById(R.id.post_item_comment_textview);
            postItemNbrOfLikes = (TextView) itemView.findViewById(R.id.post_item_nbr_of_likes);
            holderLayout = (CardView) itemView.findViewById(R.id.post_list_item);
            postItemLikeButton = (ImageButton) itemView.findViewById(R.id.post_item_like_button);
            postItemMoreButton = (ImageButton) itemView.findViewById(R.id.post_item_more_button);
            postItemTopbar = (FrameLayout) itemView.findViewById(R.id.post_item_topbar);
        }
    }

    public interface AdapterCallback {
        /**
         * Determines what will happen when the like button is pressed.
         *
         * @param post the post that has been liked.
         * @param imageButton the like button which has been pressed.
         * @param displayLikes the text view which displays the number of likes.
         */
        void onLikeClick(Post post, ImageButton imageButton, TextView displayLikes);

        /**
         * Determines what will happen when the more button of a post is pressed.
         *
         * @param imageButton the more button which has been pressed.
         */
        void onMoreClick(ImageButton imageButton);

        /**
         * Determines what will happen when the more button of a comment is pressed.
         *
         * @param comment the comment the more button belongs to
         * @param imageButton the more button which has been pressed.
         */
        void onCommentMoreClick(Comment comment, ImageButton imageButton);

        /**
         * Displays a snackbar with a message.
         *
         * @param text the text that will be displayed in the snackbar.
         */
        void onShowSnackbar(String text);
    }
}