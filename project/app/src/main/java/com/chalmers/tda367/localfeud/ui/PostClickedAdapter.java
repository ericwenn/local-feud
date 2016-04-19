package com.chalmers.tda367.localfeud.ui;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chalmers.tda367.localfeud.R;
import com.chalmers.tda367.localfeud.data.Comment;
import com.chalmers.tda367.localfeud.data.Post;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Daniel Ahlqvist on 2016-04-18.
 */
public class PostClickedAdapter extends RecyclerView.Adapter<PostClickedAdapter.ViewHolder>
{
    private ArrayList<Comment> comments = new ArrayList<>();
    private final Context context;
    private final LayoutInflater inflater;
    private AdapterCallback adapterCallback;

    public PostClickedAdapter(Context context)
    {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        adapterCallback = (AdapterCallback) context;

        View view = inflater.inflate(R.layout.comment_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        final Comment comment = comments.get(position);
        holder.commentItemMsgTextView.setText(comment.getText());
        holder.commentItemTimeTextView.setText(comment.getDatePosted().get(Calendar.HOUR_OF_DAY) + ":" +
                comment.getDatePosted().get(Calendar.MINUTE));
        holder.commentItemSenderTextView.setText("" + comment.getAuthor());
    }

    public void addCommentToAdapter(Comment comment)
    {
        comments.add(comment);
        notifyItemChanged(comments.size());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        private final TextView commentItemMsgTextView;
        private final TextView commentItemSenderTextView;
        private final TextView commentItemTimeTextView;
        private final CardView holderLayout;

        public ViewHolder(View itemView)
        {
            super(itemView);
            commentItemSenderTextView = (TextView) itemView.findViewById(R.id.comment_item_sender_textview);
            commentItemMsgTextView = (TextView) itemView.findViewById(R.id.comment_item_msg_textview);
            commentItemTimeTextView = (TextView) itemView.findViewById(R.id.comment_item_time_textview);
            holderLayout = (CardView) itemView.findViewById(R.id.comment_list_item);
        }
    }

    public interface AdapterCallback
    {
        void onPostClick(Post post);
    }
}