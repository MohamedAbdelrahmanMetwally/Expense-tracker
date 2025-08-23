package com.example.expensetracker;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetracker.core.database.Expense;
import com.example.expensetracker.core.database.ExpenseDao;
import com.example.expensetracker.core.database.ExpensesDatabase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ShowingTotalExpenses extends AppCompatActivity {
    TextView tvTotalSum;
    RecyclerView rv2;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_showing_total_expenses);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tvTotalSum = findViewById(R.id.tvTotalSum);
        rv2 = findViewById(R.id.rv2);

        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            ExpensesDatabase db = ExpensesDatabase.getDatabase(getApplicationContext());
            ExpenseDao expenseDao = db.expenseDao();
            // Get all expenses
            List<Expense> expenses = expenseDao.getAllExpenses();
            // Get total sum
            // Aggregate by category
            String totalSum = String.valueOf(expenseDao.getTotalExpenses());
            HashSet<String> categories = new HashSet<>();
            for (Expense expense : expenses) {
                categories.add(expense.getCategory());
            }
            ArrayList<Double> amounts = new ArrayList<>();
            for (String category : categories) {
                amounts.add(expenseDao.getTotalExpensesByCategory(category));
            }
            List<Model> models = new ArrayList<>();
            for (int i = 0; i < categories.size(); i++) {
                models.add(new Model(categories.toArray()[i].toString(), amounts.get(i)));
            }
            // Update UI on main thread
            runOnUiThread(() -> {
                tvTotalSum.setText(totalSum);
                AdapterTotal adapter = new AdapterTotal(models, this);
                rv2.setAdapter(adapter);
                rv2.setLayoutManager(new LinearLayoutManager(this));
            });
        });
        Button btnBackTotal = findViewById(R.id.btnBackTotal);
        btnBackTotal.setOnClickListener(view -> finish());
    }
}