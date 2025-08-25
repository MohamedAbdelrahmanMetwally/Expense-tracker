package com.example.expensetracker.Main.ui;

import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.expensetracker.Main.util.FactoryViewModel;
import com.example.expensetracker.Main.util.Repository;
import com.example.expensetracker.Main.util.TotalCategory;
import com.example.expensetracker.Main.view_model.MainViewModel;
import com.example.expensetracker.R;
import com.example.expensetracker.core.database.ExpensesDatabase;
import com.example.expensetracker.databinding.ActivityStatistcsBinding;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class StatistcsActivity extends AppCompatActivity {
private ActivityStatistcsBinding binding;
private Repository repository;
private FactoryViewModel factoryViewModel;
private MainViewModel mainViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityStatistcsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        repository = new Repository(ExpensesDatabase.getDatabase(this).expenseDao());
        factoryViewModel = new FactoryViewModel(repository);
        mainViewModel = new ViewModelProvider(this, factoryViewModel).get(MainViewModel.class);
        mainViewModel.getTotalByCategory().observe(this, totalCategories -> {
            if (totalCategories == null || totalCategories.isEmpty()) {
                binding.pieChart.clear();
                binding.pieChart.invalidate();
                return;
            }

            ArrayList<PieEntry> entries = new ArrayList<>();
            for (TotalCategory totalCategory : totalCategories) {
                entries.add(new PieEntry((float) totalCategory.getTotal(), totalCategory.getCategory()));
            }
            PieDataSet dataSet = new PieDataSet(entries, "Categories");
            ArrayList<Integer> colors = new ArrayList<>();
            for (int c : com.github.mikephil.charting.utils.ColorTemplate.MATERIAL_COLORS) {
                colors.add(c);
            }
            dataSet.setColors(colors);
            PieData data = new PieData(dataSet);
            data.setValueTextSize(12f);
            data.setValueTextColor(android.graphics.Color.WHITE);
            binding.pieChart.setUsePercentValues(true);
            binding.pieChart.setDrawHoleEnabled(true);
            binding.pieChart.setHoleColor(android.graphics.Color.TRANSPARENT);
            binding.pieChart.setTransparentCircleRadius(58f);
            binding.pieChart.setEntryLabelColor(android.graphics.Color.BLACK);
            binding.pieChart.setData(data);
            binding.pieChart.invalidate();
        });
        binding.btnBackStatistics.setOnClickListener(v -> {
            finish();
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.pieChart.setData(null);
    }
}