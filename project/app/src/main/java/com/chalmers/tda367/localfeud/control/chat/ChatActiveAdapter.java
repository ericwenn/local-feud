package com.chalmers.tda367.localfeud.control.chat;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chalmers.tda367.localfeud.R;
import com.chalmers.tda367.localfeud.data.Chat;
import com.chalmers.tda367.localfeud.data.ChatMessage;
import com.chalmers.tda367.localfeud.data.handler.DataHandlerFacade;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter used by ChatActiveActivity to display chat messages
 * bubbles in its recycler view. The adapter is created using the
 * layout XML files chat_message_bubble_me and chat_message_bubble_not_me
 */
public class ChatActiveAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "ChatActiveAdapter";
    private final ArrayList<ChatMessage> messages = new ArrayList<>();
    private final LayoutInflater inflater;
    private Chat chat;
    private final int myId;

    /**
     * Constructor.
     *
     * @param context the current state of the object it is called from.
     */
    public ChatActiveAdapter(Context context) {
        Context context1 = context;
        inflater = LayoutInflater.from(context);
        this.myId = DataHandlerFacade.getMeDataHandler().getMe().getId();
        ArrayList<ChatMessage> newMessages = new ArrayList<>();
        clearAdapter();
        addChatMessageListToAdapter(newMessages);

        try {
            AdapterCallback adapterCallback = (AdapterCallback) context1;
        } catch (ClassCastException e) {
            throw new ClassCastException("ChatActiveAdapter: Activity must implement AdapterCallback.");
        }
    }

    /**
     * Stops the recycler view from observing the adapter
     *
     * @param recyclerView the recycler view which stops observing this adapter.
     */
    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    /**
     * Determines if the adapter will be used for a message sent by the user or by somebody else.
     *
     * @param parent the view group in which the adapter will be placed
     * @param viewType what kind of object the adapter will show.
     *                 0 represents a message sent by the user and 1 represents a message
     *                 sent by somebody else.
     * @return view holder for a message, which will be used in a recycler view.
     */
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

    /**
     * Binds the data of a message object to a view holder.
     *
     * @param holder the basic view holder in which the data will be placed.
     *               There are two different types: one for messages sent by the user
     *               and one for messages sent by somebody else.
     * @param position the position of the message in the messages list.
     */
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

    /**
     * Removes everything from the recycler view.
     */
    private void clearAdapter() {
        messages.clear();
        notifyDataSetChanged();
    }

    /**
     * Determines it the object at a given position in the messages list is an instance of
     * a message sent by the user or a message sent by somebody else.
     *
     * @param position the position in the list that will be checked.
     * @return an integer value. 0 represents a message sent by the user and
     *         1 represents a message sent by somebody else.
     */
    @Override
    public int getItemViewType(int position) {
        if (messages.get(position).getUser().getId() == myId) {
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * Used to add all messages in a chat to the messages list at the same time.
     *
     * @param messages the list of all messages in a chat
     */
    public void addChatMessageListToAdapter(final List<ChatMessage> messages) {
        final int currentCount = this.messages.size();
        synchronized (this.messages) {
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

    /**
     * Adds a new message to the chat messages list
     *
     * @param message the message which will be added to the comments list.
     */
    public void addChatMessageToAdapter(final ChatMessage message){
        synchronized (messages) {
            messages.add(message);
        }

        notifyItemInserted(messages.indexOf(message));
    }

    /**
     * Counts the number of objects in the messages list.
     *
     * @return the number of objects in the messages list.
     */
    @Override
    public int getItemCount() {
        return messages.size();
    }

    /**
     * A class which is used to model a view holder for a message sent by the user.
     * The variables are connected to the id values from the corresponding layout XML file.
     */
    private class MeViewHolder extends RecyclerView.ViewHolder {
        private final TextView messageText, timeDisplay;

        /**
         * Constructor.
         *
         * @param itemView the chat message view which has been created using the layout XML file.
         */
        public MeViewHolder(View itemView) {
            super(itemView);
            messageText = (TextView) itemView.findViewById(R.id.me_chat_text);
            timeDisplay = (TextView) itemView.findViewById(R.id.time_display);
        }
    }

    /**
     * A class which is used to model a view holder for a message sent by somebody else.
     * The variables are connected to the id values from the corresponding layout XML file.
     */
    private class NotMeViewHolder extends RecyclerView.ViewHolder {
        private final TextView messageText, timeDisplay;

        /**
         * Constructor.
         *
         * @param itemView the chat message view which has been created using the layout XML file.
         */
        public NotMeViewHolder(View itemView) {
            super(itemView);
            messageText = (TextView) itemView.findViewById(R.id.not_me_chat_text);
            timeDisplay = (TextView) itemView.findViewById(R.id.time_display);
        }
    }

    public interface AdapterCallback {

    }
}
