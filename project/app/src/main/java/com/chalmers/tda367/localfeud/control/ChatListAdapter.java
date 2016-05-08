package com.chalmers.tda367.localfeud.control;

import android.content.Context;
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
        chatList.add(new Chat("Alfred", "Tjena tjena"));
        chatList.add(new Chat("Eric", context.getString(R.string.really_long_text)));
        chatList.add(new Chat("Daniel", "Pladder"));
        chatList.add(new Chat("David", "Hej"));
        chatList.add(new Chat("Chrille", context.getString(R.string.really_long_text)));
        chatList.add(new Chat("Chrille", context.getString(R.string.really_long_text)));
        Log.d(TagHandler.MAIN_TAG, "Lägger till 4 chats");

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
        holder.titleTextView.setText(chat.getUserName());
        holder.msgTextView.setText(chat.getMsg());
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