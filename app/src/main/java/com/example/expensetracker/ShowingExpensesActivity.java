package com.example.expensetracker;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ShowingExpensesActivity extends AppCompatActivity {
  RecyclerView rv;
  Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_showing_expenses);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        rv = findViewById(R.id.rv);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(view -> finish());
        ExpensesDatabase expensesDatabase = ExpensesDatabase.getDatabase(this);
        ExpenseDao expenseDao = expensesDatabase.expenseDao();
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            List<Expense> expenses = expenseDao.getAllExpenses(); // دي لازم ترجع List<Expense> مش LiveData
            runOnUiThread(() -> {
                Adapter adapter = new Adapter(expenses, this);
                rv.setAdapter(adapter);
                rv.setLayoutManager(new LinearLayoutManager(this))  ;
            });
        });
    }
}