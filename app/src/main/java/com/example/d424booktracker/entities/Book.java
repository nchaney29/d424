package com.example.d424booktracker.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "books")
public class Book {
    @PrimaryKey(autoGenerate = true)
    private int bookID;
    private String bookName;
    private String author;
    private String startDate;
    private String endDate;

    public Book(int bookID, String bookName, String endDate, String startDate, String author) {
        this.bookID = bookID;
        this.bookName = bookName;
        this.endDate = endDate;
        this.startDate = startDate;
        this.author = author;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getBookName(){ return bookName; }

    public void setBookName(String bookName) {this.bookName = bookName; }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String toString(){
        return bookName;
    }
}
