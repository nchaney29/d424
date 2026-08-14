package com.example.d424booktracker.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.d424booktracker.dao.AudiobookDAO;
import com.example.d424booktracker.dao.BookDAO;
import com.example.d424booktracker.dao.UserDAO;
import com.example.d424booktracker.entities.Audiobook;
import com.example.d424booktracker.entities.Book;
import com.example.d424booktracker.entities.User;

@Database(entities = {Book.class, Audiobook.class, User.class}, version = 8, exportSchema = false)
public abstract class BookDatabaseBuilder extends RoomDatabase {
    public abstract BookDAO bookDAO();
    public abstract AudiobookDAO audiobookDAO();
    public abstract UserDAO userDAO();
    private static volatile BookDatabaseBuilder INSTANCE;

    static BookDatabaseBuilder getDatabase(final Context context){
        if (INSTANCE==null){
            synchronized (BookDatabaseBuilder.class){
                if(INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(), BookDatabaseBuilder.class,"MyBookDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
