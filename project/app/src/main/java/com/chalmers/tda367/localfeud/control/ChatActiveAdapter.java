package com.chalmers.tda367.localfeud.control;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.chalmers.tda367.localfeud.R;
import com.chalmers.tda367.localfeud.data.Chat;
import com.chalmers.tda367.localfeud.data.ChatMessage;
import com.chalmers.tda367.localfeud.data.GeneralPost;

import java.util.ArrayList;

/**
 * Created by Daniel Ahlqvist on 2016-05-08.
 */
public class ChatActiveAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private final ArrayList<ChatMessage> messages = new ArrayList<>();
    private final Context context;
    private final LayoutInflater inflater;
    private Chat chat;

    public ChatActiveAdapter(Context context, Chat chat)
    {
        this.context = context;
        this.chat = chat;
        clearAdapter();
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        switch (viewType)
        {
            case 0:
                return new MeViewHolder(inflater.inflate(R.layout.chat_message_bubble_me, parent, false));
            case 1:
                return new NotMeViewHolder(inflater.inflate(R.layout.chat_message_bubble_not_me, parent, false));
            default:
                throw new IndexOutOfBoundsException("ViewType not found");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {

    }

    private void clearAdapter()
    {
        messages.clear();
        ChatMessage test = new ChatMessage();
        test.setText("Testtext");
        messages.add(new ChatMessage());
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    private class MeViewHolder extends RecyclerView.ViewHolder
    {
        public MeViewHolder(View itemView)
        {
            super(itemView);
        }
    }

    private class NotMeViewHolder extends RecyclerView.ViewHolder
    {
        public NotMeViewHolder(View itemView)
        {
            super(itemView);
        }
    }
}
