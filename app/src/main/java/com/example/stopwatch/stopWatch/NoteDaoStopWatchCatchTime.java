package com.example.stopwatch.stopWatch;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NoteDaoStopWatchCatchTime {
    @Insert
    void insertTimeStopWatch(StopWatchCatchTimeTable note);

    @Query(value = "DELETE FROM stop_watch_catch_time_table")
    void deleteAllTimeStopWatch();
    @Query("SELECT * FROM stop_watch_catch_time_table")
    LiveData<List<StopWatchCatchTimeTable>> getAllNoteTimeStopWatch();

}
