package com.example.stopwatch;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface NoteDaoCatchTime {
    @Insert
    void insert(CatchTimeTable catchTimeTable);

    @Query(value = "DELETE FROM catch_time_table")
    void deleteAllNotes();

    @Query("SELECT * FROM catch_time_table")
    LiveData<List<CatchTimeTable>> getCatchList();


}
