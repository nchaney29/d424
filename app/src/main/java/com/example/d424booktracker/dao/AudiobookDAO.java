package com.example.d424booktracker.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.d424booktracker.entities.Audiobook;

import java.util.List;

@Dao
public interface AudiobookDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Audiobook audiobook);

    @Update
    void update(Audiobook audiobook);

    @Delete
    void delete(Audiobook audiobook);

    @Query("SELECT * FROM audiobooks ORDER BY bookID ASC")
    List<Audiobook> getAllAudiobooks();

    @Query("SELECT * FROM audiobooks WHERE userID = :currentUserId")
    List<Audiobook> getAudiobooksForUser(int currentUserId);
}
