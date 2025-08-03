package com.example.expensetracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class AdapterTotal extends RecyclerView.Adapter<AdapterTotal.ViewHolder> {
    List<Model> list;
    Context context;

    public AdapterTotal(List<Model> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterTotal.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.card_for_total_expenses, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTotal.ViewHolder holder, int position) {
        holder.tvCategoryTotal.setText(list.get(position).getCategory());
        holder.tvTotal.setText(String.valueOf(list.get(position).getAmount()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategoryTotal, tvTotal;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategoryTotal = itemView.findViewById(R.id.tvCategoryTotal);
            tvTotal = itemView.findViewById(R.id.tvTotal);
        }
    }
}
