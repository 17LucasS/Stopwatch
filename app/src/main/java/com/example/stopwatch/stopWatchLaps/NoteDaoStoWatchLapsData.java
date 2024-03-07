package com.example.stopwatch.stopWatchLaps;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NoteDaoStoWatchLapsData {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertStopWatchLapsData(StopWatchLapsDataTable table);
    @Query(value = "DELETE FROM stop_watch_laps_data_table")
    void deleteStopWatchLapsData();

    @Query("SELECT * FROM stop_watch_laps_data_table")
    LiveData<List<StopWatchLapsDataTable>> getLiveDataStopWatchLapsData();

}
