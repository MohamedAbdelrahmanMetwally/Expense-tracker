package com.example.expensetracker;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddingExpensesActvity extends AppCompatActivity {

    EditText etCategoryAdd, etAmountAdd, etDateAdd, etNotes;
    Button btnAdd, btnBackAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_expenses_actvity);

        // ربط العناصر
        etCategoryAdd = findViewById(R.id.etCategoryAdd);
        etAmountAdd = findViewById(R.id.etAmountAdd);
        etDateAdd = findViewById(R.id.etDateAdd);
        etNotes = findViewById(R.id.etNotes); // ده كان اسمه etNotes في xml
        btnAdd = findViewById(R.id.btnAdd);
        btnBackAdd = findViewById(R.id.btnBackAdd);
        // تحديد التاريخ الحالي
        SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = SDF.format(new Date());
        etDateAdd.setText(currentDate);
        // زر الإضافة
        btnAdd.setOnClickListener(v -> {
            String category = etCategoryAdd.getText().toString().trim();
            String amountStr = etAmountAdd.getText().toString().trim();
            String dateStr = etDateAdd.getText().toString().trim();
            String notes = etNotes.getText().toString().trim();

            if (category.isEmpty() || amountStr.isEmpty() || dateStr.isEmpty()) {
                Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            double amount;
            Date date;
            try {
                amount = Double.parseDouble(amountStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid amount", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                date = sdf.parse(dateStr);
            } catch (ParseException e) {
                Toast.makeText(this, "Invalid date format (use yyyy-MM-dd)", Toast.LENGTH_SHORT).show();
                return;
            }

            Expense expense = new Expense(category, amount, date, notes);

            new Thread(() -> {
                ExpensesDatabase.getDatabase(this).expenseDao().insert(expense);
                runOnUiThread(() -> Toast.makeText(this, "Expense added!", Toast.LENGTH_SHORT).show());
            }).start();
        });

        // زر الرجوع
        btnBackAdd.setOnClickListener(v -> finish());
    }
}
