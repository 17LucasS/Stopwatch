package com.example.stopwatch.stopWatchLaps;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NoteDaoStopWatchLapsCatch {
    @Insert
    void insertStopWatchLapsCatch(StopWatchLapsCatchTable stopWatchLapsCatchTable);

    @Query(value = "DELETE FROM stop_watch_laps_catch_table")
    void deleteStopWatchLapsCatch();

    @Query("SELECT * FROM stop_watch_laps_catch_table")
    LiveData<List<StopWatchLapsCatchTable>> getLiveDataStopWatchLapsCatch();
}
