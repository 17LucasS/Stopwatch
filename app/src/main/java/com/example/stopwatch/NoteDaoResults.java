package com.example.stopwatch;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDaoResults {
    @Insert
    void insertResults(NoteResults note);

    @Update
    void updateResults(NoteResults note);

    @Delete
    void deleteResults(NoteResults note);

    @Query(value = "DELETE FROM NOTE_RESULTS_TABLE")
    void deleteAllNoteResults();

    @Query("SELECT * FROM NOTE_RESULTS_TABLE")
    LiveData<List<NoteResults>> getAllNotesResults();



}
