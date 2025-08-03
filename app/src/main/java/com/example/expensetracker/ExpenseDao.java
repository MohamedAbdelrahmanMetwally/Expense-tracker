package com.example.expensetracker;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Date;
import java.util.List;

@Dao
public interface ExpenseDao {
    @Insert
    void insert(Expense expense);
    @Query("SELECT * FROM expense")
    List<Expense> getAllExpenses();
    @Query("SELECT SUM(amount) FROM expense")
    double getTotalExpenses();
    @Query("SELECT SUM(amount) FROM expense WHERE category = :category")
    double getTotalExpensesByCategory(String category);
    @Delete
    void delete(Expense expense);
    @Query("DELETE FROM expense where id = :id")
    void deleteByID(int id);
    @Query("DELETE FROM expense where category = :category")
    void deleteByCategory(String category);
    @Query("DELETE FROM expense where amount = :amount")
    void deleteByAmount(double amount);
    @Query("DELETE FROM expense where date = :date")
    void deleteByDate(Date date);
    @Query("DELETE FROM expense where notes = :notes")
    void deleteByNotes(String notes);
    @Update
    void update(Expense expense);
}
