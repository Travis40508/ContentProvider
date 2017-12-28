package com.example.rodneytressler.contentproviderpractice;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

/**
 * Created by rodneytressler on 12/27/17.
 */

@Dao
public interface PersonDao {

    @Insert
    long insert(Person person);

    @Query("SELECT * FROM " + Person.TABLE_NAME)
    Cursor selectAll();

    @Query("SELECT * FROM " + Person.TABLE_NAME + " WHERE " + Person.COLUMN_ID + " = :id")
    Cursor selectById(long id);

    @Query("DELETE FROM " + Person.TABLE_NAME + " WHERE " + Person.COLUMN_ID + " = :id")
    int deleteById(long id);

    @Update
    int update(Person person);
}
