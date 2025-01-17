package com.example.proyecto.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto.R;
import com.example.proyecto.models.Thread;

import java.util.List;

public class ForumAdapter extends RecyclerView.Adapter<ForumAdapter.ViewHolder> {
    private List<Thread> threads;
    private Context context;
    private OnThreadClickListener listener;

    public interface OnThreadClickListener {
        void onThreadClick(Thread thread);
    }

    public ForumAdapter(Context context, List<Thread> threads, OnThreadClickListener listener) {
        this.context = context;
        this.threads = threads;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_thread, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Thread thread = threads.get(position);
        holder.threadTitle.setText(thread.getTitle());
        holder.threadDate.setText(thread.getDate());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onThreadClick(thread);
            }
        });
    }

    @Override
    public int getItemCount() {
        return threads.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView threadTitle;
        TextView threadDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            threadTitle = itemView.findViewById(R.id.threadTitle);
            threadDate = itemView.findViewById(R.id.threadDate);
        }
    }
}