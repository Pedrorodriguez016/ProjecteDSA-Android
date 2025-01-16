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
import com.example.proyecto.models.Inventario;

import java.util.ArrayList;
import java.util.List;

public class InventarioAdapter extends RecyclerView.Adapter<InventarioAdapter.ViewHolder> {
    private List<Inventario> items = new ArrayList<>();
    private Context context;

    public InventarioAdapter(Context context, List<Inventario> items) {
        this.context = context;
        this.items = items;
    }

    public void updateItems(List<Inventario> newItems) {
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
        Inventario inventario = items.get(position);

        switch(inventario.getItem()) {
            case 3:
                holder.itemImage.setImageResource(R.drawable.sword);
                break;
            case 4:
                holder.itemImage.setImageResource(R.drawable.armadura);
                break;
            case 2:
                holder.itemImage.setImageResource(R.drawable.escudo);
                break;
            case 1:
                holder.itemImage.setImageResource(R.drawable.knife);
                break;
            case 5:
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