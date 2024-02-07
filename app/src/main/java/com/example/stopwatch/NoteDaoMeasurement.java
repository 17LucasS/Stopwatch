package com.example.stopwatch;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface NoteDaoMeasurement {
    @Insert
    void insert(NoteMeasurement noteMeasurement);

    @Query(value = "DELETE FROM note_table")
    void deleteAllNotes();

    @Query("SELECT * FROM note_table")
    LiveData<List<NoteMeasurement>> getAllNotes();


}
