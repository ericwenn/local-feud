package com.chalmers.tda367.localfeud.control;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chalmers.tda367.localfeud.R;
import com.chalmers.tda367.localfeud.authentication.AuthenticatedUser;
import com.chalmers.tda367.localfeud.data.Chat;
import com.chalmers.tda367.localfeud.data.ChatMessage;
import com.chalmers.tda367.localfeud.data.Me;
import com.chalmers.tda367.localfeud.data.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Ahlqvist on 2016-05-08.
 */
public class ChatActiveAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final ArrayList<ChatMessage> messages = new ArrayList<>();
    private final Context context;
    private final LayoutInflater inflater;
    private Chat chat;
    private int myId;
    private AdapterCallback adapterCallback;

    public ChatActiveAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.myId = AuthenticatedUser.getInstance().getMe().getId();      // SKALL ÄNDRAS
        ArrayList<ChatMessage> newMessages = new ArrayList<>();
        addChatMessageListToAdapter(newMessages);

        try {
            adapterCallback = (AdapterCallback) this.context;
        } catch (ClassCastException e) {
            throw new ClassCastException("ChatActiveAdapter: Activity must implement AdapterCallback.");
        }
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
        if (holder.getClass() == MeViewHolder.class) {
            final ChatMessage message = messages.get(position);
            final MeViewHolder viewHolder = (MeViewHolder) holder;
            viewHolder.messageText.setText(message.getText());

        } else if (holder.getClass() == NotMeViewHolder.class) {
            final ChatMessage message = messages.get(position);
            final NotMeViewHolder viewHolder = (NotMeViewHolder) holder;
            viewHolder.messageText.setText(message.getText());

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
        private final TextView messageText;

        public MeViewHolder(View itemView) {
            super(itemView);
            messageText = (TextView) itemView.findViewById(R.id.me_chat_text);
        }
    }

    private class NotMeViewHolder extends RecyclerView.ViewHolder {
        private final TextView messageText;

        public NotMeViewHolder(View itemView) {
            super(itemView);
            messageText = (TextView) itemView.findViewById(R.id.not_me_chat_text);
        }
    }

    public interface AdapterCallback {

    }
}
