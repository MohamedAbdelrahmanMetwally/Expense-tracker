package com.example.expensetracker.Main.util;
import androidx.lifecycle.LiveData;
import com.example.expensetracker.core.database.Expense;
import com.example.expensetracker.core.database.ExpenseDao;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
public class Repository {
    private ExpenseDao expenseDao;
    private Executor executor;
    private LiveData<List<Expense>> expenses;
   public Repository(ExpenseDao expenseDao) {
        this.expenseDao = expenseDao;
        this.executor = Executors.newSingleThreadExecutor();
       expenses=expenseDao.getAllExpenses();
    }
    public LiveData<List<Expense>> getExpenses() {
        return expenses;
    }
    public void addExpense(Expense expense) {
        executor.execute(() -> expenseDao.insertExpnse(expense));
    }
    public void deleteExpense(Expense expense) {
        executor.execute(() -> expenseDao.deleteExpense(expense));
    }
    public LiveData<List<TotalCategory>> getTotalByCategory() {
        return expenseDao.getTotalByCategory();
    }
}