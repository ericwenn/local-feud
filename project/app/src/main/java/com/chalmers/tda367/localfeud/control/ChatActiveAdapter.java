package com.chalmers.tda367.localfeud.control;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.chalmers.tda367.localfeud.data.Chat;
import com.chalmers.tda367.localfeud.data.GeneralPost;

import java.util.ArrayList;

/**
 * Created by Daniel Ahlqvist on 2016-05-08.
 */
public class ChatActiveAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private final ArrayList<? super GeneralPost> messages = new ArrayList<>();

    public ChatActiveAdapter(Context context, Chat chat)
    {

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
