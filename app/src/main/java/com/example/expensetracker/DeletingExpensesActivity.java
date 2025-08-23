package com.example.expensetracker;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.expensetracker.core.database.Expense;
import com.example.expensetracker.core.database.ExpenseDao;
import com.example.expensetracker.core.database.ExpensesDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DeletingExpensesActivity extends AppCompatActivity {
    EditText etIdDelete, etCategoryDelete, etNotesDelete, etAmountDelete, etDateDelete;
    Button btnDelete, btnBackDelete;
    String[] options = {"id", "category", "notes", "amount", "date", "all"};

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_deleting_expenses);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        etIdDelete = findViewById(R.id.etIdDelete);
        etCategoryDelete = findViewById(R.id.etCategoryDelete);
        etNotesDelete = findViewById(R.id.etNotesDelete);
        etAmountDelete = findViewById(R.id.etAmountDelete);
        etDateDelete = findViewById(R.id.etDateDelete);
        btnDelete = findViewById(R.id.btnDelete);
        btnBackDelete = findViewById(R.id.btnBackDelete);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // قفل الكل في الأول
                etIdDelete.setEnabled(false);
                etCategoryDelete.setEnabled(false);
                etNotesDelete.setEnabled(false);
                etAmountDelete.setEnabled(false);
                etDateDelete.setEnabled(false);

                // فعل بس اللي المستخدم اختاره
                switch (options[i]) {
                    case "id":
                        etIdDelete.setEnabled(true);
                        break;
                    case "category":
                        etCategoryDelete.setEnabled(true);
                        break;
                    case "notes":
                        etNotesDelete.setEnabled(true);
                        break;
                    case "amount":
                        etAmountDelete.setEnabled(true);
                        break;
                    case "date":
                        etDateDelete.setEnabled(true);
                        break;
                    case "all":
                        etIdDelete.setEnabled(true);
                        etCategoryDelete.setEnabled(true);
                        etNotesDelete.setEnabled(true);
                        etAmountDelete.setEnabled(true);
                        etDateDelete.setEnabled(true);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Executor executor = Executors.newSingleThreadExecutor();
                executor.execute(() -> {
                    ExpenseDao dao = ExpensesDatabase.getDatabase(getApplicationContext()).expenseDao();
                    String selectedOption = spinner.getSelectedItem().toString();
                    try {
                        switch (selectedOption) {
                            case "id":
                                int id = Integer.parseInt(etIdDelete.getText().toString());
                                dao.deleteByID(id);
                                break;
                            case "category":
                                dao.deleteByCategory(etCategoryDelete.getText().toString().trim());
                                break;
                            case "notes":
                                dao.deleteByNotes(etNotesDelete.getText().toString().trim());
                                break;
                            case "amount":
                                double amount = Double.parseDouble(etAmountDelete.getText().toString());
                                dao.deleteByAmount(amount);
                                break;
                            case "date":
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                Date date = format.parse(etDateDelete.getText().toString().trim());
                                dao.deleteByDate(date);
                                break;
                            case "all":
                                String category = etCategoryDelete.getText().toString();
                                double a = Double.parseDouble(etAmountDelete.getText().toString());
                                String notes = etNotesDelete.getText().toString();
                                int i = Integer.parseInt(etIdDelete.getText().toString());
                                SimpleDateFormat formatAll = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                Date dateAll = formatAll.parse(etDateDelete.getText().toString());

                                Expense expense = new Expense(category, a, dateAll, notes);
                                expense.setId(i);
                                dao.delete(expense);
                                break;
                        }

                        // Show Toast on main thread
                        runOnUiThread(() ->
                                Toast.makeText(DeletingExpensesActivity.this, "Deleted successfully", Toast.LENGTH_SHORT).show()
                        );

                    } catch (Exception e) {
                        e.printStackTrace();
                        runOnUiThread(() ->
                                Toast.makeText(DeletingExpensesActivity.this, "Check inputs, invalid data!", Toast.LENGTH_SHORT).show()
                        );
                    }
                });
            }
        });

        btnBackDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}