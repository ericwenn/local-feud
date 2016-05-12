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
import com.chalmers.tda367.localfeud.service.ResponseError;
import com.chalmers.tda367.localfeud.util.TagHandler;

import java.util.ArrayList;
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

    private final ArrayList<Chat> chatList = new ArrayList<>();

    public ChatListAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);

        try {
            adapterCallback = (AdapterCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("ChatListAdapter: Activity must implement AdapterCallback.");
        }
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
        String chatMessage = chat.toString();
        holder.msgTextView.setText(chatMessage);

        if (chat.getNumberOfUnreadMessages() > 0)
            holder.msgTextView.setTypeface(null, Typeface.BOLD);

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

    public void addChatToAdapter(Chat chat) {
        chatList.add(chat);
        notifyItemChanged(chatList.size());
    }

    public void addChatListToAdapter(final List<Chat> chatList) {
        Log.d(TagHandler.MAIN_TAG, "Trying to add " + chatList.size() + " chats");
        final int currentCount = this.chatList.size();
        synchronized (this.chatList) {
            Log.d(TagHandler.MAIN_TAG, "Uppdaterar inlägg...");
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

    public void showError(ResponseError responseError) {
        String errorText;
        switch (responseError) {
            case NOTFOUND:
                errorText = context.getString(R.string.notfound_error_msg);
                break;
            case UNAUTHORIZED:
                errorText = context.getString(R.string.unauthorized_error_msg);
                break;
            default:
                errorText = context.getString(R.string.server_error_msg);
                break;
        }
        Log.d(TagHandler.MAIN_TAG, "Error: " + errorText);
        adapterCallback.onShowSnackbar(errorText);
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