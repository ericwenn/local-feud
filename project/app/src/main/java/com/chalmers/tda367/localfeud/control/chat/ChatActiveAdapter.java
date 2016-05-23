package com.chalmers.tda367.localfeud.control.chat;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chalmers.tda367.localfeud.R;
import com.chalmers.tda367.localfeud.control.notifications.IMessageListener;
import com.chalmers.tda367.localfeud.control.notifications.MessageHandler;
import com.chalmers.tda367.localfeud.data.Chat;
import com.chalmers.tda367.localfeud.data.ChatMessage;
import com.chalmers.tda367.localfeud.data.handler.DataHandlerFacade;
import com.chalmers.tda367.localfeud.services.NotificationFacade;
import com.chalmers.tda367.localfeud.util.MapEntry;
import com.chalmers.tda367.localfeud.util.TagHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Daniel Ahlqvist on 2016-05-08.
 */
public class ChatActiveAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final ArrayList<ChatMessage> messages = new ArrayList<>();
    private final LayoutInflater inflater;
    private Chat chat;
    private final int myId;

    public ChatActiveAdapter(Context context) {
        Context context1 = context;
        inflater = LayoutInflater.from(context);
        this.myId = DataHandlerFacade.getMeDataHandler().getMe().getId();
        ArrayList<ChatMessage> newMessages = new ArrayList<>();
        addChatMessageListToAdapter(newMessages);

        try {
            AdapterCallback adapterCallback = (AdapterCallback) context1;
        } catch (ClassCastException e) {
            throw new ClassCastException("ChatActiveAdapter: Activity must implement AdapterCallback.");
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                return new MeViewHolder(inflater.inflate(R.layout.chat_message_bubble_me, parent, false));
            case 1:
                return new NotMeViewHolder(inflater.inflate(R.layout.chat_message_bubble_not_me, parent, false));
            default:
                throw new IndexOutOfBoundsException("ViewType not found");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ChatMessage message = messages.get(position);
        if (holder.getClass() == MeViewHolder.class) {
            final MeViewHolder viewHolder = (MeViewHolder) holder;
            viewHolder.messageText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    if(viewHolder.timeDisplay.getVisibility() == (View.VISIBLE))
                    {
                        viewHolder.timeDisplay.setVisibility(View.GONE);
                    }
                    else
                    {
                        viewHolder.timeDisplay.setVisibility(View.VISIBLE);
                    }
                }
            });
            viewHolder.messageText.setText(message.getText());
            viewHolder.timeDisplay.setText(message.getStringDatePosted());

        } else if (holder.getClass() == NotMeViewHolder.class) {
            final NotMeViewHolder viewHolder = (NotMeViewHolder) holder;
            viewHolder.messageText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    if(viewHolder.timeDisplay.getVisibility() == (View.VISIBLE))
                    {
                        viewHolder.timeDisplay.setVisibility(View.GONE);
                    }
                    else
                    {
                        viewHolder.timeDisplay.setVisibility(View.VISIBLE);
                    }
                }
            });
            viewHolder.messageText.setText(message.getText());
            viewHolder.timeDisplay.setText(message.getStringDatePosted());
        }
    }

    private void clearAdapter() {
        messages.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (messages.get(position).getUser().getId() == myId) {
            return 0;
        } else {
            return 1;
        }
    }

    public void addChatMessageListToAdapter(final List<ChatMessage> messages) {
        final int currentCount = this.messages.size();
        synchronized (this.messages) {
            clearAdapter();
            this.messages.addAll(messages);
        }
        if (Looper.getMainLooper() == Looper.myLooper()) {
            notifyItemRangeInserted(currentCount, messages.size());
        } else {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    notifyItemRangeInserted(currentCount, messages.size());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    private class MeViewHolder extends RecyclerView.ViewHolder {
        private final TextView messageText, timeDisplay;

        public MeViewHolder(View itemView) {
            super(itemView);
            messageText = (TextView) itemView.findViewById(R.id.me_chat_text);
            timeDisplay = (TextView) itemView.findViewById(R.id.time_display);
        }
    }

    private class NotMeViewHolder extends RecyclerView.ViewHolder {
        private final TextView messageText, timeDisplay;

        public NotMeViewHolder(View itemView) {
            super(itemView);
            messageText = (TextView) itemView.findViewById(R.id.not_me_chat_text);
            timeDisplay = (TextView) itemView.findViewById(R.id.time_display);
        }
    }

    public interface AdapterCallback {

    }
}
