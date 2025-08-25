package com.example.expensetracker.core.database;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.expensetracker.Main.util.TotalCategory;

import java.util.List;

@Dao
public interface ExpenseDao {
    @Insert
    void insertExpnse(Expense expense);
    @Query("SELECT * FROM expense")
    LiveData<List<Expense>> getAllExpenses();
    @Delete
    void deleteExpense(Expense expense);
    @Query("SELECT category, SUM(amount) AS total FROM expense GROUP BY category")
    LiveData<List<TotalCategory>> getTotalByCategory();

}