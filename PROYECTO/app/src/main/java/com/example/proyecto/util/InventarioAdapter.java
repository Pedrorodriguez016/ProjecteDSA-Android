package com.example.proyecto.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto.R;
import com.example.proyecto.models.Item;

import java.util.ArrayList;
import java.util.List;

public class InventarioAdapter extends RecyclerView.Adapter<InventarioAdapter.ViewHolder> {
    private List<Item> items = new ArrayList<>();
    private Context context;

    public InventarioAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    public void updateItems(List<Item> newItems) {
        this.items.clear();
        if (newItems != null) {
            this.items.addAll(newItems);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_inventario, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.itemName.setText(item.getType());

        switch(item.getType()) {
            case "sword":
                holder.itemImage.setImageResource(R.drawable.sword);
                break;
            case "armor":
                holder.itemImage.setImageResource(R.drawable.armadura);
                break;
            case "shield":
                holder.itemImage.setImageResource(R.drawable.escudo);
                break;
            case "knife":
                holder.itemImage.setImageResource(R.drawable.knife);
                break;
            case "potion":
                holder.itemImage.setImageResource(R.drawable.potion);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.itemimage);
            itemName = itemView.findViewById(R.id.itemname);
        }
    }
}