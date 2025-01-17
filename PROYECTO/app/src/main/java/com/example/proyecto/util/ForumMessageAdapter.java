package com.example.proyecto.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto.R;
import com.example.proyecto.models.ForumMessage;

import java.util.List;

public class ForumMessageAdapter extends RecyclerView.Adapter<ForumMessageAdapter.ViewHolder> {
    private List<ForumMessage> forumMessages;
    private Context context;

    public ForumMessageAdapter(Context context, List<ForumMessage> forumMessages) {
        this.context = context;
        this.forumMessages = forumMessages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_forum_message, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ForumMessage message = forumMessages.get(position);
        holder.messageText.setText(message.getMessage());
        holder.messageDate.setText(message.getDate());
        holder.messageSender.setText("Usuario " + message.getSender());
    }

    @Override
    public int getItemCount() {
        return forumMessages.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView messageDate;
        TextView messageSender;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.messageText);
            messageDate = itemView.findViewById(R.id.messageDate);
            messageSender = itemView.findViewById(R.id.messageSender);
        }
    }
}