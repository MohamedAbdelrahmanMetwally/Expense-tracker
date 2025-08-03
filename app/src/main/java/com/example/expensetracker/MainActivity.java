package com.example.expensetracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
 Button btnAddExpense, btnDeleteExpense, btnShowExpenses, btnShowResults;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnAddExpense = findViewById(R.id.btnAddExpense);
        btnDeleteExpense = findViewById(R.id.btnDeleteExpense);
        btnShowExpenses = findViewById(R.id.btnShowExpenses);
        btnShowResults = findViewById(R.id.btnShowResults);
        btnAddExpense.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AddingExpensesActvity.class));
        });
        btnDeleteExpense.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, DeletingExpensesActivity.class));
        });
        btnShowExpenses.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ShowingExpensesActivity.class));
        });
        btnShowResults.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ShowingTotalExpenses.class));
        });

    }
}