package com.example.expensetracker;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;
import java.util.Objects;

@Entity(tableName = "expense")
@TypeConverters(DateConverter.class)
public class Expense implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String category;
    private double amount;
    private Date date;
    private String notes;

    public Expense(String category, double amount, Date date, String notes) {
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.notes = notes;
    }

    public Expense(Parcel in) {
        id = in.readLong();
        category = in.readString();
        amount = in.readDouble();
        long dateMillis = in.readLong(); // ← إضافة دي مهمة
        date = new Date(dateMillis);
        notes = in.readString();
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

    // Getters & Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", amount=" + amount +
                ", date=" + date +
                ", notes='" + notes + '\'' +
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
                notes.equals(expense.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, category, amount, date, notes);
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
        parcel.writeLong(date.getTime());
        parcel.writeString(notes);
    }
}
