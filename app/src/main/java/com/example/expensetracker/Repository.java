package com.example.expensetracker;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.expensetracker.core.database.Expense;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    private static final MutableLiveData<List<Expense>> expenses = new MutableLiveData<>();
    public static LiveData<List<Expense>> getExpenses() {
        if(expenses.getValue()==null)
        {
            expenses.setValue(new ArrayList<>());
        }
        return expenses;
    }
}
