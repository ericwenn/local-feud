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
 *  Adapter for RecyclerView in ChatFragment.
 *  Displaying all active chats in a list.
 */
public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private AdapterCallback adapterCallback;
    private Comparator<Chat> comparator;

//    chatList will contain all active chats
    private final ArrayList<Chat> chatList = new ArrayList<>();

    public ChatListAdapter(Context context) {
        inflater = LayoutInflater.from(context);

        try {
//            Casting context to AdapterCallback. Will be used for click events
            adapterCallback = (AdapterCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("ChatListAdapter: Activity must implement AdapterCallback.");
        }

//        Sorting list after date
        comparator = new Comparator<Chat>() {
            @Override
            public int compare(Chat lhs, Chat rhs) {
                return rhs.getDate(rhs.getLastActivity()).compareTo(lhs.getDate(lhs.getLastActivity()));
            }
        };
    }

    /**
     *  Creates a view holder for the chat. The holder is connected to the corresponding
     *  layout XML file.
     *
     *  @param parent the view group in which the adapter will be placed
     *  @param viewType what kind of object the adapter will show. (Not used, since the adapter
     *                 is only used for chats)
     *  @return view holder for a chat, which will be used in a recycler view.
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.chat_list_item, parent, false);
        return new ViewHolder(view);
    }

    /**
     *  Binds the data of a chat object to a view holder.
     *
     *  @param holder the basic view holder in which the data will be placed.
     *  @param position the position of the chat in the chatList.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Chat chat = chatList.get(position);

//        Setting all data from chat to the ViewHolder
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

    /**
     *  Counts the number of objects in the chatList.
     *
     *  @return the number of objects in the chatList.
     */
    @Override
    public int getItemCount() {
        return chatList.size();
    }

    /**
     *  Removes every chat from the list.
     */
    private void clearAdapter() {
        chatList.clear();
        notifyDataSetChanged();
    }

    /**
     *  Used when changing data in existing chat.
     *
     *  @param oldChat the chat which should be replaced
     *  @param newChat the chat that should replace oldChat
     */
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

    /**
     *  Adds a new chat to the chatList.
     *
     *  @param chat the post which will be added to the chatList.
     */
    public void addChatToAdapter(Chat chat) {
        chatList.add(chat);
        notifyItemChanged(chatList.size());
    }

    /**
     *  Used to add a number of a chats to the chatList at the same time.
     *  Also sorts the list after given comparator.
     *
     *  @param chatList the list of all post to be added
     */
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

    /**
     *  A class which is used to model a view holder for a chat. The variables are connected
     *  to the id values from the corresponding layout XML file.
     */
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

        /**
         * Determines what will happen when a chat is clicked
         *
         * @param chat the chat that has been clicked.
         */
        void onChatClicked(Chat chat);

        /**
         * Displays a snackbar with a message.
         *
         * @param text the text that will be displayed in the snackbar.
         */
        void onShowSnackbar(String text);
    }
}