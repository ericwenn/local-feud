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
import android.widget.TextView;

import com.chalmers.tda367.localfeud.R;
import com.chalmers.tda367.localfeud.data.Comment;
import com.chalmers.tda367.localfeud.data.Post;
import com.chalmers.tda367.localfeud.util.TagHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Daniel Ahlqvist on 2016-04-18.
 */
public class PostClickedAdapter extends RecyclerView.Adapter<PostClickedAdapter.ViewHolder>
{
    private ArrayList<Comment> comments = new ArrayList<>();
    private final Context context;
    private final LayoutInflater inflater;

    public PostClickedAdapter(Context context)
    {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = inflater.inflate(R.layout.comment_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        final Comment comment = comments.get(position);
        holder.commentItemMsgTextView.setText(comment.getText());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        holder.commentItemTimeTextView.setText(simpleDateFormat.format(comment.getDatePosted().getTime()));
        holder.commentItemSenderTextView.setText("" + comment.getUser().getId());
        Log.d(TagHandler.MAIN_TAG, "Comment: " + comment.getText());
    }

    public void addCommentToAdapter(Comment comment)
    {
        comments.add(comment);
        notifyItemChanged(comments.size());
    }

    private void clearAdapter() {
        comments.clear();
        notifyDataSetChanged();
    }

    public void addCommentListToAdapter(final List<Comment> comments)
    {
        final int currentCount = this.comments.size();
        synchronized (this.comments)
        {
            Log.d(TagHandler.MAIN_TAG, "Uppdaterar inlägg...");
            if (this.comments.containsAll(comments))
            {
                Log.d(TagHandler.MAIN_TAG, "Inga kommentarer");
            }
            else
            {
                clearAdapter();
                this.comments.addAll(comments);
            }
        }
        if (Looper.getMainLooper() == Looper.myLooper()) {
            notifyItemRangeInserted(currentCount, comments.size());
        }
        else {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    notifyItemRangeInserted(currentCount, comments.size());
                }
            });
        }
    }

    @Override
    public int getItemCount()
    {
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
}