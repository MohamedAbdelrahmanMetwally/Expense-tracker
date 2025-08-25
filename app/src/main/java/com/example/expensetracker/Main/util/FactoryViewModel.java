package com.example.expensetracker.Main.util;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.example.expensetracker.Main.view_model.MainViewModel;
public class FactoryViewModel implements ViewModelProvider.Factory {
    private Repository repository;
    public FactoryViewModel(Repository repository){
        this.repository = repository;
    }
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)){
            return (T) new MainViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}