package com.example.stopwatch.stopWatch;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NoteDaoStopWatchData {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     void insertStopWatchData(StopWatchDataTable note);

    @Query(value = "DELETE FROM stop_watch_data_table")
    void deleteStopWatchData();

    @Query("SELECT * FROM stop_watch_data_table")
    LiveData<List<StopWatchDataTable>> getAllNoteStopWatchData();
}
