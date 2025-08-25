package com.example.expensetracker.core.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Expense.class}, version = 3, exportSchema = false)
public abstract class ExpensesDatabase extends RoomDatabase {

    public abstract ExpenseDao expenseDao();

    private static volatile ExpensesDatabase INSTANCE;

    public static ExpensesDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ExpensesDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    ExpensesDatabase.class,
                                    "expenses_database"
                            )
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
