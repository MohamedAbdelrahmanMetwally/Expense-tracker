package com.example.expensetracker.core.database;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.Objects;
@Entity(tableName = "expense")
public class Expense implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String category;
    private double amount;
    private String date;
    private String title;
    public Expense(String category, double amount, String date, String title) {
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.title = title;
    }
    public Expense(Parcel in) {
        id = in.readLong();
        category = in.readString();
        amount = in.readDouble();
        date= in.readString();
        title = in.readString();
    }
    public static final Creator<Expense> CREATOR = new Creator<Expense>() {
        @Override
        public Expense createFromParcel(Parcel in) {
            return new Expense(in);
        }
        @Override
        public Expense[] newArray(int size) {
            return new Expense[size];
        }
    };
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", amount=" + amount +
                ", date=" + date +
                ", title='" + title + '\'' +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Expense)) return false;
        Expense expense = (Expense) o;
        return id == expense.id &&
                Double.compare(expense.amount, amount) == 0 &&
                category.equals(expense.category) &&
                date.equals(expense.date) &&
                title.equals(expense.title);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, category, amount, date, title);
    }
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(category);
        parcel.writeDouble(amount);
        parcel.writeString(date);
        parcel.writeString(title);
    }
}