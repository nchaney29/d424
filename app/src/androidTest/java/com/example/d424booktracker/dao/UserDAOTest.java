package com.example.d424booktracker.dao;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.d424booktracker.database.BookDatabaseBuilder;
import com.example.d424booktracker.entities.User;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class UserDAOTest extends TestCase {
    private BookDatabaseBuilder db;
    private UserDAO userDAO;

    @Before
    public void createDB() {
        Context context = ApplicationProvider.getApplicationContext();

        db = Room.inMemoryDatabaseBuilder(context, BookDatabaseBuilder.class)
                .allowMainThreadQueries()
                .build();

        userDAO = db.userDAO();
    }

    @After
    public void closeDB() throws IOException {
        db.close();
    }

@Test
    public void testRegisterUser() throws SQLException {
        User user = new User(1, "sampleuser", "samplepassword");
        userDAO.registerUser(user);

        List<User> users = userDAO.getAllUsers();
        assertEquals(1, users.size());
        assertEquals("sampleuser", users.get(0).getUsername());
    }

    @Test
    public void testRegisterUser_withDuplicateUN() throws SQLException {
        User user = new User(1, "username1", "testpassword");
        User duplicateUN = new User(2, "username1", "testing");

        userDAO.registerUser(user);

        try {
            userDAO.registerUser(duplicateUN);
            Assert.fail("Duplicate user was registered w/o error");
        } catch (SQLiteConstraintException e) {
            Assert.assertNotNull(e.getMessage());
        }
    }

    @Test
    public void testUserLogin() throws SQLException {
        User user = new User(1, "testuser", "testpassword");
        userDAO.registerUser(user);

        assertEquals(1, userDAO.userLogin("testuser"));
    }
}