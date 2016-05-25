package com.chalmers.tda367.localfeud.control.post;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chalmers.tda367.localfeud.R;
import com.chalmers.tda367.localfeud.data.Post;
import com.chalmers.tda367.localfeud.util.DateString;
import com.chalmers.tda367.localfeud.util.DistanceColor;
import com.chalmers.tda367.localfeud.util.DistanceString;
import com.chalmers.tda367.localfeud.util.TagHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Text om klassen
 *
 * @author David Söderberg
 * @since 16-04-11
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private final Context context;
    private final Comparator<Post> comparator;
    private final LayoutInflater inflater;
    private final String transitionName;
    private AdapterCallback adapterCallback;

    private final ArrayList<Post> postList = new ArrayList<>();

    /**
     * Constructor.
     *
     * @param context the current state of the object it is called from.
     * @param comparator used to compare different posts with each other.
     */
    public PostAdapter(Context context, Comparator<Post> comparator, String transitionName) {
        this.context = context;
        this.comparator = comparator;
        this.transitionName = transitionName;
        inflater = LayoutInflater.from(context);

        try {
            adapterCallback = (AdapterCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("PostAdapter: Activity must implement AdapterCallback.");
        }
    }

    /**
     * Creates a view holder for the post. The holder is connected to the corresponding
     * layout XML file.
     *
     * @param parent the view group in which the adapter will be placed
     * @param viewType what kind of object the adapter will show. (Not used, since the adapter
     *                 is only used for posts)
     * @return view holder for a post, which will be used in a recycler view.
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.post_list_item, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Binds the data of a post object to a view holder.
     *
     * @param holder the basic view holder in which the data will be placed.
     * @param position the position of the post in the posts list.
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Post post = postList.get(position);
        // Distance
        int distanceColor = DistanceColor.distanceColor(post.getDistance());
        int distanceTextColor = DistanceColor.distanceTextColor(distanceColor);
        holder.postItemTopbar.setBackgroundColor(ContextCompat.getColor(context, distanceColor));
        String distance = DistanceString.getDistanceString(context, post.getDistance());
        holder.postItemDistanceTextView.setText(distance);
        holder.postItemDistanceTextView.setTextColor(ContextCompat.getColor(context, distanceTextColor));
        holder.holderLayout.setTransitionName(context.getString(R.string.post_transition_start) + transitionName + "_" + position);


        holder.postItemTimeTextView.setText(DateString.convert(post.getDatePosted()));
        holder.postItemMsgTextView.setText(post.getContent().getText());

        holder.postItemSenderTextView.setText(post.getUser().getGenderSymbol() + " " + post.getUser().getAge());
        holder.postItemSenderTextView.setTextColor(ContextCompat.getColor(context, distanceTextColor));
        String numberOfComments = "" + post.getNumberOfComments();
        holder.postItemCommentTextView.setText(numberOfComments);
        holder.holderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterCallback.onPostClick(post, holder.holderLayout);
            }
        });
        holder.postItemLikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterCallback.onLikeClick(post, holder.postItemLikeButton, holder.postItemNbrLikesTextView);
            }
        });
        holder.postItemMoreButton.setVisibility(View.GONE);
        holder.postItemMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterCallback.onMoreClick(post);
            }
        });
        if (post.isLiked())
            holder.postItemLikeButton.setImageResource(R.drawable.ic_favorite_black_24dp);
        else holder.postItemLikeButton.setImageResource(R.drawable.ic_favorite_border_black_24dp);

        holder.postItemNbrLikesTextView.setText("" + post.getNumberOfLikes());
    }

    /**
     * Counts the number of objects in the posts list.
     *
     * @return the number of objects in the posts list.
     */
    @Override
    public int getItemCount() {
        return postList.size();
    }

    /**
     * Adds a new post to the post list and uses the comparator to sort the list.
     *
     * @param post the post which will be added to the posts list.
     */
    public void addPostToAdapter(Post post) {
        postList.add(post);
        Collections.sort(postList, comparator);
        notifyItemInserted(postList.indexOf(post));
    }

    /**
     * Replaces the post in the adapter with another post. Used to update the data in the
     * post view holder, when a change is made to the post.
     *
     * @param oldPost the current post which will be replaced
     * @param newPost the (updated) post which will replace the current post in the adapter.
     */
    public void changePostInAdapter(Post oldPost, Post newPost) {
        if (postList.contains(oldPost)) {
            postList.set(postList.indexOf(oldPost), newPost);
            notifyItemChanged(postList.indexOf(newPost));
        }
        else {
            Log.e(TagHandler.MAIN_TAG, "PostAdapter doesn't contain post " + oldPost.getId());
        }
    }

    /**
     * Removes everything from the recycler view.
     */
    private void clearAdapter() {
        postList.clear();
        notifyDataSetChanged();
    }

    /**
     * Used to add a number of a post to the posts list at the same time.
     *
     * @param postList the list of all post to be added
     */
    public void addPostListToAdapter(final List<Post> postList) {
        final int currentCount = this.postList.size();

        Collections.sort(postList, comparator);

        synchronized (this.postList) {
            clearAdapter();
            this.postList.addAll(postList);
        }
        if (Looper.getMainLooper() == Looper.myLooper()) {
            notifyItemRangeInserted(currentCount, postList.size());
        } else {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    notifyItemRangeInserted(currentCount, postList.size());
                }
            });
        }
    }


    /**
     * A class which is used to model a view holder for a post. The variables are connected
     * to the id values from the corresponding layout XML file.
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView postItemMsgTextView;
        private final TextView postItemSenderTextView;
        private final TextView postItemDistanceTextView;
        private final TextView postItemTimeTextView;
        private final TextView postItemCommentTextView;
        private final TextView postItemNbrLikesTextView;
        private final CardView holderLayout;
        private final ImageButton postItemLikeButton;
        private final ImageButton postItemMoreButton;
        private final FrameLayout postItemTopbar;

        /**
         * Constructor.
         *
         * @param itemView the post view which has been created using the layout XML file.
         */
        public ViewHolder(View itemView) {
            super(itemView);

            postItemSenderTextView = (TextView) itemView.findViewById(R.id.post_item_sender_textview);
            postItemMsgTextView = (TextView) itemView.findViewById(R.id.post_item_msg_textview);
            postItemDistanceTextView = (TextView) itemView.findViewById(R.id.post_item_distance_textview);
            postItemTimeTextView = (TextView) itemView.findViewById(R.id.post_item_time_textview);
            postItemCommentTextView = (TextView) itemView.findViewById(R.id.post_item_comment_textview);
            holderLayout = (CardView) itemView.findViewById(R.id.post_list_item);
            postItemLikeButton = (ImageButton) itemView.findViewById(R.id.post_item_like_button);
            postItemNbrLikesTextView = (TextView) itemView.findViewById(R.id.post_item_nbr_of_likes);
            postItemMoreButton = (ImageButton) itemView.findViewById(R.id.post_item_more_button);
            postItemTopbar = (FrameLayout) itemView.findViewById(R.id.post_item_topbar);
        }
    }

    public interface AdapterCallback {

        /**
         * Determines what will happen when a post is clicked
         *
         * @param post the post that has been clicked.
         * @param view a CardView that will be used for transition
         */
        void onPostClick(Post post, CardView view);

        /**
         * Determines what will happen when the like button is pressed.
         *
         * @param post the post that has been liked.
         * @param imageButton the like button which has been pressed.
         * @param displayLikes the text view which displays the number of likes.
         */
        void onLikeClick(Post post, ImageButton imageButton, final TextView displayLikes);

        /**
         * Determines what will happen when the more button of a post is pressed.
         *
         * @param post the post the more button belongs to.
         */
        void onMoreClick(Post post);

        /**
         * Displays a snackbar with a message.
         *
         * @param text the text that will be displayed in the snackbar.
         */
        void onShowSnackbar(String text);
    }
}
