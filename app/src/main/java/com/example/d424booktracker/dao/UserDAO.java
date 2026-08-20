package com.example.d424booktracker.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.d424booktracker.entities.Audiobook;
import com.example.d424booktracker.entities.Book;
import com.example.d424booktracker.entities.CombineSearch;
import com.example.d424booktracker.entities.User;

import java.sql.SQLException;
import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    long registerUser(User user) throws SQLException;


    //login
    @Query("SELECT * FROM users WHERE username = :username AND password = :password LIMIT 1")
    User userLogin(String username, String password);

    @Query("SELECT * FROM users")
    List<User> getAllUsers();

    //check if username is already registered
    @Query("SELECT * FROM users WHERE username = :username LIMIT 1 ")
    User findUsername(String username);

    @Query("SELECT * FROM users WHERE password = :password LIMIT 1")
    User findPassword(String password);

    //search implementation
    @Query("SELECT bookName AS title, author AS author, bookID AS bookID, 'book' AS bookType FROM books WHERE title LIKE :searchQuery AND userId = :currentUserId " + "UNION " + "SELECT bookName AS title, author AS author, bookID AS bookID, 'audiobook' as bookType FROM audiobooks WHERE title LIKE :searchQuery AND userId = :currentUserId")
    List<CombineSearch> searchBooks(String searchQuery, int currentUserId);

    @Query("SELECT * FROM books WHERE bookName = :title AND bookID = :bookID AND userId = :currentUserId")
    Book bookLookup(String title, int bookID, int currentUserId);

    @Query("SELECT * FROM audiobooks WHERE bookName = :title AND bookID = :bookID AND userId = :currentUserId")
    Audiobook audiobookLookup(String title, int bookID, int currentUserId);
}
