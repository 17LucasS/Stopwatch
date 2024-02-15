package com.example.stopwatch;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NoteDaoMainActData {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMainData(MainActDataTable data);

    @Query(value = "DELETE FROM mainActData_table")
    void deleteAllMainData();

    @Query("SELECT * FROM mainActData_table")
    LiveData<List<MainActDataTable>> getLiveDataMainData();

}
