package com.example.expensetracker.Main.view_model;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.expensetracker.Main.util.Repository;
import com.example.expensetracker.Main.util.TotalCategory;
import com.example.expensetracker.core.database.Expense;
import java.util.List;
public class MainViewModel extends ViewModel {
    private Repository repository;
    public MainViewModel(Repository repository){
        this.repository = repository;
    }
    public LiveData<List<Expense>> getExpenses(){
        return repository.getExpenses();
    }
    public void addExpense(Expense expense){
        repository.addExpense(expense);
    }
    public void deleteExpense(Expense expense){
        repository.deleteExpense(expense);
    }
    public LiveData<List<TotalCategory>> getTotalByCategory(){
        return repository.getTotalByCategory();
    }
}