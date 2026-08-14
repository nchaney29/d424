package com.example.d424booktracker.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

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
}
