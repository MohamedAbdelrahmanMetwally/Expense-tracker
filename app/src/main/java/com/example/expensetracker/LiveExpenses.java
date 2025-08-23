package com.example.expensetracker;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.expensetracker.core.database.Expense;

import java.util.List;

public class LiveExpenses extends ViewModel {
    private final LiveData<List<Expense>> repository = Repository.getExpenses();
    public List<Expense> getExpenses() {
        return repository.getValue();
    }
}
