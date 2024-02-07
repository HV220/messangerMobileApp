package com.example.messanger.models.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messanger.R;
import com.example.messanger.models.Message;

import java.util.ArrayList;
import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessageViewHolder> {
    private final static int VIEW_TYPE_MY_MESSAGE = 100;
    private final static int VIEW_TYPE_OTHER_MESSAGE = 101;
    private List<Message> messages = new ArrayList<>();

    private String currentUserId;

    public MessagesAdapter(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout_item;

        if (viewType == VIEW_TYPE_MY_MESSAGE)
            layout_item = R.layout.my_message_item;
        else
            layout_item = R.layout.other_message_item;

        View view = LayoutInflater.from(parent.getContext()).inflate(layout_item, parent, false);

        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {

        Message item = messages.get(position);

        holder.textMessage.setText(item.getTextMessage());
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        return (message.getSenderId().equals(currentUserId)) ? VIEW_TYPE_MY_MESSAGE
                : VIEW_TYPE_OTHER_MESSAGE;

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        private TextView textMessage;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
                textMessage = itemView.findViewById(R.id.textViewMessage);
        }
    }
}
