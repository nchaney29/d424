package com.example.d424booktracker.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.d424booktracker.entities.Audiobook;
import com.example.d424booktracker.entities.Book;
import com.example.d424booktracker.entities.CombineSearch;
import com.example.d424booktracker.entities.ReportItem;
import com.example.d424booktracker.entities.User;

import java.sql.SQLException;
import java.util.List;

@Dao
public interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    long registerUser(User user) throws SQLException;


    //login
    @Query("SELECT userId FROM users WHERE username = :username LIMIT 1")
    int userLogin(String username);

    @Query("SELECT * FROM users")
    List<User> getAllUsers();

    //check if username is already registered
    @Query("SELECT * FROM users WHERE username = :username LIMIT 1 ")
    User findUsername(String username);

    @Query("SELECT password FROM users WHERE username = :username LIMIT 1")
    String findPassword(String username);

    //search implementation
    @Query("SELECT bookName AS title, author AS author, bookID AS bookID, 'book' AS bookType FROM books WHERE title LIKE :searchQuery AND userId = :currentUserId " + "UNION " + "SELECT bookName AS title, author AS author, bookID AS bookID, 'audiobook' as bookType FROM audiobooks WHERE title LIKE :searchQuery AND userId = :currentUserId")
    List<CombineSearch> searchBooks(String searchQuery, int currentUserId);

    @Query("SELECT * FROM books WHERE bookName = :title AND bookID = :bookID AND userId = :currentUserId")
    Book bookLookup(String title, int bookID, int currentUserId);

    @Query("SELECT * FROM audiobooks WHERE bookName = :title AND bookID = :bookID AND userId = :currentUserId")
    Audiobook audiobookLookup(String title, int bookID, int currentUserId);

    //for report generation
    @Query("SELECT bookName AS title, author AS author, startDate AS startDate, endDate AS endDate FROM books WHERE userId = :currentUserId " + "UNION " + "SELECT bookName AS title, author AS author, startDate AS startDate, endDate AS endDate FROM audiobooks WHERE userId = :currentUserId")
    List<ReportItem> reportGenerate(int currentUserId);
}
