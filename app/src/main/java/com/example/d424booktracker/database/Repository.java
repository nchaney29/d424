package com.example.d424booktracker.database;

import android.app.Application;

import com.example.d424booktracker.dao.BookDAO;
import com.example.d424booktracker.entities.Book;


import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private BookDAO mBookDAO;

    private List<Book> mAllBooks;
//    private List<Excursion> mAllExcursions;

    private static int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application){
        BookDatabaseBuilder db= BookDatabaseBuilder.getDatabase(application);
//        mExcursionDAO=db.excursionDAO();
        mBookDAO =db.bookDAO();
    }

    public List<Book> getmAllBooks(){
        databaseExecutor.execute(()->{
            mAllBooks = mBookDAO.getAllBooks();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e){
            throw new RuntimeException(e);
        }

        return mAllBooks;
    }

//    public List<Excursion>getmAllExcursions(){
//        databaseExecutor.execute(()->{
//            mAllExcursions=mExcursionDAO.getAllExcursions();
//        });
//
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e){
//            throw new RuntimeException(e);
//        }
//
//        return mAllExcursions;
//    }
//
//    public List<Excursion>getAssociatedExcursions(int vacationID){
//        databaseExecutor.execute(()->{
//            mAllExcursions=mExcursionDAO.getAssociatedExcursions(vacationID);
//        });
//
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return mAllExcursions;
//    }

    public void insert(Book book){
        databaseExecutor.execute(()-> {
            mBookDAO.insert(book);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void delete(Book book){
        databaseExecutor.execute(()-> {
            mBookDAO.delete(book);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void update(Book book){
        databaseExecutor.execute(()-> {
            mBookDAO.update(book);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

//    public void insert(Excursion excursion){
//        databaseExecutor.execute(()-> {
//            mExcursionDAO.insert(excursion);
//        });
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public void delete(Excursion excursion){
//        databaseExecutor.execute(()-> {
//            mExcursionDAO.delete(excursion);
//        });
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public void update(Excursion excursion){
//        databaseExecutor.execute(()-> {
//            mExcursionDAO.update(excursion);
//        });
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//    }


}
