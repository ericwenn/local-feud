package com.chalmers.tda367.localfeud.control.post;

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
import com.chalmers.tda367.localfeud.data.Post;
import com.chalmers.tda367.localfeud.util.DateString;
import com.chalmers.tda367.localfeud.util.DistanceColor;

import java.util.ArrayList;
import java.util.List;

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

        try {
            adapterCallback = (AdapterCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("PostAdapter: Activity must implement AdapterCallback.");
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.post_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Post post = postList.get(position);

        // Distance
        int distanceColor = DistanceColor.distanceColor(post.getLocation().getDistance());
        int distanceTextColor = DistanceColor.distanceTextColor(distanceColor);
        holder.postItemTopbar.setBackgroundColor(ContextCompat.getColor(context, distanceColor));
        String distance = "" + post.getLocation().getDistance();
        holder.postItemDistanceTextView.setText(distance);
        holder.postItemDistanceTextView.setTextColor(ContextCompat.getColor(context, distanceTextColor));


        holder.postItemTimeTextView.setText(DateString.convert(post.getDatePosted()));
        holder.postItemMsgTextView.setText(post.getContent().getText());

        holder.postItemSenderTextView.setText(post.getUser().getGenderSymbol() + " " + post.getUser().getAge());
        holder.postItemSenderTextView.setTextColor(ContextCompat.getColor(context, distanceTextColor));
        String numberOfComments = "" + post.getNumberOfComments();
        holder.postItemCommentTextView.setText(numberOfComments);
        holder.holderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterCallback.onPostClick(post);
            }
        });
        holder.postItemLikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterCallback.onLikeClick(post, holder.postItemLikeButton);
            }
        });
        holder.postItemMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterCallback.onMoreClick(post);
            }
        });
        if (post.isLiked())
            holder.postItemLikeButton.setImageResource(R.drawable.ic_favorite_black_24dp);
        else holder.postItemLikeButton.setImageResource(R.drawable.ic_favorite_border_black_24dp);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public void addPostToAdapter(Post post) {
        postList.add(post);
        notifyItemChanged(postList.size());
    }

    private void clearAdapter() {
        postList.clear();
        notifyDataSetChanged();
    }

    public void addPostListToAdapter(final List<Post> postList) {
        final int currentCount = this.postList.size();
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



    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView postItemMsgTextView;
        private final TextView postItemSenderTextView;
        private final TextView postItemDistanceTextView;
        private final TextView postItemTimeTextView;
        private final TextView postItemCommentTextView;
        private final CardView holderLayout;
        private final ImageButton postItemLikeButton;
        private final ImageButton postItemMoreButton;
        private final RelativeLayout postItemTopbar;

        public ViewHolder(View itemView) {
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
        void onPostClick(Post post);

        void onLikeClick(Post post, ImageButton imageButton);

        void onMoreClick(Post post);

        void onShowSnackbar(String text);
    }
}
