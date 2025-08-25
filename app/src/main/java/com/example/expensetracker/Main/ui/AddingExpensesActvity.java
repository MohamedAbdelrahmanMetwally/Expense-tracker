package com.example.expensetracker.Main.ui;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.DatePicker;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.expensetracker.Main.util.FactoryViewModel;
import com.example.expensetracker.Main.util.Repository;
import com.example.expensetracker.Main.view_model.MainViewModel;
import com.example.expensetracker.core.database.Expense;
import com.example.expensetracker.core.database.ExpenseDao;
import com.example.expensetracker.core.database.ExpensesDatabase;
import com.example.expensetracker.databinding.ActivityAddingExpensesActvityBinding;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
public class AddingExpensesActvity extends AppCompatActivity {
    private ActivityAddingExpensesActvityBinding binding;
    private Repository repository;
    private FactoryViewModel factoryViewModel;
    private MainViewModel mainViewModel;
    private Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddingExpensesActvityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        calendar = Calendar.getInstance();
        repository = new Repository(ExpensesDatabase.getDatabase(this).expenseDao());
        factoryViewModel = new FactoryViewModel(repository);
        mainViewModel = new ViewModelProvider(this, factoryViewModel).get(MainViewModel.class);
        binding.etDateAdd.setOnClickListener(v -> {
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(
                            this,
                            (DatePicker view, int selectedYear, int selectedMonth, int selectedDay) -> {
                                calendar.set(Calendar.YEAR, selectedYear);
                                calendar.set(Calendar.MONTH, selectedMonth);
                                calendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                binding.etDateAdd.setText(sdf.format(calendar.getTime()));
                            },
                            year, month, day
                    );
                    datePickerDialog.show();
                });
        binding.btnBackAdd.setOnClickListener(v -> {
            finish();
        });
        binding.btnAdd.setOnClickListener(v -> {
            String category = binding.etCategoryAdd.getText().toString();
            String amount = binding.etAmountAdd.getText().toString();
            String date = binding.etDateAdd.getText().toString();
            String title = binding.etNotes.getText().toString();
            if (category.isEmpty() || amount.isEmpty() || date.isEmpty() || title.isEmpty()) {
                if (category.isEmpty()) {
                    binding.layoutCategory.setError("Category is required");
                } else {
                    binding.layoutCategory.setError(null);
                }
                if (amount.isEmpty()) {
                    binding.layoutAmount.setError("Amount is required");
                } else {
                    binding.layoutAmount.setError(null);
                }
                if (date.isEmpty()) {
                    binding.layoutDate.setError("Date is required");
                } else {
                    binding.layoutDate.setError(null);
                }
                if (title.isEmpty()) {
                    binding.layoutTitle.setError("Title is required");
                } else {
                    binding.layoutTitle.setError(null);
                }
            }
            else {
                binding.layoutCategory.setError(null);
                binding.layoutAmount.setError(null);
                binding.layoutDate.setError(null);
                binding.layoutTitle.setError(null);
                Expense expense = new Expense(category, Double.parseDouble(amount), date, title);
                mainViewModel.addExpense(expense);
            }
        });
    }
}