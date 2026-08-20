package com.example.d424booktracker.database;

import android.app.Application;
import android.app.appsearch.SearchResult;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.example.d424booktracker.dao.AudiobookDAO;
import com.example.d424booktracker.dao.BookDAO;
import com.example.d424booktracker.dao.UserDAO;
import com.example.d424booktracker.entities.Audiobook;
import com.example.d424booktracker.entities.Book;
import com.example.d424booktracker.entities.CombineSearch;
import com.example.d424booktracker.entities.User;


import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private BookDAO mBookDAO;
    private AudiobookDAO mAudiobookDAO;
    private UserDAO mUserDAO;

    private List<Book> mAllBooks;
    private List<Audiobook> mAllAudiobooks;
    private List<User> mAllUsers;

    private List<Book> userBooksList;
    private List<Audiobook> userAudiobooksList;

    private String username;
    private String password;
    private boolean result;
    int userId;
    private List<CombineSearch> results;
    private Book foundBook;
    private Audiobook foundAudiobook;

    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application) {
        BookDatabaseBuilder db = BookDatabaseBuilder.getDatabase(application);
        mAudiobookDAO = db.audiobookDAO();
        mBookDAO = db.bookDAO();
        mUserDAO = db.userDAO();
    }

    public List<Book> getmAllBooks() {
        databaseExecutor.execute(() -> {
            mAllBooks = mBookDAO.getAllBooks();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return mAllBooks;
    }

    public List<Audiobook> getmAllAudiobooks() {
        databaseExecutor.execute(() -> {
            mAllAudiobooks = mAudiobookDAO.getAllAudiobooks();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return mAllAudiobooks;
    }

    public List<User> getmAllUsers() {
        databaseExecutor.execute(() -> {
            mAllUsers = mUserDAO.getAllUsers();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return mAllUsers;
    }


    public List<Book> getmBooksForUser(int currentUserID) {
        databaseExecutor.execute(() -> {
            userBooksList = mBookDAO.getBooksForUser(currentUserID);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return userBooksList;
    }


    public List<Audiobook> getmAudiobooksForUser(int currentUserID) {
        databaseExecutor.execute(() -> {
            userAudiobooksList = mAudiobookDAO.getAudiobooksForUser(currentUserID);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return userAudiobooksList;
    }


    public void insert(Book book) {
        databaseExecutor.execute(() -> {
            mBookDAO.insert(book);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void delete(Book book) {
        databaseExecutor.execute(() -> {
            mBookDAO.delete(book);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void update(Book book) {
        databaseExecutor.execute(() -> {
            mBookDAO.update(book);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void insert(Audiobook audiobook) {
        databaseExecutor.execute(() -> {
            mAudiobookDAO.insert(audiobook);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void abDelete(Audiobook audiobook) {
        databaseExecutor.execute(() -> {
            mAudiobookDAO.delete(audiobook);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void update(Audiobook audiobook) {
        databaseExecutor.execute(() -> {
            mAudiobookDAO.update(audiobook);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void registerUser(User user) {
        databaseExecutor.execute(() -> {
            try {
                mUserDAO.registerUser(user);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public int loginUser(String username, String password) {
        databaseExecutor.execute(() -> {
            userId = (mUserDAO.userLogin(username, password)).getUserId();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return userId;
    }

    public boolean validateName(String username) {
        databaseExecutor.execute(() -> {
            User currentUser = mUserDAO.findUsername(username);
            result = currentUser != null;
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean validatePassword(String password) {
        databaseExecutor.execute(() -> {
            User currentUser = mUserDAO.findPassword(password);
            result = currentUser != null;
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<CombineSearch> searchDatabase(String query, int userID) {
        String searchQuery = "%" + query + "%";

        databaseExecutor.execute(() -> {
            results = mUserDAO.searchBooks(searchQuery, userID);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return results;
    }

    public Book findBook(String title, int bookID, int userID) {
        databaseExecutor.execute(() -> {
            foundBook = mUserDAO.bookLookup(title, bookID, userID);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return foundBook;
    }

    public Audiobook findAudiobook(String title, int bookID, int userID) {
        databaseExecutor.execute(() -> {
            foundAudiobook = mUserDAO.audiobookLookup(title, bookID, userID);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return foundAudiobook;
    }
}
