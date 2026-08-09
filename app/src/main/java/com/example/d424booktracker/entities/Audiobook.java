package com.example.d424booktracker.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "audiobooks")
//        foreignKeys = @ForeignKey(
//        entity = Book.class,
//        parentColumns = "bookID",
//        childColumns = "bookID",
//        onDelete = ForeignKey.CASCADE))
public class Audiobook extends Book {
    private double listeningSpeed;
    public Audiobook(int bookID, String bookName, String endDate, String startDate, String author, double listeningSpeed) {
        super(bookID, bookName, endDate, startDate, author);
        this.listeningSpeed = listeningSpeed;
    }


    public double getListeningSpeed() {
        return listeningSpeed;
    }

    public void setListeningSpeed(double listeningSpeed) {
        this.listeningSpeed = listeningSpeed;
    }



}
