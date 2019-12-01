package com.example.fuellog;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface LogDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Log log);

    @Query("DELETE FROM log_table")
    void deleteAll();

    @Query("SELECT * from log_table ORDER BY log ASC")
    LiveData<List<Log>> getAllLogs();

    @Query("SELECT * from log_table LIMIT 1")
    Log[] getAnyLog();

    @Delete
    void deleteLog(Log log);

    @Update
    void update(Log... log);
}

