package com.example.d424booktracker.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "audiobooks",
        foreignKeys = @ForeignKey(
        entity = User.class,
        parentColumns = "userId",
        childColumns = "userID",
        onDelete = ForeignKey.CASCADE))
public class Audiobook extends Book {
    private double listeningSpeed;
    public Audiobook(int bookID, int userID, String bookName, String endDate, String startDate, String author, double listeningSpeed) {
        super(bookID, userID, bookName, endDate, startDate, author);
        this.listeningSpeed = listeningSpeed;
    }


    public double getListeningSpeed() {
        return listeningSpeed;
    }

    public void setListeningSpeed(double listeningSpeed) {
        this.listeningSpeed = listeningSpeed;
    }



}
