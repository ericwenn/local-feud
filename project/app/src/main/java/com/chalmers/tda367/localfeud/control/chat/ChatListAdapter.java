package com.chalmers.tda367.localfeud.control.chat;

import android.content.Context;
import android.graphics.Typeface;
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
import com.chalmers.tda367.localfeud.data.Chat;
import com.chalmers.tda367.localfeud.util.TagHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Text om metoden
 *
 * @author David Söderberg
 * @since 16-05-08
 */
public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {

    private final Context context;
    private final LayoutInflater inflater;
    private AdapterCallback adapterCallback;
    private Comparator<Chat> comparator;

    private final ArrayList<Chat> chatList = new ArrayList<>();

    public ChatListAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);

        try {
            adapterCallback = (AdapterCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("ChatListAdapter: Activity must implement AdapterCallback.");
        }

        comparator = new Comparator<Chat>() {
            @Override
            public int compare(Chat lhs, Chat rhs) {
                return rhs.getDate(rhs.getLastActivity()).compareTo(lhs.getDate(lhs.getLastActivity()));
            }
        };
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.chat_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Chat chat = chatList.get(position);
        
        holder.titleTextView.setText(chat.getChatName());
        holder.msgTextView.setText(chat.getLastMessage());

        if (chat.getNumberOfUnreadMessages() > 0)
            holder.msgTextView.setTypeface(null, Typeface.BOLD);
        else
            holder.msgTextView.setTypeface(Typeface.DEFAULT);

        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterCallback.onChatClicked(chat);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    private void clearAdapter() {
        chatList.clear();
        notifyDataSetChanged();
    }

    public void changeChatInAdapter(Chat oldChat, Chat newChat) {
        if (chatList.contains(oldChat)) {
            int chatIndex = chatList.indexOf(oldChat);
            chatList.set(chatIndex, newChat);
            Collections.sort(chatList, comparator);
            if (chatIndex != chatList.indexOf(newChat)) {
                notifyItemMoved(chatIndex, chatList.indexOf(newChat));
            }
            notifyItemChanged(chatList.indexOf(newChat));
        }
        else {
            Log.e(TagHandler.MAIN_TAG, "ChatListAdapter doesn't contain chat " + oldChat.getId());
        }
    }

    public void addChatToAdapter(Chat chat) {
        chatList.add(chat);
        notifyItemChanged(chatList.size());
    }

    public void addChatListToAdapter(final List<Chat> chatList) {
        final int currentCount = this.chatList.size();

        Collections.sort(chatList, comparator);

        synchronized (this.chatList) {
            clearAdapter();
            this.chatList.addAll(chatList);
        }
        if (Looper.getMainLooper() == Looper.myLooper()) {
            notifyItemRangeInserted(currentCount, chatList.size());
        } else {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    notifyItemRangeInserted(currentCount, chatList.size());
                }
            });
        }
    }



    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTextView;
        private final TextView msgTextView;
        private final CircleImageView imageView;
        private final CardView root;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.chat_list_item_title);
            msgTextView = (TextView) itemView.findViewById(R.id.chat_list_item_msg);
            imageView = (CircleImageView) itemView.findViewById(R.id.chat_list_item_image);
            root = (CardView) itemView.findViewById(R.id.chat_list_item_root);
        }
    }

    public interface AdapterCallback {
        void onChatClicked(Chat chat);
        void onShowSnackbar(String text);
    }
}