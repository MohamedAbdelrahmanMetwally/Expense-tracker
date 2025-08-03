package com.example.expensetracker;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Expense.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class ExpensesDatabase extends RoomDatabase {
     public abstract ExpenseDao expenseDao();
     private static volatile ExpensesDatabase INSTANCE;
     public synchronized static ExpensesDatabase getDatabase( Context context) {
         if (INSTANCE == null) {
             synchronized (ExpensesDatabase.class) {
                 if (INSTANCE == null) {
                     INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                     ExpensesDatabase.class, "expenses_database")
                             .build();
                 }

             }
         }
         return INSTANCE;
     }
}
