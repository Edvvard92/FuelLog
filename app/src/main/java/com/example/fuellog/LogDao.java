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
    void insert(LogCar logCar);

    @Query("DELETE FROM  log_table")
    void deleteAll();

    @Query("SELECT * from log_table ORDER BY log ASC")
    LiveData<List<LogCar>> getAllLogs();

    @Query("SELECT * from log_table LIMIT 1")
    LogCar[] getAnyLog();

    @Delete
    void deleteLog(LogCar logCar);

    @Update
    void update(LogCar... logCar);
}

