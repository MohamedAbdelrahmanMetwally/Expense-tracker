package com.example.expensetracker.Main.ui;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetracker.Main.util.ExpenseAdapter;
import com.example.expensetracker.Main.util.FactoryViewModel;
import com.example.expensetracker.Main.util.Repository;
import com.example.expensetracker.Main.view_model.MainViewModel;
import com.example.expensetracker.R;
import com.example.expensetracker.core.database.Expense;
import com.example.expensetracker.core.database.ExpensesDatabase;
import com.example.expensetracker.databinding.ActivityMainBinding;
import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {
 private ActivityMainBinding binding;
 private Repository repository;
 private FactoryViewModel factoryViewModel;
 private MainViewModel mainViewModel;
 private ExpenseAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        repository = new Repository(ExpensesDatabase.getDatabase(this).expenseDao());
        factoryViewModel = new FactoryViewModel(repository);
        mainViewModel = new ViewModelProvider(this, factoryViewModel).get(MainViewModel.class);
        adapter = new ExpenseAdapter(new ArrayList<Expense>(), this, mainViewModel);
        binding.recyclerViewExpenses.setAdapter(adapter);
        binding.recyclerViewExpenses.setLayoutManager(new LinearLayoutManager(this));
        mainViewModel.getExpenses().observe(this, expenses -> {
            adapter.update(expenses);
            if (expenses.isEmpty()) {
                binding.textEmpty.setVisibility(View.VISIBLE);
            } else {
                binding.textEmpty.setVisibility(View.GONE);
            }
        });
        binding.btnAdd.setOnClickListener(v -> {
            startActivity(new Intent(this, AddingExpensesActvity.class));
        });
        binding.btnStatistics.setOnClickListener(v -> {
            startActivity(new Intent(this, StatistcsActivity.class));
        });
    }
}