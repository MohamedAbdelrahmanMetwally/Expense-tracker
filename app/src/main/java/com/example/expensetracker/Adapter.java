package com.example.expensetracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    List<Expense> expenses;
    Context context;

    public Adapter(List<Expense> expenses, Context context) {
        this.expenses = expenses;
        this.context = context;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
       holder.tvId.setText(String.valueOf(expenses.get(position).getId()));
       holder.tvAmount.setText(String.valueOf(expenses.get(position).getAmount()));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        holder.tvDate.setText(sdf.format(expenses.get(position).getDate()));
       holder.tvCategory.setText(expenses.get(position).getCategory());
       holder.tvNotes.setText(expenses.get(position).getNotes());
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvAmount, tvDate, tvCategory, tvNotes;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvId);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvNotes = itemView.findViewById(R.id.tvNotes);
        }
    }
}
