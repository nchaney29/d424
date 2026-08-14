package com.example.d424booktracker.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.d424booktracker.entities.Audiobook;
import com.example.d424booktracker.entities.Book;

import java.util.List;
@Dao
public interface BookDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Book book);

    @Update
    void update(Book book);

    @Delete
    void delete(Book book);

    @Query("SELECT * FROM books ORDER BY bookID ASC")
    List<Book> getAllBooks();


    @Query("SELECT * FROM books WHERE userID = :currentUserId")
    List<Book> getBooksForUser(int currentUserId);
}
