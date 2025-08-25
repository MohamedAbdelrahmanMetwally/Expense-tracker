package com.example.expensetracker.Main.util;

import android.app.AlertDialog;
import android.content.Context;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetracker.Main.view_model.MainViewModel;
import com.example.expensetracker.R;
import com.example.expensetracker.core.database.Expense;

import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {
    List<Expense> expenses;
    Context context;
    MainViewModel mainViewModel;
    public ExpenseAdapter(List<Expense> expenses, Context context, MainViewModel mainViewModel) {
        this.expenses = expenses;
        this.context = context;
        this.mainViewModel = mainViewModel;
    }
    @NonNull
    @Override
    public ExpenseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ExpenseAdapter.ViewHolder holder, int position) {
       holder.tvId.setText(String.valueOf(expenses.get(position).getId()));
       holder.tvAmount.setText(String.valueOf(expenses.get(position).getAmount()));
        holder.tvDate.setText(expenses.get(position).getDate());
       holder.tvCategory.setText(expenses.get(position).getCategory());
       holder.tvTitle.setText(expenses.get(position).getTitle());
       int pos=position;
       holder.cv.setOnLongClickListener(v -> {
           new AlertDialog.Builder(context)
                   .setTitle("Delete Expense")
                   .setMessage("Are you sure you want to delete this expense?")
                   .setPositiveButton("Yes", (dialog, which) -> {
                       mainViewModel.deleteExpense(expenses.get(pos));
                   })
                   .setNegativeButton("No", (dialog, which) -> {}).show();
           return true;
       });
    }
    @Override
    public int getItemCount() {
        return expenses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvTitle, tvAmount, tvDate, tvCategory;
        CardView cv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvId);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvCategory = itemView.findViewById(R.id.tvCategory);
           tvTitle= itemView.findViewById(R.id.tvTitle);
            cv = itemView.findViewById(R.id.cv);
        }
    }
    public void update(List<Expense> expenses) {
        this.expenses = expenses;
        notifyDataSetChanged();
    }
}
